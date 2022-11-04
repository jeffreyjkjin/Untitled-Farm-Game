package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import app.GamePanel;

/**
 * Abstract class for game objects that contains the objects properties.
 * Data stored in this object are used by other objects.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Seughwan Kim
 * @see object.ObjectManager
 */
public abstract class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle hitbox = new Rectangle(0,0,48,48); 
	//set object hitbox
	public int hitboxDefaultX = 0;
	public int hitboxDefaultY = 0;
	public int index;
	
	/**
	 * Determines the location of the object on the map.
	 * Checks if the player is able to see the object on their screen, then draws the object if they are able to.
	 * 
	 * @param g2 the main graphics object used to draw object sprites onto the screen
	 * @param gp GramePanel object that is used to run the game
	 */
	public void draw(Graphics2D g2, GamePanel gp) 
	{
		// Determine objects location on the screen
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		// If the object is on the players screen, draw it
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
		{
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

	/**
	 * Method is not used in this class but can be implemented by different objects for different features.
	 * 
	 * @param gp GamePanel object that is used to run the game
	 */
	public void update(GamePanel gp) {
	}

}
