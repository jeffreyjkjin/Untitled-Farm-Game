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
	 * Initializes an expireTime and randomly generates a time between 30-90 which is how long the egg will last before despawning.
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

		expireTime = (double) (30 + randGen.nextInt(60));
	}

	/**
	 * If player's health is above 3, update their score by 100.
	 * Else, increase their health by 1 health point.
	 * 
	 * @param gp GamePanel object that is used to run the game
	 */
	public void update(GamePanel gp) {
		sound.play(2);
		if (gp.player.health >= 3) {
			gp.player.score += 100;
			gp.uiM.showMessage("+100 POINTS");
		}
		else {
			gp.player.health++;
			gp.uiM.showMessage("+1 HP");
		}
	}

	/**
	 * Updates expireTime every second.
	 * After the timer hits zero, the egg will despawn from the map and play a sound to indicate that.
	 * 
	 * @param gp GamePanel object that is used to the game 
	 */
	public void updateEggTimer(GamePanel gp) {
		if (gp.stateM.getCurrentState() == gameState.PLAY) {
			expireTime -= (double) 1/60;
			if (expireTime < 0) {
				gp.mapM.getMap().objects[index] = null;
				sound.play(6);
			}
		}
	}

}
