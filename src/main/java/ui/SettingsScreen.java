package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import app.GamePanel;
import audio.Music;
import audio.SoundEffects;

/**
 * Used to draw the settings screen.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 * @see ui.UIManager
 */
public class SettingsScreen extends UI {
	Music music;
	SoundEffects sound;

	BufferedImage bgImage1, bgImage2, bgImage3, bgImage4, currentBGImage;
	int bgCount = 0;

	/**
	 * Calls the UI constructor.
	 * Sets the total number of options to four.
	 * Links Music and SoundEffects singletons to this class.
	 * Also loads background images.
	 * 
	 * @param gp GamePanel object that is used to run the game
	 */
	protected SettingsScreen(GamePanel gp) {
        super(gp);

		music = Music.getInstance();
		sound = SoundEffects.getInstance();

		totalOptions = 4;

		try {
			bgImage1 = ImageIO.read(getClass().getResourceAsStream("/settingsbg/1.png"));
			bgImage2 = ImageIO.read(getClass().getResourceAsStream("/settingsbg/2.png"));
			bgImage3 = ImageIO.read(getClass().getResourceAsStream("/settingsbg/3.png"));
			bgImage4 = ImageIO.read(getClass().getResourceAsStream("/settingsbg/4.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		currentBGImage = bgImage1;
    }
    
    /**
	 * Draws the settings menu for the player.
	 * Also animates background images.
	 * Menu contains options to change the music and sound effect volumes, toggle fullscreen mode, resetting the highest score and returning to the main menu.
	 * A selector icon is used to show the user what they have currently selected.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI to the screen
	 */
	public void draw(Graphics2D g2) {
		// Background image
		g2.drawImage(currentBGImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		// Change background every 30 frames or 1/2 seconds
		switch(bgCount) {
			case 30:
				currentBGImage = bgImage1;
				break;
			case 60:
				currentBGImage = bgImage2;
				break;
			case 90:
				currentBGImage = bgImage3;
				break;
			case 120:
				currentBGImage = bgImage4;
				bgCount = 0;
				break;
		}
		bgCount++;
		
		// Settings
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20 * gp.scale));
		g2.setColor(Color.white);
		
		String settings = "SETTINGS";

		g2.drawString(settings, getHorizontalCenter(settings, g2, gp.screenWidth), gp.tileSize * 3);

		String music = "MUSIC VOLUME";
		String sound = "SOUND VOLUME";
		String fullscreen = "FULL SCREEN";
		String on = "ON";
		String off = "OFF";
		String resetScore = "RESET HIGH SCORE";
		String back = "RETURN";

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 10 * gp.scale));

		// Music Volume
		// g2.drawString(music, 2*gp.tileSize, gp.tileSize * 5);
		g2.drawString(music, getHorizontalCenter(music, g2, gp.screenWidth/2), gp.tileSize * 5);

		g2.drawRect((gp.screenWidth/2 - 240)/2 + gp.screenWidth/2, gp.tileSize*4, 240, 48); //draw a rect
		int volumeWidth = 48* this.music.getVolumeScale();
		g2.fillRect((gp.screenWidth/2 - 240)/2 + gp.screenWidth/2, gp.tileSize*4, volumeWidth, 48); //fill part of the rect

		if(selectPosition == 0){
			g2.drawString(">", getHorizontalCenter(music, g2, gp.screenWidth/2) - gp.tileSize, gp.tileSize * 5); //drawing > before the button
		}

		// Sound Effect Volume
		g2.drawString(sound, getHorizontalCenter(music, g2, gp.screenWidth/2), gp.tileSize * 13/2);

		g2.drawRect((gp.screenWidth/2 - 240)/2 + gp.screenWidth/2, gp.tileSize*11/2, 240, 48); //draw a rect
		int volumeWidth2 = 48* this.sound.getVolumeScale();
		g2.fillRect((gp.screenWidth/2 - 240)/2 + gp.screenWidth/2, gp.tileSize*11/2, volumeWidth2, 48); //fill part of the rect

		if(selectPosition == 1){
			g2.drawString(">", getHorizontalCenter(music, g2, gp.screenWidth/2) - gp.tileSize, gp.tileSize * 13/2); //drawing > before the button
		}

		// Fullscreen
		g2.drawString(fullscreen, getHorizontalCenter(music, g2, gp.screenWidth/2), gp.tileSize * 16/2);
		
		if(selectPosition == 2){
			g2.drawString(">", getHorizontalCenter(music, g2, gp.screenWidth/2) - gp.tileSize, gp.tileSize * 16/2); //drawing > before the button
		}

		if(!gp.uiM.getFullScreen()){
			g2.drawString(off, getHorizontalCenter(off, g2, gp.screenWidth/2) + gp.screenWidth/2, gp.tileSize*16/2);
		}
		else {
			g2.drawString(on, getHorizontalCenter(on, g2, gp.screenWidth/2) + gp.screenWidth/2, gp.tileSize*16/2);
		}

		// Reset High Score
		g2.drawString(resetScore, getHorizontalCenter(music, g2, gp.screenWidth/2), gp.tileSize * 19/2);

		if(selectPosition == 3){
			g2.drawString(">", getHorizontalCenter(music, g2, gp.screenWidth/2) - gp.tileSize, gp.tileSize * 19/2); //drawing > before the button
		}

		// Return
		g2.drawString(back, getHorizontalCenter(music, g2, gp.screenWidth/2), gp.tileSize * 22/2);
		
		if(selectPosition == 4){
			g2.drawString(">", getHorizontalCenter(music, g2, gp.screenWidth/2) - gp.tileSize, gp.tileSize * 22/2); //drawing > before the button
		}
	}
}
