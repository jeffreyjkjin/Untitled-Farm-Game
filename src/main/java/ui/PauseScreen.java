package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import app.GamePanel;

public class PauseScreen extends PlayScreen {
	
	protected PauseScreen(GamePanel gp) {
        super(gp);

		totalOptions = 3;
    }

    /**
	 * Display pause menu onto the player's screen.
	 * Menu contains buttons for resuming the game, returning to main menu, going to settings screen and closing the game.
	 * A selector icon is used to show the user what option they currently have selected.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI onto the screen
	 */
	public void draw(Graphics2D g2) {
        super.draw(g2);

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
		if(selectPosition == 0) {
			g2.drawString(">",getHorizontalCenter(resume, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 8);
		}

		// Settings
		g2.drawString(settings, getHorizontalCenter(settings, g2, gp.screenWidth), gp.tileSize * 9);
		if(selectPosition == 1) {
			g2.drawString(">",getHorizontalCenter(settings, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 9);
		}

		// Main Menu
		g2.drawString(menu, getHorizontalCenter(menu, g2, gp.screenWidth), gp.tileSize * 10);
		if(selectPosition == 2) {
			g2.drawString(">",getHorizontalCenter(menu, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 10);
		}
		
		// Quit
		g2.drawString(quit, getHorizontalCenter(quit, g2, gp.screenWidth), gp.tileSize * 11);
		if(selectPosition == 3) {
			g2.drawString(">",getHorizontalCenter(quit, g2, gp.screenWidth) - gp.tileSize, gp.tileSize * 11);
		}	
		
	}
}
