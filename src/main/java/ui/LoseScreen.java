package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import app.GamePanel;

/**
 * Used to draw the game over screen.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see ui.UIManager
 */
public class LoseScreen extends UI {
    BufferedImage bgImage, chicken;

	boolean showHighScore = false;

    /**
	 * Calls the UI constructor.
	 * Loads the background image.
	 * 
     * @param gp GamePanel object that is used to run the game
     */
    protected LoseScreen(GamePanel gp) {
        super(gp);

		totalOptions = 2;

        try {
            bgImage = ImageIO.read(getClass().getResourceAsStream("/losebg/losebg.png"));
			chicken = ImageIO.read(getClass().getResourceAsStream("/losebg/chicken.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Draws the game over screen for the player with a menu.
	 * Menu contains buttons for retrying, returning to main menu and closing the game.
	 * A selector icon is used to show the user what they have currently selected.
	 * Also displays the player's current score and high score.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI to the screen
	 */
	public void draw(Graphics2D g2) {
		
		// Background Image
		g2.drawImage(bgImage, 0, 0, gp.screenWidth, gp.screenHeight, null);

		int chickenWidth = 200 * gp.scale;
		int chickenHeight = 120 * gp.scale;

		g2.drawImage(chicken, (gp.screenWidth - chickenWidth)/2, (gp.screenHeight - chickenHeight)/2 - 36 * gp.scale, chickenWidth, chickenHeight, null);
		
		// Game Over
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20 * gp.scale));
		g2.setColor(Color.white);
		
		String gameOver = "GAME OVER!";

		g2.drawString(gameOver, getHorizontalCenter(gameOver, g2, gp.screenWidth), gp.tileSize * 3);

		// High Score
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 15));

		String highScore = "HIGH SCORE";
		String savedHighScore = String.valueOf(settings.getHighScore());

		g2.drawString(highScore, getHorizontalCenter(highScore, g2, gp.screenWidth/2), gp.tileSize * 5);
		g2.drawString(savedHighScore, getHorizontalCenter(savedHighScore, g2, gp.screenWidth/2), (int)(gp.tileSize * 5.5));

		
		// Your Score 
		g2.setColor(Color.white);
		String yourScore = "YOUR SCORE";
		String playerScore = String.valueOf(gp.player.score);
		
		g2.drawString(yourScore, getHorizontalCenter(yourScore, g2, gp.screenWidth/2) + gp.screenWidth/2, gp.tileSize * 5);
		g2.drawString(playerScore, getHorizontalCenter(playerScore, g2, gp.screenWidth/2) + gp.screenWidth/2, (int)(gp.tileSize * 5.5));

		// Show message if player's current high score is higher than the saved score
		if (showHighScore) {
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

	/**
	 * Shows new high score message if a new high score is achieved
	 * 
	 * @param showHighScore boolean value for whether if player's score is a new high score
	 */
	public void showNewHighScore(boolean showHighScore) {
		this.showHighScore = showHighScore;
	}
}
