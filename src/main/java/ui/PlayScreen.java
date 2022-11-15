package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import app.GamePanel;
import app.StateManager.gameState;
import object.OBJ_Heart;

/**
 * Used to draw the user interface during gameplay onto the screen.
 * Contains methods to update different elements of the UI.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Seughwan Kim
 * @see ui.UIManager
 */
public class PlayScreen extends UI {
	OBJ_Heart playerHP[] = new OBJ_Heart[3];
    double playTime = 0;

    double messageCounter = 0;
	boolean messageOn = false;
	String message = "";

    /**
	 * Calls the UI constructor.
	 * Loads heart sprites into the player health array.
	 * 
     * @param gp GamePanel object that is used to run the game
     */
    protected PlayScreen(GamePanel gp) {
        super(gp);

        playerHP[0] = new OBJ_Heart();
		playerHP[1] = new OBJ_Heart();
		playerHP[2] = new OBJ_Heart();
    }
    
	/**
	 * Displays the player's health points on the screen.
	 * Hearts are either empty or full depending on how many health points the player has.
	 * 
	 * @param g2 the main graphics object that is used to draw the hearts onto the screen
	 */
	private void drawPlayerLife(Graphics2D g2) {
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

	/**
	 * Displays a message on the screen.
	 * 
	 * @param text the message that is to be displayed
	 */
	public void showMessage(String text) {
		if (messageOn) {
			messageCounter = 0;
		}
		message = text;
		messageOn = true;
	}

    /**
	 * Reset the timer to zero.
	 */
	public void resetTimer() {
		playTime = 0;
	}

	/**
	 * Turns off messages and deletes the saved messsage.
	 */
	public void resetMessage() {
		message = "";
		messageOn = false;
	}

    /**
	 * Creates a timer that displays minutes and seconds.
	 * 
	 * @return a string with the players current time
	 */
	private String drawTimer() {
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

    /**
	 * Draws the game UI while the user is playing the game.
	 * Shows current health, level name, score and time.
	 * Messages may appear on the screen to indicate to the player what is happening such as when a player interacts with an object or entity.
	 * 
	 * @param g2 the main graphics object that is used to draw the UI onto the screen
	 */
	public void draw(Graphics2D g2) {
		
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
		if (gp.stateM.getCurrentState() == gameState.PLAY) {
			playTime += (double)1/60;
		}
		g2.drawString(drawTimer(), getHorizontalCenter(drawTimer(), g2, gp.screenWidth/4) + (gp.screenWidth * 3/4), 64);

		// Message
		if (messageOn) {
			
			g2.setFont(g2.getFont().deriveFont(15F));
			g2.drawString(message, getHorizontalCenter(message, g2, (gp.tileSize * 3)) + gp.player.screenX - gp.tileSize, gp.player.screenY - gp.tileSize/2);
			
			messageCounter += (double)1/60;
			
			if (messageCounter > 2) { // message will only exist for 2 seconds 
				messageCounter = 0;
				messageOn = false;
			}
		}	
	}
}
