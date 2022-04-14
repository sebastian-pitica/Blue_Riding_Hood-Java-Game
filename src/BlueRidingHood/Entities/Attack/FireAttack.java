package BlueRidingHood.Entities.Attack;

import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;

public class FireAttack extends Attack{

    public static void initAnimation()
    {
        attackUp = new Animation(animationSpeed, Assets.fireAttackUp);
        attackDown = new Animation(animationSpeed,Assets.fireAttackDown);
        attackLeft = new Animation(animationSpeed,Assets.fireAttackLeft);
        attackRight = new Animation(animationSpeed,Assets.fireAttackRight);
    }

    public FireAttack(int xCoord, int yCoord, char axis, char sign)
    {
        super(xCoord,yCoord,axis,sign);
    }
}
