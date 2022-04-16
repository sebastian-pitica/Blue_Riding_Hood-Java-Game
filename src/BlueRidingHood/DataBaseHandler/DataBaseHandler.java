package BlueRidingHood.DataBaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseHandler {
    //todo player scores
    //todo matrice de adiacenta
    private Connection connection = null;
    private Statement statement = null;

    public DataBaseHandler()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:maps.db");
            statement = connection.createStatement();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public int[][] getMap1()
    {
        int[][] map1=new int[16][30];

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM map1;");
            for(int i=0;i<16;++i)
            {
                resultSet.next();
                for(int j=0;j<30;++j)
                {
                    map1[i][j] = resultSet.getInt("Coloana"+j);
                }
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return map1;
    }

    public int[][] getMap2()
    {
        int[][] map2=new int[16][30];

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM map2;");
            for(int i=0;i<16;++i)
            {
                resultSet.next();
                for(int j=0;j<30;++j)
                {
                    map2[i][j] = resultSet.getInt("Coloana"+j);
                }
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return map2;
    }

    public void createDBMap()
    {
        String sql = "CREATE TABLE map1" +
                    "(Rand TEXT PRIMARY KEY NOT NULL,";

            for(int i=0;i<map1[0].length-1;++i)
            {
                sql+="Coloana"+i+" INT NOT NULL,";
            }

            sql+="Coloana"+map1[0].length+" INT NOT NULL);";

            try
            {
                statement.execute(sql);
                for(int i=0;i<map1.length;++i)
                {
                    sql = "INSERT INTO map1 VALUES ('Rand" + i + "'";
                    method(statement, sql, i, map1);

                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }


            sql = "CREATE TABLE map2" +
                    "(Rand TEXT PRIMARY KEY NOT NULL,";

            for(int i=0;i<map1[0].length-1;++i)
            {
                sql+="Coloana"+i+" INT NOT NULL,";
            }

            sql+="Coloana"+map1[0].length+" INT NOT NULL);";

            try
            {
                statement.execute(sql);
                for(int i=0;i<map1.length;++i)
                {
                    sql = "INSERT INTO map2 VALUES ('Rand" + i + "'";
                    method(statement, sql, i, map2);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

    }

    private void method(Statement statement, String sql, int i, int[][] map2) {
        for(int j=0;j<map1[0].length-1;++j) {
            sql+=","+ map2[i][j]+"";
        }
        sql+=","+ map2[i][map1[0].length-1] +")";

        try {
            statement.executeUpdate(sql);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static int[][] map1 = new int[][]{
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

    private static int[][] map2 = new int[][]{
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


