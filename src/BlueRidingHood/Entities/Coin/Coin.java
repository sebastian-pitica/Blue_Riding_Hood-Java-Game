package BlueRidingHood.Entities.Coin;

import BlueRidingHood.Graphics.Animation.Animation;
import BlueRidingHood.Graphics.Assets;
import BlueRidingHood.Graphics.Tile;

import java.awt.*;

public class Coin {

    private final int xCoord;
    private final int yCoord;
    final private static Animation coinAnimation = new Animation(150, Assets.coin);

    public Coin(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    public void drawCoin(Graphics g)
    {
        coinAnimation.drawAnimation(g,xCoord* Tile.TILE_WIDTH + Tile.TILE_WIDTH / 2 - 8
                ,yCoord* Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 2 - 8,Tile.TILE_WIDTH/4,Tile.TILE_HEIGHT/4);
    }

    public void runAnimation()
    {
        coinAnimation.runAnimation();
    }

}
