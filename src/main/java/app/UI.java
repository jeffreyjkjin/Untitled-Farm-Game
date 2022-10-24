package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Egg;

public class UI {
	
	GamePanel gp;
	Font arial_40; // set font as 'Arial' with size '40'
	BufferedImage eggImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40); // (FONT_NAME, FONT_STYLE, FONT_SIZE)
		OBJ_Egg egg = new OBJ_Egg();
		eggImage = egg.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);
		g2.drawImage(eggImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null); // set imageSize & coordinate
		g2.drawString("X " +gp.player.hasEgg, 74, 65); // 74 because eggImage obtain 24+48=72 size 
		
		// Message
		if (messageOn == true) {
			
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.player.screenX-20, gp.player.screenY - 24);
			
			messageCounter++;
			
			if (messageCounter > 120) { // message will only exist for 120 FRAMES (== 2 sec.) 
				messageCounter = 0;
				messageOn = false;
			}
		}
		
	}

}
