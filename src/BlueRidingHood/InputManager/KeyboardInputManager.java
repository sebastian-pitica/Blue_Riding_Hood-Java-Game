package BlueRidingHood.InputManager;

import BlueRidingHood.Game.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputManager implements KeyListener {
    //todo add la documentatie celelalte key adaugate
    //todo add celelate comenzi

    public Boolean up, down, left, right;
    public Boolean attack, shieldActivated;
    public Boolean escape, GODModeOn, resetHitCounter,killAllEnemies, reset, rectangular, grid, quit, faster;
    public Direction lastHorizontalDirection = Direction.right, lastMovementDirection =Direction.right;

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
        up = down = left = right = attack =
                  shieldActivated = escape = GODModeOn = resetHitCounter =
                        killAllEnemies = faster = reset = rectangular = quit = grid = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressedCode = e.getKeyCode();

        if(keyPressedCode == KeyEvent.VK_W)
        {
            up=true;
            lastMovementDirection =Direction.up;
        }
        if(keyPressedCode == KeyEvent.VK_S)
        {
            down = true;
            lastMovementDirection =Direction.down;
        }
        if(keyPressedCode == KeyEvent.VK_A)
        {
            left = true;
            lastHorizontalDirection =Direction.left;
            lastMovementDirection = Direction.left;
        }
        if(keyPressedCode == KeyEvent.VK_D)
        {
            right = true;
            lastHorizontalDirection =Direction.right;
            lastMovementDirection =Direction.right;
        }
        if(keyPressedCode == KeyEvent.VK_R)
        {
            reset = true;
        }
        if(keyPressedCode == KeyEvent.VK_T)
        {
            rectangular = !rectangular;
        }
        if(keyPressedCode == KeyEvent.VK_G)
        {
            grid = !grid;
        }




        if(keyPressedCode == KeyEvent.VK_SPACE)
        {
            attack =true;
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
            GODModeOn = !GODModeOn;
        }
        if(keyPressedCode == KeyEvent.VK_F9)
        {
            faster = !faster;
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
            attack =false;
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

    @Override public void keyTyped(KeyEvent e) {}
}
