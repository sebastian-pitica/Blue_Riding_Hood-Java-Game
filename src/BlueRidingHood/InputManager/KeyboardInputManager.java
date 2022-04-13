package BlueRidingHood.InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputManager implements KeyListener {
    //todo add la documentatie celelalte key adaugate
    //todo add celelate comenzi

    public Boolean up, down, left, right;
    public Boolean swordAttack, iceAttack, shieldActivated;
    public Boolean escape, GODModeOn, resetHitCounter,killAllEnemies, reset, rectangular, grid, quit, faster;
    public String lastHorizontalDirection ="right", lastMovementKeYPressed="right";

    protected static KeyboardInputManager keyboardInputManager=null;

    public static KeyboardInputManager provideKeyboardInputManager()
    {
        if(keyboardInputManager == null)
        {
            keyboardInputManager =  new KeyboardInputManager();
        }
            return keyboardInputManager;
    }

    protected KeyboardInputManager()
    {
        up = down = left = right = swordAttack = iceAttack =
                  shieldActivated = escape = GODModeOn = resetHitCounter =
                        killAllEnemies = faster = reset = rectangular = quit = grid = false;
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
            lastHorizontalDirection ="left";
            lastMovementKeYPressed="left";
        }
        if(keyPressedCode == KeyEvent.VK_D)
        {
            right = true;
            lastHorizontalDirection ="right";
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
        if(keyPressedCode == KeyEvent.VK_G)
        {
            if(grid)
            {
                grid = false;
            }
            else
            {
                grid = true;
            }
        }




        if(keyPressedCode == KeyEvent.VK_SPACE)
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
            if(GODModeOn)
            {
                GODModeOn = false;
            }
            else
            {
                GODModeOn = true;
            }
        }
        if(keyPressedCode == KeyEvent.VK_F9)
        {
            if(faster)
            {
                faster = false;
            }
            else
            {
                faster = true;
            }
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


        if(keyReleasedCode == KeyEvent.VK_SPACE)
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
            //verifica daca a fost apasat vreo tasta de miscare
    {
        return up || down || left || right;     }

    @Override public void keyTyped(KeyEvent e) {

    }
}
