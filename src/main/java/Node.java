public class Node {
    protected Node pre;
    protected int move;
    protected int x, y, round;

    public Node(int x, int y, int round, int move, Node pre) {
        this.x = x;
        this.y = y;
        this.round = round;
        this.move = move;
        this.pre = pre;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //if not override equals method, same Node in moveArea will be stored more than once,
    // resulting that blue tilesets which show move area to be overlapped.
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Node) {
            if (this.x == ((Node) obj).x && this.y == ((Node) obj).y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 100 * this.x + y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
