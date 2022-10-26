package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import entity.Player;
import object.OBJ_Heart;
import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font arial_30, arial_80B; // set font as 'Arial' with size '40'
	BufferedImage keyImage, heartImage;
	Font pressStart2P;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font("Arial", Font.PLAIN, 30); // (FONT_NAME, FONT_STYLE, FONT_SIZE)
		arial_80B = new Font("Arial", Font.BOLD, 80);
		// OBJ_Key key = new OBJ_Key();
		// keyImage = key.image;
		OBJ_Heart heart = new OBJ_Heart();
		heartImage = heart.image;

		try {
			InputStream input = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
			pressStart2P = Font.createFont(Font.TRUETYPE_FONT, input);
		}
		catch(FontFormatException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void drawPlayerLife(Graphics2D g2) {
		for(int i =0; i < gp.player.curLife; i++) {
			g2.drawImage(heartImage, 24 + i*gp.tileSize , 32, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		switch(gp.currState) {
			case PLAY:
				drawPlayScreen(g2);
				break;
			case PAUSE:
				break;
			case WIN:
				drawWinScreen(g2);
				break;
			case LOSE:
				break;
		}
	}

	private void drawPlayScreen(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20));
		// g2.drawImage(eggImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null); // set imageSize & coordinate
		// g2.drawString("X " +gp.player.hasEgg, 74, 65); // 74 because eggImage obtain 24+48=72 size 
		
		// TODO: need to figure out how to center these UI elements
		// Health
		drawPlayerLife(g2);

		// Level Name
		g2.drawString("LEVEL", 5 * gp.tileSize, 32);
		// g2.drawString(gp.map.levelName, 5 * gp.tileSize, 64);
		
		// Time
		g2.drawString("TIME", 13 * gp.tileSize, 32);
		playTime += (double)1/60;
		g2.drawString(dFormat.format(playTime), 13 * gp.tileSize, 64);
		
		// Score
		g2.drawString("SCORE", 9 * gp.tileSize, 32);
		g2.drawString(""+gp.player.score, 9 * gp.tileSize, 64);

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

	private void drawWinScreen(Graphics2D g2) {
		g2.setFont(pressStart2P);
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
		text = "Your time is :" + dFormat.format(playTime) + " SEC";
		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWidth/2 - textLength/2; 
		y = gp.screenHeight/2 + (gp.tileSize*3);
		g2.drawString(text, x, y);

		gp.gameThread = null;
	}

}