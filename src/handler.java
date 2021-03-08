import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class handler {
    static int startX;
    static int startY;
    static ArrayList<Integer> endPointX;
    static ArrayList<Integer> endPointY;

    public static void main(String[] args) throws Exception {
        /*
        - Take in argument for file name of map
        - convert map into matrix ??
        - send matrix to navigation alg
        - output???
         */
        endPointY = new ArrayList<Integer>();
        endPointX = new ArrayList<Integer>();

        int[][] map = createMap(args[0]);
        createVisualMap(map);
        System.out.println("Starting point: (" + startX + ", " + startY + ")");

        navigator nvg = new navigator(map,startX,startY,endPointX,endPointY);


//        for(int y = 0; y < 10; y++){
//            for(int x = 0; x < 10; x++) {
//                System.out.print("[" + map[x][y] + "] ");
//            }
//            System.out.print("\n");
//        }

    }

    /* Turns the input file's contents into a 2D array */
    public static int[][] createMap(String input) throws Exception
    {
        int[][] map = new int[10][10];

        File mapFile = new File("./maps/" + input);
        Scanner scnr = new Scanner(mapFile);
        String contents = null;
        String[] cells = new String[100];

        //assumes map file contains all information on a single line
        if (scnr.hasNextLine())
            contents = scnr.nextLine();

        if (contents != null)
            cells = contents.split("\\s+");

        for(int y = 0; y < 10; y++){
            for(int x = 0; x < 10; x++){
                switch (cells[(y*10) + x]) {
                    case "0":
                        map[x][y] = 0;
                        break;
                    case "1":
                        // Save the location of the starting cell
                        startX = x;
                        startY = y;
                        map[x][y] = 1;
                        break;
                    case "2":
                        // Save the location of the endpoints
                        endPointX.add(x);
                        endPointY.add(y);
                        map[x][y] = 2;
                        break;
                    case "3":
                        map[x][y] = 3;
                        break;
                    default:
                        System.out.println("Uh oh.");
                }
            }
        }

        return map;
    }

    /* Creates a visual representation of the 2D map */
    public static void createVisualMap(int[][] map) {
        System.out.println("╔════════════════════╗");
        for(int y = 0; y < 10; y++){
            System.out.print("║");
            for(int x = 0; x < 10; x++){
                switch (map[x][y]) {
                    case 0:
                        //cell is empty
                        System.out.print("  ");
                        break;
                    case 1:
                        //starting cell
                        System.out.print("ST");
                        break;
                    case 2:
                        //goal cell
                        System.out.print("EN");
                        break;
                    case 3:
                        //wall
                        System.out.print("██");
                        break;
                    default:
                        System.out.println("Uh oh.");
                }
            }
            System.out.println("║");
            //System.out.println("");
        }
        System.out.println("╚════════════════════╝");
    }

}
