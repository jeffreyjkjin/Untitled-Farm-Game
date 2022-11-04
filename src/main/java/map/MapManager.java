package map;

import java.awt.Graphics2D;

import app.GamePanel;

import app.GamePanel.gameState;

import object.SuperObject;

/**
 * Stores each map for the game and manages progression between them.
 * Keeps track of the current level so that its corresponding tiles, objects and entities can be drawn.
 * Needs to be instantiated from GamePanel so that other objects can access its data.
 *  
 * @author Jeffrey Jin (jjj9)
 * @see map.Map
 */
public class MapManager {
    GamePanel gamePanel;
    Map mapList[];
    int currMap;

    /**
     * Constructs a new MapManager object and loads map files.
     * Links gamePanel to this object so that it can be used by its methods.
     * Maps are stored in an array whose size can be raised or lowered to support less or more maps as needed.
     * 
     * @param gamePanel GamePanel object that is used to run the game
     */
    public MapManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mapList = new Map[2]; // 2 maps, can increase/decrease as needed;
        currMap = 0;

        loadMapFiles();
    }
    
    /**
     * Instantiates new Map classes for the specified levels.
     * This method can be modified to add new maps.
     */
    private void loadMapFiles() {
        mapList[0] = new Map(gamePanel, "/levels/stage2.txt");
        mapList[1] = new Map(gamePanel, "/levels/stage1.txt");
        
        // add more maps here
    }

    /**
     * Sets up the objects and entities on the current map.
     */
    public void setupMap() {
        mapList[currMap].setObject();
        mapList[currMap].setFarmer();
    }

    /**
     * Reloads all the map files and sets the current map to the first level.
     */
    public void resetMap() {
        currMap = 0;
        loadMapFiles();
        setupMap();
    }

    /**
     * Calls the Map methods to draw tiles, objects and entities to onto the screen.
     * 
     * @param g2 the main graphics object used to draw tile, object and entity sprites to the screen
     */
    public void draw(Graphics2D g2) {
        mapList[currMap].drawTiles(g2);
        mapList[currMap].drawObjects(g2);
        mapList[currMap].drawFarmers(g2);
    }

    /**
     * Advances the player to the next game map.
     * Increases players score by 1000 points every time a map is completed.
     * Resets players key counter and setups the next map.
     * If final level is completed, it sends the user to the win screen; if the players score is also greater than the saved high score, update and save it to the config file.
     */
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
    
    /**
     * @return the current map
     */
    public Map getMap() {
        return mapList[currMap];
    }

    /**
     * @return the tile map of the current map
     */
    public int[][] getTileMap() {
        return mapList[currMap].tileMap;
    }

    /**
     * @param index the index of the object in the object array of the current map
     * @return the object at the index of the object array of the current map
     */
    public SuperObject getObject(int index) {
        return mapList[currMap].objects[index];
    }

}
