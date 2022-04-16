package BlueRidingHood.Map;

import BlueRidingHood.DataBaseHandler.DataBaseHandler;
import BlueRidingHood.Entities.EntitiesFactory;
import BlueRidingHood.Entities.Entity;

import java.util.LinkedList;
import java.util.Vector;

public class Map  {
    //todo pozitie player, pozitie inamici, check, return, etc
    //todo remove coin from print

    private static final Map map1 = new Map(1);
    private static final Map map2 = new Map(2);
    private static Map currentMap = map1; //valoarea de inceput
    //harta curenta
    private final int[][] matrix;
    //harta in format informatie
    private final int mapNr;
    private DataBaseHandler dataBaseHandler;
    //indicele hartii


    private Map(int mapNumber) {
        this.dataBaseHandler = new DataBaseHandler();
        dataBaseHandler.createDBMap();
        this.mapNr = mapNumber;
        if(mapNumber ==1) {
            this.matrix = dataBaseHandler.getMap1();
        }
        else
        {
            this.matrix = dataBaseHandler.getMap2();
        }

        //legenda elemente matrice
        //1=deplasabil
        //0=nedeplasabil
        //3=instant death
        //2=pozitia jucatorului //todo
        //4=poztiei inamici //todo
        //5=pozitie monede //todo

    }

    public static void setMap( )
    {
        currentMap = map2;
    }

    public int getMapNr()
    {
        return currentMap.mapNr;
    }

    public static Map getCurrentMap()
    {
        return currentMap;
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    public LinkedList<Integer> getAllAvailablePositions()
    {
        LinkedList<Integer> result = new LinkedList<>();

        for(int i=0;i<16;++i)
        {
            for(int j=0;j<30;++j)
            {
                if(matrix[i][j]==1) {
                    result.add(i*100+j);
                }
            }
        }
        return result;
    }

    public boolean canAdvance(int x, int y)
        //functie ce returneaza daca pe pozitia matriceala x,y se poate inainta
        //y de pe ecran in matrice este indicele pentru rand
        //x de pe ecran in matrice este indicele pentru coloana
    {
        if(y>=0 && x >=0) {
            int testValue = matrix[y][x];
            return matrix[y][x] == 1 || matrix[y][x]==3;
        }
        else
            return false;

    }

    public boolean canKill(int x, int y)
        //functie ce ofera returneaza daca pe pozitia matriceala x,y se afla un element de instant death
        //y de pe ecran in matrice este indicele pentru rand
        //x de pe ecran in matrice este indicele pentru coloana
    {
        return matrix[y][x] == 3;
    }

    public boolean end(int x, int y)
    //functie ce returneaza daca pe pozitia matriceala x,y se afla finalul hartii
    //y de pe ecran in matrice este indicele pentru rand
    //x de pe ecran in matrice este indicele pentru coloana
    {
        if(y>=0 && x >=0) {
            boolean testValue = y==endY() && x==29;
            return y==endY() && x==29;
        }
        else
            return false;

    }

    public int startY()
    //functie ce furnizeaza pozitia, de start, matriceala a lui y, in functi de harata
    {
        if(mapNr == 1)
        {
            return 10;
        }
        else
        {
            return 8;
        }

    }


    public int endY()
        //functie ce furnizeaza pozitia, de final, matriceala a lui y, in functi de harata
    {
        if(mapNr == 1)
        {
            return 14;
        }
        else
        {
            return 12;
        }

    }


}
