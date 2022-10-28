package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Trap extends SuperObject {
	
	public OBJ_Trap() {
		
		name = "Trap";
		try {
			// temporary sprite for trap
			image = ImageIO.read(getClass().getResourceAsStream("/objects/egg.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}