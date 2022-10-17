package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import app.GamePanel;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	//public Rectangle hitbox = new Rectangle(0,0,?,?) 
	//set object hitbox
	public int hitboxDefaultX = 0;
	public int hitboxDefaultY = 0;
	public Rectangle hitbox;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		   int screenX = worldX - gp.player.x + gp.player.screenX;
           int screenY = worldY - gp.player.y + gp.player.screenY;

           if (worldX + gp.tileSize > gp.player.x - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.x + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.y - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.y + gp.player.screenY)
             {
               g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
             }
	}

}
