package BlueRidingHood.State;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Game.Action;
import BlueRidingHood.Game.Direction;

public abstract class State {

    protected Animation leftStand, rightStand, leftRun, rightRun;
    protected Animation leftDrawSword, rightDrawSword,rightRetractSword, leftRetractSword, leftAttack, rightAttack;
    protected Animation defaultAnimation;
    protected static ShieldOFF defaultState=ShieldOFF.getShieldOFF();

    public abstract Animation getDefaultAnimation() ;

    static public State getDefaultState()
    {
        return defaultState;
    }

    public Animation getAnimation(Direction direction, Action action) {
        switch (action)
        {
            case RUN -> {
                return getRunAnimation(direction);
            }
            case STAND -> {
                return getStandAnimation(direction);
            }
            case ATTACK -> {
                return getAttackAnimation(direction);
            }
            case DRAWSWORD -> {
                return getDrawSwordAnimation(direction);
            }
            case RETRACTSWORD -> {
                return getRetractSwordAnimation(direction);
            }
        }
        return defaultAnimation;
    }

    protected Animation getRunAnimation(Direction direction) {
        switch (direction)
        {
            case right ->{
                return rightRun;
            }
            case left -> {
                return leftRun;
            }
        }
        return defaultAnimation;
    }

    protected Animation getStandAnimation(Direction direction) {
        switch (direction)
        {
            case right ->{
                return rightStand;
            }
            case left -> {
                return leftStand;
            }
        }
        return defaultAnimation;
    }

    protected Animation getAttackAnimation(Direction direction) {
        switch (direction)
        {
            case right ->{
                return rightAttack;
            }
            case left -> {
                return leftAttack;
            }
        }
        return defaultAnimation;
    }

    protected Animation getDrawSwordAnimation(Direction direction) {
        switch (direction)
        {
            case right ->{
                return rightDrawSword;
            }
            case left -> {
                return leftDrawSword;
            }
        }
        return defaultAnimation;
    }

    protected Animation getRetractSwordAnimation(Direction direction) {
        switch (direction)
        {
            case right ->{
                return rightRetractSword;
            }
            case left -> {
                return leftRetractSword;
            }
        }
        return defaultAnimation;
    }
    protected abstract void initAnimation();

}
