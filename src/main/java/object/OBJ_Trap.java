package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import app.GamePanel;
import audio.SoundEffects;
import entity.Farmer;

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
	SoundEffects sound;

	/**
	 * Creates a new trap object and loads its sprite.
	 * Also links sound singleton to this object.
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

		sound = SoundEffects.getInstance();
	}

	/** 
	 * Lowers the player's health upon collision.
	 * Resets player and farmer positions on the map.
	 * Displays message and sound effect to indicate damage has been taken.
	 * 
	 * @param gp GamePanel object that is used to the game 
	 */
	public void update(GamePanel gp) {
		// Lower player health and set player and farmer positions
		gp.player.health--;
		gp.player.spawnPlayer();
		Farmer farmers[] = gp.mapM.getMap().farmers;
		Farmer.respawnFarmers(farmers);
		
		sound.play(3);
		sound.play(7);

		gp.uiM.showMessage("-1 HP");
	}

}
