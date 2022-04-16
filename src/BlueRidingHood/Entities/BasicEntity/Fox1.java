package BlueRidingHood.Entities.BasicEntity;

import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.InputManager.PlayerInputHandler;

import java.awt.*;
import java.util.LinkedList;

import static BlueRidingHood.Entities.Dijkstra.Node.getPath;
import static BlueRidingHood.Map.Map.getCurrentMap;

public class Fox1 extends EnemieEntity {
    //todo vezi animatii proaste
    Animation up;
    Animation down;
    Animation left;
    Animation right;
    private void InitAnimation() {
        up = new Animation(6, Assets.fox2Up);
        down = new Animation(6, Assets.fox2Down);
        left = new Animation(6, Assets.fox2Left);
        right = new Animation(6, Assets.fox2Right);
    }

    public Fox1( int entityMatrixX, int entityMatrixY, int stepSize)
    {
        this.attackPower = 1;
        this.alive = true;
        this.attackResistence = 10;
        this.matrixX = entityMatrixX;
        this.matrixY = entityMatrixY;
        this.xCoord = entityMatrixX * Tile.TILE_WIDTH;
        this.yCoord = entityMatrixY *Tile.TILE_HEIGHT;
        this.stepSize =stepSize;
        InitAnimation();
        this.path = null;

    }

    @Override
    public void isHit() {
        hitCounter++;
    }

    @Override
    public boolean alive() {
        return alive;
    }

    public void followPlayer()
    {
            if(Player.isPositionChanged())
            {
                this.path = getPath(matrixY * 100 + matrixX, Player.getPlayer().matrixY * 100 + Player.getPlayer().matrixX);
            }

           try {
               if (this.path == null)
                   this.path = getPath(matrixY * 100 + matrixX, Player.getPlayer().matrixY * 100 + Player.getPlayer().matrixX);

               int next = path.get(0);

                int nextPosY = next / 100;
                int nextPosX = next % 100;

                // System.out.println("xnext: "+nextPosX+", ynext: "+nextPosY+", xcurr: "+matrixX+", ycurr: "+matrixY
                //       +"\nxcoord: "+xCoord+", ycoord: "+yCoord+"\n");
                if (nextPosX == matrixX && nextPosY == matrixY) {
                    path.remove(0);
                } else {
                    if (nextPosX > matrixX) {
                        stepHorizontal(PlayerInputHandler.Sign.plus);
                    } else if (nextPosX < matrixX) {
                        stepHorizontal(PlayerInputHandler.Sign.minus);
                    }

                    if (nextPosY > matrixY) {
                        stepVertical(PlayerInputHandler.Sign.plus);
                    } else if (nextPosY < matrixY) {
                        stepVertical(PlayerInputHandler.Sign.minus);
                    }
                }
            } catch (Exception e) {

            }
    }
    
    private void updatePositionInMatrix()
    {
        matrixX = (xCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        matrixY = (yCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
    }

    private void stepVertical(PlayerInputHandler.Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePositionInMatrix();
        if(sign== PlayerInputHandler.Sign.plus) { //deplasare in jos
            boolean test =  getCurrentMap().canAdvance(matrixX, matrixY + 1);
            if (test) {
                yCoord += stepSize;
            }
            else
            {   //corectie deplasare in jos
                //cat timp coordonata actuala y e mai mica ca cea ideala
                if(yCoord<matrixY*Tile.TILE_HEIGHT)
                {
                    yCoord += stepSize;
                }
            }

        }
        else { //deplasare in sus
            boolean test =  getCurrentMap().canAdvance(matrixX, matrixY - 1);
            if (test) {
                yCoord -= stepSize;
            }
            else
            {   //corectie deplasare in sus
                //cat timp coordonata actuala y e mai mare decat cea ideala
                if(yCoord>matrixY*Tile.TILE_HEIGHT)
                {
                    yCoord -= stepSize;
                }
            }
        }
        updatePositionInMatrix();
    }

    private void stepHorizontal(PlayerInputHandler.Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePositionInMatrix();
        if(sign== PlayerInputHandler.Sign.plus) //deplasare la dreapta
        {
            boolean test =  getCurrentMap().canAdvance(matrixX+1, matrixY);
            if (test) {
                xCoord += stepSize;}
            else
            {
                //corectie deplasare la dreapta
                //cat timp pozitia reala pe x este mai mica decat cea ideala
                if(xCoord<matrixX*Tile.TILE_HEIGHT)
                {
                    xCoord += stepSize;
                }
            }
        }
        else
        {//deplasare la stanga
            boolean test =  getCurrentMap().canAdvance(matrixX-1, matrixY);
            if (test) {
                xCoord -= stepSize;}
            else
            {
                //corectie deplasare la stanga
                //cat timp pozitia reala pe x este mai marre de cea ideala
                if(xCoord>matrixX*Tile.TILE_HEIGHT)
                {
                    xCoord -= stepSize;
                }
            }
        }
        updatePositionInMatrix();
    }


    @Override
    public void runAnimation() {
        up.runAnimation();
    }

    @Override
    public void draw(Graphics graphics) {
        up.drawAnimation(graphics,matrixX*Tile.TILE_WIDTH+Tile.TILE_HEIGHT/4,matrixY*Tile.TILE_HEIGHT+Tile.TILE_HEIGHT/4,Tile.TILE_WIDTH/2,Tile.TILE_HEIGHT/2);
    }
}
