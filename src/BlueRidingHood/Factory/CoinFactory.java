package BlueRidingHood.Factory;

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

    public Vector<Integer> createCoins(Map currentMap)
    {

        LinkedList<Integer> availablePositions = currentMap.getAllAvailablePositions();

        int length = availablePositions.size();

        HashSet<Integer> actualPositionsToDraw = new HashSet<>();

        Random randomGenerator = new Random();

        Vector<Integer> coinsPositions = new Vector<>(28);
        int i=0;

        while(actualPositionsToDraw.size()!=28)
        {
            actualPositionsToDraw.add(availablePositions.get(randomGenerator.nextInt(length)));
        }

        for(int position : actualPositionsToDraw)
        {
            coinsPositions.insertElementAt(position,i++);
        }

        return coinsPositions;
    }
}
