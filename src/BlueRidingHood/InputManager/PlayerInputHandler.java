package BlueRidingHood.InputManager;

import BlueRidingHood.Entities.Player;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;

public class PlayerInputHandler {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;

    public PlayerInputHandler(Player player)
    {
        keyboardInputManager = KeyboardInputManager.provideKeyboardInputManager();
        mouseInputManager = MouseInputManager.provideMouseInputManager();
        this.player = player;
    }

    public void handler()
    //functie care se ocupa cu prelucrarea inputului de la jucator
    {
        currentMap = Map.getCurrentMap();

        if(!player.attackActive) {
            //restrictionez miscarile si atacul simultan
            if (keyboardInputManager.left || keyboardInputManager.right)
            //restrictionez miscarile simultane pe cele doua axead
            {
                if (keyboardInputManager.left) {
                    stepHorizontal('-');
                    displayPlayerPosition();
                }
                if (keyboardInputManager.right) {
                    if(! currentMap.end(player.matrixX, player.matrixY)) {
                        stepHorizontal('+');
                        displayPlayerPosition();
                    }else
                    {
                        win();
                    }
                }
            } else {
                if (keyboardInputManager.up) {
                    stepVertical('-');
                    displayPlayerPosition();

                }
                if (keyboardInputManager.down) {
                    stepVertical('+');
                    displayPlayerPosition();

                }
            }
        }
        //reset pozitie jucator
        if(keyboardInputManager.reset)
        {
            player.yCoord= currentMap.startY()* Tile.TILE_HEIGHT;
            player.xCoord=0;

        }

        if(keyboardInputManager.quit)//todo close window + close game
        {
            //todo
            //StopGame();
        }

    }

    private void stepVertical(char sign)
    //todo add la documentatie corectia
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign=='+') { //deplasare in jos
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

    private void stepHorizontal(char sign)
    //todo add la documentatie corectia
    //functie de actualizare a pozitiei jucatorului atunci cand se deplaseaza pe vertical
    //todo cazuri exceptionale cand esti lanaga pozitie de 3 sau cand esti la inceput/final de matrice
    {
        updatePlayerPositionInMatrix();
        if(sign=='+') //deplasare la dreapta
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
            Map.setCurrentMap(2);
            currentMap = Map.getCurrentMap();
            player.yCoord = currentMap.startY() * Tile.TILE_HEIGHT;
            player.xCoord = 0;
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
            player.yCoord= currentMap.startY()*Tile.TILE_HEIGHT;
            player.xCoord=0;

            updatePlayerPositionInMatrix();
        }
        //todo other interactions
    }

    private void displayPlayerPosition()
    //afiseaza pozitia jcuatorului in coordonate x, y si in coordonate matriceale
    {
        System.out.print("\nx: "+player.xCoord+", y: "+player.yCoord+"\nmatx: "+player.matrixX+", maty: "+player.matrixY+"\n");
    }

}
