package BlueRidingHood.Entities;

import BlueRidingHood.Entities.BasicEntity.*;
import BlueRidingHood.Map.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

public class EntitiesFactory {

    private static EntitiesFactory entitiesFactory=null;
    private Map currentMap;

    public static EntitiesFactory getEntitiesFactory()
    {
        if(entitiesFactory == null)
        {
            entitiesFactory = new EntitiesFactory();

        }

        return entitiesFactory;
    }

    protected EntitiesFactory()
    {}

    public Vector<Entity> produceMapEntities(int mapNr)
    {
        //todo entities coords altfel
        Vector<Entity> result = new Vector<>(6);

        if(mapNr == 1)
        {
            result.add(new Fox1(26,12,6));
            result.add(new Fox1(26,14,6));
            result.add(new Fox1(28,12,6));
            result.add(new Fox2(24,8,6));
            result.add(new Fox2(22,5,6));
            result.add(new Fox2(24,2,6));
    }
        else
        {
            result.add(new Bear1(24,1,6));
            result.add(new Bear1(21,3,6));
            result.add(new Bear1(21,1,6));
            result.add(new Bear2(26,8,6));
            result.add(new Bear2(24,9,6));
            result.add(new Bear2(24,13,6));
        }

        return result;
    }

    public Vector<Wolf> produceWolfs()
    {
        Vector<Wolf> result = new Vector<>(3);

        LinkedList<Integer> availablePositions = currentMap.getAllAvailablePositions();

        int length = availablePositions.size();

        HashSet<Integer> actualPositions = new HashSet<>();

        Random randomGenerator = new Random();

        while(actualPositions.size()!=3)
        {
            actualPositions.add(availablePositions.get(randomGenerator.nextInt(length)));
        }

        for(int position : actualPositions)
        {
            int y = position/100;
            int x = position%100;

            if(x>=0 && y>=0) {
                result.add(new Wolf(x,y,6));
            }
        }

        return result;
    }
}
