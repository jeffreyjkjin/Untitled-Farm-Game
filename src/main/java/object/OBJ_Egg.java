package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.Random;

import app.GamePanel;
import app.StateManager.gameState;
import audio.SoundEffects;

/**
 * Egg object restores the player's health by one if their health is not full or gives the player 100 points.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see object.ObjectManager
 * @see object.SuperObject
*/
public class OBJ_Egg extends SuperObject {
	SoundEffects sound;

	Random randGen = new Random();
	double expireTime;

	/**
	 * Creates a new egg object and loads its sprite.
	 * Links Sound singleton to this class;
	 * Initializes an expireTime and randomly generates a time between 20-50 which is how long the egg will last before despawning.
	 */
	public OBJ_Egg() {
		name = "Egg";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/egg.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;

		sound = SoundEffects.getInstance();

		expireTime = (double) (20 + randGen.nextInt(30));
	}

	/**
	 * Updates expireTime every second. 
	 * After the timer hits zero, the egg will despawn from the map and play a sound to indicate that.
	 * 
	 * @param gp GamePanel object that is used to run the game
	 */
	public void update(GamePanel gp) {
		
		if (gp.stateM.getCurrentState() == gameState.PLAY) {
			expireTime -= (double) 1/60;
			if (expireTime < 0) {
				gp.mapM.getMap().objects[index] = null;
				sound.play(6);
			}
		}
	}

}
