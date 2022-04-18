package BlueRidingHood.State;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Graphics.Assets;

public class ShieldOFF extends State {
    private static ShieldOFF shieldOFF=null;

    public static ShieldOFF getShieldOFF()
    {
        if(shieldOFF == null)
        {
            shieldOFF = new ShieldOFF();
        }

        return shieldOFF;
    }

    @Override
    public Animation getDefaultAnimation() {
        return defaultAnimation;
    }

    protected ShieldOFF(){
        initAnimation();
    }
    @Override
    protected void initAnimation() {
        int speed = Player.getPlayer().getSpeed();
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
        defaultAnimation = rightStand;
    }

}
