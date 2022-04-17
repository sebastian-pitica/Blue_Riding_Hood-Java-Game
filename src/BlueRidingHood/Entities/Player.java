package BlueRidingHood.Entities;

import BlueRidingHood.Game.Sign;
import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;

import static BlueRidingHood.Map.Map.getCurrentMap;

public class Player extends Entity{ //todo add other atributes, methods ex:hit counter, life counter, alte constrangeri de timp
    //todo counter monede, scor
    //todo check singleton
    //todo score rulles

    private static Player player = null;
    private static boolean positionChanged;
    private final int speed = 6;
    public int stepSize = 2;
    public int score = 0; //viteza jucatorului, marimea unui pas
    public boolean shieldActive, attackActive; //todo add other flags

    public Animation leftStand, rightStand, leftRun, rightRun;
    public Animation leftDrawSword, rightDrawSword,rightRetractSword, leftRetractSword, leftAttack, rightAttack;

    public Animation leftShieldStand, rightShieldStand, leftShieldRun, rightShieldRun;
    public Animation leftShieldDrawSword, rightShieldDrawSword,rightShieldRetractSword, leftShieldRetractSword, leftShieldAttack, rightShieldAttack;

    public void addPointsToScore(int amount)
    {
        score+=amount;
    }

    protected Player()
    {
        this.attackPower = 1;
        this.alive = true;
        this.attackResistence = 15;
        this.matrixX = 0;
        this.matrixY = Map.getCurrentMap().startY();
        this.xCoord = 0;
        this.yCoord = Map.getCurrentMap().startY()* Tile.TILE_HEIGHT;
        shieldActive = attackActive =  false;
        animationInit();
    }

    public static Player getPlayer()
    {
        if(player == null)
        {
            player = new Player();
        }

        return player;
    }

    public static boolean isPositionChanged()
    {
        return positionChanged;
    }

    public static void setPositionChanged() {
        positionChanged = true;
    }

    public static void unsetPositionChanged()
    {
        positionChanged = false;
    }

    @Override
    public void isHit()
    {
        if(++hitCounter > attackResistence)
        {
            isKilled();
        }
    }

    public void isKilled()
    {
        alive = false;
    }

    public void restarted()
    {
        alive = true;
        score = 0;
        shieldActive = attackActive =  false;
    }

    @Override
    public boolean alive() {
        return alive;
    }

    public void resetHitCounter()
    {
        hitCounter = 0;
    }

    public void GODmodeON()
    {
        attackResistence=9999999;
        attackPower =9999999;
    }

    public void GODmodeOFF()
    {
        attackResistence = 15;
        attackPower = 1;
    }

    public void fasterON()
    {
        stepSize = 8;
    }

    public void fasterOFF()
    {
        stepSize = 2;
    }

    public void displayPlayerDetails()
    //afiseaza pozitia jcuatorului in coordonate x, y si in coordonate matriceale
    {
       System.out.print("\n\nx: "+xCoord+", y: "+yCoord+"\nmatrixXCoord: "+matrixX+", matrixYCoord: "+matrixY+"\n");
        System.out.print("Resistence: "+attackResistence+", HitCounter: "+hitCounter+"\n"+
                "CloseAttackPower: "+ attackPower +", Speed: "+stepSize+"\n"
            +"Score: "+score+"\n");

    }

    private void animationInit()
    {
        leftStand = new Animation(speed, Assets.playerLeftStand);
        rightStand = new Animation(speed, Assets.playerRightStand);
        leftRun = new Animation(speed, Assets.playerLeftRun);
        rightRun = new Animation(speed, Assets.playerRightRun);
        leftDrawSword = new Animation(speed, Assets.playerLeftDrawSword);
        rightDrawSword = new Animation(speed,Assets.playerRightDrawSword);
        rightRetractSword = new Animation(speed, Assets.playerRightRetractSword);
        leftRetractSword = new Animation(speed,Assets.playerLeftRetractSword);
        leftAttack = new Animation(speed, Assets.playerLeftAttack);
        rightAttack = new Animation(speed, Assets.playerRightAttack);

        leftShieldStand = new Animation(speed, Assets.playerLeftShieldStand);
        rightShieldStand = new Animation(speed, Assets.playerRightShieldStand);
        leftShieldRun = new Animation(speed, Assets.playerLeftShieldRun);
        rightShieldRun = new Animation(speed, Assets.playerRightShieldRun);
        leftShieldDrawSword = new Animation(speed, Assets.playerLeftShieldDrawSword);
        rightShieldDrawSword = new Animation(speed,Assets.playerRightShieldDrawSword);
        rightShieldRetractSword = new Animation(speed, Assets.playerRightShieldRetractSword);
        leftShieldRetractSword = new Animation(speed,Assets.playerLeftShieldRetractSword);
        leftShieldAttack = new Animation(speed, Assets.playerLeftShieldAttack);
        rightShieldAttack = new Animation(speed, Assets.playerRightShieldAttack);

    }

    @Override
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

    @Override
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

    @Override
    public void updatePositionInMatrix()
    {
        matrixX = (xCoord+ Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        matrixY = (yCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        if( getCurrentMap().canKill(player.matrixX, player.matrixY))
        //todo move this from here! make another function for it!
        //todo kill the player
        //daca jucatorul ajunge pe un camp insta kill este teleportat pe pozitia initiala
        {

            player.isKilled();
            player.restarted();
            resetPlayerPosition();

        }
        //todo other interactions
    }

    private void resetPlayerPosition()
    {
        player.yCoord= getCurrentMap().startY()*Tile.TILE_HEIGHT;
        player.xCoord=0;
        player.updatePositionInMatrix();
    }

}
