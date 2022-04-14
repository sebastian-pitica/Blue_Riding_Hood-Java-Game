package BlueRidingHood.Entities.Attack;

import BlueRidingHood.Graphics.Animation.Animation;

import java.util.Vector;

public abstract class Attack {
    protected int xCoord,yCoord;
    protected static Animation attackUp,attackDown,attackLeft,attackRight;
    static protected final int animationSpeed=6;
    protected char axis, sign;

    protected Attack(int xCoord, int yCoord, char axis, char sign)
    {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.axis = axis;
        this.sign = sign;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}
