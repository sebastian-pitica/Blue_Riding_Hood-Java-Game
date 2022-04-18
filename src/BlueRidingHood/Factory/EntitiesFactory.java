package BlueRidingHood.Factory;

import BlueRidingHood.Entities.BasicEnemieEntities.*;
import BlueRidingHood.Entities.EnemieEntity;
import BlueRidingHood.Entities.Player;
import BlueRidingHood.Map.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntitiesFactory {

    private static final int maxEntityNumberPerMap=4;
    private static EntitiesFactory entitiesFactory=null;
    protected Player player = Player.getPlayer();
    //todo map observer
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

    public CopyOnWriteArrayList<EnemieEntity> produceMapEntities()
    {
        //todo entities coords altfel
        CopyOnWriteArrayList<EnemieEntity> result = new CopyOnWriteArrayList<>();
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
                        Fox1 fox1 = new Fox1(x,y);
                        result.add(fox1);
                        player.attach(fox1);
                    }
                    else
                    {
                        Fox2 fox2 = new Fox2(x,y);
                        result.add(fox2);
                        player.attach(fox2);
                    }
                }
                else
                {
                    if(i<maxEntityNumberPerMap/2)
                    {
                        Bear1 bear1 = new Bear1(x,y);
                        result.add(bear1);
                        player.attach(bear1);
                    }
                    else
                    {
                        Bear2 bear2 = new Bear2(x,y);
                        result.add(bear2);
                        player.attach(bear2);
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
