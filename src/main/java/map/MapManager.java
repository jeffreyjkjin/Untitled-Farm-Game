package map;

import java.awt.Graphics2D;

import app.GamePanel;

public class MapManager {
    GamePanel gamePanel;
    public Map mapList[];
    public int currMap;

    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mapList = new Map[10]; // 10 maps, can increase/decrease as needed;
        currMap = 0;

        loadMapFiles();
    }
    
    private void loadMapFiles() {
        mapList[0] = new Map(gamePanel, "/levels/levelTest1.txt");
        mapList[1] = new Map(gamePanel, "/levels/levelTest2.txt");
        
        // add more maps here
    }

    public void setupMap() {
        mapList[currMap].setObject();
        mapList[currMap].setFarmer();
    }

    public void draw(Graphics2D g2) {
        mapList[currMap].drawTiles(g2);
        mapList[currMap].drawObjects(g2);
        mapList[currMap].drawFarmers(g2);
    }

    public void nextMap() {
        currMap++;
        setupMap();
    }

    public Map getCurrentMap() {
        return mapList[currMap];
    }

}
