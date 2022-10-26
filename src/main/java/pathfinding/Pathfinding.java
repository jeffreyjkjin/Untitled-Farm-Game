package pathfinding;

import java.util.ArrayList;
import java.lang.Math;

import app.GamePanel;

/*
 *  KNOWN BUGS WITH PATHFINDING:
 * 
 *  - Collision is only checked when the player moves, so if you stand still the enemies will go through you
 * 
 *  - They seem to randomly stop pathing if you move to certain areas
 * 
 *  - Can easily safespot by either moving up and down repeatedly or getting them stuck behind a tree
 * 
 *  - Enemies can walk inside of eachother so collision detection needs fixing
 */

public class Pathfinding {
    
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node start, goal, current;
    boolean goalReached = false;
    int step = 0;

    public Pathfinding(GamePanel gp)
    {
        this.gp = gp;
        createNodes();
    }

    public void createNodes()
    {
        node = new Node[gp.mapM.getMap().maxWorldCol][gp.mapM.getMap().maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gp.mapM.getMap().maxWorldCol && row < gp.mapM.getMap().maxWorldRow)
        {
            node[col][row] = new Node(col, row);
            col++;

            if (col == gp.mapM.getMap().maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes()
    {
        int col = 0;
        int row = 0;

        while (col < gp.mapM.getMap().maxWorldCol && row < gp.mapM.getMap().maxWorldRow)
        {
            node[col][row].open = false;
            node[col][row].blocked = false;
            node[col][row].checked = false;

            col++;

            if (col == gp.mapM.getMap().maxWorldCol)
            {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow)
    {
        resetNodes();

        start = node[startCol][startRow];
        current = start;
        goal = node[goalCol][goalRow];

        openList.add(current);

        int col = 0;
        int row = 0;

        while (col < gp.mapM.getMap().maxWorldCol && row < gp.mapM.getMap().maxWorldRow)
        {
            int tileNum = gp.mapM.getMap().tileMap[row][col];
            // Check tiles for collision. If yes, change blocked bool to true
            if (gp.tileM.checkTileCollision(tileNum))
            {
                node[col][row].blocked = true;
            }

            // Set cost for A* algo
            getCost(node[col][row]);
            col++;

            if (col == gp.mapM.getMap().maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node)
    {
        // g cost
        int xDist = Math.abs(node.col - start.col);
        int yDist = Math.abs(node.row - start.row);
        node.gCost = xDist + yDist;

        // h cost
        xDist = Math.abs(node.col - goal.col);
        yDist = Math.abs(node.row - goal.row);
        node.hCost = xDist + yDist;

        // f cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search()
    {
        while(!goalReached && step < 500)
        {
            int col = current.col;
            int row = current.row;

            current.checked = true;
            openList.remove(current);
            // Open up node
            if (row - 1 >= 0)
            {
                openNode(node[col][row - 1]);
            }
            // Open left
            if (col - 1 >= 0)
            {
                openNode(node[col - 1][row]);
            }
            // Open down
            if (row + 1 < gp.mapM.getMap().maxWorldRow)
            {
                openNode(node[col][row + 1]);
            }
            // Open right
            if (col + 1 < gp.mapM.getMap().maxWorldCol)
            {
                openNode(node[col + 1][row]);
            }
            // Find most efficient node to go to
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++)
            {
                if (openList.get(i).fCost < bestNodeFCost)
                {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodeFCost)
                {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
                    {
                        bestNodeIndex = i;
                    }
                }
            }
            // End loop if empty
            if(openList.size() == 0)
            {
                break;
            }

            current = openList.get(bestNodeIndex);

            if(current == goal)
            {
                goalReached = true;
                pathTracker();
            }
            step++;
        }

        return goalReached;
    }

    public void openNode(Node node)
    {
        if(!node.open && !node.checked && !node.blocked)
        {
            node.open = true;
            node.parent = current;
            openList.add(node);
        }
    }

    public void pathTracker()
    {
        Node currentNode = goal;

        while (currentNode != start)
        {
            pathList.add(0, currentNode);
            currentNode = currentNode.parent;
        }
    }
}

