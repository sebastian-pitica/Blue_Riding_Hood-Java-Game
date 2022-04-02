package BlueRidingHood.Entities.Coin;

import BlueRidingHood.Map.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;


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

    public Coin[] createCoins(Map currentMap)
    {

        LinkedList<Integer> availablePositions = currentMap.getAllAvailablePositions();

        int length = availablePositions.size();

        HashSet<Integer> actualPositionsToDraw = new HashSet<>();

        Random randomGenerator = new Random();

        while(actualPositionsToDraw.size()!=28)
        {
            actualPositionsToDraw.add(availablePositions.get(randomGenerator.nextInt(length)));
        }

        Coin[] coins = new Coin[28];
        int i=0;

        for(int position : actualPositionsToDraw)
        {
            int y = position/100;
            int x = position%100;

            if(x>=0 && y>=0) {
                coins[i++] = new Coin(x,y);
            }
        }

        return coins;
    }
}
