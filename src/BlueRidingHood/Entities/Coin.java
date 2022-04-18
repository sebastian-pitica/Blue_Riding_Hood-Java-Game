package BlueRidingHood.Entities;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;

import java.awt.*;

public class Coin {

    final static private Animation coinAnimation = new Animation(6, Assets.coin);
    static private Coin coin;

    protected Coin() {}

    public static Coin getCoin() {
        if(coin == null)
        {
            coin = new Coin();
        }
        return coin;
    }

    public void drawCoin(Graphics g, int xCoord, int yCoord)
    {
        coinAnimation.drawAnimation(g,xCoord* Tile.TILE_WIDTH + Tile.TILE_WIDTH / 2 - 8
                ,yCoord* Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 2 - 8,Tile.TILE_WIDTH/4,Tile.TILE_HEIGHT/4);
    }

    public void runAnimation()
    {
        coinAnimation.runAnimation();
    }

}
