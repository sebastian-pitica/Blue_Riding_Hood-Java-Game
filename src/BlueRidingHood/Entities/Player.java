package BlueRidingHood.Entities;

import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;

public class Player extends RangeEntity{ //todo add other atributes, methods ex:hit counter, life counter, alte constrangeri de timp
    //todo counter monede, scor
    public int speed, stepSize; //viteza jucatorului, marimea unui pas
    public boolean shieldActive, swordAttackActive, iceAttackActive; //todo add other flags

    public Animation leftStand, rightStand, leftRun, rightRun;
    public Animation leftDrawSword, rightDrawSword,rightRetractSword, leftRetractSword, leftAttackSword, rightAttackSword;
   //todo initialize atack ice
    public Animation leftStartAttackIce,leftStopAttackIce, rightStartAttackIce, rightStopAttackIce,leftAttackIce,rightAttackIce;

    public Animation leftShieldStand, rightShieldStand, leftShieldRun, rightShieldRun;
    public Animation leftShieldDrawSword, rightShieldDrawSword,rightShieldRetractSword, leftShieldRetractSword, leftShieldAttackSword, rightShieldAttackSword;
    public Animation leftShieldStartAttackIce,leftShieldStopAttackIce, rightShieldStartAttackIce, rightShieldStopAttackIce,leftShieldAttackIce,rightShieldAttackIce;

    public Animation iceAttackUp,iceAttackDown,iceAttackLeft,iceAttackRight;

    public Player(int playerXCoord,int playerYCoord,int playerMatrixX,int playerMatrixY, int speed, int stepSize)
    {
        this.closeAttackPower = 1;
        this.rangeAttackPower = 2;
        this.alive = true;
        this.attackResistence = 15;
        this.matrixX = playerMatrixX;
        this.matrixY = playerMatrixY;
        this.xCoord = playerXCoord;
        this.yCoord = playerYCoord;
        this.speed = speed;
        this.stepSize =stepSize;
        shieldActive = swordAttackActive = iceAttackActive = false;
        animationInit();
    }

    @Override
    public void isHit()
    {
        if(++hitCounter > attackResistence)
        {
            alive = false;
        }
    }

    @Override
    public boolean alive()
    {
        return alive;
    }

    public void resetHitCounter()
    {
        hitCounter = 0;
    }

    public void GODmodeON()
    {
        attackResistence=9999999;
        rangeAttackPower=9999999;
        closeAttackPower=9999999;
    }

    public void GODmodeOFF()
    {
        attackResistence = 15;
        rangeAttackPower = 2;
        closeAttackPower = 1;
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
        System.out.print("\nx: "+xCoord+", y: "+yCoord+"\nmatrixXCoord: "+matrixX+", matrixYCoord: "+matrixY+"\n");
        System.out.print("Resistence: "+attackResistence+", HitCounter: "+hitCounter+"\n"+
                "CloseAttackPower: "+closeAttackPower+", RangeAttackPower: "+rangeAttackPower+"\n");
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
        leftAttackSword = new Animation(speed, Assets.playerLeftAttackSword);
        rightAttackSword = new Animation(speed, Assets.playerRightAttackSword);
        leftAttackIce = new Animation(speed, Assets.playerLeftAttackIce);
        rightAttackIce = new Animation(speed,Assets.playerRightAttackIce);

        leftShieldStand = new Animation(speed, Assets.playerLeftShieldStand);
        rightShieldStand = new Animation(speed, Assets.playerRightShieldStand);
        leftShieldRun = new Animation(speed, Assets.playerLeftShieldRun);
        rightShieldRun = new Animation(speed, Assets.playerRightShieldRun);
        leftShieldDrawSword = new Animation(speed, Assets.playerLeftShieldDrawSword);
        rightShieldDrawSword = new Animation(speed,Assets.playerRightShieldDrawSword);
        rightShieldRetractSword = new Animation(speed, Assets.playerRightShieldRetractSword);
        leftShieldRetractSword = new Animation(speed,Assets.playerLeftShieldRetractSword);
        leftShieldAttackSword = new Animation(speed, Assets.playerLeftShieldAttackSword);
        rightShieldAttackSword = new Animation(speed, Assets.playerRightShieldAttackSword);

        leftStartAttackIce = new Animation(speed, Assets.playerLeftStartAttackIce);
        rightStartAttackIce = new Animation(speed, Assets.playerRightStartAttackIce);
        leftShieldStartAttackIce = new Animation(speed, Assets.playerLeftShieldStartAttackIce);
        rightShieldStartAttackIce = new Animation(speed, Assets.playerRightShieldStartAttackIce);

        leftStopAttackIce = new Animation(speed, Assets.playerLeftStopAttackIce);
        rightStopAttackIce = new Animation(speed, Assets.playerRightStopAttackIce);
        leftShieldStopAttackIce = new Animation(speed, Assets.playerLeftShieldStopAttackIce);
        rightShieldStopAttackIce = new Animation(speed, Assets.playerRightShieldStopAttackIce);

        leftShieldAttackIce = new Animation(speed, Assets.playerLeftShieldAttackIce);
        rightShieldAttackIce = new Animation(speed,Assets.playerRightShieldAttackIce);

        iceAttackUp = new Animation(speed,Assets.iceAttackUp);
        iceAttackDown = new Animation(speed,Assets.iceAttackDown);
        iceAttackLeft = new Animation(speed,Assets.iceAttackLeft);
        iceAttackRight = new Animation(speed,Assets.iceAttackRight);
    }
}
