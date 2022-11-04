package map;

import java.awt.Graphics2D;

import app.GamePanel;

import app.GamePanel.gameState;

import object.SuperObject;

public class MapManager {
    GamePanel gamePanel;
    Map mapList[];
    int currMap;

    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mapList = new Map[2]; // 2 maps, can increase/decrease as needed;
        currMap = 0;

        loadMapFiles();
    }
    
    private void loadMapFiles() {
        mapList[0] = new Map(gamePanel, "/levels/stage1.txt");
        mapList[1] = new Map(gamePanel, "/levels/stageTest2.txt");
        
        // add more maps here
    }

    public void setupMap() {
        mapList[currMap].setObject();
        mapList[currMap].setFarmer();
    }

    public void resetMap() {
        currMap = 0;
        loadMapFiles();
        setupMap();
    }

    public void draw(Graphics2D g2) {
        mapList[currMap].drawTiles(g2);
        mapList[currMap].drawObjects(g2);
        mapList[currMap].drawFarmers(g2);
    }

    public void nextMap() {
        // + 1000 score for making it to the next level
        gamePanel.player.score += 1000;
        // If final level is beaten, get players score and set as high score if it is new best
        if (currMap == mapList.length-1) {
            if (gamePanel.player.score > gamePanel.settings.getHighScore()) {
                gamePanel.settings.setHighScore(gamePanel.player.score);
                gamePanel.settings.saveConfigFile();
            }

            gamePanel.currState = gameState.WIN;
        }
        else { // On non-final levels, increment what level you are on and set up the new map
            currMap++;

            gamePanel.player.keyCount = 0;
            
            setupMap();
            gamePanel.player.spawnPlayer();
        }
    }
    
    public Map getMap() {
        return mapList[currMap];
    }

    public int[][] getTileMap() {
        return mapList[currMap].tileMap;
    }

    public SuperObject getObject(int index) {
        return mapList[currMap].objects[index];
    }

}
