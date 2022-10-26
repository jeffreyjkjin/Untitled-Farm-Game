package object;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import app.GamePanel;

public class OBJ_Gate extends SuperObject {
	BufferedImage closedGate, openedGate;
	Boolean open = false;

	public OBJ_Gate() {
		name = "Gate";
		try {
			closedGate = ImageIO.read(getClass().getResourceAsStream("/objects/closedgate.png"));
			openedGate = ImageIO.read(getClass().getResourceAsStream("/objects/closedgate.png"));
		}
        catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
		image = closedGate;
	}

	public void update(GamePanel gp) {
		if (open == false) {
			image = openedGate;
		}
		else {
			// TODO: load next level
		}
	}

}
