package map;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Color;

import java.util.Random;

import app.GamePanel;
import entity.Farmer;
import object.OBJ_Egg;
import object.ObjectManager;
import object.SuperObject;
import tile.TileManager;

/**
 * Reads and stores the level data for one map file.
 * The game map is stored in a two-dimensional integer array which contains tile data and positions.
 * Each position in this array contains an integer which corresponds to a specific tile.
 * Another two-dimensional array stores the type and location of objects and entity .
 * Also stores data for level name, size of the map, start and gate locations, number of objects and entities and the number of required keys to move on to the next level.
 * Used to draw and set objects, entities and tiles.
 * Must be instantiated within MapManager to be used in GamePanel and other classes within it.
 * 
 * @author Andrew Hein (ach17)
 * @author Jeffrey Jin (jjj9)
 * @see map.MapManager
 * @see object.ObjectManager
 * @see tile.TileManager
 */
public class Map {
    GamePanel gp;
    TileManager tileM;
    Random randGen = new Random();

    public int[][] tileMap, objMap;
    public SuperObject objects[];
    public Farmer farmers[];

    public String levelName;
    public int maxWorldCol, maxWorldRow, playerStartX, playerStartY, objectNum, entityNum, keyNum;
    public int gateIndex;

    boolean drawPath = false; // FOR TESTING PATHFINDING
    boolean hitboxTest = false; // FOR VISUALIZING HITBOXES
    boolean showCoords = false; // FOR SEEING TILE COORDS

    /**
     * Constructs a new Map object and loads the mapFile.
     * Links gp and TileManager singleton to this class.
     * Loads mapFile; saves data to classes attributes.
     * 
     * @param gp GamePanel object that is used to run the game
     * @param mapFile The name of the map file
     */
    protected Map(GamePanel gp, String mapFile) {
        this.gp = gp;
        tileM = TileManager.getInstance();

        loadMap(mapFile);
    }

    
    /**
     * Parses the data in the map file line by line and saves the data to this classes various attributes to be used by itself and other classes.
     * Reads through the mapFile's tile and object arrays and stores that data in separate two-dimensional arrays.
     * Also saves the header of the mapFile which contains general level data.
     * 
     * @param mapFile the name of the map file
     */
    private void loadMap(String mapFile) {
        try {
            /**
             format of mapFile:
             first line of mapFile contains level data and should be formatted as shown below:
             levelName,maxWorldRow,maxWorldCol,playerStartX,playerStartY,objectNum,entityNum,keyNum
             below this header, there is a maxWorldRow x maxWorldCol array with numbers that
             correspond with tiles in TileManager
             below the array is a line break
             below the line break is another maxWorldRow x maxWorldCol array with numbers that
             correspond with objects in ObjectManager
            */

            InputStream input = getClass().getResourceAsStream(mapFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            String firstLine = reader.readLine();
            String settings[] = firstLine.split(",");

            // save level data
            levelName = settings[0];
            maxWorldRow = Integer.parseInt(settings[1]);
            maxWorldCol = Integer.parseInt(settings[2]);
            playerStartX = Integer.parseInt(settings[3]) * gp.tileSize;
            playerStartY = Integer.parseInt(settings[4]) * gp.tileSize;
            objectNum = Integer.parseInt(settings[5]);
            entityNum = Integer.parseInt(settings[6]);
            keyNum = Integer.parseInt(settings[7]);

            // creating array for tile locations
            tileMap = new int[maxWorldRow][maxWorldCol];
            readArray(reader, tileMap);

            // creating array for object locations
            reader.readLine(); // skip blank space line
            objMap = new int[maxWorldRow][maxWorldCol];
            objects = new SuperObject[objectNum];
            farmers = new Farmer[entityNum];
            readArray(reader, objMap);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Reads through an array in the mapFile from reader row by row.
     * Saves the values from mapFile into arr.
     * 
     * @param reader BuffereredReader object that contains the data from mapFile
     * @param arr the two-dimensional integer array that will store the data read by this method
     */
    private void readArray(BufferedReader reader, int[][] arr) {
        try {
            for (int row = 0; row < maxWorldRow; row++) {
                String line = reader.readLine();
                String numbers[] = line.split(" ");
                for (int col = 0; col < maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    arr[row][col] = num;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads through the two-dimensional tile array and draws tiles corresponding to their tile number in TileManager.
     * Only draws tiles that are currently visible to the player.
     * 
     * @param graphic2 the main graphics object used to draw sprites on the screen
     */
    protected void drawTiles(Graphics2D graphic2) {
    	
        int col = 0;
        int row = 0;

        while (col < maxWorldCol && row < maxWorldRow) {
            int tileNum = tileMap[row][col];

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                graphic2.drawImage(tileM.getTileImage(tileNum), screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == maxWorldCol) {
                col = 0;
                row++;
            }
        }

        if (showCoords) // To show tile coordinates
        {
            col = 0;
            row = 0;

            while (col < maxWorldCol && row < maxWorldRow)
            {
                int worldX = (col * gp.tileSize) + 8;
                int worldY = (row * gp.tileSize) + 24;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                String toPrint = "(" + col + ", " + row + ")";

                graphic2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphic2.drawString(toPrint, screenX, screenY);

                col++;
                if (col == maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
        }

        if (drawPath) // FOR TESTING PATHFINDING
        {
            graphic2.setColor(new Color(255,0,0,70));

            
            for (int i = 0; i < gp.pathFinder.pathList.size(); i++)
            {
                int worldX = gp.pathFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pathFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                graphic2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }

        if (hitboxTest) // VISUALIZE HITBOXES
        {
            graphic2.setColor(new Color(0,0,255,100));

            for (int i = 0; i < farmers.length; i++)
            {
                if (farmers[i] != null)
                {   
                    int worldX = farmers[i].worldX + farmers[i].hitbox.x;
                    int worldY = farmers[i].worldY + farmers[i].hitbox.y;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    graphic2.fillRect(screenX, screenY, farmers[i].hitbox.width, farmers[i].hitbox.height);
                }

                if (i == 0)
                {
                    graphic2.fillRect(gp.player.screenX + gp.player.hitbox.x, gp.player.screenY + gp.player.hitbox.y, gp.player.hitbox.width, gp.player.hitbox.height);
                }
            }
        }
    }
    
    /**
     * Reads the data from the two-dimensional object map array and creates objects corresponding to their object number in ObjectManager.
     * Sets their spawn location to their location in the object map array.
     * If the object is an egg, a random number generator is used to only spawn the eggs 50% of the time.
     * The location of the exit gate is also saved to a variable. 
     * 
     * @see object.OBJ_Egg
     * @see object.OBJ_Gate
     */
    protected void setObject() {
        int col = 0;
        int row = 0;
        int i = 0;

        while (col < maxWorldCol && row < maxWorldRow) {
            if (objMap[row][col] != 0) {
                if (objMap[row][col] != 99 && objects[i] == null) {
                    objects[i] = ObjectManager.createObject(objMap[row][col]);

                    // eggs on the map will only appear 50% of the time  
                    if (objects[i].name == "Egg") {
                        int n = randGen.nextInt(10);
                        if (n < 5) {
                            objects[i] = null;
                        }
                        else {
                            objects[i].index = i;
                            objects[i].worldX = col * gp.tileSize;
                            objects[i].worldY = row * gp.tileSize;
                        }
                    }

                    // non-egg objects
                    else {
                        objects[i].worldX = col * gp.tileSize;
                        objects[i].worldY = row * gp.tileSize;

                        // saves the location of the exit gate
                        if (objects[i].name == "Gate") {
                            gateIndex = i;
                    }
                    }

                    i++;
                }
            }

            col++;

            if (col == maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Uses the read information about the map from earlier to determine where to place Farmers on the map.
     * Creates a new farmer and sets their starting location.
     */
    protected void setFarmer()
    {
        int col = 0;
        int row = 0;
        int i = 0;

        while (col < maxWorldCol && row < maxWorldRow) {
            if (objMap[row][col] == 99) {
                
                farmers[i] = new Farmer(gp);
                farmers[i].startingX = col * gp.tileSize;
                farmers[i].startingY = row * gp.tileSize;
                farmers[i].worldX = farmers[i].startingX;
                farmers[i].worldY = farmers[i].startingY;

                i++;  
            }

            col++;

            if (col == maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Reads the objects array and draws each object corresponding to their object numbers in ObjectManager.
     * Calls update method in OBJ_Egg every time this method is called.
     * 
     * @param graphic2 the main graphics object used to draw sprites onto the screen
     */
    protected void drawObjects(Graphics2D graphic2) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                objects[i].draw(graphic2, gp);

                // updates timer in egg object
                if (objects[i].name == "Egg") {
                    OBJ_Egg egg = (OBJ_Egg) objects[i];
                    egg.updateEggTimer(gp);
                }
            }
        }
    }

    /**
     * Goes through Farmer ArrayList and draws each individual farm on to the map.
     * 
     * @param graphic2 main graphic used by gamePanel to draw the maps sprites and tiles
     */
    protected void drawFarmers(Graphics2D graphic2)
    {
        for (int i = 0; i < farmers.length; i++)
        {
            if (farmers[i] != null)
            {
                farmers[i].draw(graphic2);
            }
        }
    }
}
