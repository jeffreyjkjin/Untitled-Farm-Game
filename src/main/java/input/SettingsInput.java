package input;

import java.awt.event.KeyEvent;

import app.GamePanel;
import app.StateManager.gameState;

/**
 * Handles input from the player's keyboard in the Settings state.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 */
public class SettingsInput extends InputHandler {

    /**
     * Calls the InputHandler constructor.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    protected SettingsInput(GamePanel gp) {
        super(gp);
    }

    /**
     * Called every time a key is pressed in the Settings state.
     * Different actions are bound to different keys.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        int position = gp.uiM.getSelectorPosition();

        switch(keyCode) {
            case KeyEvent.VK_ESCAPE:
                gp.stateM.revertPreviousState();
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (!select) {
                    sound.play(4);
                    gp.uiM.moveSelectorUp();
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

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (position == 0){
                    music.lowerVolume();
                    music.checkVolume();
                    settings.setMusicVolume(music.getVolumeScale());
                    sound.play(4);
                }
                else if (position == 1){
                    sound.lowerVolume();
                    sound.checkVolume();
                    settings.setSoundVolume(sound.getVolumeScale());
                    sound.play(4);
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (position == 0){
                    music.increaseVolume();
                    music.checkVolume();
                    settings.setMusicVolume(music.getVolumeScale());
                    sound.play(4);
                }
                else if (position == 1){
                    sound.increaseVolume();
                    sound.checkVolume();
                    settings.setSoundVolume(sound.getVolumeScale());
                    sound.play(4);
                }
                break;

            // Enter
            case KeyEvent.VK_ENTER:
                if (!enter) {
                    sound.play(5);
                    if (position == 2){ // Full screen
                        if (!gp.uiM.getFullScreen()){
                            gp.uiM.setFullScreen(true);
                            gp.setFullScreen();
                        }
                        else if (gp.uiM.getFullScreen()){
                            gp.uiM.setFullScreen(false);
                            gp.setWindowScreen();
                        }
                    }
                    else if (position == 3) { // Reset high score
                        settings.setHighScore(0);
                        settings.saveConfigFile();
                    }
                    else if (position == 4) { // Return
                        gameState prevState = gp.stateM.getPreviousState();
                        gp.stateM.setCurrentState(prevState);
                    }
                }
                enter = true;
                break;
        }
    }
}
