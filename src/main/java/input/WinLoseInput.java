package input;

import java.awt.event.KeyEvent;

import app.GamePanel;
import app.StateManager.gameState;

/**
 * Handles input from the player's keyboard in the Win/Lose states.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 */
public class WinLoseInput extends InputHandler {
    
    /**
     * Calls the InputHandler constructor.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    protected WinLoseInput(GamePanel gp) {
        super(gp);
    }

    /**
     * Called every time a key is pressed in the Win/Lose states.
     * Different actions are bound to different keys.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            // Select
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (!select) {
                    sound.play(4);
                    gp.uiM.moveSelectorUp();;
                }
                select = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if (!select) {
                    sound.play(4);
                    gp.uiM.moveSelectorDown();
                }
                select = true;
                break;

            // Enter
            case KeyEvent.VK_ENTER:
                if (!enter) {
                    int position = gp.uiM.getSelectorPosition();
                    sound.play(4);
                    if (position == 0) { // retry
                        gp.stateM.retryGame();
                    }
                    else if (position == 1) { // Main Menu
                        gp.stateM.setCurrentState(gameState.TITLE);
                    }
                    else if (position == 2) { // Quit
                        System.exit(0);
                    }
                }
                enter = true;
                break;
        }
    }

}
