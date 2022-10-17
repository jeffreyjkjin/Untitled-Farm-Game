package com.example.tile;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import com.example.*;
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
        levelTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

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

            while(col < gp.maxScreenCol && row < gp.maxScreenRow)
            {
                String line = reader.readLine();

                while(col < gp.maxScreenCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    levelTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxScreenCol)
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
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow)
        {
            int tileNum = levelTileNum[col][row];

            graphic2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);

            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol)
            {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }   
}
