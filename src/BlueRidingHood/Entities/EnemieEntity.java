package BlueRidingHood.Entities;

import BlueRidingHood.Animation.Animation;
import BlueRidingHood.Game.Sign;
import BlueRidingHood.Observer.Observer;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import static BlueRidingHood.Dijkstra.Node.getPath;

public abstract class EnemieEntity extends Entity implements Observer {
    public static CopyOnWriteArrayList<EnemieEntity> actualEntities;
    //https://www.geeksforgeeks.org/fail-fast-fail-safe-iterators-java/
    //Fail-safe iterators allow modifications of a collection while iterating over it.
    protected Animation up;
    protected Animation down;
    protected Animation left;
    protected Animation right;
    protected Animation currentAnimation;
    protected LinkedList<Integer> path;
    protected long followStartTime =0;
    protected static long hitPlayerStartTime=0;
    protected static int playerMatrixY, playerMatrixX;
    protected static Player player = Player.getPlayer(); //subject for observer

    protected void followPlayer()
    {
        try {
             if(System.nanoTime()- followStartTime >=4000000) {
                //limit time
                followStartTime = System.nanoTime();
                int next = path.get(0);

                int nextPosY = next / 100;
                int nextPosX = next % 100;

                // System.out.println("xnext: "+nextPosX+", ynext: "+nextPosY+", xcurr: "+matrixX+", ycurr: "+matrixY
                //       +"\nxcoord: "+xCoord+", ycoord: "+yCoord+"\n");
                if (nextPosX == matrixX && nextPosY == matrixY){
                    path.remove(0);
                } else if(!isAnotherEntityHere(nextPosX,nextPosY, this)) {
                    if (nextPosX > matrixX) {
                        stepHorizontal(Sign.plus);
                        currentAnimation=left;
                    } else if (nextPosX < matrixX) {
                        stepHorizontal(Sign.minus);
                        currentAnimation=right;
                    }
                    if (nextPosY > matrixY) {
                        stepVertical(Sign.plus);
                        currentAnimation=up;
                    } else if (nextPosY < matrixY) {
                        stepVertical(Sign.minus);
                        currentAnimation=down;
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    protected void establishNewPath()
    {
        playerMatrixX = player.getMatrixX();
        playerMatrixY = player.getMatrixY();
        this.path = getPath(matrixY * 100 + matrixX, playerMatrixY * 100 + playerMatrixX);
    }

    public static void entitysFollowPlayer()
    {
        if(actualEntities!=null && actualEntities.size()!=0)
            for(EnemieEntity entity: actualEntities)
        {
            entity.followPlayer();
        }
    }

    public static void checkHitPlayer()
    {
        if(System.nanoTime()-hitPlayerStartTime>=1000000000 && isAnotherEntityHere(playerMatrixX,playerMatrixY,null))
        {
            hitPlayerStartTime=System.nanoTime();
            //System.out.println("matx: "+playerMatrixX+", maty: "+playerMatrixY);
            player.isHit();
        }
    }

    public abstract void runAnimation();

    public abstract void draw(Graphics graphics);

    public void update()
    {
        establishNewPath();
    }

    public static boolean isAnotherEntityHere(int matrixX, int matrixY, EnemieEntity callerEntity)
    {
        for(EnemieEntity entity:actualEntities)
        {
            if(matrixX == entity.matrixX && matrixY ==entity.matrixY && entity!=callerEntity)
            {
                return true;
            }
        }
        return false;
    }

    public static void hitEntityAtCoords(int matrixX, int matrixY)
    {
        for(EnemieEntity entity:actualEntities)
        {
            if(matrixX == entity.matrixX && matrixY ==entity.matrixY)
            {
                entity.isHit();
                if(!entity.alive)
                {
                    actualEntities.remove(entity);
                }
            }
        }
    }

    public static void killThemAll()
    {
        if(actualEntities!=null)
            for(EnemieEntity entity:actualEntities)
        {
            entity.alive=false;
        }
        actualEntities=null;
    }
}
