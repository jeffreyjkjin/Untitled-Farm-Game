package app;

import java.awt.Graphics2D;


/**
 * Manages and controls the differing game states of our game.
 * Contains methods used to change the user's current state and draw or update objects and entities depending on the current state.
 * Should be instantianted in the main GamePanel object so other objects within it can access this classes methods. 
 * 
 * @author Jeffrey Jin (jjj9)
 */
public class StateManager {
    // Game States
    public enum gameState {
        PLAY(0),
        PAUSE(1),
        WIN(2),
        LOSE(3),
        TITLE(4),
        SETTINGS(5),
        CREDITS(6);

        private int value;

        /**
         * Constructs a new gameState enum and associates it with a value
         * 
         * @param value of the gameState
         */
        private gameState(int value) {
            this.value = value;
        }

        /**
         * @return the numerical value of the gameState
         */
        public int getValue() {
            return value;
        }
    }
    gameState currState, prevState;

    GamePanel gp;

    /**
     * Constructs a new StateManager object and links the GamePanel object to this class.
     * Sets the current game state to title.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    public StateManager(GamePanel gp) {
        this.gp = gp;

        currState = gameState.TITLE;
    }

    /**
     * Sets the current state to the new state.
     * Saves the previous state.
     * Resets the position of the selector.
     * 
     * @param state the new state the user will be entering
     */
    public void setCurrentState(gameState state) {
        prevState = currState;
        currState = state;
        
        gp.uiM.resetSelectorPosition();
    }

    /**
     * @return the user's current game state
     */
    public gameState getCurrentState() {
        return currState;
    }

    /**
     * @return the user's previous game state
     */
    public gameState getPreviousState() {
        return prevState;
    }

    /**
     * Returns the user back to their previous game state.
     */
    public void revertPreviousState() {
        gameState temp = currState;
        currState = prevState;
        prevState = temp;
    }

    /**
     * Draws the player and tile sprites onto the screen if the user is in gameState play or pause.
     * Always draws the user interface.
     * 
     * @param g2 main graphics object used by gamePanel to draw the maps sprites and tiles
     */
    public void draw(Graphics2D g2) {
        if (currState == gameState.PLAY || currState == gameState.PAUSE) {
            gp.mapM.draw(g2);
            gp.player.draw(g2);
        }

        gp.uiM.draw(g2);
    }

    /**
     * Calls the update method for player and the other entities on the map.
     * Only updates during gameplay; otherwise does nothing.
     */
    public void update() {
        if (currState == gameState.PLAY) {
            gp.player.update();
    
            for (int i = 0; i < gp.mapM.getMap().farmers.length; i++) {
                if (gp.mapM.getMap().farmers[i] != null) {
                    gp.mapM.getMap().farmers[i].update();
                }
            }
        }

    }
}
