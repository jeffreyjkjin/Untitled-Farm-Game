package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import app.GamePanel;

public class SettingsScreen extends UI {

	protected SettingsScreen(GamePanel gp) {
        super(gp);

		totalOptions = 4;
    }
    
    /**
	 * Draws the settings menu for the player.
	 * Menu contains options to change the music and sound effect volumes, toggle fullscreen mode, resetting the highest score and returning to the main menu.
	 * A selector icon is used to show the user what they have currently selected.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI to the screen
	 */
	public void draw(Graphics2D g2) {
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

		if(selectPosition == 0){
			g2.drawString(">", gp.tileSize, gp.tileSize * 5); //drawing > before the button
		}

		// Sound Effect Volume
		g2.drawString(sound, 2*gp.tileSize, gp.tileSize * 13/2);

		g2.drawRect(gp.tileSize*13, gp.tileSize*11/2, 240, 48); //draw a rect
		int volumeWidth2 = 48* gp.sound.getVolumeScale();
		g2.fillRect(gp.tileSize*13, gp.tileSize*11/2, volumeWidth2, 48); //fill part of the rect

		if(selectPosition == 1){
			g2.drawString(">", gp.tileSize, gp.tileSize * 13/2); //drawing > before the button
		}

		// Fullscreen
		g2.drawString(fullscreen, 2*gp.tileSize, gp.tileSize * 16/2);
		if(selectPosition == 2){
			g2.drawString(">", gp.tileSize, gp.tileSize * 16/2); //drawing > before the button
		}

		if(!gp.uiM.getFullScreen()){
			g2.drawString("OFF",gp.tileSize*12, gp.tileSize*16/2);
		}
		else {
			g2.drawString("ON",gp.tileSize*12, gp.tileSize*16/2);
		}

		// Reset High Score
		g2.drawString(resetScore, 2*gp.tileSize, gp.tileSize * 19/2);
		if(selectPosition == 3){
			g2.drawString(">", gp.tileSize, gp.tileSize * 19/2); //drawing > before the button
		}

		// Return
		g2.drawString(back, 2*gp.tileSize, gp.tileSize * 22/2);
		if(selectPosition == 4){
			g2.drawString(">", gp.tileSize, gp.tileSize * 22/2); //drawing > before the button
		}
	}

}
