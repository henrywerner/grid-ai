public class node implements Comparable<node> {

    public int X, Y, f, g, h;
    public node parent;

    node(int x, int y) {
        X = x;
        Y = y;
    }

    void updateF() {
        f = g + h;
    }

    // In theory, this should let me override how priority queue handles the comparison for this custom object.
    // I say "In theory" because I have absolutely no idea if this will work.
    @Override
    public int compareTo(node N) {
        return N.f < this.f ? 1 : -1;
    }
}
