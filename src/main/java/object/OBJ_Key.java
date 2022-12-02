package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import app.GamePanel;
import audio.SoundEffects;

/**
 * Key object that the player must collect in order to advance to the next level.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Seunghwan Kim
 * @see object.ObjectManager
 * @see object.SuperObject
 */
public class OBJ_Key extends SuperObject {
	SoundEffects sound;	
	
	/**
	 * Creates a new key object and loads it sprite.
	 * Also links sound singleton to this object.
	 */
	public OBJ_Key() {
		
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;

		sound = SoundEffects.getInstance();
	}

	public void update(GamePanel gp) {
		gp.player.keyCount++;
		if (gp.player.keyCount == gp.mapM.getMap().keyNum) {
			gp.mapM.getObject(gp.mapM.getMap().gateIndex).update(gp);
			sound.play(8);
			gp.uiM.showMessage("GATE OPENED");
		}
		else {
			int keysLeft = gp.mapM.getMap().keyNum - gp.player.keyCount;
			if (keysLeft != 1) {
				gp.uiM.showMessage("NEED " + keysLeft + " MORE KEYS");
			}
			else {
				gp.uiM.showMessage("NEED " + keysLeft + " MORE KEY");
			}

		}
		sound.play(2);
	}

}
