package input;

import app.GamePanel;
import app.StateManager.gameState;

/**
 * Manages the input handlers for each game state.
 * Should be instantianted in the main GamePanel object so that the objects within it can access this classes methods.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see input.InputHandler
 */
public class InputManager {
    GamePanel gp;
    InputHandler[] inputs;    
    
    InputHandler currInput, prevInput;

    /**
     * Constructs a new InputManager object and initializes an array with each input handler position corresponding with the value of its game state.
     * Sets default input handler to TitleInput.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    public InputManager(GamePanel gp) {
        this.gp = gp;

        inputs = new InputHandler[7];
        inputs[0] = new PlayInput(gp);
        inputs[1] = new PauseInput(gp);
        inputs[2] = new WinLoseInput(gp);
        inputs[3] = inputs[2];
        inputs[4] = new TitleInput(gp);
        inputs[5] = new SettingsInput(gp);
        inputs[6] = new CreditsInput(gp);

        currInput = inputs[4];
    }

    /**
     * Saves previous game state and sets key listener in GamePanel to the current one.
     * 
     * @param state the new game state
     */
    public void changeInput(gameState state) {
        prevInput = currInput;
        currInput = inputs[state.getValue()];
        currInput.resetKeyPress();

        gp.removeKeyListener(prevInput);
        gp.addKeyListener(currInput);
    }

    /**
     * @return the current input handler
     */
    public InputHandler getCurrentInput() {
        return currInput;
    }

    /**
     * @return the input handler for Play state
     */
    public PlayInput getPlayInput() {
        return (PlayInput) inputs[0];
    }
}
