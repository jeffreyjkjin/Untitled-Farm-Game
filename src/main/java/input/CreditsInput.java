package input;

import java.awt.event.KeyEvent;

import app.GamePanel;
import app.StateManager.gameState;

/**
 * Handles input from the player's keyboard in the Credits state.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 */
public class CreditsInput extends InputHandler {

    /**
     * Calls the InputHandler constructor.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    protected CreditsInput(GamePanel gp) {
        super(gp);
    }
    
    /**
     * Called every time a key is pressed in the Credits state.
     * Different actions are bound to different keys.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_ESCAPE:
                sound.play(5);
                gp.stateM.setCurrentState(gameState.TITLE);
                break;
        }
    }
}
