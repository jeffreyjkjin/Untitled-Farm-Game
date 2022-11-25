package ui;

import java.awt.Graphics2D;

import app.GamePanel;
import app.StateManager.gameState;
import settings.Settings;

/**
 * Manages the user interface depending on the current state of the game.
 * Contains methods to control different aspects of the UI.
 * Should be instantianted in the main GamePanel object so that the objects within it can access this classes methods.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see ui.UI
 */
public class UIManager {
    Settings settings;
    GamePanel gp;
    UI ui[];

    boolean fullScreen;

    /**
     * Constructs a UIManager object and links the Settings singleton to this class.
     * Createds a UI array and fills it with each game screen.
     * Reads the fullscreen setting from the configuration file.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    public UIManager(GamePanel gp) {
        this.gp = gp;
        settings = Settings.getInstance();

        ui = new UI[7];
        ui[0] = new PlayScreen(gp);
        ui[1] = new PauseScreen(gp);
        ui[2] = new WinScreen(gp);
        ui[3] = new LoseScreen(gp);
        ui[4] = new TitleScreen(gp);
        ui[5] = new SettingsScreen(gp);
        ui[6] = new CreditsScreen(gp);

        fullScreen = settings.getFullScreen();
    }

    /**
     * Draws the user interface of the current game state onto the player's screen.
     * 
     * @param g2 main graphics object used by gamePanel to draw the maps sprites and tiles
     */
    public void draw(Graphics2D g2) {
        if (gp.stateM.getCurrentState() == gameState.PAUSE) {
            ui[0].draw(g2);
            ui[1].draw(g2);
        }
        ui[gp.stateM.getCurrentState().getValue()].draw(g2);;
    }

    /**
     * Moves the user's selector up on the screen.
     */
    public void moveSelectorUp() {
        ui[gp.stateM.getCurrentState().getValue()].moveSelectorUp();
    }

    /**
     * Moves the user's selector down on the screen.
     */
    public void moveSelectorDown() {
        ui[gp.stateM.getCurrentState().getValue()].moveSelectorDown();
    }

    /**
     * @return the position of the selector
     */
    public int getSelectorPosition() {
        return ui[gp.stateM.getCurrentState().getValue()].getSelectorPosition();
    }

    /**
     * Resets the postion of the selector to zero
     */
    public void resetSelectorPosition() {
        ui[gp.stateM.getCurrentState().getValue()].resetSelectorPosition();
    }

    /**
     * @return the status of full screen
     */
    public boolean getFullScreen() {
        return fullScreen;
    }

    /**
     * Saves the status of full screen.
     * Writes the status to the configuration file.
     * 
     * @param fullScreen a boolean for the status of full screen
     */
    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;

        settings.setFullScreen(fullScreen);
        settings.saveConfigFile();
    }

    /**
     * Resets the player's timer and messages on the PlayScreen.
     */
    public void resetPlayScreen() {
        PlayScreen playScreen = (PlayScreen) ui[0];
        playScreen.resetTimer();
        playScreen.resetMessage();
    }

    /**
     * Draws the provided message onto the user's screen.
     * Only runs if the player is in the game state play; otherwise does nothing.
     * 
     * @param message the message to be displayed on the screen
     */
    public void showMessage(String message) {
        if (gp.stateM.getCurrentState() == gameState.PLAY) {
            PlayScreen playScreen = (PlayScreen) ui[gp.stateM.getCurrentState().getValue()];
            playScreen.showMessage(message);
        }
    }

    /**
     * If player enters win or lose screen, show new high score message if player's score is higher than the saved score.
     * 
     * @param showHighScore boolean value for whether if player's score is a new high score
     */
    public void showNewHighScore(boolean showHighScore) {
        gameState currState = gp.stateM.getCurrentState();
        if (currState == gameState.WIN) {
            WinScreen winScreen = (WinScreen) ui[currState.getValue()];
            winScreen.showNewHighScore(showHighScore);
        }
        else if (currState == gameState.LOSE) {
            LoseScreen loseScreen = (LoseScreen) ui[currState.getValue()];
            loseScreen.showNewHighScore(showHighScore);
        }
    }

    /**
     * @return the player's play time from the play screen
     */
    public int getPlayTime() {
        PlayScreen playScreen = (PlayScreen) ui[0];

        return playScreen.getPlayTime();
    }
}
