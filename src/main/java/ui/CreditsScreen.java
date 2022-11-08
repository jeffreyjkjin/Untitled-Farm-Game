package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import app.GamePanel;

/**
 * Used to draw the credits screen.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 * @see ui.UIManager
 */
public class CreditsScreen extends UI {

    /**
	 * Calls UI constructor.
	 * 
     * @param gp GamePanel object that is used to run the game
     */
    protected CreditsScreen(GamePanel gp) {
        super(gp);
    }
    
	/**
	 * Draws the credit screen for the player.
	 * Shows the names of the creators of this game.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI onto the screen
	 */
	public void draw(Graphics2D g2) {
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
}
