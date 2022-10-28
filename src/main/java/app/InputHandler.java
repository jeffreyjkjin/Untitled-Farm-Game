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
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(gamePanel.currState){

            //PAUSE STATE
            case PAUSE:
                switch(keyCode) {
                    case KeyEvent.VK_ESCAPE:
                    if (!paused) {
                        gamePanel.currState = gameState.PLAY;
                    }
                    paused = true;
                }
                break;

            //PLAY STATE
            case PLAY:
                switch(keyCode) {
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
                    case KeyEvent.VK_H:
                        if (!cluck) {
                            gamePanel.playSoundE(2);
                        }
                        cluck = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (!paused) {
                            gamePanel.currState = gameState.PAUSE;
                        }
                        paused = true;
                }
                break;
            
            // TILE STATE
            case TITLE:
                switch(keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (!select) {
                            gamePanel.playSoundE(6);
                            gamePanel.ui.commandNum--;
                            if(gamePanel.ui.commandNum < 0){
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
                            if(gamePanel.ui.commandNum > 3){
                                gamePanel.ui.commandNum = 0;
                            }
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            gamePanel.playSoundE(7);
                            if(gamePanel.ui.commandNum == 0){
                                gamePanel.currState = gameState.PLAY;
                                gamePanel.playMusic(0);
                            }
                            if(gamePanel.ui.commandNum == 1){
                                //TODO: implement for setting
                            }
                            if(gamePanel.ui.commandNum == 2){
                                //TODO: implement for credit
                            }
                            if(gamePanel.ui.commandNum == 3){
                                System.exit(0);
                            }
                        }
                        enter = true;
                        break;
                }
                break;

            // WIN STATE
            case WIN:
                break;

            // LOSE STATE
            case LOSE:
                switch(keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if (!select) {
                            gamePanel.playSoundE(6);
                            gamePanel.ui.commandNum--;
                            if(gamePanel.ui.commandNum < 0){
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
                            if(gamePanel.ui.commandNum > 2){
                                gamePanel.ui.commandNum = 0;
                            }
                        }
                        select = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        if (!enter) {
                            gamePanel.playSoundE(7);
                            if(gamePanel.ui.commandNum == 0){
                                gamePanel.currState = gameState.PLAY;
                            }
                            if(gamePanel.ui.commandNum == 1){
                                //TODO: implement for setting
                                gamePanel.currState = gameState.TITLE;
                            }
                            if(gamePanel.ui.commandNum == 2){
                                
                                System.exit(0);
                                //TODO: implement for credit
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

            // PAUSE STATE
            case PAUSE:
            switch(keyCode) {
                case KeyEvent.VK_ESCAPE:
                paused = false;
                break;
            }
            break;

            // PLAY STATE
            case PLAY:
            switch(keyCode) {
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
                case KeyEvent.VK_H:
                gamePanel.playSoundE(3);
                cluck = false;
                break;
                case KeyEvent.VK_ESCAPE:
                paused = false;
                break;
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

            // LOSE STATE
            case LOSE:

            // TITLE STATE
            case TITLE:
                switch(keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        select = false;
                        break;
                    case KeyEvent.VK_ENTER:
                        enter = false;
                }
                break;

            // WIN STATE
            case WIN:
                break;
        }
    }
}
