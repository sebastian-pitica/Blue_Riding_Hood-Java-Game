package BlueRidingHood;

public class Map {

    public static Map map1 = new Map();

    int[][] matrix = new int[32][60];

    boolean canAdvance(int rand, int coloana)
    {
        return matrix[rand][coloana] == 1;
    }

    Map()
    {

    }
}
