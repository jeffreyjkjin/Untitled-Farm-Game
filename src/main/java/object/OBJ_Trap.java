package object;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Trap object that damages the player upon collision.
 * Reduces the player's health points by one.
 * Disappears after use. 
 * 
 * @author Jeffrey Jin (jjj9)
 * @see object.ObjectManager
 * @see object.SuperObject
*/
public class OBJ_Trap extends SuperObject {
	
	/**
	 * Creates a new trap object and loads its sprite.
	 */
	public OBJ_Trap() {
		
		name = "Trap";
		try {
			// temporary sprite for trap
			image = ImageIO.read(getClass().getResourceAsStream("/objects/trap.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
