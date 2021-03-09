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
    private int[][] map;

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
        cell[x][y] = new node(x, y);
        cell[x][y].h = manhattanDist(cell[x][y]);
        cell[x][y].g = 0;
        cell[x][y].updateF();
        cell[x][y].parent = null; //the starting node has no parent

        // add starting node to openSet
        openSet.add(cell[x][y]);

        // run alg
        AStar();

        // draw the solved map
        handler.createVisualMap(map);
    }

    /* Determine if a desired move is possible */
    node move(node N, String dir) {
        switch (dir) {
            case "left":
                //check if moving would go out of bounds or hit a wall
                if (N.X == 0 || map[N.X - 1][N.Y] == 3) {
                    // moving left is illegal
                    return N;
                }
                return cell[N.X - 1][N.Y];
            case "right":
                if (N.X == 9 || map[N.X + 1][N.Y] == 3) {
                    return N;
                }
                return cell[N.X + 1][N.Y];
            case "up":
                if (N.Y == 0 || map[N.X][N.Y - 1] == 3) {
                    return N;
                }
                return cell[N.X][N.Y - 1];
            case "down":
                if (N.Y == 9 || map[N.X][N.Y + 1] == 3) {
                    return N;
                }
                return cell[N.X][N.Y + 1];
        }
        return null;
    }

    /* Determine the distance from the given node to the closest endpoint */
    // As of right now, this just targets any goal but doesn't record which one. This will be a problem later.
    int manhattanDist(node N) {
        ArrayList<Integer> paths = new ArrayList<>();

        //from node N to nearest endpoint
        for (int i = 0; i < endPointY.size(); i++) {
            //endPointY and endPointX should always have the same number of elements, so I can get away with this.
            int k = Math.abs(N.X - endPointX.get(i)) + Math.abs(N.Y - endPointY.get(i));
            paths.add(k);
        }

        //if there is more than one endpoint
        if (paths.size() > 1) {
            int min = 99999; //There is certainly a better way to do this, but I'm tired.
            for (Integer p : paths) {
                if (p < min)
                    min = p;
            }
            return min;
        } else {
            return paths.get(0);
        }
    }

    boolean isEndpoint(node N) {
        for (int k = 0; k < endPointY.size(); k++) {
            if (N.X == endPointX.get(k) && N.Y == endPointY.get(k)) {
                //we found an endpoint
                System.out.println("Endpoint found.");
                return true;
            }
        }
        return false;
    }

    boolean isEndpoint(node N, int movementX, int movementY) {
        for (int k = 0; k < endPointY.size(); k++) {
            if (N.X + movementX == endPointX.get(k) && N.Y + movementY == endPointY.get(k)) {
                //we found an endpoint
                System.out.println("Endpoint found.");
                return true;
            }
        }
        return false;
    }

    /* Trace the path from a given node back to the starting point */
    void traceBack(node ep) {
        StringBuilder steps = new StringBuilder();
        map[ep.X][ep.Y] = 6; //6 signifies a reached endpoint

        while (ep.parent != null) {
            int directionY = ep.Y - ep.parent.Y;
            int directionX = ep.X - ep.parent.X;

            switch (directionY) {
                case 1:
                    //moved down
                    steps.insert(0, "Down\n");
                    break;
                case -1:
                    //moved up
                    steps.insert(0, "Up\n");
                    break;
                default:
                    if (directionX == -1) {
                        steps.insert(0, "Left\n");
                        break;
                    }
                    steps.insert(0, "Right\n");
            }

            ep = ep.parent;
            map[ep.X][ep.Y] = 5;
        }
        System.out.println("Solution:");
        System.out.println(steps);
    }

    /* A* Search algorithm */
    void AStar() {
        while (!openSet.isEmpty()) {
            //explore the node in the open set that has the lowest fScore value
            node currentNode = openSet.poll();
            openSet.remove(currentNode);

            //check all cardinal directions?
            node downNode = move(currentNode, "down");
            if (downNode != currentNode) { //move stays in bounds
                if (downNode == null) { //we haven't been to this node before
                    downNode = new node(currentNode.X, currentNode.Y + 1);
                    downNode.g = currentNode.g + 1;
                    downNode.h = manhattanDist(downNode);
                    downNode.updateF();
                    downNode.parent = currentNode;
                    cell[currentNode.X][currentNode.Y + 1] = downNode;
                    openSet.add(cell[currentNode.X][currentNode.Y + 1]);

                    if (isEndpoint(downNode)) {
                        traceBack(downNode);
                        return;
                    }
                } else if (downNode.g > currentNode.g + 1) { //replace worse path with cool new path
                    downNode.g = currentNode.g + 1;
                    downNode.updateF();
                    downNode.parent = currentNode;
                    cell[currentNode.X][currentNode.Y + 1] = downNode;
                    openSet.add(cell[currentNode.X][currentNode.Y + 1]);
                }
            }

            node upNode = move(currentNode, "up");
            if (upNode != currentNode) { //move stays in bounds
                if (upNode == null) { //we haven't been to this node before
                    upNode = new node(currentNode.X, currentNode.Y - 1);
                    upNode.g = currentNode.g + 1;
                    upNode.h = manhattanDist(upNode);
                    upNode.updateF();
                    upNode.parent = currentNode;
                    cell[currentNode.X][currentNode.Y - 1] = upNode;
                    openSet.add(cell[currentNode.X][currentNode.Y - 1]);

                    if (isEndpoint(upNode)) {
                        traceBack(upNode);
                        return;
                    }
                } else if (upNode.g > currentNode.g + 1) { //replace worse path with cool new path
                    upNode.g = currentNode.g + 1;
                    upNode.updateF();
                    upNode.parent = currentNode;
                    cell[currentNode.X][currentNode.Y - 1] = upNode;
                    openSet.add(cell[currentNode.X][currentNode.Y - 1]);
                }
            }

            node leftNode = move(currentNode, "left");
            if (leftNode != currentNode) { //move stays in bounds
                if (leftNode == null) { //we haven't been to this node before
                    leftNode = new node(currentNode.X - 1, currentNode.Y);
                    leftNode.g = currentNode.g + 1;
                    leftNode.h = manhattanDist(leftNode);
                    leftNode.updateF();
                    leftNode.parent = currentNode;
                    cell[currentNode.X - 1][currentNode.Y] = leftNode;
                    openSet.add(cell[currentNode.X - 1][currentNode.Y]);

                    if (isEndpoint(leftNode)) {
                        traceBack(leftNode);
                        return;
                    }
                } else if (leftNode.g > currentNode.g + 1) { //replace worse path with cool new path
                    leftNode.g = currentNode.g + 1;
                    leftNode.updateF();
                    leftNode.parent = currentNode;
                    cell[currentNode.X - 1][currentNode.Y] = leftNode;
                    openSet.add(cell[currentNode.X - 1][currentNode.Y]);
                }
            }

            node rightNode = move(currentNode, "right");
            if (rightNode != currentNode) { //move stays in bounds
                if (rightNode == null) { //we haven't been to this node before
                    rightNode = new node(currentNode.X + 1, currentNode.Y);
                    rightNode.g = currentNode.g + 1;
                    rightNode.h = manhattanDist(rightNode);
                    rightNode.updateF();
                    rightNode.parent = currentNode;
                    cell[currentNode.X + 1][currentNode.Y] = rightNode;
                    openSet.add(cell[currentNode.X + 1][currentNode.Y]);

                    if (isEndpoint(rightNode)) {
                        traceBack(rightNode);
                        return;
                    }
                } else if (rightNode.g > currentNode.g + 1) { //replace worse path with cool new path
                    rightNode.g = currentNode.g + 1;
                    rightNode.updateF();
                    rightNode.parent = currentNode;
                    cell[currentNode.X + 1][currentNode.Y] = rightNode;
                    openSet.add(cell[currentNode.X + 1][currentNode.Y]);
                }
            }
        }
    }
}

