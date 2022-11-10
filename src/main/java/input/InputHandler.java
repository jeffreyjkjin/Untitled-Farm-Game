package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.GamePanel;
import audio.Music;
import audio.SoundEffects;
import settings.Settings;

/**
 * Handles input from the player's keyboard.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 */
public abstract class InputHandler implements KeyListener {
    Settings settings;
    Music music;
    SoundEffects sound;
    GamePanel gp;

    public boolean up, down, left, right;
    public boolean select, enter;
    public boolean cluck;
    public boolean paused;

    /**
     * Constructs a new InputHandler object and links it to Music, Settings and SoundEffects singletons.
     * 
     * @param gp GamePanel object that is used to run the game
     */
    protected InputHandler(GamePanel gp) {
        this.gp = gp;

        music = Music.getInstance();
        settings = Settings.getInstance();
        sound = SoundEffects.getInstance();
    }

    /**
     * Called every time a key is pressed.
     * Different actions are bound to different keys.
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }    

    /**
     * Called every time a key is released.
     * Different actions are bound to different keys.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            // Movement
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                up = false;
                left = false;
                down = false;
                right = false;
                select = false;
                break;

            // Cluck
            case KeyEvent.VK_H:
                cluck = false;
                break;

            // Menu functions
            case KeyEvent.VK_ESCAPE:
                paused = false;
                break;
            case KeyEvent.VK_ENTER:
                enter = false;
                break;
        }
    }

    /**
     * Resets the boolean values of each key action to false.
     */
    public void resetKeyPress() {
        up = false;
        down = false;
        left = false;
        right = false;
        select = false;
        enter = false;
        cluck = false;
        paused = false;;
    }
}
