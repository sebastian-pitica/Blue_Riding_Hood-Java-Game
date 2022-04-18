package BlueRidingHood.State;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Graphics.Assets;

public class ShieldON extends State{

    private static ShieldON shieldON=null;

    public static ShieldON getShieldON()
    {
        if(shieldON == null)
        {
            shieldON = new ShieldON();
        }

        return shieldON;
    }

    protected ShieldON(){
        initAnimation();
    }

    @Override
    public Animation getDefaultAnimation() {
        return defaultAnimation;
    }

    @Override
    protected void initAnimation() {
        int speed = Player.getPlayer().getSpeed();
        leftStand = new Animation(speed, Assets.playerLeftShieldStand);
        rightStand = new Animation(speed, Assets.playerRightShieldStand);
        leftRun = new Animation(speed, Assets.playerLeftShieldRun);
        rightRun = new Animation(speed, Assets.playerRightShieldRun);
        leftDrawSword = new Animation(speed, Assets.playerLeftShieldDrawSword);
        rightDrawSword = new Animation(speed,Assets.playerRightShieldDrawSword);
        rightRetractSword = new Animation(speed, Assets.playerRightShieldRetractSword);
        leftRetractSword = new Animation(speed,Assets.playerLeftShieldRetractSword);
        leftAttack = new Animation(speed, Assets.playerLeftShieldAttack);
        rightAttack = new Animation(speed, Assets.playerRightShieldAttack);
        defaultAnimation = rightStand;
    }

}
