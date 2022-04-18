package BlueRidingHood.Entities;

import BlueRidingHood.Game.Sign;
import BlueRidingHood.Game.StateTypes;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;
import BlueRidingHood.Observer.Observer;
import BlueRidingHood.Observer.Subject;
import BlueRidingHood.State.State;

import java.util.LinkedList;

import static BlueRidingHood.Map.Map.getCurrentMap;
import static BlueRidingHood.State.ShieldOFF.getShieldOFF;
import static BlueRidingHood.State.ShieldON.getShieldON;

public class Player extends Entity implements Subject { //todo add other atributes, methods ex:hit counter, life counter, alte constrangeri de timp
    //todo counter monede, scor
    //todo check singleton
    //todo score rulles
    //todo score pentru playerhit -, enemie killed+, coin +
    private static Player player = null;
    private static boolean positionChanged;
    private final int speed = 6;
    public int stepSize = 2;
    public int score = 0; //viteza jucatorului, marimea unui pas
    public boolean shieldActive, attackActive; //todo add other flags
    protected int oldMatrixX, oldMatrixY;
    protected LinkedList<Observer> observers;
    protected State currentState;



    public void addPointsToScore(int amount)
    {
        score+=amount;
    }

    protected Player()
    {
        this.attackPower = 1;
        this.alive = true;
        this.attackResistence = 15;
        this.matrixX = oldMatrixX = 0;
        this.matrixY = oldMatrixY = Map.getCurrentMap().startY();
        this.xCoord = 0;
        this.yCoord = Map.getCurrentMap().startY()* Tile.TILE_HEIGHT;
        shieldActive = attackActive =  false;
        observers = new LinkedList<>();
        animationInit();
    }

    public static Player getPlayer()
    {
        if(player == null)
        {
            player = new Player();
        }

        return player;
    }

    public static boolean isPositionChanged()
    {
        return positionChanged;
    }

    public static void setPositionChanged() {
        positionChanged = true;
    }

    public static void unsetPositionChanged()
    {
        positionChanged = false;
    }

    @Override
    public void isHit()
    {
        if(++hitCounter > attackResistence)
        {
            isKilled();
        }
    }

    public void isKilled()//todo other actions
    {
        alive = false;
    }

    public void restarted()
    {
        alive = true;
        score = 0;
        shieldActive = attackActive =  false;
    }

    @Override
    public boolean alive() {
        return alive;
    }

    public void resetHitCounter()
    {
        hitCounter = 0;
    }

    public void GODmodeON()
    {
        attackResistence=9999999;
        attackPower =9999999;
    }

    public void GODmodeOFF()
    {
        attackResistence = 15;
        attackPower = 1;
    }

    public void fasterON()
    {
        stepSize = 8;
    }

    public void fasterOFF()
    {
        stepSize = 2;
    }

    public void displayPlayerDetails()
    //afiseaza pozitia jcuatorului in coordonate x, y si in coordonate matriceale
    {
      System.out.print("\n\nx: "+xCoord+", y: "+yCoord+"\nmatrixXCoord: "+matrixX+", matrixYCoord: "+matrixY+"\n");
       System.out.print("Resistence: "+attackResistence+", HitCounter: "+hitCounter+"\n"+
               "CloseAttackPower: "+ attackPower +", Speed: "+stepSize+"\n"
               +"Score: "+score+"\n");

    }

    public int getSpeed() {
        return speed;
    }

    private void animationInit()
    {


    }

    @Override
    public void stepVertical(Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePositionInMatrix();
        if(sign== Sign.plus) { //deplasare in jos
            boolean test =  getCurrentMap().canAdvance(matrixX, matrixY + 1);
            if (test) {
                yCoord += stepSize;
            }
            else
            {   //corectie deplasare in jos
                //cat timp coordonata actuala y e mai mica ca cea ideala
                if(yCoord<matrixY* Tile.TILE_HEIGHT)
                {
                    yCoord += stepSize;
                }
            }

        }
        else { //deplasare in sus
            boolean test =  getCurrentMap().canAdvance(matrixX, matrixY - 1);
            if (test) {
                yCoord -= stepSize;
            }
            else
            {   //corectie deplasare in sus
                //cat timp coordonata actuala y e mai mare decat cea ideala
                if(yCoord>matrixY*Tile.TILE_HEIGHT)
                {
                    yCoord -= stepSize;
                }
            }
        }
        updatePositionInMatrix();
    }

    @Override
    public void stepHorizontal(Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePositionInMatrix();
        if(sign== Sign.plus) //deplasare la dreapta
        {
            boolean test =  getCurrentMap().canAdvance(matrixX+1, matrixY);
            if (test) {
                xCoord += stepSize;}
            else
            {
                //corectie deplasare la dreapta
                //cat timp pozitia reala pe x este mai mica decat cea ideala
                if(xCoord<matrixX*Tile.TILE_HEIGHT)
                {
                    xCoord += stepSize;
                }
            }
        }
        else
        {//deplasare la stanga
            boolean test =  getCurrentMap().canAdvance(matrixX-1, matrixY);
            if (test) {
                xCoord -= stepSize;}
            else
            {
                //corectie deplasare la stanga
                //cat timp pozitia reala pe x este mai marre de cea ideala
                if(xCoord>matrixX*Tile.TILE_HEIGHT)
                {
                    xCoord -= stepSize;
                }
            }
        }
        updatePositionInMatrix();

    }

    @Override
    public void updatePositionInMatrix()
    {
        matrixX = (xCoord+ Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        matrixY = (yCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        if( getCurrentMap().canKill(player.matrixX, player.matrixY))
        //todo move this from here! make another function for it!
        //todo kill the player
        //daca jucatorul ajunge pe un camp insta kill este teleportat pe pozitia initiala
        {

            player.isKilled();
            player.restarted();
            resetPlayerPosition();

        }

        if(player.wasPositionChanged())
        {
            Player.setPositionChanged();
            notifyObservers();
        }
        else
        {
            Player.unsetPositionChanged();
        }

        //todo other interactions
    }

    public boolean wasPositionChanged()
    {
        boolean result = false;

        if(matrixY!=oldMatrixY)
        {
            oldMatrixY = matrixY;
            result = true;
        }
        if(matrixX!=oldMatrixX)
        {
            oldMatrixX = matrixX;
            result = true;
        }

        return result;
    }

    private void resetPlayerPosition()
    {
        player.yCoord= getCurrentMap().startY()*Tile.TILE_HEIGHT;
        player.xCoord=0;
        player.updatePositionInMatrix();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        try {
            observers.remove(observer);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void notifyObservers() {
        for(Observer observer :observers)
        {
            observer.update();
        }
    }

    public void setCurrentState(StateTypes state)
    {
        switch (state)
        {
            case SHIELDON -> {
                currentState = getShieldON();
            }
            case SHIELDOFF -> {
                currentState = getShieldOFF();
            }

        }

    }

    public void setCurrentState(State state)
    {
       currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

}
