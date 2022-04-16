package BlueRidingHood.Entities;

import BlueRidingHood.Entities.BasicEntity.*;
import BlueRidingHood.Map.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

public class EntitiesFactory {

    private static final int maxEntityNumberPerMap=4;
    private static EntitiesFactory entitiesFactory=null;

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

    public Vector<EnemieEntity> produceMapEntities()
    {
        //todo entities coords altfel
        Vector<EnemieEntity> result = new Vector<>(maxEntityNumberPerMap);
        LinkedList<Integer> availablePositions = Map.getCurrentMap().getAllAvailablePositions(15);
        int mapNr= Map.getCurrentMap().getMapNr();
        int length = availablePositions.size();
        LinkedList<Integer> actualPositions = new LinkedList<>();
        Random randomGenerator = new Random();

        while(actualPositions.size()!=maxEntityNumberPerMap)
        {
            actualPositions.add(availablePositions.get(randomGenerator.nextInt(length)));
        }

        for(int i=0; actualPositions.size()!=0;++i)
        {
            Integer position = actualPositions.get(0);
            actualPositions.remove(0);
            int y = position/100;
            int x = position%100;

            if(x>=0 && y>=0) {
                if(mapNr == 1)
                {
                    if(i<maxEntityNumberPerMap/2)
                    {
                        result.add(new Fox1(x,y));
                    }
                    else
                    {
                        result.add(new Fox2(x,y));
                    }
                }
                else
                {
                    if(i<maxEntityNumberPerMap/2)
                    {
                        result.add(new Bear1(x,y));
                    }
                    else
                    {
                        result.add(new Bear2(x,y));
                    }
                }
            }
        }


        return result;
    }

    public Vector<Wolf> produceWolfs()
    {
        Vector<Wolf> result = new Vector<>(3);

        LinkedList<Integer> availablePositions = Map.getCurrentMap().getAllAvailablePositions(15);

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
                result.add(new Wolf(x,y));
            }
        }

        return result;
    }

}
