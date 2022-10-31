package app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.GamePanel.gameState;

public class InputHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right;
    public boolean select, enter;
    public boolean cluck;
    public boolean paused;

    public boolean bgMusic;

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

    public InputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        gamePanel.playMusic(0);
        bgMusic = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

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
                            gamePanel.playSoundE(6);
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
                            gamePanel.playSoundE(6);
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
                            gamePanel.playSoundE(7);
                            if(gamePanel.ui.commandNum == 0) { // resume
                                gamePanel.currState = gameState.PLAY;
                            }
                            if(gamePanel.ui.commandNum == 1) { // Settings
                                //gamePanel.currState = gameState.SETTINGS;
                            }
                            if(gamePanel.ui.commandNum == 2) { // Main Menu
                                
                                gamePanel.currState = gameState.TITLE;
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
                            gamePanel.playSoundE(2);
                        }
                        cluck = true;
                        break;

                    // Pause
                    case KeyEvent.VK_ESCAPE:
                        if (!paused) {
                            gamePanel.currState = gameState.PAUSE;
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
                            gamePanel.playSoundE(6);
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
                            gamePanel.playSoundE(6);
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
                            gamePanel.playSoundE(7);
                            if(gamePanel.ui.commandNum == 0){ // Play
                                gamePanel.mapM.resetMap();
                                gamePanel.ui.resetTimer();
                                gamePanel.player.setDefaultValues();
                                gamePanel.currState = gameState.PLAY;
                            }
                            if(gamePanel.ui.commandNum == 1) { // Settings 
                                //TODO: implement for setting
                            }
                            if(gamePanel.ui.commandNum == 2) { // Credits
                                //TODO: implement for credit
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
                            gamePanel.playSoundE(6);
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
                            gamePanel.playSoundE(6);
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
                            gamePanel.playSoundE(7);
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
                break;
        }

    }

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
                        gamePanel.playSoundE(3);
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
