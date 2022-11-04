package object;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Key object that the player must collect in order to advance to the next level.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Seunghwan Kim
 * @see object.ObjectManager
 * @see object.SuperObject
 */
public class OBJ_Key extends SuperObject {
	
	/**
	 * Creates a new key object and loads it sprite.
	 */
	public OBJ_Key() {
		
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
