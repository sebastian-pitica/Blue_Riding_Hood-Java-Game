package BlueRidingHood.Entities;

public class Fox1 extends Entity{
    public Fox1(int entityXCoord, int entityYCoord, int entityMatrixX, int entityMatrixY, int speed, int stepSize)
    {
        this.closeAttackPower = 1;
        this.alive = true;
        this.attackResistence = 5;
        this.matrixX = entityMatrixX;
        this.matrixY = entityMatrixY;
        this.xCoord = entityXCoord;
        this.yCoord = entityYCoord;
        this.speed = speed;
        this.stepSize = stepSize;
    }

    @Override
    public void isHit() {
        hitCounter++;
    }

    @Override
    public boolean alive() {
        return alive;
    }
}
