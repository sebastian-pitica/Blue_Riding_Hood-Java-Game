package BlueRidingHood.Graphics.Animation;

import BlueRidingHood.Entities.Coin.Coin;
import BlueRidingHood.Entities.Coin.CoinFactory;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.InputManager.KeyboardInputManager;
import BlueRidingHood.InputManager.MouseInputManager;
import BlueRidingHood.Map.Map;

import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class AnimationHandler {

    private final KeyboardInputManager keyboardInputManager;
    private final MouseInputManager mouseInputManager;
    private final Player player;
    private Map currentMap;
    private Vector<Coin> coins;
    private long shieldStartTime;
    private long shieldStopTime;
    //todo other entities animations: monede, inamici, atacuri
    //todo other entities
    //todo possible other time animations: atacuri, etc
    //todo init pentru alte entitati

    public AnimationHandler(Player player)
    {
        keyboardInputManager = KeyboardInputManager.provideKeyboardInputManager();
        mouseInputManager = MouseInputManager.provideMouseInputManager();
        this.player = player;
        coinInit();
    }

    private void coinInit()
    {
        currentMap = Map.getCurrentMap();
        CoinFactory coinFactory = CoinFactory.provideCoinFactory();
        coins = coinFactory.createCoins(currentMap);
    }

    public Animation currentPlayerAnimation()
    {
        //todo add la documentatie deficitul de animatie
        //pentru urcare/coborare (deplasare pe axa y) nu exista spriteuri
        //in aceste cazuri animatia de deplasare este cea a ultimei directii pe orizontala
        Animation result = null;

        if (keyboardInputManager.up || keyboardInputManager.down) {

            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "left"))
            {
                if(!player.shieldActive) {
                    result = player.leftRun;
                }
                else
                {
                    result = player.leftShieldRun;
                }
            }
            else
            {
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "right"))
                {
                    if(!player.shieldActive) {
                        result = player.rightRun;
                    }
                    else
                    {
                        result = player.rightShieldRun;
                    }
                }
            }
        }

        if (keyboardInputManager.left) {
            if(!player.shieldActive) {
                result = player.leftRun;
            }
            else
            {
                result = player.leftShieldRun;
            }
        }
        if (keyboardInputManager.right) {
            if(!player.shieldActive) {
                result = player.rightRun;
            }
            else
            {
                result = player.rightShieldRun;
            }
        }

        if(keyboardInputManager.reset || !keyboardInputManager.anyMovementKeyPressed())
        //daca s-a facut reset sau nu s-a apasat nici o tasta de miscare
        //todo add to documentatie explicatii
        {
            if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "left"))
            {
                if(!player.shieldActive) {
                    result = player.leftStand;
                }
                else
                {
                    result = player.leftShieldStand;
                }
            }
            else
            {
                if(Objects.equals(keyboardInputManager.lastHorizontalDirection, "right"))
                {
                    if(!player.shieldActive) {
                        result = player.rightStand;
                    }
                    else
                    {
                        result = player.rightShieldStand;
                    }
                }
            }
        }

        if(keyboardInputManager.swordAttack) {
            //todo add explicatii documentatie
            //daca este apasata tasta de atac
            if (player.attackActive) {
                //daca atacul este in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "left")) {
                    if (!player.shieldActive) {
                        result = player.leftAttackSword;
                    } else {
                        result = player.leftShieldAttackSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "right")) {
                        if (!player.shieldActive) {
                            result = player.rightAttackSword;
                        } else {
                            result = player.rightShieldAttackSword;
                        }
                    }
                }
            } else {
                //daca atacul nu este in desfasurare
                player.attackActive = true;
                //atacul este acum in desfasurare
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "left")) {
                    if (!player.shieldActive) {
                        result = player.leftDrawSword;
                    } else {
                        result = player.leftShieldDrawSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "right")) {
                        if (!player.shieldActive) {
                            result = player.rightDrawSword;
                        } else {
                            result = player.rightShieldDrawSword;
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
                if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "left")) {
                    if (!player.shieldActive) {
                        result = player.leftRetractSword;
                    } else {
                        result = player.leftShieldRetractSword;
                    }
                } else {
                    if (Objects.equals(keyboardInputManager.lastHorizontalDirection, "right")) {
                        if (!player.shieldActive) {
                            result = player.rightRetractSword;
                        } else {
                            result = player.rightShieldRetractSword;
                        }
                    }
                }
            }
        }

        return result;
    }

    public void animationStartTimeHandler() //todo for all animations
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

        //todo verific daca e mai mare ca 15 secunde
        if((nowTime - stopTime)/15 >= 1000000000) //for shield animation switch case
        //todo variabila cu limita de secunde
        {
            return true;
        }
        else
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

    public void runCoinAnimations() {

        checkCoinVector();

        for (Coin element:coins)
        {
            element.runAnimation();
        }
    }

    private void checkCoinVector()
    {
        if(isCoinAtThisPosition(player.matrixX, player.matrixY))
        {
            eliminateCoinAtCoords(player.matrixX, player.matrixY);
        }

        if(currentMap != Map.getCurrentMap() || coins.size() == 0)
        {
            coinInit();
        }
    }

    public void drawCoinAnimations(Graphics g) //todo coin disapear
    //todo mark coin pos
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
}