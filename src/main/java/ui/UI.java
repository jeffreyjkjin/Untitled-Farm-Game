package ui;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.InputStream;
import java.io.IOException;

import app.GamePanel;

public abstract class UI {
    GamePanel gp;
    Font pressStart2P;

    int selectPosition = 0;
    int totalOptions;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream input = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            pressStart2P = Font.createFont(Font.TRUETYPE_FONT, input);
        }
        catch (FontFormatException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Centers a string of text in the middle of the provided screen width.
     * 
     * @param text the text that is to be centered
     * @param g2 the main graphics object that is used to draw the text onto the screen
     * @param screenWidth the current width of the screen
     * @return x coordinate for the text you want to draw on the center of the screen
	 */
    protected int getHorizontalCenter(String text, Graphics2D g2, int screenWidth) {
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = screenWidth/2 - textLength/2; // Print message on center of screen
		return x;
	}
    
    public void moveSelectorUp() {
        selectPosition--;
        if (selectPosition < 0) {
            selectPosition = totalOptions;
        }
    }
    
    public void moveSelectorDown() {
        selectPosition++;
        if (selectPosition > totalOptions) {
            selectPosition = 0;
        }
    }
    
    public int getSelectorPosition() {
        return selectPosition;
    }
    
    public void resetSelectorPosition() {
        selectPosition = 0;
    }

    public void draw(Graphics2D g2) {}
}
