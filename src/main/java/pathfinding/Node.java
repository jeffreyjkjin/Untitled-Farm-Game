package pathfinding;

/** 
 * Node that represents a tile used in the Pathfinding class
 * Stores various information about the ttile such as location, costs for pathfinding algorithm, collision status, etc.
 * 
 * @author Andrew Hein (ach17)
*/
public class Node {

    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean blocked;
    boolean open;
    boolean checked;

    /**
     * Constructs this node with the tiles row and column that this Node will represent
     * 
     * @param col column of the tile
     * @param row row of the tile
     */
    public Node(int col, int row)
    {
        this.col = col;
        this.row = row;
    }

}
