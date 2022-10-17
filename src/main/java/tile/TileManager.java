package tile;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import app.GamePanel;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    int levelTileNum[][];

    public TileManager(GamePanel gp)
    {
        this.gp = gp;

        tile = new Tile[10]; // 10 types of tiles. Can change this number
        levelTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/levels/levelTest1.txt");
    }

    public void getTileImage()
    {
        try
        {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/FILE NAME1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/FILE NAME2.png"));

            // Keep copy pasting and adding new tiles for each image you want to load into it

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String fileName)
    {
        try
        {
            InputStream is = getClass().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow)
            {
                String line = reader.readLine();

                while(col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    levelTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }

            reader.close();

        } catch (Exception e){

        }
    }

    public void draw(Graphics2D graphic2) 
    {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            int tileNum = levelTileNum[col][row];

            int x = col * gp.tileSize;
            int y = row * gp.tileSize;
            int screenX = x - gp.player.x + gp.player.screenX;
            int screenY = y - gp.player.y + gp.player.screenY;

            if (x + gp.tileSize > gp.player.x - gp.player.screenX &&
             x - gp.tileSize < gp.player.x + gp.player.screenX &&
             y + gp.tileSize > gp.player.y - gp.player.screenY &&
             y - gp.tileSize < gp.player.y + gp.player.screenY)
              {
                graphic2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
              }

            col++;

            if (col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }   
}
