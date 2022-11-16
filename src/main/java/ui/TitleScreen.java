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
    BufferedImage bgImage[] = new BufferedImage[40];
	BufferedImage currentBGImage;
	int bgPosition = 0, bgCount = 0;

	BufferedImage chicken1, chicken2, chicken3, chickenImage;
	BufferedImage farmer1, farmer2, farmer3, farmer4, farmerImage;
	int spriteCount = 0;

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
			chicken1 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenright1.png"));
			chicken2 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenrightstill.png"));
			chicken3 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenright2.png"));

			farmer1 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerright1.png"));
			farmer2 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerrightstill1.png"));
			farmer3 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerright2.png"));
			farmer4 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerrightstill2.png"));

			for (int i = 0; i < 40; i++) {
				bgImage[i] = ImageIO.read(getClass().getResourceAsStream("/titlebg/" + i + ".png"));
			}
        }
        catch (IOException e) {
            e.printStackTrace();
        }

		currentBGImage = bgImage[0];

		chickenImage = chicken1;
		farmerImage = farmer1;
    }

    /**
	 * Draws the title screen for the player with a menu.
	 * Animates a farmer and a chicken sprite walking to the right while also scrolling through the background image.
	 * Menu contains buttons for playing the game, heading to the settings menu, showing the credits and closing the game.
	 * A selector icon is used to show the user what they have currently selected.
	 * Also displays the player's highest score.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI to the screen
	 */
	public void draw(Graphics2D g2){
		
		// Background Image
		g2.drawImage(currentBGImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		// Changes the background image every 30 frames or 1/2 seconds
		if (bgCount == 30) {
			bgPosition++;
			if (bgPosition >= 40) {
				bgPosition = 0;
			}
			currentBGImage = bgImage[bgPosition];
			bgCount = 0;
		}
		bgCount++;

		// Farmer and Chicken sprites 
		int spriteSize = gp.tileSize * gp.scale;

		g2.drawImage(chickenImage, (gp.screenWidth/4 + gp.screenWidth/2) - spriteSize/2, (gp.screenHeight - spriteSize)/2 + 3 * gp.tileSize, spriteSize, spriteSize, null);
		g2.drawImage(farmerImage, gp.screenWidth/4 - spriteSize/2, (gp.screenHeight - spriteSize)/2 + 3 * gp.tileSize, spriteSize, spriteSize, null);
		// Changes the sprite for the chicken and farmer every 30 frames or 1/2 second.
		switch(spriteCount) {
			case 30:
				chickenImage = chicken1;
				farmerImage = farmer1;
				break;
			case 60:
				chickenImage = chicken2;
				farmerImage = farmer2;
				break;
			case 90:
				chickenImage = chicken3;
				farmerImage = farmer3;
				break;
			case 120:
				chickenImage = chicken2;
				farmerImage = farmer4;
				spriteCount = 0;
		}
		spriteCount++;
		
		// Title
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20 * gp.scale));
		
		String title1 = "UNTITLED";
		String title2 = "FARM GAME";
		
		// Drop shadow behind title
		g2.setColor(Color.BLACK);
		g2.drawString(title1, getHorizontalCenter(title1, g2, gp.screenWidth) + (5/2) * gp.scale, gp.tileSize * 2 + (5/2) * gp.scale);
		g2.drawString(title2, getHorizontalCenter(title2, g2, gp.screenWidth) + (5/2) * gp.scale, gp.tileSize * 4 + (5/2) * gp.scale);

		// Actual title
		g2.setColor(Color.WHITE);
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
