package BlueRidingHood.InputManager;

import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Factory.EntitiesFactory;
import BlueRidingHood.Game.Sign;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;

import static BlueRidingHood.Entities.EnemieEntity.*;

public class PlayerInputHandler {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;
    private static PlayerInputHandler playerInputHandler = null;
    private long hitEntityStartTime=0;
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
            //todo map observer
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
                    if(! currentMap.end(player.getMatrixX(), player.getMatrixY())) {
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
        else
        {
            for(EnemieEntity entity: actualEntities)
            {
                if(System.nanoTime()-hitEntityStartTime>=1000000000 && isAnotherEntityHere(player.getMatrixX(), player.getMatrixY(), null))
                {
                    hitEntityStartTime=System.nanoTime();
                    System.out.println("Hit: "+hitEntityStartTime);
                    hitEntityAtCoords(player.getMatrixX(), player.getMatrixY());
                }
            }
        }
        if(keyboardInputManager.reset)
        {
            player.setyCoord(currentMap.startY() * Tile.TILE_HEIGHT);
            player.setxCoord(0);

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

        if(keyboardInputManager.killAllEnemies)
        {
            EnemieEntity.killThemAll();
        }

        if(keyboardInputManager.resetHitCounter)
        {
            player.resetHitCounter();
        }

    }

    private void win()
    //functie care determina ce se intampla dupa ce se petrece win la nivelul curent
    //todo modificare criterii de win
    {
        System.out.println("Win!");

        if(currentMap.getMapNr()==1) {
            Map.setMap();
            currentMap.notifyObservers();
            currentMap = Map.getCurrentMap();
            player.setyCoord(currentMap.startY() * Tile.TILE_HEIGHT);
            player.setxCoord(0);
            player.resetHitCounter();//ai scris ca la noul nivel se reseteaza hit counterul
            actualEntities = EntitiesFactory.getEntitiesFactory().produceMapEntities();
        }
        else
        {
            //todo
            //StopGame();
        }
    }

}
