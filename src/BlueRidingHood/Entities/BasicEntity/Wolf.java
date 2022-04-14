package BlueRidingHood.Entities.BasicEntity;

import BlueRidingHood.Entities.Entity;

public class Wolf extends Entity {
    @Override
    public void isHit() {
        hitCounter++;
    }

    @Override
    public boolean alive() {
        return alive;
    }
}
