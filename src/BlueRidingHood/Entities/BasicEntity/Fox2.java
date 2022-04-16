package BlueRidingHood.Entities.BasicEntity;

import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;

import java.awt.*;

public class Fox2 extends EnemieEntity {

     private void InitAnimation() {
        up = new Animation(6, Assets.fox2Up);
        down = new Animation(6, Assets.fox2Down);
        left = new Animation(6, Assets.fox2Left);
        right = new Animation(6, Assets.fox2Right);
    }

    public Fox2( int entityMatrixX, int entityMatrixY)
    {
        this.attackPower = 1;
        this.alive = true;
        this.attackResistence = 10;
        this.matrixX = entityMatrixX;
        this.matrixY = entityMatrixY;
        this.xCoord = entityMatrixX * Tile.TILE_WIDTH;
        this.yCoord = entityMatrixY *Tile.TILE_HEIGHT;
        InitAnimation();
        this.path = null;
        currentAnimation = right;
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
        currentAnimation.runAnimation();
    }

    @Override
    public void draw(Graphics graphics) {
        currentAnimation.drawAnimation(graphics,matrixX*Tile.TILE_WIDTH+Tile.TILE_HEIGHT/4,matrixY*Tile.TILE_HEIGHT+Tile.TILE_HEIGHT/4,Tile.TILE_WIDTH/2,Tile.TILE_HEIGHT/2);
    }
}
