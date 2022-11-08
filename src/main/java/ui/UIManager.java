package ui;

import java.awt.Graphics2D;

import app.GamePanel;
import app.StateManager.gameState;

public class UIManager {
    GamePanel gp;
    UI ui[];

    boolean fullScreen;

    public UIManager(GamePanel gp) {
        this.gp = gp;

        ui = new UI[7];
        ui[0] = new PlayScreen(gp);
        ui[1] = new PauseScreen(gp);
        ui[2] = new WinScreen(gp);
        ui[3] = new LoseScreen(gp);
        ui[4] = new TitleScreen(gp);
        ui[5] = new SettingsScreen(gp);
        ui[6] = new CreditsScreen(gp);

        fullScreen = this.gp.settings.getFullScreen();
    }

    public void draw(Graphics2D g2) {
        ui[gp.stateM.getCurrentState().getValue()].draw(g2);;
    }

    public void moveSelectorUp() {
        ui[gp.stateM.getCurrentState().getValue()].moveSelectorUp();
    }

    public void moveSelectorDown() {
        ui[gp.stateM.getCurrentState().getValue()].moveSelectorDown();
    }

    public int getSelectorPosition() {
        return ui[gp.stateM.getCurrentState().getValue()].getSelectorPosition();
    }

    public void resetSelectorPosition() {
        ui[gp.stateM.getCurrentState().getValue()].resetSelectorPosition();
    }

    public boolean getFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;

        gp.settings.setFullScreen(fullScreen);
        gp.settings.saveConfigFile();
    }

    public void resetTimer() {
        if (gp.stateM.getCurrentState() == gameState.PLAY) {
            PlayScreen playScreen = (PlayScreen) ui[gp.stateM.getCurrentState().getValue()];
            playScreen.resetTimer();
        }
    }

    public void showMessage(String message) {
        if (gp.stateM.getCurrentState() == gameState.PLAY) {
            PlayScreen playScreen = (PlayScreen) ui[gp.stateM.getCurrentState().getValue()];
            playScreen.showMessage(message);
        }
    }
}
