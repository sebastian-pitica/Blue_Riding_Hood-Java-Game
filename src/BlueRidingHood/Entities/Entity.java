package BlueRidingHood.Entities;

import java.awt.*;
import java.util.Vector;

public abstract class Entity {//todo ce detine fiecare entitate
    public static Vector<Entity> actualEntities;

    protected int attackPower , attackResistence , hitCounter , speed, stepSize;
    public int matrixX,matrixY,xCoord,yCoord;  //cordonatele carteziene si matriceale ale entitatii
    protected boolean alive;

    //todo vezi ca close atack e la toate mai putin zawalfo 1 momentan
   // Entity createEntity();
    public abstract void isHit();
    abstract public boolean alive();
    public abstract void runAnimation();
    public abstract void draw(Graphics graphics);

}


