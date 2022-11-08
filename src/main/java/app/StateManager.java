package app;

import java.awt.Graphics2D;

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

        private gameState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    gameState currState, prevState;

    GamePanel gp;

    public StateManager(GamePanel gp) {
        this.gp = gp;

        currState = gameState.TITLE;
    }

    public void setCurrentState(gameState state) {
        prevState = currState;
        currState = state;
        
        gp.uiM.resetSelectorPosition();
    }

    public gameState getCurrentState() {
        return currState;
    }

    public gameState getPreviousState() {
        return prevState;
    }

    public void revertPreviousState() {
        gameState temp = currState;
        currState = prevState;
        prevState = temp;
    }

    public void draw(Graphics2D g2) {
        if (currState == gameState.PLAY || currState == gameState.PAUSE) {
            gp.mapM.draw(g2);
            gp.player.draw(g2);
        }

        gp.uiM.draw(g2);
    }

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
