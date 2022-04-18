package BlueRidingHood.InputManager;

import BlueRidingHood.Entities.Player;
import BlueRidingHood.Graphics.Tile;
import BlueRidingHood.Map.Map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputManager implements MouseListener { // TODO

    static private MouseInputManager mouseInputManager=null;

    public static MouseInputManager provideMouseInputManager()
    {
        if(mouseInputManager ==null)
        {
            mouseInputManager =  new MouseInputManager();
        }
        return mouseInputManager;
    }

    protected MouseInputManager(){} //todo

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(Map.getCurrentMap().canAdvance((x) / Tile.TILE_HEIGHT,(y) / Tile.TILE_HEIGHT)) {
            Player.getPlayer().xCoord=x-Tile.TILE_HEIGHT/2;
            Player.getPlayer().yCoord=y-Tile.TILE_WIDTH/2;
            Player.getPlayer().updatePositionInMatrix();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Entered frame at X: " + x + " - Y: " + y);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Exited frame at X: " + x + " - Y: " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Pressed at X: " + x + " - Y: " + y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Released at X: " + x + " - Y: " + y);
    }
}
