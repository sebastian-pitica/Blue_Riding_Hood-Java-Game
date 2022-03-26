package BlueRidingHood;

public class Map  {
    //todo pozitie player, pozitie inamici, check, return, etc
    //todo remove coin from print

    public static final Map map1 = new Map(1);
    public static final Map map2 = new Map(2);

    private int[][] matrix;
    private int[] coin;
    //harta in format informatie

    public final int mapNr;
    //indicele hartii

    boolean canAdvance(int x, int y)
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

    boolean canKill(int x, int y)
        //functie ce ofera returneaza daca pe pozitia matriceala x,y se afla un element de instant death
        //y de pe ecran in matrice este indicele pentru rand
        //x de pe ecran in matrice este indicele pentru coloana
    {
        return matrix[y][x] == 3;
    }

    boolean end(int x, int y)
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

    int startY()
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


    int endY()
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
            this.coin = new int[]{104,109,117,121,126,213,301,305,320,428,519,624,627,706,707,713,715,809,915,921,928,105,1110,1323,146,1412,1417,1422};
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
            this.coin = new int[]{103,120,226,311,314,320,328,413,528,601,605,624,627,707,716,817,907,910,921,1005,1019,1110,1121,1201,1312,1402,1407,1427};
        }

        //legenda elemente matrice
        //1=deplasabil
        //0=nedeplasabil
        //3=instant death
        //2=pozitia jucatorului //todo
        //4=poztiei inamici //todo
        //5=pozitie monede //todo

    }

    public int getCoinPositionAtIndex(int index)
    {
        if(index<28)
        {
            return coin[index];
        }
        else
        {
            return -1;
        }
    }

    public boolean coinPositionCheck(int y, int x)
    {
        int data = y*100+x;

        for(int i=0;i<28;++i)
        {
            if(data == coin[i])
            {
                return true;
            }
        }

        return false;
    }
}
