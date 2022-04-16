package BlueRidingHood.Graphics.Animation;

import BlueRidingHood.Entities.Coin.Coin;
import BlueRidingHood.Entities.Coin.CoinFactory;
import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Game.Direction;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.InputManager.KeyboardInputManager;
import BlueRidingHood.InputManager.MouseInputManager;
import BlueRidingHood.Map.Map;

import java.awt.*;
import java.util.Objects;
import java.util.Vector;

import static BlueRidingHood.Entities.EnemieEntity.actualEntities;

public class AnimationHandler {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;
    private Vector<Coin> coins;
    private long shieldStartTime;
    private long shieldStopTime;
    final private int corectiePlayerCoord = 12;
    private Animation currentPlayerAnimation;

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
        currentPlayerAnimation = player.rightStand; //animatia default
        coinInit();

    }

    private void coinInit()
    {
        currentMap = Map.getCurrentMap();
        CoinFactory coinFactory = CoinFactory.provideCoinFactory();
        coins = coinFactory.createCoins(currentMap);
    }

    private void currentPlayerAnimation()
    {
        //todo add la documentatie deficitul de animatie
        //pentru urcare/coborare (deplasare pe axa y) nu exista spriteuri
        //in aceste cazuri animatia de deplasare este cea a ultimei directii pe orizontala


        if (keyboardInputManager.up || keyboardInputManager.down) {

            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left))
            {
                if(!player.shieldActive) {
                    currentPlayerAnimation = player.leftRun;
                }
                else
                {
                    currentPlayerAnimation = player.leftShieldRun;
                }
            }
            else
            {
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right))
                {
                    if(!player.shieldActive) {
                        currentPlayerAnimation = player.rightRun;
                    }
                    else
                    {
                        currentPlayerAnimation = player.rightShieldRun;
                    }
                }
            }
        }

        if (keyboardInputManager.left) {
            if(!player.shieldActive) {
                currentPlayerAnimation = player.leftRun;
            }
            else
            {
                currentPlayerAnimation = player.leftShieldRun;
            }
        }
        if (keyboardInputManager.right) {
            if(!player.shieldActive) {
                currentPlayerAnimation = player.rightRun;
            }
            else
            {
                currentPlayerAnimation = player.rightShieldRun;
            }
        }

        if(keyboardInputManager.reset || !keyboardInputManager.anyMovementKeyPressed())
        //daca s-a facut reset sau nu s-a apasat nici o tasta de miscare
        //todo add to documentatie explicatii
        {
            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left))
            {
                if(!player.shieldActive) {
                    currentPlayerAnimation = player.leftStand;
                }
                else
                {
                    currentPlayerAnimation = player.leftShieldStand;
                }
            }
            else
            {
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right))
                {
                    if(!player.shieldActive) {
                        currentPlayerAnimation = player.rightStand;
                    }
                    else
                    {
                        currentPlayerAnimation = player.rightShieldStand;
                    }
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
                    if (!player.shieldActive) {
                        currentPlayerAnimation = player.leftAttack;
                    } else {
                        currentPlayerAnimation = player.leftShieldAttack;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right)) {
                        if (!player.shieldActive) {
                            currentPlayerAnimation = player.rightAttack;
                        } else {
                            currentPlayerAnimation = player.rightShieldAttack;
                        }
                    }
                }
            } else {
                //daca atacul nu este in desfasurare
                player.attackActive = true;
                //atacul este acum in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.left)) {
                    if (!player.shieldActive) {
                        currentPlayerAnimation = player.leftDrawSword;
                    } else {
                        currentPlayerAnimation = player.leftShieldDrawSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right)) {
                        if (!player.shieldActive) {
                            currentPlayerAnimation = player.rightDrawSword;
                        } else {
                            currentPlayerAnimation = player.rightShieldDrawSword;
                        }
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
                    if (!player.shieldActive) {
                        currentPlayerAnimation = player.leftRetractSword;
                    } else {
                        currentPlayerAnimation = player.leftShieldRetractSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, Direction.right)) {
                        if (!player.shieldActive) {
                            currentPlayerAnimation = player.rightRetractSword;
                        } else {
                            currentPlayerAnimation = player.rightShieldRetractSword;
                        }
                    }
                }
            }
        }

    }

    public void animationStartTimeHandler() //todo for all animations
            //momentan doar shield
    {
        if(keyboardInputManager.shieldActivated &&
                timeToStartAnimation(shieldStopTime,"shield")) //daca scutul a fost activat
        {
          player.shieldActive = true;
          shieldStartTime = System.nanoTime();
        }

    }

    public void aniomationStopTimeHandler() //todo for all animations
    {
        if(player.shieldActive &&
                timeToStopAnimation(shieldStartTime, "shield")) //verific daca este nevoie sa opresc animatia scutului
        {
            player.shieldActive = false;
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
        for(EnemieEntity element: actualEntities)
        {
            element.runAnimation();
        }
    }

    private void drawEntitiesAnimation(Graphics g)
    {
        for(EnemieEntity element: actualEntities)
        {
            element.draw(g);
        }
    }

    private void runCoinAnimations() {

        checkCoinVector();

        coins.get(0).runAnimation();

    }

    private void checkCoinVector() //todo enemie
    {
        if(isCoinAtThisPosition(player.matrixX, player.matrixY))
        {
            player.addPointsToScore(100);
            eliminateCoinAtCoords(player.matrixX, player.matrixY);
        }

        if(currentMap != Map.getCurrentMap() || coins.size() == 0)
        {
            currentMap = Map.getCurrentMap();
            coinInit();
        }
    }

    private void drawCoinAnimations(Graphics g)
    {
        for(Coin element: coins)
        {
            element.drawCoin(g);
        }
    }

    private boolean isCoinAtThisPosition(int x, int y)
    {
        for(Coin element :coins)
        {
            if(element.xCoord == x && element.yCoord == y)
            {
                return true;
            }
        }
        return false;
    }

    private void eliminateCoinAtCoords(int x, int y)
    {
        coins.removeIf(element -> element.xCoord == x && element.yCoord == y);
    }

    private void drawPlayerAnimation(Graphics graphics) {
        currentPlayerAnimation();
        currentPlayerAnimation.drawAnimation(graphics,player.xCoord,player.yCoord-corectiePlayerCoord, Tile.TILE_HEIGHT,Tile.TILE_WIDTH);
    }

    private void runPlayerAnimation()
    {
        currentPlayerAnimation.runAnimation(); //pornesc animatiad
    }
}
