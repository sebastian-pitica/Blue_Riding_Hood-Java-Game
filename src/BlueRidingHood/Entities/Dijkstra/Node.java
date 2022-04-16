package BlueRidingHood.Entities.Dijkstra;

import BlueRidingHood.Map.Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

//todo matrice de adiacenta in db

public class Node {
    int value;
    int indice;
    int up, down, left, right;

    private static LinkedHashMap<Integer, Node> mapNodes;
    private static HashMap<Integer, Integer> nodeDictionary;
    static LinkedList<Integer> pathNodes;
    private static int[][] adiacenteMatrix;
    private static int mapIdForData = 0;
    private static Map currentMap;
    private static int lastStart=-1, lastStop=-1;

    Node(int indice, int value, int left, int right, int up, int down) {
        this.down = down;
        this.up = up;
        this.left = left;
        this.right = right;
        this.indice = indice;
        this.value = value;
    }

    @Override
    public String toString() {
        return "value: " + value + ", indice: " + indice + ", up: " + up + ", down: " + down + ", left: " + left + ", right: " + right + "\n";
    }

    private static void buildMapNodes() {
        int map[][] = currentMap.getMatrix();

        for (int i = 0; i < map.length; ++i) {

            for (int j = 0; j < map[0].length; ++j) {
                int up = -1, down = -1, left = -1, right = -1;

                    if (map[i][j] == 1) {
                        int value = i * 100 + j;

                        if (((i-1) >= 0) && map[i - 1][j] == 1) {
                            up = (i - 1) * 100 + j;
                        }
                        if (((i+1))<map.length && map[i + 1][j] == 1) {
                            down = (i + 1) * 100 + j;
                        }
                        if ((j-1)>=0 && map[i][j - 1] == 1) {
                            left = i * 100 + j - 1;
                        }
                        if ((j+1)<map[0].length && map[i][j + 1] == 1) {
                            right = i * 100 + j + 1;
                        }

                        mapNodes.put(mapNodes.size(), new Node(mapNodes.size(), value, left, right, up, down));
                        nodeDictionary.put(nodeDictionary.size(), value);
                    }
            }
        }
    }

    private static void buildAdiMatrix()
    {
        adiacenteMatrix = new int[mapNodes.size()][mapNodes.size()];

        for (int i = 0; i < 211; ++i) {
            Node currentNode = mapNodes.get(i);
            if (currentNode.left != -1) {
                for (Node element : mapNodes.values()) {
                    if (element.value == currentNode.left) {
                        adiacenteMatrix[i][element.indice] = 1;
                        break;
                    }
                }
            }
            if (currentNode.right != -1) {
                for (Node element : mapNodes.values()) {
                    if (element.value == currentNode.right) {
                        adiacenteMatrix[i][element.indice] = 1;
                        break;
                    }
                }
            }
            if (currentNode.up != -1) {
                for (Node element : mapNodes.values()) {
                    if (element.value == currentNode.up) {
                        adiacenteMatrix[i][element.indice] = 1;
                        break;
                    }
                }
            }
            if (currentNode.down != -1) {
                for (Node element : mapNodes.values()) {
                    if (element.value == currentNode.down) {
                        adiacenteMatrix[i][element.indice] = 1;
                        break;
                    }
                }
            }


        }

    }

    private static void clean()
    {
        mapNodes = new LinkedHashMap<>();
        nodeDictionary = new HashMap<>();
        pathNodes = new LinkedList<>();
        adiacenteMatrix = null;
        mapIdForData = 0;
    }

    private static void build()
    {
        currentMap = Map.getCurrentMap();
        mapIdForData = currentMap.getMapNr();
    }

    private static int getNodeNumber(int value)
    {
        for(Integer key: nodeDictionary.keySet())
        {
            if(nodeDictionary.get(key) == value)
            {
                return key;
            }
            if(nodeDictionary.get(key)>value)
            {
                break;
            }
        }

        return -1;
    }

    private static void translatePath()
    {
        LinkedList<Integer> coordsPath = new LinkedList<Integer>();
        for(int element: pathNodes)
        {
            int translatedCoord = 0;
            for(int key: nodeDictionary.keySet())
            {
                if(element == key)
                {
                    translatedCoord = nodeDictionary.get(key);
                    break;
                }
            }
            coordsPath.add(translatedCoord);
        }
        pathNodes = coordsPath;
    }

    public static LinkedList<Integer> getPath(int start, int stop)
    {
        if(mapIdForData != Map.getCurrentMap().getMapNr())
        {
            clean();
            build();
            buildMapNodes();
            buildAdiMatrix();
        }

        if(start!= lastStart || stop!=lastStop)
        {
            lastStart = start;
            lastStop = stop;
            if (getNodeNumber(start) != -1 && getNodeNumber(stop) != -1) {
                DijkstrasAlgorithm.dijkstra(adiacenteMatrix, getNodeNumber(start), getNodeNumber(stop));
                translatePath();
                return pathNodes;
            } else {
                return null; //todo check if null la iesire
            }
        }
        else
        {
            return pathNodes;
        }
    }

}

