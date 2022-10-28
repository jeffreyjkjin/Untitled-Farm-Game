package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;

public class UI {
	public enum menu {
		PLAY,
		SETTINGS,
		CREDITS,
		QUIT
	}

	menu menuSelect = menu.PLAY;

	GamePanel gp;
	Font arial_30, arial_80B; // set font as 'Arial' with size '40'
	BufferedImage keyImage, heartImage;
	Font pressStart2P;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	int commandNum = 0;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		OBJ_Heart heart = new OBJ_Heart();
		heartImage = heart.image;
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;

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
			case PAUSE:
				drawPauseScreen(g2);
			case PLAY:
				drawPlayScreen(g2);
				break;
			case WIN:
				drawWinScreen(g2);
				break;
			case LOSE:
				drawLoseScreen(g2);
				break;
			case TITLE:
				drawTitleScreen(g2);
				break;
		}
	}

	private void drawPauseScreen(Graphics2D g2) {
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 80F));
		g2.setColor(Color.white);

		String text = "PAUSED";
		g2.drawString(text, getXforCenter(text, g2), gp.tileSize * 6);
	}

	private void drawPlayScreen(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20));

		// Keys
		g2.drawImage(keyImage, 24, 88, gp.tileSize, gp.tileSize, null); // set imageSize & coordinate
		g2.drawString("X " +gp.player.keyCount, 72, 124); // 72 because eggImage obtain 24+48 = 72 size and 136 = 88 + 36. Draw string draw it differently from drawimage
		
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

	private void drawLoseScreen(Graphics2D g2) {
	}

	private void drawWinScreen(Graphics2D g2) {
		g2.setFont(pressStart2P);
		g2.setColor(Color.white);
		
		String text;
		// int textLength;
		int x;
		int y;
		
		// Complete Message
		text = "Stage Complete!";
		x = getXforCenter(text, g2);
		y = gp.screenHeight/2 - (gp.tileSize*3);
		g2.drawString(text, x, y);
		
		// Time Message
		text = "Your time is :" + dFormat.format(playTime) + " SEC";
		x = getXforCenter(text, g2);
		y = gp.screenHeight/2 + (gp.tileSize*3);
		g2.drawString(text, x, y);

		gp.gameThread = null;
	}

	private void drawTitleScreen(Graphics2D g2){
		
		// Title
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 60));
		g2.setColor(Color.white);

		String title1 = "UNTITLED";
		String title2 = "FARM GAME";

		g2.drawString(title1, getXforCenter(title1, g2), gp.tileSize * 2);
		g2.drawString(title2, getXforCenter(title2, g2), gp.tileSize * 4);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";

		// TODO: save high scores to a file and show on menu screen

		g2.drawString(highScore, gp.tileSize, gp.tileSize * 5);

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		String play = "PLAY";
		String settings = "SETTINGS";
		String credits = "CREDITS";
		String quit = "QUIT";

		g2.drawString(play, getXforCenter(play, g2), gp.tileSize * 8);
		if(commandNum == 0){
			g2.drawString(">",getXforCenter(play, g2) - gp.tileSize, gp.tileSize * 8); //drawing > before the button
		}
		g2.drawString(settings, getXforCenter(settings, g2), gp.tileSize * 9);
		if(commandNum == 1){
			g2.drawString(">",getXforCenter(settings, g2) - gp.tileSize, gp.tileSize * 9);
		}
		g2.drawString(credits, getXforCenter(credits, g2), gp.tileSize * 10);
		if(commandNum == 2){
			g2.drawString(">",getXforCenter(credits, g2) - gp.tileSize, gp.tileSize * 10);
		}
		g2.drawString(quit, getXforCenter(quit, g2), gp.tileSize * 11);
		if(commandNum == 3){
			g2.drawString(">",getXforCenter(quit, g2) - gp.tileSize, gp.tileSize * 11);
		}
	}

	//method for centering text
	public int getXforCenter(String text, Graphics2D g2){
		int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - textLength/2; // Print message on center of screen
		return x;
	}
}