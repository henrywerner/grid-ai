public class node implements Comparable<node> {

    public int X, Y, f, g, h;
    public node parent;

    node(int x, int y) {
        X = x;
        Y = y;
    }

    //F equals the g (path length from start) plus h (heuristic value)
    void updateF() {
        f = g + h;
    }

    // Override how the priority queue handles the comparison for nodes
    @Override
    public int compareTo(node N) {
        // Set the priority queue to sort for the node with the smallest f value
        return N.f < this.f ? 1 : -1;
    }
}
