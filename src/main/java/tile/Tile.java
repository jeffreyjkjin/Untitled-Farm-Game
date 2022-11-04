package tile;

import java.awt.image.BufferedImage;

/** 
 * Tiles make up the entire map and are the core of the game
 * These are what objects sit on and entities move on
 * There will be many different types of tiles, some with collision and some just for decoration
 * image is the current tiles image (grass, fence, water, flowers, etc.)
 * 
 * @author Andrew Hein (ach17)
*/
public class Tile {
    
    BufferedImage image;
    boolean collision = false;
}
