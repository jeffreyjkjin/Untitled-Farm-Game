package tile;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import app.GamePanel;

/** 
 * This class manages the tiles by setting their images and keeping track of their collision status
 * It also creates the main array of tiles that is used to draw the map and place objects
 * 
 * @author Jeffrey Jin (jjj9)
*/
public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    /**
     * Constructs the tile manager by connecting it to gp, reading in and setting collision status, and gettin tile image
     * 
     * @param gp the main GamePanel used to build the game
     */
    public TileManager(GamePanel gp)
    {
        this.gp = gp;
        
        // Read TileData from files
        InputStream is = getClass().getResourceAsStream("/tiles/tileData.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // Get tilenames & collision info from the file
        String line;
        // Attempt to read in the collision status of each tile
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

    /**
     * Finds the proper images for the tiles and links them to collision status
     */
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
    		// Create the tiles and link them to their image and collision status
    		setup(i, fileName, collision);
    	}
    }
    	
    /**
     * This functions links the imageName and collision status to the indexed tile in the array
     * This tile can now be placed on the map later and CollisionChecker will be able to tell if this tile is solid or not
     * 
     * @param index where the tile is in the tile[] array
     * @param imageName the name of the image file that needs to be linked to this tile
     * @param collision collision status of this tile that also needs to be linked
     */
    public void setup(int index, String imageName, boolean collision) {
    	// Create the tiles and link them to their image and collision status
    	try {
    		tile[index] = new Tile();
    		tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
    		tile[index].collision = collision;
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Returns the indexed tiles image
     * 
     * @param index tiles location in the tile[] array
     * @return tiles image as type BufferedImage
     */
    public BufferedImage getTileImage(int index) {
        return tile[index].image;
    }

    /**
     * Returns the indexed tiles collision status
     * 
     * @param index tiles location in the tile[] array
     * @return tiles collision status as a boolean
     */
    public Boolean checkTileCollision(int index) {
        return tile[index].collision;
    }
}
