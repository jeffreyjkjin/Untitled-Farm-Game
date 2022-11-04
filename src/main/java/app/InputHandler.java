package app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.GamePanel.gameState;

/**
 * Handles input from the player's keyboard.
 * Each gameState has different control schemas.
 * 
 * @author Jeffrey Jin (jjj9)
 * @author Long Nguyen (dln3)
 */
public class InputHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right;
    public boolean select, enter;
    public boolean cluck;
    public boolean paused;

    public boolean bgMusic;

    gameState prevState;

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

    /**
     * Constructs a new InputHandler object and links this object to a GamePanel object so that it can be used by other objects within it.
     * 
     * @param gamePanel GamePanel object that is used to run the game
     */
    public InputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        
        // TODO: move bg music handling to a new gamestate class 
        gamePanel.music.play(0);
        bgMusic = true;
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

        switch(gamePanel.currState){
            case PAUSE:
                switch(keyCode) {
                    // Unpause
                    case KeyEvent.VK_ESCAPE:
                    if (!paused) {
                        gamePanel.currState = gameState.PLAY;
                    }
                    paused = true;

                    // Select
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (!select) {
                            gamePanel.sound.play(4);
                            gamePanel.ui.commandNum--;
                            if(gamePanel.ui.commandNum < 0) {
                                gamePanel.ui.commandNum = 2;
                            }
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (!select) {
                            gamePanel.sound.play(4);
                            gamePanel.ui.commandNum++;
                            if(gamePanel.ui.commandNum > 3) {
                                gamePanel.ui.commandNum = 0;
                            }
                        }
                        select = true;
                        break;

                    // Enter
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            gamePanel.sound.play(5);
                            if(gamePanel.ui.commandNum == 0) { // resume
                                gamePanel.currState = gameState.PLAY;
                            }
                            if(gamePanel.ui.commandNum == 1) { // Settings
                                prevState = gamePanel.currState;
                                gamePanel.currState = gameState.SETTINGS;
                                gamePanel.ui.commandNum = 0;
                            }
                            if(gamePanel.ui.commandNum == 2) { // Main Menu
                                
                                gamePanel.currState = gameState.TITLE;
                                gamePanel.ui.commandNum = 0;
                            }
                            if (gamePanel.ui.commandNum == 3) { // Quit
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
                        if (!cluck) {
                            gamePanel.sound.play(0);
                        }
                        cluck = true;
                        break;

                    // Pause
                    case KeyEvent.VK_ESCAPE:
                        if (!paused) {
                            gamePanel.currState = gameState.PAUSE;
                            gamePanel.ui.commandNum = 0;
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
                            gamePanel.sound.play(4);
                            gamePanel.ui.commandNum--;
                            if(gamePanel.ui.commandNum < 0) {
                                gamePanel.ui.commandNum = 3;
                            }
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (!select) {
                            gamePanel.sound.play(4);
                            gamePanel.ui.commandNum++;
                            if(gamePanel.ui.commandNum > 3) {
                                gamePanel.ui.commandNum = 0;
                            }
                        }
                        select = true;
                        break;

                    // Enter
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            gamePanel.sound.play(5);
                            if(gamePanel.ui.commandNum == 0){ // Play
                                gamePanel.mapM.resetMap();
                                gamePanel.ui.resetTimer();
                                gamePanel.player.setDefaultValues();
                                gamePanel.currState = gameState.PLAY;
                            }
                            if(gamePanel.ui.commandNum == 1) { // Settings 
                                prevState = gamePanel.currState;
                                gamePanel.currState = gameState.SETTINGS;
                                gamePanel.ui.commandNum = 0;
                            }
                            if(gamePanel.ui.commandNum == 2) { // Credits
                                gamePanel.currState = gameState.CREDITS;
                            }
                            if(gamePanel.ui.commandNum == 3) { // Quit
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
                            gamePanel.sound.play(4);
                            gamePanel.ui.commandNum--;
                            if(gamePanel.ui.commandNum < 0) {
                                gamePanel.ui.commandNum = 2;
                            }
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if (!select) {
                            gamePanel.sound.play(4);
                            gamePanel.ui.commandNum++;
                            if(gamePanel.ui.commandNum > 2) {
                                gamePanel.ui.commandNum = 0;
                            }
                        }
                        select = true;
                        break;

                    // Enter
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            gamePanel.sound.play(4);
                            if(gamePanel.ui.commandNum == 0) { // retry
                                gamePanel.mapM.resetMap();
                                gamePanel.ui.resetTimer();
                                gamePanel.player.setDefaultValues();
                                gamePanel.currState = gameState.PLAY;
                            }
                            if(gamePanel.ui.commandNum == 1) { // Main Menu
                                gamePanel.currState = gameState.TITLE;
                            }
                            if(gamePanel.ui.commandNum == 2) { // Quit
                                
                                System.exit(0);
                            }
                        }
                        enter = true;
                        break;
                }
                case SETTINGS:
                    switch(keyCode) {
                        case KeyEvent.VK_ESCAPE:
                            gamePanel.currState = prevState;
                        case KeyEvent.VK_W:
                        case KeyEvent.VK_UP:
                            if (!select) {
                                gamePanel.sound.play(4);
                                gamePanel.ui.commandNum--;
                                if(gamePanel.ui.commandNum < 0) {
                                    gamePanel.ui.commandNum = 4;
                                }
                            }
                            select = true;
                            break;
                        case KeyEvent.VK_S:
                        case KeyEvent.VK_DOWN:
                            if (!select) {
                                gamePanel.sound.play(4);
                                gamePanel.ui.commandNum++;
                                if(gamePanel.ui.commandNum > 4) {
                                    gamePanel.ui.commandNum = 0;
                                }
                            }
                            select = true;
                            break;

                        case KeyEvent.VK_A:
                        case KeyEvent.VK_LEFT:
                            if(gamePanel.ui.commandNum == 0){
                                gamePanel.music.lowerVolume();
                                gamePanel.music.checkVolume();
                                gamePanel.settings.setMusicVolume(gamePanel.music.getVolumeScale());
                                gamePanel.sound.play(4);
                            }
                            if(gamePanel.ui.commandNum == 1){
                                gamePanel.sound.lowerVolume();
                                gamePanel.sound.checkVolume();
                                gamePanel.settings.setSoundVolume(gamePanel.sound.getVolumeScale());
                                gamePanel.sound.play(4);
                            }
                            break;
                        
                        case KeyEvent.VK_D:
                        case KeyEvent.VK_RIGHT:
                            if(gamePanel.ui.commandNum == 0){
                                gamePanel.music.increaseVolume();
                                gamePanel.music.checkVolume();
                                gamePanel.settings.setMusicVolume(gamePanel.music.getVolumeScale());
                                gamePanel.sound.play(4);
                            }
                            if(gamePanel.ui.commandNum == 1){
                                gamePanel.sound.increaseVolume();
                                gamePanel.sound.checkVolume();
                                gamePanel.settings.setSoundVolume(gamePanel.sound.getVolumeScale());
                                gamePanel.sound.play(4);
                            }
                            break;
 
                        // Enter
                        case KeyEvent.VK_ENTER:
                            if (!enter) {
                                gamePanel.sound.play(5);
                                if(gamePanel.ui.commandNum == 2){ // Full screen
                                    if (gamePanel.ui.fullScreen == false){
                                        if (prevState != gameState.PLAY) {
                                            gamePanel.ui.fullScreen = true;
                                            gamePanel.settings.setFullScreen(gamePanel.ui.fullScreen);
                                            gamePanel.settings.saveConfigFile();
                                            gamePanel.setFullScreen();
                                        }
                                    }
                                    else if (gamePanel.ui.fullScreen == true){
                                        gamePanel.ui.fullScreen = false;
                                        gamePanel.settings.setFullScreen(gamePanel.ui.fullScreen);
                                        gamePanel.settings.saveConfigFile();
                                        gamePanel.setWindowScreen();
                                    }
                                }
                                if(gamePanel.ui.commandNum == 3) { // Reset high score
                                    gamePanel.settings.setHighScore(0);
                                    gamePanel.settings.saveConfigFile();
                                }
                                if(gamePanel.ui.commandNum == 4) { // Return
                                    gamePanel.currState = prevState;
                                    gamePanel.ui.commandNum = 0;
                                }
                            }
                            enter = true;
                            break;
                    }
                break;

                case CREDITS:
                    switch(keyCode) {
                        case KeyEvent.VK_ESCAPE:
                            gamePanel.currState = gameState.TITLE;
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
        
        switch(gamePanel.currState) {
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
                        gamePanel.sound.play(1);
                        cluck = false;
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
