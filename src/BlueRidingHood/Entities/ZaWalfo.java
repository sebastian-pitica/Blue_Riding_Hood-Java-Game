package BlueRidingHood.Entities;

public class ZaWalfo extends RangeEntity{

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

    protected ZaWalfo() {} //todo

    @Override
    public void isHit() {
        hitCounter++;
    }

    @Override
    public boolean alive() {
        return alive;
    }
}
