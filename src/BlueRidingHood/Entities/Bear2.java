package BlueRidingHood.Entities;

public class Bear2 extends Entity{// TODO: 24.03.2022
    public Bear2(int entityXCoord, int entityYCoord, int entityMatrixX, int entityMatrixY, int speed, int stepSize)
    {
        this.closeAttackPower = 1;
        this.alive = true;
        this.attackResistence = 12;
        this.matrixX = entityMatrixX;
        this.matrixY = entityMatrixY;
        this.xCoord = entityXCoord;
        this.yCoord = entityYCoord;
        this.speed = speed;
        this.stepSize =stepSize;
    }

    @Override
    public void isHit() {

    }

    @Override
    public boolean alive() {
        return false;
    }
}
