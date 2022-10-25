package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font arial_30, arial_80B; // set font as 'Arial' with size '40'
	BufferedImage keyImage, heartImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font("Arial", Font.PLAIN, 30); // (FONT_NAME, FONT_STYLE, FONT_SIZE)
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
		OBJ_Heart heart = new OBJ_Heart();
		heartImage = heart.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		if (gameFinished == true) {
			
			//Print Ending screen
			
			g2.setFont(arial_80B);
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x;
			int y;
			
			// Complete Message
			text = "Stage Complete!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2; // Print message on center of screen
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			// Time Message
			g2.setFont(arial_30);
			text = "Your time is :" + dFormat.format(playTime) + " SEC";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2; 
			y = gp.screenHeight/2 + (gp.tileSize*3);
			g2.drawString(text, x, y);

			gp.gameThread = null;
		}
		else {
			
			//Points
			g2.setFont(arial_30);
			g2.setColor(Color.WHITE);
			g2.drawString("Pts: " +gp.player.points, 408, 65);

			//Keys
			g2.setFont(arial_30);
			g2.setColor(Color.WHITE);
			g2.drawImage(keyImage, 280, gp.tileSize/2, gp.tileSize, gp.tileSize, null); // set imageSize & coordinate
			g2.drawString("X " +gp.player.hasKey, 328, 65); //print key at x=280 then print the rest at x=280 +48

			//Health
			g2.setFont(arial_30);
			g2.setColor(Color.WHITE);
			g2.drawImage(heartImage, 24 + gp.tileSize*5/4, gp.tileSize/2, gp.tileSize, gp.tileSize, null); // set imageSize & coordinate
			g2.drawImage(heartImage, 24 + gp.tileSize*5/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawImage(heartImage, 24 + gp.tileSize*15/4, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("HP:" , 24, 65); 
			
			// Time
			playTime += (double)1/60;
			g2.drawString("Time: "+ dFormat.format(playTime), gp.tileSize*12, 65);
			
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

}
