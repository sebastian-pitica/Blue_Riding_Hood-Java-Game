package BlueRidingHood.Entities.BasicEntity;

import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Entities.Entity;
import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;

import java.awt.*;

public class Wolf extends EnemieEntity {

    Animation up;
    Animation down;
    Animation left;
    Animation right;

    private void InitAnimation() {
        up = new Animation(6, Assets.fox2Up);
        down = new Animation(6, Assets.fox2Down);
        left = new Animation(6, Assets.fox2Left);
        right = new Animation(6, Assets.fox2Right);
    }
    public Wolf( int entityMatrixX, int entityMatrixY, int stepSize)
    {
        this.attackPower = 1;
        this.alive = true;
        this.attackResistence = 10;
        this.matrixX = entityMatrixX;
        this.matrixY = entityMatrixY;
        this.xCoord = entityMatrixX * Tile.TILE_WIDTH;
        this.yCoord = entityMatrixY *Tile.TILE_HEIGHT;
        this.stepSize =stepSize;
        InitAnimation();
    }

    @Override
    public void isHit() {
        hitCounter++;
    }

    @Override
    public boolean alive() {
        return alive;
    }

    @Override
    public void runAnimation() {
        left.runAnimation();
    }

    @Override
    public void draw(Graphics graphics) {
        left.drawAnimation(graphics,matrixX*Tile.TILE_WIDTH+Tile.TILE_HEIGHT/4,matrixY*Tile.TILE_HEIGHT+Tile.TILE_HEIGHT/4,Tile.TILE_WIDTH/2,Tile.TILE_HEIGHT/2);
    }
}
