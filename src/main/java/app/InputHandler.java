package app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.StateManager.gameState;
import audio.Music;
import audio.SoundEffects;
import settings.Settings;

/**
 * Handles input from the player's keyboard.
 * Each gameState has different control schemas.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 */
public class InputHandler implements KeyListener {

    Settings settings;
    Music music;
    SoundEffects sound;
    GamePanel gamePanel;

    public boolean up, down, left, right;
    public boolean select, enter;
    public boolean cluck;
    public boolean paused;

    int[] konamiCode = {
        KeyEvent.VK_UP, 
        KeyEvent.VK_UP, 
        KeyEvent.VK_DOWN, 
        KeyEvent.VK_DOWN,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT,
        KeyEvent.VK_LEFT,
        KeyEvent.VK_RIGHT,
        KeyEvent.VK_B,
        KeyEvent.VK_A,
        KeyEvent.VK_ENTER
    };
    int konamiCount = 0;

    // TODO: Break up InputHandler into separate classes for each game state

    /**
     * Constructs a new InputHandler object and links it to Music, Settings and SoundEffects singletons.
     * 
     * @param gamePanel GamePanel object that is used to run the game
     */
    public InputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        music = Music.getInstance();
        settings = Settings.getInstance();
        sound = SoundEffects.getInstance();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Perform an action dependings on which key was pressed and the current state of the game.
     * Inputs on the title, settings, credits, win, lose and pause screens control the menus of the corresponding screen.
     * Sound effects are played while navigating menu screens and selecting options.
     * Inputs on the play screen control the players movement and interactions.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(gamePanel.stateM.getCurrentState()){
            case PAUSE:
                switch(keyCode) {
                    // Unpause
                    case KeyEvent.VK_ESCAPE:
                    if (!paused) {
                        sound.play(5);
                        gamePanel.stateM.setCurrentState(gameState.PLAY);
                    }
                    paused = true;

                    // Select
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (!select) {
                            sound.play(4);
                            gamePanel.uiM.moveSelectorUp();;
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (!select) {
                            sound.play(4);
                            gamePanel.uiM.moveSelectorDown();
                        }
                        select = true;
                        break;

                    // Enter
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            int position = gamePanel.uiM.getSelectorPosition();
                            sound.play(5);
                            if (position == 0) { // resume
                                gamePanel.stateM.setCurrentState(gameState.PLAY);
                            }
                            else if (position == 1) { // Settings
                                gamePanel.stateM.setCurrentState(gameState.SETTINGS);
                            }
                            else if (position == 2) { // Main Menu
                                gamePanel.stateM.setCurrentState(gameState.TITLE);
                            }
                            else if (position == 3) { // Quit
                                System.exit(0);
                            }
                        }
                        enter = true;
                        break;
                }
                break;

            case PLAY:
                switch(keyCode) {
                    // Movement
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        up = true;
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        left = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        down = true;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        right = true;
                        break;

                    // Cluck
                    case KeyEvent.VK_H:
                        if (!cluck) 
                        {
                            sound.play(0);
                        }
                        cluck = true;
                        break;

                    // Pause
                    case KeyEvent.VK_ESCAPE:
                        if (!paused) {
                            sound.play(5);
                            gamePanel.stateM.setCurrentState(gameState.PAUSE);
                        }
                        paused = true;
                }
                break;
            
            case TITLE:
                switch(keyCode) {
                    // Select
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (!select) {
                            sound.play(4);
                            gamePanel.uiM.moveSelectorUp();
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (!select) {
                            sound.play(4);
                            gamePanel.uiM.moveSelectorDown();
                        }
                        select = true;
                        break;

                    // Enter
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            int position = gamePanel.uiM.getSelectorPosition();
                            sound.play(5);
                            if (position == 0){ // Play
                                gamePanel.stateM.setCurrentState(gameState.PLAY);
                            }
                            else if (position == 1) { // Settings 
                                gamePanel.stateM.setCurrentState(gameState.SETTINGS);
                            }
                            else if (position == 2) { // Credits
                                gamePanel.stateM.setCurrentState(gameState.CREDITS);
                            }
                            else if (position == 3) { // Quit
                                System.exit(0);
                            }
                        }
                        enter = true;
                        break;
                }
                break;

            case WIN:
            case LOSE:
                switch(keyCode) {
                    // Select
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (!select) {
                            sound.play(4);
                            gamePanel.uiM.moveSelectorUp();;
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (!select) {
                            sound.play(4);
                            gamePanel.uiM.moveSelectorDown();
                        }
                        select = true;
                        break;

                    // Enter
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            int position = gamePanel.uiM.getSelectorPosition();
                            sound.play(4);
                            if (position == 0) { // retry
                                gamePanel.stateM.setCurrentState(gameState.PLAY);
                            }
                            else if (position == 1) { // Main Menu
                                gamePanel.stateM.setCurrentState(gameState.TITLE);
                            }
                            else if (position == 2) { // Quit
                                System.exit(0);
                            }
                        }
                        enter = true;
                        break;
                }
                case SETTINGS:
                    int position = gamePanel.uiM.getSelectorPosition();
                    switch(keyCode) {
                        case KeyEvent.VK_ESCAPE:
                            gamePanel.stateM.revertPreviousState();
                        case KeyEvent.VK_W:
                        case KeyEvent.VK_UP:
                            if (!select) {
                                sound.play(4);
                                gamePanel.uiM.moveSelectorUp();
                            }
                            select = true;
                            break;
                        case KeyEvent.VK_S:
                        case KeyEvent.VK_DOWN:
                            if (!select) {
                                sound.play(4);
                                gamePanel.uiM.moveSelectorDown();
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
                                    // Prevent player from toggling full screen during gameplay
                                    if (!gamePanel.uiM.getFullScreen()){
                                        if (gamePanel.stateM.getPreviousState() != gameState.PAUSE) {
                                            gamePanel.uiM.setFullScreen(true);
                                            gamePanel.setFullScreen();
                                        }
                                    }
                                    else if (gamePanel.uiM.getFullScreen()){
                                        if (gamePanel.stateM.getPreviousState() != gameState.PAUSE) {
                                            gamePanel.uiM.setFullScreen(false);
                                            gamePanel.setWindowScreen();
                                        }
                                    }
                                }
                                else if (position == 3) { // Reset high score
                                    settings.setHighScore(0);
                                    settings.saveConfigFile();
                                }
                                else if (position == 4) { // Return
                                    gameState prevState = gamePanel.stateM.getPreviousState();
                                    gamePanel.stateM.setCurrentState(prevState);
                                }
                            }
                            enter = true;
                            break;
                    }
                break;

                case CREDITS:
                    switch(keyCode) {
                        case KeyEvent.VK_ESCAPE:
                            gamePanel.stateM.setCurrentState(gameState.TITLE);
                            break;
                    }
                break;
        }

    }

    /** 
     * Stop performing the action of the corresponding key when it is released.
     * Also contains implementation of the Konami Code which can be used during gameplay to activate developer mode.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch(gamePanel.stateM.getCurrentState()) {
            case PLAY:
                switch(keyCode) {
                    // Movement
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        up = false;
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        left = false;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        down = false;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        right = false;
                        break;

                    // Cluck
                    case KeyEvent.VK_H:
                        sound.play(1);
                        cluck = false;

                        if (gamePanel.player.freezeCooldown == 0)
                        {   // Freeze the farmers if ability is not on cooldown and set the new cooldown after
                            gamePanel.player.freezeFarmers();
                        }

                        break;

                    // Pause Menu functions
                    case KeyEvent.VK_ESCAPE:
                        paused = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        enter = false;
                }
            
                // Konami Code
                if (keyCode == konamiCode[konamiCount]) {
                    konamiCount++;
                }
                else {
                    konamiCount = 0;
                }
                
                if (konamiCount == 11) {
                    System.out.println("Cheats Activated!");
                    konamiCount = 0;
                    // TODO: implement function to activate developer mode cheats
                }
            break;

            case CREDITS:
            case SETTINGS:
            case WIN:
            case LOSE:
            case PAUSE:
            case TITLE:
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
                break;
        }
    }
}
