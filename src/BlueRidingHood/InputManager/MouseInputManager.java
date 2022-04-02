package BlueRidingHood.InputManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputManager implements MouseListener { // TODO: 24.03.2022

    static private MouseInputManager mouseInputManager=null;

    public static MouseInputManager provideMouseInputManager()
    {
        if(mouseInputManager ==null)
        {
            mouseInputManager =  new MouseInputManager();
        }
        return mouseInputManager;
    }

    protected MouseInputManager(){}; //todo

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
