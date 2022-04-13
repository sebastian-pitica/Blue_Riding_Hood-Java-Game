package BlueRidingHood.Entities.Coin;

import BlueRidingHood.Map.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;


public class CoinFactory {

    protected CoinFactory(){}//todo if you need

    protected static CoinFactory factory = null;

    public static CoinFactory provideCoinFactory()
    {
        if(factory == null)
        {
            factory = new CoinFactory();
        }
        return factory;
    }

    public Vector<Coin> createCoins(Map currentMap)
    {

        LinkedList<Integer> availablePositions = currentMap.getAllAvailablePositions();

        int length = availablePositions.size();

        HashSet<Integer> actualPositionsToDraw = new HashSet<>();

        Random randomGenerator = new Random();

        while(actualPositionsToDraw.size()!=28)
        {
            actualPositionsToDraw.add(availablePositions.get(randomGenerator.nextInt(length)));
        }

        Vector<Coin> coins = new Vector<>(28);
        int i=0;

        for(int position : actualPositionsToDraw)
        {
            int y = position/100;
            int x = position%100;

            if(x>=0 && y>=0) {
                coins.insertElementAt(new Coin(x, y),i++);
            }
        }

        return coins;
    }
}
