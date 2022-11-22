package app;

import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.StateManager.gameState;
import input.*;

public class StateManagerTest {
    Random randGen;

    GamePanel gp;
    StateManager stateM;

    gameState states[] = {
        gameState.PLAY,
        gameState.PAUSE,
        gameState.WIN,
        gameState.LOSE,
        gameState.TITLE,
        gameState.SETTINGS,
        gameState.CREDITS
    };
    
    @Before
    public void setupTests() {
        randGen = new Random();

        gp = new GamePanel();

        stateM = new StateManager(gp);
    }

    @Test
    public void changeToNewState() {
        gameState prevState = states[randGen.nextInt(states.length)];
        gameState currState = states[randGen.nextInt(states.length)];
        
        // Change currState until it is not equal to prevState
        while (currState == prevState) {
            currState = states[randGen.nextInt(states.length)];
        }

        stateM.setCurrentState(prevState);
        stateM.setCurrentState(currState);

        // Check states
        assertEquals(prevState, stateM.getPreviousState());
        assertEquals(currState, stateM.getCurrentState());

        // Check input
        InputHandler input = gp.inputM.getCurrentInput();

        switch (currState) {
            case CREDITS:
                assertTrue(input instanceof CreditsInput);
                break;
            case WIN:
            case LOSE:
                assertTrue(input instanceof WinLoseInput);
                break;
            case PAUSE:
                assertTrue(input instanceof PauseInput);
            break;
            case PLAY:
                assertTrue(input instanceof PlayInput);
                break;
            case SETTINGS:
                assertTrue(input instanceof SettingsInput);
                break;
            case TITLE:
                assertTrue(input instanceof TitleInput);
                break;
        }
    }

    @Test
    public void revertToPreviousState() {
        gameState prevState = states[randGen.nextInt(states.length)];
        gameState currState = states[randGen.nextInt(states.length)];
        
        // Change currState until it is not equal to prevState
        while (currState == prevState) {
            currState = states[randGen.nextInt(states.length)];
        }

        stateM.setCurrentState(prevState);
        stateM.setCurrentState(currState);

        stateM.revertPreviousState();

        assertEquals(prevState, stateM.getCurrentState());
        assertEquals(currState, stateM.getPreviousState());

        // Check input
        InputHandler input = gp.inputM.getCurrentInput();

        switch (prevState) {
            case CREDITS:
                assertTrue(input instanceof CreditsInput);
                break;
            case WIN:
            case LOSE:
                assertTrue(input instanceof WinLoseInput);
                break;
            case PAUSE:
                assertTrue(input instanceof PauseInput);
            break;
            case PLAY:
                assertTrue(input instanceof PlayInput);
                break;
            case SETTINGS:
                assertTrue(input instanceof SettingsInput);
                break;
            case TITLE:
                assertTrue(input instanceof TitleInput);
                break;
        }
    }

    @Test
    public void changeToLoseStateWithNewHighScore() {
        // Ensure player score is always higher than saved high score
        gp.player.score = gp.settings.getHighScore() + 1;

        gp.stateM.setCurrentState(gameState.LOSE);

        assertEquals(gp.player.score, gp.settings.getHighScore());
    }

    @Test
    public void changeToLoseStateWithoutNewHighScore() {
        // Ensure player score is always lower than saved high score
        gp.player.score = gp.settings.getHighScore() - 1;

        gp.stateM.setCurrentState(gameState.LOSE);

        assertTrue(gp.player.score != gp.settings.getHighScore());
    }
}
