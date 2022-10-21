package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Egg extends SuperObject {
	
	public OBJ_Egg() {
		
		name = "Egg";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/egg.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
