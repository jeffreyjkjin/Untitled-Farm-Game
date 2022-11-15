package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import app.GamePanel;

/**
 * Used to draw the title screen.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 * @see ui.UIManager
 */
public class TitleScreen extends UI {
    BufferedImage bgImage;

    /**
	 * Calls the UI constructor.
	 * Sets the total number of options to three.
	 * Loads the background image.
	 * 
     * @param gp GamePanel object that is used to run the game
     */
    protected TitleScreen(GamePanel gp) {
        super(gp);

		totalOptions = 3;

        try {
            bgImage = ImageIO.read(getClass().getResourceAsStream("/screens/titleScreen.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
	 * Draws the title screen for the player with a menu.
	 * Menu contains buttons for playing the game, heading to the settings menu, showing the credits and closing the game.
	 * A selector icon is used to show the user what they have currently selected.
	 * Also displays the player's highest score.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI to the screen
	 */
	public void draw(Graphics2D g2){
		
		// Background Image
		g2.drawImage(bgImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		// Title
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20 * gp.scale));
		g2.setColor(Color.black);

		String title1 = "UNTITLED";
		String title2 = "FARM GAME";

		g2.drawString(title1, getHorizontalCenter(title1, g2, gp.screenWidth), gp.tileSize * 2);
		g2.drawString(title2, getHorizontalCenter(title2, g2, gp.screenWidth), gp.tileSize * 4);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";
		String savedHighScore = String.valueOf(settings.getHighScore());

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
		if(selectPosition == 0){
			g2.drawString(">",getHorizontalCenter(play, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 8); //drawing > before the button
		}

		// Settings
		g2.drawString(settings, getHorizontalCenter(settings, g2, gp.screenWidth), gp.tileSize * 9);
		if(selectPosition == 1){
			g2.drawString(">",getHorizontalCenter(settings, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Credits
		g2.drawString(credits, getHorizontalCenter(credits, g2, gp.screenWidth), gp.tileSize * 10);
		if(selectPosition == 2){
			g2.drawString(">",getHorizontalCenter(credits, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}

		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(selectPosition == 3){
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}
	}
}
