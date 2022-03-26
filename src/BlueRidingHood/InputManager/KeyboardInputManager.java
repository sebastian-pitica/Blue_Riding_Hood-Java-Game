package BlueRidingHood.InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputManager implements KeyListener {
    //todo add la documentatie celelalte key adaugate
    //todo add celelate comenzi

    public Boolean up, down, left, right;
    public Boolean swordAttack, iceAttack, shieldActivated;
    public Boolean escape, GODModeOn, resetHitCounter,killAllEnemies, reset, rectangular, quit;
    public String lastMovementDirection ="right", lastMovementKeYPressed="right";

    public KeyboardInputManager()
    {
        up = down = left = right = swordAttack = iceAttack =
                  shieldActivated = escape = GODModeOn = resetHitCounter =
                        killAllEnemies =reset = rectangular = quit = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressedCode = e.getKeyCode();

        if(keyPressedCode == KeyEvent.VK_W)
        {
            up=true;
            lastMovementKeYPressed="up";
        }
        if(keyPressedCode == KeyEvent.VK_S)
        {
            down = true;
            lastMovementKeYPressed="down";
        }
        if(keyPressedCode == KeyEvent.VK_A)
        {
            left = true;
            lastMovementDirection ="left";
            lastMovementKeYPressed="left";
        }
        if(keyPressedCode == KeyEvent.VK_D)
        {
            right = true;
            lastMovementDirection ="right";
            lastMovementKeYPressed="right";
        }
        if(keyPressedCode == KeyEvent.VK_R)
        {
            reset = true;
        }
        if(keyPressedCode == KeyEvent.VK_T)
        {
            if(rectangular)
            {
                rectangular = false;
            }
            else
            {
                rectangular = true;
            }
        }



        if(keyPressedCode == KeyEvent.VK_F)
        {
            swordAttack=true;
        }
        if(keyPressedCode == KeyEvent.VK_E)
        {
            iceAttack=true;
        }
        if(keyPressedCode == KeyEvent.VK_Q)
        {
            shieldActivated = true;
        }
        if(keyPressedCode == KeyEvent.VK_P)
        {
            quit = true;
        }

        if(keyPressedCode == KeyEvent.VK_F10)
        {
            GODModeOn = true;
        }
        if(keyPressedCode == KeyEvent.VK_F11)
        {
            resetHitCounter = true;
        }
        if(keyPressedCode == KeyEvent.VK_F12)
        {
            killAllEnemies = true;
        }

        if(keyPressedCode == KeyEvent.VK_ESCAPE)
        {
            escape = true;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleasedCode = e.getKeyCode();

        if(keyReleasedCode == KeyEvent.VK_W)
        {
            up=false;
        }
        if(keyReleasedCode == KeyEvent.VK_S)
        {
            down = false;
        }
        if(keyReleasedCode == KeyEvent.VK_A)
        {
            left = false;
        }
        if(keyReleasedCode == KeyEvent.VK_D)
        {
            right = false;
        }
        if(keyReleasedCode == KeyEvent.VK_R)
        {
            reset = false;
        }


        if(keyReleasedCode == KeyEvent.VK_F)
        {
            swordAttack=false;
        }
        if(keyReleasedCode == KeyEvent.VK_E)
        {
            iceAttack=false;
        }
        if(keyReleasedCode == KeyEvent.VK_Q)
        {
            shieldActivated = false;
        }

        if(keyReleasedCode == KeyEvent.VK_F10)
        {
            GODModeOn = false;
        }
        if(keyReleasedCode == KeyEvent.VK_F11)
        {
            resetHitCounter = false;
        }
        if(keyReleasedCode == KeyEvent.VK_F12)
        {
            killAllEnemies = false;
        }

        if(keyReleasedCode == KeyEvent.VK_ESCAPE)
        {
            escape = false;
        }
    }

    public boolean anyMovementKeyPressed()
    {
        return up || down || left || right;     }

    @Override public void keyTyped(KeyEvent e) {

    }
}
