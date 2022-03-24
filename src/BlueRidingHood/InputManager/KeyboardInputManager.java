package BlueRidingHood.InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputManager implements KeyListener { // TODO: 24.03.2022 check other shit

    public Boolean up, down, left, right;
    public Boolean swordAttack, iceAttack, shieldActivate;
    public Boolean escape, GODModeOn, resetHitCounter,killAllEnemies, reset;

    public KeyboardInputManager()
    {
        up = down = left = right = swordAttack = iceAttack = shieldActivate = escape = GODModeOn = resetHitCounter = killAllEnemies =reset = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressedCode = e.getKeyCode();

        if(keyPressedCode == KeyEvent.VK_W)
        {
            up=true;
        }
        if(keyPressedCode == KeyEvent.VK_S)
        {
            down = true;
        }
        if(keyPressedCode == KeyEvent.VK_A)
        {
            left = true;
        }
        if(keyPressedCode == KeyEvent.VK_D)
        {
            right = true;
        }
        if(keyPressedCode == KeyEvent.VK_R)
        {
            reset = true;
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
            shieldActivate = true;
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
            shieldActivate = false;
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


    @Override public void keyTyped(KeyEvent e) {

    }
}
