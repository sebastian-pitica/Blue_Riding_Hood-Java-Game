package BlueRidingHood.Entities.BasicEnemieEntities;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;

import java.awt.*;

public class Bear1 extends EnemieEntity {

    private void InitAnimation() {
        up = new Animation(6, Assets.bear1Up);
        down = new Animation(6, Assets.bear1Down);
        left = new Animation(6, Assets.bear1Left);
        right = new Animation(6, Assets.bear1Right);
    }

    public Bear1( int entityMatrixX, int entityMatrixY)
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
    public void isHit()
    //todo is hit with quantity
    {
        hitCounter++;
        if(hitCounter>attackResistence)
        {
            alive=false;
        }
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
