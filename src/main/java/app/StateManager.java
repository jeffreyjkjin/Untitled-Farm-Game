package app;

import java.awt.Graphics2D;

import audio.Music;
import settings.Settings;

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

    Music music;
    Settings settings;
    GamePanel gp;

    boolean bgMusic1, bgMusic2;

    /**
     * Constructs a new StateManager object and links the Music and Settings singletons to this class.
     * Sets the current game state to title.
     * Plays the default background track.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    public StateManager(GamePanel gp) {
        this.gp = gp;
        music = Music.getInstance();
        settings = Settings.getInstance();

        currState = gameState.TITLE;
        music.play(0);
        bgMusic1 = true;
    }

    /**
     * Sets the current state to the new state.
     * Saves the previous state.
     * Resets the position of the selector.
     * Changes music depending on the game state.
     * Also changes the current input handler.
     * 
     * @param state the new state the user will be entering
     */
    public void setCurrentState(gameState state) {
        prevState = currState;
        currState = state;
        
        gp.inputM.changeInput(state);

        switch(state) {
            case WIN:
                if (bgMusic1) {
                    music.stop();
                    bgMusic1 = false;
                }
                music.play(1);
                bgMusic2 = true;

                // Calculate score based on play time
                int score = calculateScore();
                
                gp.player.score = score;
                
            case LOSE:

                boolean showHighScore;
                if (gp.player.score > settings.getHighScore()) {
                    settings.setHighScore(gp.player.score);
                    showHighScore = true;
                }
                else {
                    showHighScore = false;
                }
                gp.uiM.showNewHighScore(showHighScore);
                break;
            case PLAY:
            case PAUSE:
            case SETTINGS:
                break;
            default:
                gp.mapM.resetMap();
                gp.uiM.resetPlayScreen();
                gp.player.setDefaultValues();
                if (bgMusic2) {
                    music.stop();
                    bgMusic2 = false;
                }
                if (!bgMusic1) {
                    music.play(0);
                    bgMusic1 = true;
                }                       
        }

        gp.uiM.resetSelectorPosition();
    }

    /**
     * Resets the game's map, player and ui to default values.
     * Changes game state and input to play.
     */
    public void retryGame() {
        gp.mapM.resetMap();
        gp.uiM.resetPlayScreen();
        gp.player.setDefaultValues();
        
        prevState = currState;
        currState = gameState.PLAY;

        gp.inputM.changeInput(currState);

        if (bgMusic2) {
            music.stop();
            bgMusic2 = false;
        }
        if (!bgMusic1) {
            music.play(0);
            bgMusic1 = true;
        }                       
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

        gp.inputM.changeInput(currState);
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
    
    /**
     * Calculate the current score depending on the playTime
     * @return score
     */
    
    public int calculateScore() {
    	int score = gp.player.score;
    	double scoreMultiplier;
    	
    	int playTime = gp.uiM.getPlayTime();

        if (playTime <= 900) { // if play time is under 15 minutes, give score 
            scoreMultiplier = (double) 2 - ((double) playTime/900);
        }
        else {
            scoreMultiplier = 1;
        }
    	
        score *= scoreMultiplier;
        
    	return score;
    }
}
