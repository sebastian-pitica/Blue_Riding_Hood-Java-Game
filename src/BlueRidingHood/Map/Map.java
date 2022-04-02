package BlueRidingHood.Map;

import java.util.LinkedList;

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
    //indicele hartii


    private Map(int mapNumber) {
        this.mapNr = mapNumber;
        if(mapNumber ==1) {
            this.matrix = new int[][]{
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,3,3,3,1,1,1,1,1,1,0,3,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,0,3,0},
                    {0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,0,1,0,0,1,0,0,0,0,1,0,1,0,3,0},
                    {0,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,3,0,1,1,1,1,0,3,0},
                    {0,1,0,1,0,0,0,0,0,1,0,3,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,1,1,0},
                    {0,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,0},
                    {0,0,0,1,0,1,0,0,0,1,1,1,0,1,0,1,0,0,0,1,0,0,0,0,1,0,1,1,1,0},
                    {0,3,0,1,0,1,1,1,0,3,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0},
                    {0,3,0,1,1,3,0,1,0,1,0,1,0,0,0,0,0,3,0,0,0,0,1,0,1,1,1,0,3,0},
                    {0,3,0,1,0,0,0,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,1,0},
                    {1,1,1,1,1,1,1,1,0,0,0,0,0,1,0,1,1,1,0,1,1,1,0,1,0,0,0,0,1,0},
                    {0,0,0,1,0,0,0,1,1,1,1,1,1,1,0,1,0,0,0,1,0,0,0,1,1,1,1,0,1,0},
                    {0,1,1,1,0,3,0,1,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,1,0,1,0},
                    {0,0,0,1,1,1,0,1,0,1,1,1,1,0,0,0,0,0,0,1,0,1,0,1,1,1,1,0,1,0},
                    {0,3,3,3,0,1,1,1,0,3,0,0,1,1,1,1,1,1,1,1,0,1,1,3,0,0,1,1,1,1},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        }
        else
        {
            this.matrix = new int[][]{
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,3,0,1,1,1,1,3,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,3,3,3,0},
                    {0,3,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0},
                    {0,1,1,1,1,1,1,1,1,1,0,1,0,3,1,1,0,1,0,3,1,1,1,1,1,0,1,0,1,0},
                    {0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1,0,0,0,0,1,0,1,1,1,1,1,0},
                    {0,1,0,1,0,3,0,1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,1,0},
                    {0,1,0,1,1,1,0,1,0,1,0,0,0,0,0,1,0,0,0,0,1,0,1,1,1,0,1,1,3,0},
                    {0,3,0,0,0,1,0,1,1,1,1,1,1,1,0,1,1,3,0,1,1,1,1,0,1,1,1,0,0,0},
                    {1,1,1,1,1,1,0,0,0,0,0,3,0,1,1,1,0,1,1,1,0,1,0,0,0,0,1,0,3,0},
                    {0,1,0,0,0,1,1,1,1,1,1,3,0,1,0,0,0,1,0,0,0,1,1,1,1,0,1,0,3,0},
                    {0,1,1,1,0,1,0,1,0,0,0,0,0,1,1,1,1,1,3,1,0,0,0,0,1,0,1,0,3,0},
                    {0,0,0,1,0,1,0,1,1,1,1,0,0,0,0,0,0,1,0,1,0,1,1,1,1,0,1,1,1,0},
                    {0,1,1,1,1,1,0,1,0,0,1,0,1,1,1,1,1,1,0,1,0,1,0,0,1,0,1,0,1,1},
                    {0,0,0,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,1,1,1,1,1,1,0,1,0,0,0},
                    {0,3,1,1,1,1,1,1,0,0,0,0,3,0,3,3,3,1,1,1,0,0,0,0,1,1,1,1,3,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        }

        //legenda elemente matrice
        //1=deplasabil
        //0=nedeplasabil
        //3=instant death
        //2=pozitia jucatorului //todo
        //4=poztiei inamici //todo
        //5=pozitie monede //todo

    }

    public static void setCurrentMap(int mapNr)
    {
        switch (mapNr)
        {
            case 1: currentMap = map1;
            case 2: currentMap = map2;
        }
    }

    public int getMapNr()
    {
        return currentMap.mapNr;
    }

    public static Map getCurrentMap()
    {
        return currentMap;
    }

    public LinkedList<Integer> getAllAvailablePositions()
    {
        LinkedList<Integer> result = new LinkedList<Integer>();

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

    public void testMap()
        //functie de afisare a mapei in format matriceal
    {
        for(int i=0;i<16;++i)
        {
            System.out.print("\t"+i+": \t");
            if(i<=9)
            {
                System.out.print("\t");
            }
            for(int j=0;j<30;++j)
            {
                System.out.print(matrix[i][j]+ "\t");
            }
            System.out.println();
        }

        System.out.print("\t\t\t");

        for(int i=0;i<30;++i)
        {
            System.out.print(i+ "\t");
        }
    }

}
