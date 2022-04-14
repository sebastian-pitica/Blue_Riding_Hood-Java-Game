package BlueRidingHood.Entities.BasicEntity;

import BlueRidingHood.Entities.Entity;

public class Fox2 extends Entity {// TODO: 24.03.2022
    public Fox2(int entityXCoord, int entityYCoord, int entityMatrixX, int entityMatrixY, int speed, int stepSize)
    {
        this.closeAttackPower = 1;
        this.alive = true;
        this.attackResistence = 7;
        this.matrixX = entityMatrixX;
        this.matrixY = entityMatrixY;
        this.xCoord = entityXCoord;
        this.yCoord = entityYCoord;
        this.speed = speed;
        this.stepSize =stepSize;
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
