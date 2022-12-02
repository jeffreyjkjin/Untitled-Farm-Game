package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_StoneGate extends OBJ_Gate {
    /**
	 * Creates a new gate object and loads its sprites.
	 * Default sprite is set to closed.
	 */
	public OBJ_StoneGate() {
		name = "Gate";
		try {
			closedGate = ImageIO.read(getClass().getResourceAsStream("/objects/closedstonegate.png"));
			openedGate = ImageIO.read(getClass().getResourceAsStream("/objects/openedstonegate.png"));
		}
        catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		image = closedGate;
	}
}
