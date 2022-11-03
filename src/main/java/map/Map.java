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
import object.SuperObject;
import object.ObjectManager;

public class Map {
    GamePanel gp;
    Random randGen = new Random();

    public int[][] tileMap, objMap;
    public SuperObject objects[];
    public Farmer farmers[];

    public String levelName;
    public int maxWorldCol, maxWorldRow, playerStartX, playerStartY, objectNum, entityNum, keyNum;
    public int gateIndex;

    boolean drawPath = false; // FOR TESTING PATHFINDING
    boolean hitboxTest = false; // FOR VISUALIZING HITBOXES
    boolean showCoords = false;; // FOR SEEING TILE COORDS

    public Map(GamePanel gp, String mapFile) {
        this.gp = gp;

        loadMap(mapFile);
    }

    public void loadMap(String mapFile) {
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

    public void drawTiles(Graphics2D graphic2) {
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
                graphic2.drawImage(gp.tileM.getTileImage(tileNum), screenX, screenY, gp.tileSize, gp.tileSize, null);
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
    
    public void setObject() {
        int col = 0;
        int row = 0;
        int i = 0;

        while (col < maxWorldCol && row < maxWorldRow) {
            if (objMap[row][col] != 0) {
                if (objects[i] == null && objMap[row][col] != 99) {
                    objects[i] = ObjectManager.createObject(objMap[row][col]);
                    if (objects[i].name == "Egg") {
                        int n = randGen.nextInt(10);
                        if (n <= 5) {
                            objects[i] = null;
                        }
                        else {
                            objects[i].index = i;
                            objects[i].worldX = col * gp.tileSize;
                            objects[i].worldY = row * gp.tileSize;
                        }
                    }
                    else {
                        objects[i].worldX = col * gp.tileSize;
                        objects[i].worldY = row * gp.tileSize;
                        // find exit gate
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

    public void setFarmer() // Same idea as setObject but places a farmer instead with code 19 in map txt file
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

    public void drawObjects(Graphics2D graphic2) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                objects[i].draw(graphic2, gp);
                if (objects[i].name == "Egg") {
                    objects[i].update(gp);
                }
            }
        }
    }

    public void drawFarmers(Graphics2D graphic2)
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
