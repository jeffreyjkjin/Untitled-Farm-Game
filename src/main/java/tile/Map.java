package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.GamePanel;

public class Map {
    GamePanel gp;
    public int[][] map;

    String levelName;
    public int maxWorldCol, maxWorldRow, playerStartX, playerStartY;

    public Map(GamePanel gp, String mapFile) {
        this.gp = gp;

        loadMap(mapFile);
    }

    public void loadMap(String mapFile) {
        try {
            InputStream input = getClass().getResourceAsStream(mapFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    
            String firstLine = reader.readLine();
            String settings[] = firstLine.split(",");
            
            // first line of mapFile contains level data and should be formatted as shown below:
            // levelName,maxWorldCol,maxWorldRow,playerStartX,playerStartY
            levelName = settings[0];
            maxWorldCol = Integer.parseInt(settings[1]);
            maxWorldRow = Integer.parseInt(settings[2]);
            playerStartX = Integer.parseInt(settings[3]) * gp.tileSize;
            playerStartY = Integer.parseInt(settings[4]) * gp.tileSize;

            map = new int[maxWorldCol][maxWorldRow];

            int col = 0;
            int row = 0;
            while(col < maxWorldCol && row < maxWorldRow) {
                String line = reader.readLine();
    
                while(col < maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    map[col][row] = num;
                    col++;
                }
    
                if (col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
    
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphic2) {
        int col = 0;
        int row = 0;

        while (col < maxWorldCol && row < maxWorldRow) {
            int tileNum = map[col][row];

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
}
