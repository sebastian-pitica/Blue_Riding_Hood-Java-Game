package BlueRidingHood.Entities;

import BlueRidingHood.Game.Sign;
import BlueRidingHood.Graphics.Tile;

import static BlueRidingHood.Map.Map.getCurrentMap;

public abstract class Entity {//todo ce detine fiecare entitate
    protected int attackPower=1, attackResistence, hitCounter=0, stepSize=1;
    protected int matrixX;
    protected int matrixY;
    protected int xCoord;
    protected int yCoord;  //cordonatele carteziene si matriceale ale entitatii
    protected boolean alive=false;


    //todo vezi ca close atack e la toate mai putin zawalfo 1 momentan
   // Entity createEntity();

    public void stepVertical(Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePositionInMatrix();
        if(sign== Sign.plus) { //deplasare in jos
            boolean test =  getCurrentMap().canAdvance(matrixX, matrixY + 1);
            if (test) {
                yCoord += stepSize;
            }
            else
            {   //corectie deplasare in jos
                //cat timp coordonata actuala y e mai mica ca cea ideala
                if(yCoord<matrixY* Tile.TILE_HEIGHT)
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

    public void stepHorizontal(Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePositionInMatrix();
        if(sign== Sign.plus) //deplasare la dreapta
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

    public void updatePositionInMatrix()
    {
        matrixX = (xCoord+ Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        matrixY = (yCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
    }

    public abstract void isHit();
    public abstract boolean alive();

    public int getyCoord() {
        return yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getMatrixX() {
        return matrixX;
    }

    public int getMatrixY() {
        return matrixY;
    }

    public void setMatrixX(int matrixX) {
        this.matrixX = matrixX;
    }

    public void setMatrixY(int matrixY) {
        this.matrixY = matrixY;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}


