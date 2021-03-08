import java.util.PriorityQueue;
import java.util.ArrayList;

 /*
    A*
    -   Use the manhattan distance to find the value for H:  the sum of absolute values of differences in the goal’s x
        and y coordinates and the current cell’s x and y coordinates respectively
    -   Store the open list as an array of booleans? array length 100 with each value corresponding to a node?
    -   A* is best when there is a singular destination, so maybe just calculate the best end point?
    -   we can check if we've been to a node by checking if it's null or not
 */

public class navigator {

    private final int startX;
    private final int startY;
    private final ArrayList<Integer> endPointX;
    private final ArrayList<Integer> endPointY;
    private final int[][] map;

    PriorityQueue<node> openSet = new PriorityQueue<node>(100);
    node[][] cell;

    /* Constructor */
    navigator(int[][] m, int x, int y, ArrayList<Integer> epX, ArrayList<Integer> epY) {
        map = m;
        cell = new node[10][10];

        startX = x;
        startY = y;
        endPointX = epX;
        endPointY = epY;

        // set up starting node
        cell[x][y] = new node(x,y);
        cell[x][y].h = manhattanDist(cell[x][y]);
        cell[x][y].g = 0;
        cell[x][y].updateF();
        System.out.println("x: " + cell[x][y].X + " y: " + cell[x][y].Y + " h: " + cell[x][y].h);

        // add starting node to openSet
        openSet.add(cell[x][y]);

        AStar();
    }

    /* Determine if a desired move is possible */
    node move(node N, String dir){
        switch (dir) {
            case "left":
                System.out.println("Left");
                if (N.X == 0 || map[N.X-1][N.Y] == 3) { //check if moving would go out of bounds or hit a wall
                    // moving left is illegal
                    System.out.println("*failed*");
                    return N;
                }
                return cell[N.X-1][N.Y];
            case "right":
                System.out.println("Right");
                if (N.X == 9 || map[N.X+1][N.Y] == 3) {
                    // moving right is illegal
                    System.out.println("*failed*");
                    return N;
                }
                return cell[N.X+1][N.Y];
            case "up":
                System.out.println("Up");
                if (N.Y == 0 || map[N.X][N.Y-1] == 3) {
                    // moving up is illegal
                    System.out.println("*failed*");
                    return N;
                }
                return cell[N.X][N.Y-1];
            case "down":
                System.out.println("Down");
                if (N.Y == 9 || map[N.X][N.Y+1] == 3) {
                    // moving down is illegal
                    System.out.println("*failed*");
                    return N;
                }
                return cell[N.X][N.Y+1];
        }
        return null;
    }

    /* Determine the distance from the given node to the closest endpoint */
    // As of right now, this just targets any goal but doesn't record which one. This will be a problem later.
    int manhattanDist(node N) {
        ArrayList<Integer> h = new ArrayList<Integer>();
        //from node N to nearest endpoint
        for (int i = 0; i < endPointY.size(); i++) {
            //endPointY and endPointX should always have the same number of elements, so I can get away with this.
            int k = Math.abs(N.X - endPointX.get(i)) + Math.abs(N.Y - endPointY.get(i));
            h.add(k);
        }

        if (h.size() > 1) {
            int min = 99999; //There is certainly a better way to do this, but I'm tired.
            for (Integer k : h){
                if (k < min)
                    min = k;
            }
            return min;
        }
        else {
            return h.get(0);
        }
    }

    /* A* Search algorithm */
    void AStar() {
        while (!openSet.isEmpty()){
            //explore the node in the open set that has the lowest fScore value
            node currentNode = openSet.poll();
            System.out.println("(" + currentNode.X + ", " + currentNode.Y + ")");
            openSet.remove(currentNode);

            //check all cardinal directions?
            node downNode = move(currentNode, "down");
            if (downNode != currentNode) { //move stays in bounds
                if (downNode == null) { //we haven't been to this node before
                    downNode = new node(currentNode.X, currentNode.Y+1);
                    downNode.g = currentNode.g+1;
                    downNode.h = manhattanDist(downNode);
                    downNode.updateF();
                    cell[currentNode.X][currentNode.Y+1] = downNode;
                    openSet.add(cell[currentNode.X][currentNode.Y+1]);
                }
                else if (downNode.g > currentNode.g+1) { //replace worse path with cool new path
                    downNode.g = currentNode.g+1;
                    downNode.updateF();
                    cell[currentNode.X][currentNode.Y+1] = downNode;
                    openSet.add(cell[currentNode.X][currentNode.Y+1]);
                }

            }

            node upNode = move(currentNode, "up");
            if (upNode != currentNode) { //move stays in bounds
                if (upNode == null) { //we haven't been to this node before
                    upNode = new node(currentNode.X, currentNode.Y-1);
                    upNode.g = currentNode.g+1;
                    upNode.h = manhattanDist(upNode);
                    upNode.updateF();
                    cell[currentNode.X][currentNode.Y-1] = upNode;
                    openSet.add(cell[currentNode.X][currentNode.Y-1]);
                }
                else if (upNode.g > currentNode.g+1) { //replace worse path with cool new path
                    upNode.g = currentNode.g+1;
                    upNode.updateF();
                    cell[currentNode.X][currentNode.Y-1] = upNode;
                    openSet.add(cell[currentNode.X][currentNode.Y-1]);
                }

            }
        }
    }
}

