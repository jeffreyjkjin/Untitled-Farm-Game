package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Heart extends SuperObject{
    
    public OBJ_Heart() {
		
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
