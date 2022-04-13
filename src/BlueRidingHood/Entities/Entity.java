package BlueRidingHood.Entities;

public abstract class Entity {//todo ce detine fiecare entitate
    protected int closeAttackPower = 0, attackResistence = 0, hitCounter = 0, speed=0, stepSize=0;
    public int matrixX=-1,matrixY=-1,xCoord=0,yCoord=0;  //cordonatele carteziene si matriceale ale entitatii
    protected boolean alive = false;
    //todo vezi ca close atack e la toate mai putin zawalfo 1 momentan
   // Entity createEntity();
    public abstract void isHit();
    abstract public boolean alive();

}


