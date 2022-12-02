package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import app.GamePanel;

/**
 * Used to draw the credits screen.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 * @see ui.UIManager
 */
public class CreditsScreen extends UI {
	BufferedImage skyImage[] = new BufferedImage[20];
	BufferedImage currentSkyImage;
	int skyPosition = 0, skyCount = 0;

	BufferedImage bgImage1, bgImage2, bgImage3, bgImage4, currentBGImage;
	int bgCount = 0;

    /**
	 * Calls UI constructor.
	 * Also loads background images.
	 * 
     * @param gp GamePanel object that is used to run the game
     */
    protected CreditsScreen(GamePanel gp) {
        super(gp);

		try {
			bgImage1 = ImageIO.read(getClass().getResourceAsStream("/creditsbg/bg1.png"));
			bgImage2 = ImageIO.read(getClass().getResourceAsStream("/creditsbg/bg2.png"));
			bgImage3 = ImageIO.read(getClass().getResourceAsStream("/creditsbg/bg3.png"));
			bgImage4 = ImageIO.read(getClass().getResourceAsStream("/creditsbg/bg4.png"));

			for (int i = 0; i < 20; i++) {
				skyImage[i] = ImageIO.read(getClass().getResourceAsStream("/creditsbg/" + i + ".png"));
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		currentBGImage = bgImage1;
		currentSkyImage = skyImage[0];
    }
    
	/**
	 * Draws the credit screen for the player.
	 * Shows the names of the creators of this game.
	 * Also animates background image.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI onto the screen
	 */
	public void draw(Graphics2D g2) {
		// Sky image
		g2.drawImage(currentSkyImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		// Changes the sky image every 30 frames or 1/2 seconds
		if (skyCount == 30) {
			skyPosition++;
			if (skyPosition >= 20) {
				skyPosition = 0;
			}
			currentSkyImage = skyImage[skyPosition];
			skyCount = 0;
		}
		skyCount++;
		
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
		
		String credits = "CREDITS";
		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 20 * gp.scale));
		
		// Drop shadow behind credits
		g2.setColor(Color.BLACK);
		g2.drawString(credits, getHorizontalCenter(credits, g2, gp.screenWidth) + (5/2) * gp.scale, gp.tileSize * 3 + (5/2) * gp.scale);

		// Credits
		g2.setColor(Color.white);
		g2.drawString(credits, getHorizontalCenter(credits, g2, gp.screenWidth), gp.tileSize * 3);

		g2.setFont(pressStart2P.deriveFont(Font.PLAIN, 10 * gp.scale));
		String name1 = "ANDREW HEIN";
		String name2 = "LONG NGUYEN";
		String name3 = "JEFFREY JIN";
		String name4 = "HWAN KIM";

		g2.drawString(name1, getHorizontalCenter(name1, g2, gp.screenWidth), gp.tileSize * 11/2);
		g2.drawString(name2, getHorizontalCenter(name2, g2, gp.screenWidth), gp.tileSize * 14/2);
		g2.drawString(name3, getHorizontalCenter(name3, g2, gp.screenWidth), gp.tileSize * 17/2);
		g2.drawString(name4, getHorizontalCenter(name4, g2, gp.screenWidth), gp.tileSize * 20/2);

	}
}
