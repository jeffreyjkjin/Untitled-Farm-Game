package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import app.GamePanel.gameState;
import object.OBJ_Heart;

public class UI {
	GamePanel gp;
	BufferedImage keyImage, heartImage;
	Font pressStart2P;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public int commandNum = 0;
	public boolean fullScreen;
	
	OBJ_Heart playerHP[] = new OBJ_Heart[3];

	double playTime;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		playerHP[0] = new OBJ_Heart();
		playerHP[1] = new OBJ_Heart();
		playerHP[2] = new OBJ_Heart();

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

		fullScreen = gp.settings.getFullScreen();		
	}

	public String drawTimer() {
		String timer = "";

		int minutes = (int) playTime / 60;
		int seconds = (int) playTime % 60;

		if (minutes < 10) {
			timer += "0" + minutes;
		}
		else {
			timer += minutes;
		}

		timer += ":";

		if (seconds < 10) {
			timer += "0" + seconds;
		}
		else {
			timer += seconds;
		}

		return timer;
	}

	public void resetTimer() {
		playTime = 0;
	}

	public void drawPlayerLife(Graphics2D g2) {
		for(int i = 3; i >= 1; i--) {
			if (gp.player.health < i) {
				playerHP[i-1].emptyHeart();
			}
			else {
				playerHP[i-1].fullHeart();
			}
			g2.drawImage(playerHP[i-1].image, (46 * (gp.scale - 2)) + (i-1)*gp.tileSize , 32, gp.tileSize, gp.tileSize, null);
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
			case SETTINGS:
				drawSettingsScreen(g2);
				break;
			case CREDITS:
				drawCreditsScreen(g2);
				break;
		}
	}

	private void drawPauseScreen(Graphics2D g2) {
		//Making the screen darker
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 80F));
		g2.setColor(Color.white);

		String text = "PAUSED";
		g2.drawString(text, getHorizontalCenter(text, g2, gp.screenWidth), gp.tileSize * 4);

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		String resume = "RESUME";
		String menu = "MAIN MENU";
		String settings = "SETTINGS";
		String quit = "QUIT";

		// resume
		g2.drawString(resume, getHorizontalCenter(resume, g2, gp.screenWidth), gp.tileSize * 8);
		if(commandNum == 0) {
			g2.drawString(">",getHorizontalCenter(resume, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 8);
		}

		// Settings
		g2.drawString(settings, getHorizontalCenter(settings, g2, gp.screenWidth), gp.tileSize * 9);
		if(commandNum == 1) {
			g2.drawString(">",getHorizontalCenter(settings, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Main Menu
		g2.drawString(menu, getHorizontalCenter(menu, g2, gp.screenWidth), gp.tileSize * 10);
		if(commandNum == 2) {
			g2.drawString(">",getHorizontalCenter(menu, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}
		
		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(commandNum == 3) {
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}	
		
	}

	private void drawPlayScreen(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20));

		String health = "HEALTH";
		String level = "LEVEL";
		String score = "SCORE";
		String time = "TIME";

		// Health
		g2.drawString(health, getHorizontalCenter(health, g2, gp.screenWidth/4), 32);
		drawPlayerLife(g2);

		// Level Name
		g2.drawString(level, getHorizontalCenter(level, g2, gp.screenWidth/4) + (gp.screenWidth * 1/4), 32);

		String mapName = gp.mapM.getMap().levelName;
		g2.drawString(mapName, getHorizontalCenter(mapName, g2, gp.screenWidth/4) + (gp.screenWidth * 1/4), 64);
		
		// Score
		g2.drawString(score, getHorizontalCenter(score, g2, gp.screenWidth/4) + (gp.screenWidth * 2/4), 32);
		
		String playerScore = String.valueOf(gp.player.score);
		g2.drawString(playerScore, getHorizontalCenter(playerScore, g2, gp.screenWidth/4) + (gp.screenWidth * 2/4), 64);

		// Time
		g2.drawString(time, getHorizontalCenter(time, g2, gp.screenWidth/4) + (gp.screenWidth * 3/4), 32);
		if (gp.currState == gameState.PLAY) {
			playTime += (double)1/60;
		}
		g2.drawString(drawTimer(), getHorizontalCenter(drawTimer(), g2, gp.screenWidth/4) + (gp.screenWidth * 3/4), 64);

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
		// Game Over
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 60));
		g2.setColor(Color.white);
		
		String gameOver = "GAME OVER!";

		g2.drawString(gameOver, getHorizontalCenter(gameOver, g2, gp.screenWidth), gp.tileSize * 3);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";
		String savedHighScore = String.valueOf(gp.settings.getHighScore());

		g2.drawString(highScore, getHorizontalCenter(highScore, g2, gp.screenWidth/2), gp.tileSize * 5);
		g2.drawString(savedHighScore, getHorizontalCenter(savedHighScore, g2, gp.screenWidth/2), (int)(gp.tileSize * 5.5));

		// Your Score 
		String yourScore = "YOUR SCORE";
		String playerScore = String.valueOf(gp.player.score);

		g2.drawString(yourScore, getHorizontalCenter(yourScore, g2, gp.screenWidth/2) + gp.screenWidth/2, gp.tileSize * 5);
		g2.drawString(playerScore, getHorizontalCenter(playerScore, g2, gp.screenWidth/2) + gp.screenWidth/2, (int)(gp.tileSize * 5.5));

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		String retry = "RETRY";
		String menu = "MAIN MENU";
		String quit = "QUIT";

		// Retry
		g2.drawString(retry, getHorizontalCenter(retry, g2, gp.screenWidth), gp.tileSize * 9);
		if(commandNum == 0) {
			g2.drawString(">",getHorizontalCenter(retry, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Main Menu
		g2.drawString(menu, getHorizontalCenter(menu, g2, gp.screenWidth), gp.tileSize * 10);
		if(commandNum == 1) {
			g2.drawString(">",getHorizontalCenter(menu, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}

		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(commandNum == 2) {
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}
	}

	private void drawWinScreen(Graphics2D g2) {
		// Game Over
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 60));
		g2.setColor(Color.white);
		
		String gameOver = "YOU'VE ESCAPED!";

		g2.drawString(gameOver, getHorizontalCenter(gameOver, g2, gp.screenWidth), gp.tileSize * 3);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";
		String savedHighScore = String.valueOf(gp.settings.getHighScore());

		g2.drawString(highScore, getHorizontalCenter(highScore, g2, gp.screenWidth/2), gp.tileSize * 5);
		g2.drawString(savedHighScore, getHorizontalCenter(savedHighScore, g2, gp.screenWidth/2), (int)(gp.tileSize * 5.5));

		// Your Score
		String yourScore = "YOUR SCORE";
		String playerScore = String.valueOf(gp.player.score);
		
		g2.drawString(yourScore, getHorizontalCenter(yourScore, g2, gp.screenWidth/2) + gp.screenWidth/2, gp.tileSize * 5);
		g2.drawString(playerScore, getHorizontalCenter(playerScore, g2, gp.screenWidth/2) + gp.screenWidth/2, (int)(gp.tileSize * 5.5));

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		String retry = "RETRY";
		String menu = "MAIN MENU";
		String quit = "QUIT";

		// Retry
		g2.drawString(retry, getHorizontalCenter(retry, g2, gp.screenWidth), gp.tileSize * 9);
		if(commandNum == 0) {
			g2.drawString(">",getHorizontalCenter(retry, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Main Menu
		g2.drawString(menu, getHorizontalCenter(menu, g2, gp.screenWidth), gp.tileSize * 10);
		if(commandNum == 1) {
			g2.drawString(">",getHorizontalCenter(menu, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}

		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(commandNum == 2) {
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}
	}

	private void drawTitleScreen(Graphics2D g2){
		// Title
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 60));
		g2.setColor(Color.white);

		String title1 = "UNTITLED";
		String title2 = "FARM GAME";

		g2.drawString(title1, getHorizontalCenter(title1, g2, gp.screenWidth), gp.tileSize * 2);
		g2.drawString(title2, getHorizontalCenter(title2, g2, gp.screenWidth), gp.tileSize * 4);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";
		String savedHighScore = String.valueOf(gp.settings.getHighScore());

		g2.drawString(highScore, getHorizontalCenter(highScore, g2, gp.screenWidth/4), gp.tileSize * 5);
		g2.drawString(savedHighScore, getHorizontalCenter(savedHighScore, g2, gp.screenWidth/4), (int)(gp.tileSize * 5.5));

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		String play = "PLAY";
		String settings = "SETTINGS";
		String credits = "CREDITS";
		String quit = "QUIT";

		// Play
		g2.drawString(play, getHorizontalCenter(play, g2, gp.screenWidth), gp.tileSize * 8);
		if(commandNum == 0){
			g2.drawString(">",getHorizontalCenter(play, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 8); //drawing > before the button
		}

		// Settings
		g2.drawString(settings, getHorizontalCenter(settings, g2, gp.screenWidth), gp.tileSize * 9);
		if(commandNum == 1){
			g2.drawString(">",getHorizontalCenter(settings, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Credits
		g2.drawString(credits, getHorizontalCenter(credits, g2, gp.screenWidth), gp.tileSize * 10);
		if(commandNum == 2){
			g2.drawString(">",getHorizontalCenter(credits, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}

		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(commandNum == 3){
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}
	}

	public void drawSettingsScreen(Graphics2D g2) {
		// Settings
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 60));
		g2.setColor(Color.white);
		
		String settings = "SETTINGS";

		g2.drawString(settings, getHorizontalCenter(settings, g2, gp.screenWidth), gp.tileSize * 3);

		String music = "MUSIC VOLUME";
		String sound = "SOUND VOLUME";
		String fullscreen = "FULL SCREEN";
		String resetScore = "RESET HIGH SCORE";
		String back = "RETURN";

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		// Music Volume
		g2.drawString(music, 2*gp.tileSize, gp.tileSize * 5);

		g2.drawRect(gp.tileSize*13, gp.tileSize*4, 240, 48); //draw a rect
		int volumeWidth = 48* gp.music.getVolumeScale();
		g2.fillRect(gp.tileSize*13, gp.tileSize*4, volumeWidth, 48); //fill part of the rect

		if(commandNum == 0){
			g2.drawString(">", gp.tileSize, gp.tileSize * 5); //drawing > before the button
		}

		// Sound Effect Volume
		g2.drawString(sound, 2*gp.tileSize, gp.tileSize * 13/2);

		g2.drawRect(gp.tileSize*13, gp.tileSize*11/2, 240, 48); //draw a rect
		int volumeWidth2 = 48* gp.sound.getVolumeScale();
		g2.fillRect(gp.tileSize*13, gp.tileSize*11/2, volumeWidth2, 48); //fill part of the rect

		if(commandNum == 1){
			g2.drawString(">", gp.tileSize, gp.tileSize * 13/2); //drawing > before the button
		}

		// Fullscreen
		g2.drawString(fullscreen, 2*gp.tileSize, gp.tileSize * 16/2);
		if(commandNum == 2){
			g2.drawString(">", gp.tileSize, gp.tileSize * 16/2); //drawing > before the button
		}

		if(!fullScreen){
			g2.drawString("OFF",gp.tileSize*12, gp.tileSize*16/2);
		}
		if(fullScreen){
			g2.drawString("ON",gp.tileSize*12, gp.tileSize*16/2);
		}

		// Reset High Score
		g2.drawString(resetScore, 2*gp.tileSize, gp.tileSize * 19/2);
		if(commandNum == 3){
			g2.drawString(">", gp.tileSize, gp.tileSize * 19/2); //drawing > before the button
		}

		// Return
		g2.drawString(back, 2*gp.tileSize, gp.tileSize * 22/2);
		if(commandNum == 4){
			g2.drawString(">", gp.tileSize, gp.tileSize * 22/2); //drawing > before the button
		}
	}
	
	public void drawCreditsScreen(Graphics2D g2) {
		// Credits
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 90));
		g2.setColor(Color.white);
		
		String credits = "CREDITS";
		g2.drawString(credits, getHorizontalCenter(credits, g2, gp.screenWidth), gp.tileSize * 3);

		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 50));
		String name1 = "ANDREW HEIN";
		String name2 = "JEFFREY JIN";
		String name3 = "HWAN KIM";
		String name4 = "LONG NGUYEN";

		g2.drawString(name1, getHorizontalCenter(name1, g2, gp.screenWidth), gp.tileSize * 11/2);
		g2.drawString(name2, getHorizontalCenter(name2, g2, gp.screenWidth), gp.tileSize * 14/2);
		g2.drawString(name3, getHorizontalCenter(name3, g2, gp.screenWidth), gp.tileSize * 17/2);
		g2.drawString(name4, getHorizontalCenter(name4, g2, gp.screenWidth), gp.tileSize * 20/2);

	}

	//method for centering text
	public int getHorizontalCenter(String text, Graphics2D g2, int screenWidth) {
		int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = screenWidth/2 - textLength/2; // Print message on center of screen
		return x;
	}
}