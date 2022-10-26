package app;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import object.SuperObject;
import object.ObjectManager;

public class Map {
    GamePanel gp;

    public int[][] tileMap, objMap;
    public SuperObject objects[];

    String levelName;
    public int maxWorldCol, maxWorldRow, playerStartX, playerStartY, objectNum, keyNum;

    public Map(GamePanel gp, String mapFile) {
        this.gp = gp;

        loadMap(mapFile);
    }

    public void loadMap(String mapFile) {
        try {
            // format of mapFile:
            // first line of mapFile contains level data and should be formatted as shown below:
            // levelName,maxWorldCol,maxWorldRow,playerStartX,playerStartY,objectNum,keyNum
            // below this header, there is a maxWorldCol x maxWorldRow array with numbers that
            // correspond with tiles in TileManager
            // below the array is a line break
            // below the line break is another maxWorldCol x maxWorldRow array with numbers that
            // correspond with objects in ObjectManager

            InputStream input = getClass().getResourceAsStream(mapFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            String firstLine = reader.readLine();
            String settings[] = firstLine.split(",");

            levelName = settings[0];
            maxWorldCol = Integer.parseInt(settings[1]);
            maxWorldRow = Integer.parseInt(settings[2]);
            playerStartX = Integer.parseInt(settings[3]) * gp.tileSize;
            playerStartY = Integer.parseInt(settings[4]) * gp.tileSize;
            objectNum = Integer.parseInt(settings[5]);
            keyNum = Integer.parseInt(settings[6]);

            // creating array for tile locations
            tileMap = new int[maxWorldCol][maxWorldRow];
            readArray(reader, tileMap);

            // creating array for object locations
            reader.readLine(); // skip blank space line
            objMap = new int[maxWorldCol][maxWorldRow];
            objects = new SuperObject[objectNum];
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
                    arr[col][row] = num;
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
            int tileNum = tileMap[col][row];

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                graphic2.drawImage(gp.tileM.tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }
    
    public void setObject() {
        int col = 0;
        int row = 0;
        int i = 0;

        while (col < maxWorldCol && row < maxWorldRow) {
            if (objMap[col][row] != 0) {
                if (objects[i] == null) {
                    objects[i] = ObjectManager.createObject(objMap[col][row]);
                    objects[i].worldX = col * gp.tileSize;
                    objects[i].worldY = row * gp.tileSize;

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

    public void drawObjects(Graphics2D graphic2) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                objects[i].draw(graphic2, gp);
            }
        }
    }
}
