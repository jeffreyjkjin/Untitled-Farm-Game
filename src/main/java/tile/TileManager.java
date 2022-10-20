package tile;

import java.io.IOException;
import javax.imageio.ImageIO;
import app.GamePanel;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;

    public TileManager(GamePanel gp)
    {
        this.gp = gp;

        tile = new Tile[20]; // 20 types of tiles. Can change this number

        getTileImage();
    }

    public void getTileImage()
    {
        try
        {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/ground/grass.png"));

            // Fences
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/fences/bottomleftfence.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/fences/bottommiddlefence.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/fences/bottomrightfence.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/fences/middleleftfence.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/fences/middlerightfence.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/fences/topleftfence.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/fences/topmiddlefence.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/fences/toprightfence.png"));
            tile[8].collision = true;

            // Trees
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/trees/bottomlefttree1.png"));
            tile[9].collision = true;
         
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/trees/bottomrighttree1.png"));
            tile[10].collision = true;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/trees/toplefttree1.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/trees/toprighttree1.png"));
            tile[12].collision = true;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/trees/bottomlefttree2.png"));
            tile[13].collision = true;
         
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/trees/bottomrighttree2.png"));
            tile[14].collision = true;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/trees/toplefttree2.png"));
            tile[15].collision = true;
            
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/trees/toprighttree2.png"));
            tile[16].collision = true;
            
            // Keep copy pasting and adding new tiles for each image you want to load into it

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}