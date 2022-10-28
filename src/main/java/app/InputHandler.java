package app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import app.GamePanel.gameState;

public class InputHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right;
    public boolean cluck;

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
                    if (gamePanel.currState == gameState.PLAY) {
                        gamePanel.currState = gameState.PAUSE;
                    }
                    else {
                        gamePanel.currState = gameState.PLAY;
                    }
            }
                break;
            case TITLE:
            switch(keyCode) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    gamePanel.ui.commandNum--;
                    if(gamePanel.ui.commandNum < 0){
                        gamePanel.ui.commandNum = 3;
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    gamePanel.ui.commandNum++;
                    if(gamePanel.ui.commandNum > 3){
                        gamePanel.ui.commandNum = 0;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if(gamePanel.ui.commandNum == 0){
                        gamePanel.currState = gameState.PLAY;
                        gamePanel.playMusic(0);
                    }
                    if(gamePanel.ui.commandNum == 1){
                        //implement for setting
                    }
                    if(gamePanel.ui.commandNum == 2){
                        //implement for credit
                    }
                    if(gamePanel.ui.commandNum == 3){
                        System.exit(0);
                    }
                    break;
            }
                break;
            case PAUSE:
                break;
            case WIN:
                break;
            case LOSE:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

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
        }

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
    }
}
