package BlueRidingHood.Entities;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Graphics.Tile;

import java.awt.*;

public class ZaWalfo extends Entity{

    static private ZaWalfo zaWalfo = null;
    private Animation currentAnimation; //todo
    static public ZaWalfo getZaWalfo()
    {
        if(zaWalfo==null)
        {
            zaWalfo = new ZaWalfo();
        }

        return zaWalfo;
    }

    public int getHitCounter()
            //todo pentru atacul la apropiere
    {
        return hitCounter;
    }

    protected ZaWalfo() {
    } //todo

    @Override
    public void isHit() {
        ++hitCounter;
    }

    @Override
    public boolean alive() {
        return alive;
    }

    public void runAnimation() {
        currentAnimation.runAnimation();

    }

    public void draw(Graphics graphics) {
        currentAnimation.drawAnimation(graphics,matrixX* Tile.TILE_WIDTH+Tile.TILE_HEIGHT/4,matrixY*Tile.TILE_HEIGHT+Tile.TILE_HEIGHT/4,Tile.TILE_WIDTH/2,Tile.TILE_HEIGHT/2);

    }
}
