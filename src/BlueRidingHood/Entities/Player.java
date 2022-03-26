package BlueRidingHood.Entities;

import BlueRidingHood.Graphics.Animation;
import BlueRidingHood.Graphics.Assets;

public class Player implements Entity{ //todo add other atributes methods
    public int xCoord, yCoord, matrixX, matrixY;
    public int speed, stepSize;
    public boolean shieldActive, attackActive;
    public Animation leftStand, rightStand, leftRun, rightRun;
    public Animation leftDrawSword, rightDrawSword,rightRetractSword, leftRetractSword, leftAttackSword, rightAttackSword;
    public Animation leftAttackIce, rightAttacIce;
    public Animation leftShieldStand, rightShieldStand, leftShieldRun, rightShieldRun;
    public Animation leftShieldDrawSword, rightShieldDrawSword,rightShieldRetractSword, leftShieldRetractSword, leftShieldAttackSword, rightShieldAttackSword;
    public Animation leftShieldAttackIce, rightShieldAttacIce;

    public Player(int playerXCoord,int playerYCoord,int playerMatrixX,int playerMatrixY, int speed, int stepSize)
    {
        this.matrixX = playerMatrixX;
        this.matrixY = playerMatrixY;
        this.xCoord = playerXCoord;
        this.yCoord = playerYCoord;
        this.speed = speed;
        this.stepSize =stepSize;
        shieldActive = attackActive = false;

       animationInit();
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
        rightAttacIce = new Animation(speed,Assets.playerRightAttackIce);

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
        leftShieldAttackIce = new Animation(speed, Assets.playerLeftShieldAttackIce);
        rightShieldAttacIce = new Animation(speed,Assets.playerRightShieldAttackIce);
    }
}
