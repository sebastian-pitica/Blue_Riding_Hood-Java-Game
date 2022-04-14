package BlueRidingHood.Entities.Attack;

import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;

public class IceAttack extends Attack {

    public static void initAnimation() {
        attackUp = new Animation(animationSpeed, Assets.iceAttackUp);
        attackDown = new Animation(animationSpeed,Assets.iceAttackDown);
        attackLeft = new Animation(animationSpeed,Assets.iceAttackLeft);
        attackRight = new Animation(animationSpeed,Assets.iceAttackRight);
    }

    public IceAttack(int xCoord, int yCoord, char axis, char sign)
    {
        super(xCoord,yCoord,axis,sign);
    }



}
