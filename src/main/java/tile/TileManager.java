package tile;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;

import app.GamePanel;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp)
    {
        this.gp = gp;
        
        // Read TileData from files
        InputStream is = getClass().getResourceAsStream("/tiles/tileData.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // Get tilenames & collision info from the file
        String line;
        
        try {
        	while((line = br.readLine()) != null) {
        		fileNames.add(line);
        		collisionStatus.add(br.readLine());
        	}
        	br.close();
        	
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
        // Initialize the tile array based on the file
        tile = new Tile[fileNames.size()];
        getTileImage();
    }

    public void getTileImage()
    {
    	for(int i = 0; i < fileNames.size(); i++) {
    		
    		String fileName;
    		boolean collision;
    		
    		// Get a file name
    		fileName = fileNames.get(i);
    		
    		// Get a collision status
    		if(collisionStatus.get(i).equals("true")) {
    			collision = true;
    		}
    		else {
    			collision = false;
    		}
    		
    		setup(i, fileName, collision);
    	}
    }
    	
    public void setup(int index, String imageName, boolean collision) {
    	
    	try {
    		tile[index] = new Tile();
    		tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
    		tile[index].collision = collision;
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    public BufferedImage getTileImage(int index) {
        return tile[index].image;
    }

    public Boolean checkTileCollision(int index) {
        return tile[index].collision;
    }
}
