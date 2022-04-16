package BlueRidingHood.Entities;


import BlueRidingHood.Game.Sign;
import BlueRidingHood.Graphics.Animation.Animation;

import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

import static BlueRidingHood.Entities.Dijkstra.Node.getPath;

public abstract class EnemieEntity extends Entity {
    public static Vector<EnemieEntity> actualEntities;
    protected Animation up;
    protected Animation down;
    protected Animation left;
    protected Animation right;
    protected Animation currentAnimation;
    protected LinkedList<Integer> path;
    protected long startTime;

    protected void followPlayer()
    {
        if(Player.isPositionChanged())
        {
            this.path = getPath(matrixY * 100 + matrixX, Player.getPlayer().matrixY * 100 + Player.getPlayer().matrixX);
        }

        try {
            if (this.path == null) {
                this.path = getPath(matrixY * 100 + matrixX, Player.getPlayer().matrixY * 100 + Player.getPlayer().matrixX);
                //startTime = System.nanoTime();
            }
           // if(System.nanoTime()-startTime>=5000000) {
            //limit time
                startTime = System.nanoTime();
                int next = path.get(0);

                int nextPosY = next / 100;
                int nextPosX = next % 100;

                // System.out.println("xnext: "+nextPosX+", ynext: "+nextPosY+", xcurr: "+matrixX+", ycurr: "+matrixY
                //       +"\nxcoord: "+xCoord+", ycoord: "+yCoord+"\n");
                if (nextPosX == matrixX && nextPosY == matrixY) {
                    path.remove(0);
                } else {
                    if (nextPosX > matrixX) {
                        stepHorizontal(Sign.plus);
                    } else if (nextPosX < matrixX) {
                        stepHorizontal(Sign.minus);
                    }

                    if (nextPosY > matrixY) {
                        stepVertical(Sign.plus);
                    } else if (nextPosY < matrixY) {
                        stepVertical(Sign.minus);
                    }
                }
            //}
        } catch (Exception e) {

        }
    }

    public static void entitysFollowPlayer()
    {

        for(EnemieEntity entity: actualEntities)
        {
            entity.followPlayer();
        }
    }

    public abstract void runAnimation();

    public abstract void draw(Graphics graphics);
}
