package BlueRidingHood.InputManager;

import BlueRidingHood.Entities.EntitiesFactory;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Game.Sign;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;

import static BlueRidingHood.Entities.EnemieEntity.actualEntities;

public class PlayerInputHandler {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;
    private static PlayerInputHandler playerInputHandler = null;

    public static PlayerInputHandler getPlayerInputHandler()
    {
        if(playerInputHandler==null)
        {
            playerInputHandler = new PlayerInputHandler();
        }

        return playerInputHandler;
    }

    protected PlayerInputHandler()
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
                    player.stepHorizontal(Sign.minus);
                    player.displayPlayerDetails();
                }
                if (keyboardInputManager.right) {
                    if(! currentMap.end(player.matrixX, player.matrixY)) {
                        player.stepHorizontal(Sign.plus);
                        player.displayPlayerDetails();
                    }else
                    {
                        win();
                    }
                }
            } else {
                if (keyboardInputManager.up) {
                    player.stepVertical(Sign.minus);
                    player.displayPlayerDetails();

                }
                if (keyboardInputManager.down) {
                    player.stepVertical(Sign.plus);
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
            actualEntities = EntitiesFactory.getEntitiesFactory().produceMapEntities();
        }
        else
        {
            //todo
            //StopGame();
        }
    }

}
