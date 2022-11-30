package pathfinding;

import java.util.ArrayList;
import java.lang.Math;

import app.GamePanel;
import tile.TileManager;
import entity.Entity;

/** 
 * Creates and sets Nodes to represent the tiles of the map player and farmers are on
 * Also stores potential paths and the eventual final path towards the farmers goal location
 * Once these tiles are stored in Nodes, the A-star pathfinding algorithm can be used to find the fastest path farmer can take to player
 * Accounts for solid obstacles on the map such as trees and fenches, paths around them
 * Only allows for up, down, left, right movement as per assignment instructions
 * Needs to be instantiated in GamePanel to be called by Farmer in the future
 * 
 * @author Andrew Hein (ach17)
*/
public class Pathfinding {
    TileManager tileM;
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>(); // For nodes that are open
    public ArrayList<Node> pathList = new ArrayList<>(); // For the most efficient path to be stored in
    Node start, goal, current;
    private boolean goalReached = false;

    /**
     * Constructs the pathfinding class by linking it to gp and TileManager singleton and creating Nodes
     * 
     * @param gp GamePanel which is the main way the game is run
     */
    public Pathfinding(GamePanel gp)
    {
        this.gp = gp;
        tileM = TileManager.getInstance();
        createNodes();
    }

    /**
     * Creates the Nodes that will store the tiles of the current map
     * The amount created equals the amount of tiles on the current map
     */
    private void createNodes()
    {
        node = new Node[gp.mapM.getMap().maxWorldCol][gp.mapM.getMap().maxWorldRow];

        int col = 0;
        int row = 0;
        // Create a new node for each tile on the map and make its location in Node array equal to its position on map
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

    /**
     * Resets all of the nodes by changing their variables to default states and recalling createNodes()
     * Also clears all potential and final paths that may have been created by other functions
     */
    private void resetNodes()
    {
        int col = 0;
        int row = 0;

        createNodes();

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
    }

    /**
     * Links the Nodes to the current maps tiles and determines whether they are solid or not for pathfinding purposes
     * Takes in the farmers start location and its goal location and gets the cost of all tiles that the algorithm will use
     * Adds everything to openList which stores all possible tiles to visit. Will determine proper path in other functions
     * 
     * @param startCol farmers starting column (current column)
     * @param startRow farmers starting row (current row)
     * @param goalCol farmers goal column to move to
     * @param goalRow farmers foal row to move to
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow)
    {
        // Goal is out of the map, so do nothing
        if (goalCol > gp.mapM.getMap().maxWorldCol || goalCol < 0 || goalRow > gp.mapM.getMap().maxWorldRow || goalRow < 0)
        {
            return;
        }

        resetNodes();
        // Variables to store where we are on the map and in the algorithm for later
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
            if (tileM.checkTileCollision(tileNum))
            {
                node[col][row].blocked = true;
            }

            // Set cost for A-star algorithm
            getCost(node[col][row]);
            col++;

            if (col == gp.mapM.getMap().maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    /**
     * gets the gCost and hCost and uses them to calculate the final fCost of travelling to the tile
     * This is the core of the A-star pathfinding algorithm. The tile with the lowest fCost will be moved to
     * 
     * @param node current node to get the cost of. This node represents a tile on the current map
     */
    private void getCost(Node node)
    {
        // gCost
        int xDist = Math.abs(node.col - start.col);
        int yDist = Math.abs(node.row - start.row);
        node.gCost = xDist + yDist;

        // hCost
        xDist = Math.abs(node.col - goal.col);
        yDist = Math.abs(node.row - goal.row);
        node.hCost = xDist + yDist;

        // fCost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Searches through all possible paths using A-star pathfinding algorithm and finds the most efficient path
     * opens nodes and checks their costs until it finds the best one. Once found, adds that Node to the final pathList ArraytList
     * If path is eventually found, returns true. Otherwise, returns false
     * calls pathTracker() to actually add the best nodes to pathList
     * 
     * @return whether or not the goal location was able to be reached by the algorithm
     */
    public boolean search()
    {
        while(!goalReached)
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
            // Variables to find most efficient node to go to
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;
            // Find most efficient node to go to
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
            // End loop if empty and did not reach goal
            if(openList.size() == 0)
            {
                break;
            }

            current = openList.get(bestNodeIndex);
            // If we reached out goal, set goalReached to true and call pathTracker() to add Nodes to pathList
            if(current == goal)
            {
                goalReached = true;
                pathTracker();
            }
        }

        return goalReached;
    }

    /**
     * Opens Node and adds it to list of opened nodes
     * search() will use this to check the possible Nodes fCostts to determine best path forward
     * 
     * @param node current Node to be opened
     */
    private void openNode(Node node)
    {
        if(!node.open && !node.checked && !node.blocked)
        {
            node.open = true;
            node.parent = current;
            openList.add(node);
        }
    }

    /**
     * Adds all of the best nodes to the pathList which will be used by Farmer to determine how to move towards Player
     */
    private void pathTracker()
    {
        Node currentNode = goal;

        while (currentNode != start)
        {
            pathList.add(0, currentNode);
            currentNode = currentNode.parent;
        }
    }

    /**
     * Uses the pathfinding class to find the most efficient path to the player
     * Once path is found, sets the appropriate direction farmer needs to go to avoid collisions based on next tile in path
     * 
     * @param goalCol goal column farmer attempts to reach. Currently the players current column
     * @param goalRow goal row farmer attempts to reach. Currently the players current row
     */
    public void searchPath(int goalCol, int goalRow, Entity entity)
    {
        int currCol = (entity.worldX + entity.hitbox.x) / gp.tileSize;
        int currRow = (entity.worldY + entity.hitbox.y) / gp.tileSize;
        gp.pathFinder.setNodes(currCol, currRow, goalCol, goalRow);
        boolean goalReached = gp.pathFinder.search();

        if (goalReached)
        {
            // Next worldX and Y
            int nextX = gp.pathFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.get(0).row * gp.tileSize;
            // Entity's hitbox
            int farmerLeftX = entity.worldX + entity.hitbox.x;
            int farmerRightX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
            int farmerTopY = entity.worldY + entity.hitbox.y;
            int farmerBotY = entity.worldY + entity.hitbox.y + entity.hitbox.height;
            // Find which direction to go next
            if (farmerTopY > nextY && farmerLeftX >= nextX && farmerRightX < nextX + gp.tileSize)
            {
                entity.direction = "up";
            }
            else if (farmerTopY < nextY && farmerLeftX >= nextX && farmerRightX < nextX + gp.tileSize)
            {
                entity.direction = "down";
            }
            else if (farmerTopY >= nextY && farmerBotY < nextY + gp.tileSize)
            {
                // Can go left or right so have to figure out which
                if (farmerLeftX > nextX)
                {
                    entity.direction = "left";
                }
                if (farmerLeftX < nextX)
                {
                    entity.direction = "right";
                }

            }
            else if (farmerTopY > nextY && farmerLeftX > nextX)
            {
                // Can go up or left, have to figoure out which
                entity.direction = "up";

                gp.checker.checkTileCollision(entity);

                if (entity.collisionOn)
                {
                    entity.direction = "left";
                }
            }
            else if(farmerTopY > nextY && farmerLeftX < nextX)
            {
                // Can go up or right
                entity.direction = "up";

                gp.checker.checkTileCollision(entity);

                if (entity.collisionOn)
                {
                    entity.direction = "right";
                }
            }
            else if (farmerTopY < nextY && farmerLeftX > nextX)
            {
                // down or left
                entity.direction = "down";

                gp.checker.checkTileCollision(entity);

                if (entity.collisionOn)
                {
                    entity.direction = "left";
                }

            }
            else if (farmerTopY < nextY && farmerLeftX < nextX)
            {
                // down or left
                entity.direction = "down";

                gp.checker.checkTileCollision(entity);

                if (entity.collisionOn)
                {
                    entity.direction = "right";
                }
            }
        }
    }
}

