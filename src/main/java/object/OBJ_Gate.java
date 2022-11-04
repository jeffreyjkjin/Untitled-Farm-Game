package object;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import app.GamePanel;

/**
 * Gate object only lets the player advance to the next level after they pick up the required number of keys for that level.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see object.ObjectManager
 * @see object.SuperObject
 */
public class OBJ_Gate extends SuperObject {
	BufferedImage closedGate, openedGate;
	Boolean open = false;

	/**
	 * Creates a new gate object and loads its sprites.
	 * Default sprite is set to closed.
	 */
	public OBJ_Gate() {
		name = "Gate";
		try {
			closedGate = ImageIO.read(getClass().getResourceAsStream("/objects/closedgate.png"));
			openedGate = ImageIO.read(getClass().getResourceAsStream("/objects/openedgate.png"));
		}
        catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		image = closedGate;
	}

	/**
	 * Opens the gate when player has the required number of keys.
	 * Then it teleports the player to the next stage after colliding with it.
	 */
	public void update(GamePanel gp) {
		if (open == false) {
			open = true;
			image = openedGate;
		}
		else {
			gp.mapM.nextMap();
		}
	}

}
