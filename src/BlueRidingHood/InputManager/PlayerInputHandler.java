package BlueRidingHood.InputManager;

import BlueRidingHood.Entities.EntitiesFactory;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;

import static BlueRidingHood.Entities.Entity.actualEntities;

public class PlayerInputHandler {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;
    public enum Sign {plus,minus}

    public PlayerInputHandler()
    {
        keyboardInputManager = KeyboardInputManager.provideKeyboardInputManager();
        mouseInputManager = MouseInputManager.provideMouseInputManager();
        this.player = Player.getPlayer();
    }

    public void handler()
    //functie care se ocupa cu prelucrarea inputului de la jucator
    {
        currentMap = Map.getCurrentMap();
   

        if(!player.attackActive ) {
            //restrictionez miscarile si atacul simultan
            if (keyboardInputManager.left || keyboardInputManager.right)
            //restrictionez miscarile simultane pe cele doua axead
            {
                if (keyboardInputManager.left) {
                    stepHorizontal(Sign.minus);
                    player.displayPlayerDetails();
                }
                if (keyboardInputManager.right) {
                    if(! currentMap.end(player.matrixX, player.matrixY)) {
                        stepHorizontal(Sign.plus);
                        player.displayPlayerDetails();
                    }else
                    {
                        win();
                    }
                }
            } else {
                if (keyboardInputManager.up) {
                    stepVertical(Sign.minus);
                    player.displayPlayerDetails();

                }
                if (keyboardInputManager.down) {
                    stepVertical(Sign.plus);
                    player.displayPlayerDetails();

                }
            }
        }
        //reset pozitie jucator
        if(keyboardInputManager.reset)
        {
            player.yCoord= currentMap.startY()* Tile.TILE_HEIGHT;
            player.xCoord=0;

        }

        if(keyboardInputManager.anyMovementKeyPressed())
        {
            Player.setPositionChanged();
        }
        else
        {
            Player.unsetPositionChanged();
        }

        if(keyboardInputManager.quit)//todo close window + close game
        {
            //todo
            //StopGame();
        }

        if(keyboardInputManager.GODModeOn)
        {
            player.GODmodeON();
        }
        else
        {
            player.GODmodeOFF();
        }

        if(keyboardInputManager.faster)
        {
            player.fasterON();
        }
        else
        {
            player.fasterOFF();
        }

    }

    private void stepVertical(Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign==Sign.plus) { //deplasare in jos
            boolean test =  currentMap.canAdvance(player.matrixX, player.matrixY + 1);
            if (test) {
                player.yCoord += player.stepSize;
            }
            else
            {   //corectie deplasare in jos
                //cat timp coordonata actuala y e mai mica ca cea ideala
                if(player.yCoord<player.matrixY*Tile.TILE_HEIGHT)
                {
                    player.yCoord += player.stepSize;
                }
            }

        }
        else { //deplasare in sus
            boolean test =  currentMap.canAdvance(player.matrixX, player.matrixY - 1);
            if (test) {
                player.yCoord -= player.stepSize;
            }
            else
            {   //corectie deplasare in sus
                //cat timp coordonata actuala y e mai mare decat cea ideala
                if(player.yCoord>player.matrixY*Tile.TILE_HEIGHT)
                {
                    player.yCoord -= player.stepSize;
                }
            }
        }
        updatePlayerPositionInMatrix();
    }

    private void stepHorizontal(Sign sign)
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign==Sign.plus) //deplasare la dreapta
        {
            boolean test =  currentMap.canAdvance(player.matrixX+1, player.matrixY);
            if (test) {
                player.xCoord += player.stepSize;}
            else
            {
                //corectie deplasare la dreapta
                //cat timp pozitia reala pe x este mai mica decat cea ideala
                if(player.xCoord<player.matrixX*Tile.TILE_HEIGHT)
                {
                    player.xCoord += player.stepSize;
                }
            }
        }
        else
        {//deplasare la stanga
            boolean test =  currentMap.canAdvance(player.matrixX-1, player.matrixY);
            if (test) {
                player.xCoord -= player.stepSize;}
            else
            {
                //corectie deplasare la stanga
                //cat timp pozitia reala pe x este mai marre de cea ideala
                if(player.xCoord>player.matrixX*Tile.TILE_HEIGHT)
                {
                    player.xCoord -= player.stepSize;
                }
            }
        }
        updatePlayerPositionInMatrix();
    }

    private void win()
    //functie care determina ce se intampla dupa ce se petrece win la nivelul curent
    //todo modificare criterii de win
    {
        System.out.println("Win!");

        if(currentMap.getMapNr()==1) {
            Map.setMap();
            currentMap = Map.getCurrentMap();
            player.yCoord = currentMap.startY() * Tile.TILE_HEIGHT;
            player.xCoord = 0;
            actualEntities = EntitiesFactory.getEntitiesFactory().produceMapEntities(2);
        }
        else
        {
            //todo
            //StopGame();
        }
    }

    private void updatePlayerPositionInMatrix()
    //updateaza pozitia jucatorului in coordonate matriceale pe baza coordonatelor x, y
    {
        player.matrixX = (player.xCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;
        player.matrixY = (player.yCoord+Tile.TILE_HEIGHT/2) / Tile.TILE_HEIGHT;

        if( currentMap.canKill(player.matrixX, player.matrixY))
        //todo move this from here! make another function for it!
        //todo kill the player
        //daca jucatorul ajunge pe un camp insta kill este teleportat pe pozitia initiala
        {

            player.isKilled();
            player.restarted();
            resetPlayerPosition();

        }
        //todo other interactions
    }

    private void resetPlayerPosition()
    {
        player.yCoord= currentMap.startY()*Tile.TILE_HEIGHT;
        player.xCoord=0;
        updatePlayerPositionInMatrix();
    }

}
