package BlueRidingHood.Entities;

import java.awt.*;

public class ZaWalfo extends Entity{

    static private ZaWalfo zaWalfo = null;
    static public ZaWalfo getZaWalfo()
    {
        if(zaWalfo==null)
        {
            zaWalfo = new ZaWalfo();
        }

        return zaWalfo;
    }

    public int getHitCounter()
            //todo pentru atacul la apropiere
    {
        return hitCounter;
    }

    protected ZaWalfo() {
    } //todo

    @Override
    public void isHit() {
        hitCounter++;
    }

    @Override
    public boolean alive() {
        return alive;
    }

    @Override
    public void runAnimation() {

    }

    @Override
    public void draw(Graphics graphics) {

    }
}
