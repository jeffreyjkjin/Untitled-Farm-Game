package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import app.GamePanel;

/**
 * Used to draw the win screen.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see ui.UIManager
 */
public class WinScreen extends UI {
    BufferedImage bgImage[] = new BufferedImage[64];
	BufferedImage currentBGImage;
	int bgPosition = 0, bgCount = 0;

	BufferedImage chicken1, chicken2, chicken3, chicken4, chickenImage;
	int spriteCount = 0;

    /**
	 * Calls the UI constructor.
	 * Sets the total number of options to two.
	 * Loads the background image.
	 * 
     * @param gpGamePanel object that is used to run the game
     */
    protected WinScreen(GamePanel gp) {
        super(gp);

		totalOptions = 2;

        try {
			for (int i = 0; i < 64; i++) {
				bgImage[i] = ImageIO.read(getClass().getResourceAsStream("/winbg/" + i + ".png"));
			}

			chicken1 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenup1.png"));
			chicken2 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenupstill1.png"));
			chicken3 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenup2.png"));
			chicken4 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenupstill2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

		chickenImage = chicken1;
		currentBGImage = bgImage[0];
    }
    
	/**
	 * Draws the win game screen for the player with a menu.
	 * Menu contains buttons for retrying, returning to main menu and closing the game.
	 * A selector icon is used to show the user what they have currently selected.
	 * Also displays the player's current score and high score.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI to the screen
	 */
	public void draw(Graphics2D g2) {
		
		// Background Image
		g2.drawImage(currentBGImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		// Changes the background image every 15 frames or 1/4 seconds
		if (bgCount == 15) {
			bgPosition++;
			if (bgPosition >= 64) {
				bgPosition = 0;
			}
			currentBGImage = bgImage[bgPosition];
			bgCount = 0;
		}
		bgCount++;


		// Chicken sprites 
		int spriteSize = gp.tileSize * gp.scale;

		g2.drawImage(chickenImage, (gp.screenWidth - spriteSize)/2, (gp.screenHeight- spriteSize)/2, spriteSize, spriteSize, null);
		// Changes the sprite for the chicken every 15 frames or 1/4 second.
		switch(spriteCount) {
			case 15:
				chickenImage = chicken1;
				break;
			case 30:
				chickenImage = chicken2;
				break;
			case 45:
				chickenImage = chicken3;
				break;
			case 60:
				chickenImage = chicken4;
				spriteCount = 0;
		}
		spriteCount++;

		// Game Over
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20 * gp.scale));
		g2.setColor(Color.white);
		
		String gameOver = "YOU'VE ESCAPED!";

		g2.drawString(gameOver, getHorizontalCenter(gameOver, g2, gp.screenWidth), gp.tileSize * 3);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";
		String savedHighScore = String.valueOf(settings.getHighScore());

		g2.drawString(highScore, getHorizontalCenter(highScore, g2, gp.screenWidth/2), gp.tileSize * 5);
		g2.drawString(savedHighScore, getHorizontalCenter(savedHighScore, g2, gp.screenWidth/2), (int)(gp.tileSize * 5.5));

		// Your Score
		String yourScore = "YOUR SCORE";
		String playerScore = String.valueOf(gp.player.score);
		
		g2.drawString(yourScore, getHorizontalCenter(yourScore, g2, gp.screenWidth/2) + gp.screenWidth/2, gp.tileSize * 5);
		g2.drawString(playerScore, getHorizontalCenter(playerScore, g2, gp.screenWidth/2) + gp.screenWidth/2, (int)(gp.tileSize * 5.5));

		// Show message if player's current high score is higher than the saved score
		if (settings.getHighScore() == gp.player.score) {
			g2.setColor(Color.RED);
			String newHighScore = "NEW HIGH SCORE!";
			g2.drawString(newHighScore, getHorizontalCenter(newHighScore, g2, gp.screenWidth/2), gp.tileSize * 6);
			g2.setColor(Color.WHITE);
		}

		// Menu
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 40));

		String retry = "RETRY";
		String menu = "MAIN MENU";
		String quit = "QUIT";

		// Retry
		g2.drawString(retry, getHorizontalCenter(retry, g2, gp.screenWidth), gp.tileSize * 9);
		if(selectPosition == 0) {
			g2.drawString(">",getHorizontalCenter(retry, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Main Menu
		g2.drawString(menu, getHorizontalCenter(menu, g2, gp.screenWidth), gp.tileSize * 10);
		if(selectPosition == 1) {
			g2.drawString(">",getHorizontalCenter(menu, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}

		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(selectPosition == 2) {
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}
	}
}
