package object;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Heart extends SuperObject{
    BufferedImage heart, emptyHeart;

    public OBJ_Heart() {
		
		name = "Heart";
		try {
			heart = ImageIO.read(getClass().getResourceAsStream("/objects/heart.png"));
			emptyHeart = ImageIO.read(getClass().getResourceAsStream("/objects/emptyheart.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		image = heart;
	}

	public void emptyHeart() {
		image = emptyHeart;
	}

	public void fullHeart() {
		image = heart;
	}
}
