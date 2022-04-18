package BlueRidingHood.Animation;

import BlueRidingHood.Entities.Coin;
import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Factory.CoinFactory;
import BlueRidingHood.Game.Action;
import BlueRidingHood.Game.Direction;
import BlueRidingHood.Game.StateTypes;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.InputManager.KeyboardInputManager;
import BlueRidingHood.InputManager.MouseInputManager;
import BlueRidingHood.Map.Map;
import BlueRidingHood.Observer.Observer;
import BlueRidingHood.State.State;

import java.awt.*;
import java.util.Objects;
import java.util.Vector;

import static BlueRidingHood.Entities.EnemieEntity.actualEntities;
import static BlueRidingHood.State.ShieldON.getShieldON;

public class AnimationHandler implements Observer {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;
    private final Coin coin = Coin.getCoin();
    private Vector<Integer> coinsPositions;
    private long shieldStartTime;
    private long shieldStopTime;
    final private int corectiePlayerCoord = 12;
    private Animation currentPlayerAnimation;
    private State currentState;

    //todo other entities animations: monede, inamici, atacuri
    //todo other entities
    //todo possible other time animations: atacuri, etc
    //todo init pentru alte entitati

    private static AnimationHandler animationHandler = null;

    public static AnimationHandler getAnimationHandler()
    {
        if(animationHandler ==null)
        {
            animationHandler = new AnimationHandler();
        }

        return animationHandler;
    }

    protected AnimationHandler()
    {
        keyboardInputManager = KeyboardInputManager.provideKeyboardInputManager();
        mouseInputManager = MouseInputManager.provideMouseInputManager();
        this.player = Player.getPlayer();
        player.setCurrentState(State.getDefaultState());
        currentState=player.getCurrentState();
        currentPlayerAnimation = player.getCurrentState().getDefaultAnimation(); //animatia default
        currentMap = Map.getCurrentMap();
        currentMap.attach(this);
        coinInit();

    }

    private void coinInit()
    {
        CoinFactory coinFactory = CoinFactory.provideCoinFactory();
        coinsPositions = coinFactory.createCoins(currentMap);
    }

    private void currentPlayerAnimation()
    {
        //todo add la documentatie deficitul de animatie
        //pentru urcare/coborare (deplasare pe axa y) nu exista spriteuri
        //in aceste cazuri animatia de deplasare este cea a ultimei directii pe orizontala


        if (keyboardInputManager.up || keyboardInputManager.down) {

            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left))
            {
                currentPlayerAnimation = currentState.getAnimation(Direction.left, Action.RUN);

            }
            else
            {
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right))
                {
                    currentPlayerAnimation = currentState.getAnimation(Direction.right, Action.RUN);

                }
            }
        }

        if (keyboardInputManager.left) {
            currentPlayerAnimation = currentState.getAnimation(Direction.left, Action.RUN);
        }
        if (keyboardInputManager.right) {
            currentPlayerAnimation = currentState.getAnimation(Direction.right, Action.RUN);
        }

        if(keyboardInputManager.reset || !keyboardInputManager.anyMovementKeyPressed())
        //daca s-a facut reset sau nu s-a apasat nici o tasta de miscare
        //todo add to documentatie explicatii
        {
            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left))
            {
                currentPlayerAnimation = currentState.getAnimation(Direction.left, Action.STAND);
            }
            else
            {
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right))
                {
                    currentPlayerAnimation = currentState.getAnimation(Direction.right, Action.STAND);

                }
            }
        }


        //todo check player alive

        if(keyboardInputManager.attack) {
            //todo add explicatii documentatie
            //daca este apasata tasta de atac
            if (player.attackActive) {
                //daca atacul este in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left)) {
                    currentPlayerAnimation = currentState.getAnimation(Direction.left, Action.ATTACK);

                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right)) {
                        currentPlayerAnimation = currentState.getAnimation(Direction.right, Action.ATTACK);

                    }
                }
            } else {
                //daca atacul nu este in desfasurare
                player.attackActive = true;
                //atacul este acum in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left)) {
                    currentPlayerAnimation = currentState.getAnimation(Direction.left, Action.DRAWSWORD);

                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right)) {
                        currentPlayerAnimation = currentState.getAnimation(Direction.right, Action.DRAWSWORD);
                    }
                }
            }
        }
        else
        {
            //daca nu a fost apasata tasta de atac, dar atacul este in desfasurare
            if(player.attackActive) {
                player.attackActive = false;
                //atacul este incheiat
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left)) {
                    currentPlayerAnimation = currentState.getAnimation(Direction.left, Action.RETRACTSWORD);

                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right)) {
                        currentPlayerAnimation = currentState.getAnimation(Direction.right, Action.RETRACTSWORD);
                    }
                }
            }
        }

    }

    public void animationStartTimeHandler() //todo for all animations
            //todo flags
            //momentan doar shield
    {
        if(keyboardInputManager.shieldActivated &&
                timeToStartAnimation(shieldStopTime,"shield")) //daca scutul a fost activat
        {
          player.setCurrentState(StateTypes.SHIELDON);
          currentState= player.getCurrentState();
          shieldStartTime = System.nanoTime();
        }

    }

    public void aniomationStopTimeHandler() //todo for all animations
    {
        if(player.getCurrentState() == getShieldON() &&
                timeToStopAnimation(shieldStartTime, "shield")) //verific daca este nevoie sa opresc animatia scutului
        {
            player.setCurrentState(StateTypes.SHIELDOFF);
            currentState= player.getCurrentState();
            shieldStopTime = System.nanoTime();
        }

    }

    private boolean timeToStartAnimation(long stopTime, String animation)
    {
        //todo for all timed animation
        long nowTime = System.nanoTime(); //masor timpul actual
        int secondsLimit = switch (animation) {
            //todo verific daca e mai mare ca 15 secunde
            case "shield" -> 5;
            default -> 0;
        };

        if ((nowTime - stopTime) / secondsLimit >= 1000000000) //for shield animation switch case
        //todo variabila cu limita de secunde
        {
            return true;
        } else
        {
            return false;
        }

    }

    private boolean timeToStopAnimation(long startTime, String animation)
    {
        //todo for all timed animation
        long nowTime = System.nanoTime(); //masor timpul actual

        //verific daca e mai mare ca 5 secunde
        if((nowTime - startTime)/5 >= 1000000000) //for shield animation switch case
        //todo variabila cu limita de secunde
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void runAnimations()
    {
        runEntitiesAnimation();
        runCoinAnimations();
        runPlayerAnimation();
    }

    public void drawAnimations(Graphics graphics)
    {
        drawPlayerAnimation(graphics);
        drawCoinAnimations(graphics);
        drawEntitiesAnimation(graphics);
    }

    private void runEntitiesAnimation()
    {
        if(actualEntities!=null)
        for(EnemieEntity element: actualEntities)
        {
            element.runAnimation();
        }
    }

    private void drawEntitiesAnimation(Graphics g)
    {
        if(actualEntities!=null)
            for(EnemieEntity element: actualEntities)
        {
            element.draw(g);
        }
    }

    private void runCoinAnimations() {

        checkCoinVector();

        coin.runAnimation();

    }

    private void checkCoinVector() //todo enemie
    {
        if(isCoinAtThisPosition(player.getMatrixX()+player.getMatrixY()*100))
        {
            player.addPointsToScore(100);
            eliminateCoinAtCoords(player.getMatrixX()+player.getMatrixY()*100);
        }

        if(currentMap != Map.getCurrentMap() || coinsPositions.size() == 0)
        {
            currentMap = Map.getCurrentMap();
            coinInit();
        }
    }

    private void drawCoinAnimations(Graphics g)
    {
        for(Integer coord: coinsPositions)
        {
            coin.drawCoin(g,coord%100,coord/100);
        }
    }

    private boolean isCoinAtThisPosition(int coords)
    {
        for(int element :coinsPositions)
        {
            if(element == coords)
            {
                return true;
            }
        }
        return false;
    }

    private void eliminateCoinAtCoords(int coords)
    {
        coinsPositions.removeIf(element -> element == coords);
    }

    private void drawPlayerAnimation(Graphics graphics) {
        currentPlayerAnimation();
        currentPlayerAnimation.drawAnimation(graphics, player.getxCoord(), player.getyCoord() -corectiePlayerCoord, Tile.TILE_HEIGHT,Tile.TILE_WIDTH);
    }

    private void runPlayerAnimation()
    {
        currentPlayerAnimation.runAnimation(); //pornesc animatiad
    }

    @Override
    public void update() {
        currentMap = Map.getCurrentMap();
        coinInit();
    }
}
