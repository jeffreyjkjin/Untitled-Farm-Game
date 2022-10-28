package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.Random;

import app.GamePanel;

public class OBJ_Egg extends SuperObject {
	
	Random randGen = new Random();
	double expireTime;

	public OBJ_Egg() {
		
		name = "Egg";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/egg.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;

		expireTime = (double) (20 + randGen.nextInt(30));
	}

	public void update(GamePanel gp) {
		expireTime -= (double) 1/60;
		if (expireTime < 0) {
			gp.mapM.getMap().objects[index] = null;
		}
	}

}
