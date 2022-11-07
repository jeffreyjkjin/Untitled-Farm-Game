package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

import audio.SoundEffects;
import audio.Music;
import entity.Player;
import map.MapManager; 
import tile.TileManager;
import pathfinding.Pathfinding;
import settings.Settings;

/**
 * Manages and controls all the systems involved with the game.
 * Other classes should have access to this object so that it can interact with other game systems.
 * 
 * @author Jeffrey Jin (jjj9)
 */
public class GamePanel extends JPanel implements Runnable {
    // Game States
    public enum gameState {
        PLAY,
        PAUSE,
        WIN,
        LOSE,
        TITLE,
        SETTINGS,
        CREDITS
    }
    
    public gameState currState = gameState.TITLE;

    // Tiles
    private final int originalTileSize = 16;
    public int scale = 3; 
    public int tileSize = originalTileSize * scale;

    // Screen
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 960 pix.
    public int screenHeight = tileSize * maxScreenRow; // 576 pix.
    public Boolean fullScreen;

    // FPS
    private final int FPS = 60;

    // System
    public Settings settings = new Settings();
    public Music music = new Music(this);
    public SoundEffects sound = new SoundEffects(this);
    InputHandler input = new InputHandler(this);
    Thread gameThread;
    public UI ui = new UI(this);
    
    // Maps
    public TileManager tileM = new TileManager(this);
    public MapManager mapM = new MapManager(this);
    
    // Entity & Object
    public CollisionChecker checker = new CollisionChecker(this);
    public Player player = new Player(this, input);
    public Pathfinding pathFinder = new Pathfinding(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
    }
    
    protected void setupGame() {
        mapM.setupMap();
        
        currState = gameState.TITLE;

        if (ui.fullScreen) {
            setFullScreen();
        }
        else {
            setWindowScreen();
        }
    }
    
    /**
     * Stores the size of the users computer screen and sets the game to fullscreen mode.
     * Scaling of the tiles are also increased.
     */
    protected void setFullScreen() {
    	// Get Local Screen Device
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(App.window);
    	
    	// Get Full Screen width & height
    	screenWidth = App.window.getWidth();
    	screenHeight = App.window.getHeight();

        scale = 4;
        tileSize = scale * originalTileSize;
    }

    /**
     * Sets the game window to windowed mode.
     * Scaling of the tiles are also decreased.
     */
    protected void setWindowScreen() {
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();

        scale = 3;
        tileSize = scale * originalTileSize;

        screenWidth = tileSize * maxScreenCol; // 960 pix.
        screenHeight = tileSize * maxScreenRow; // 576 pix.
        
        gd.setFullScreenWindow(null);
    }

    /**
     * Starts the main game loop.
     */
    protected void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Runs the main game loop. 
     * Updates all entities and objects and draws game sprites 60 times per second.
     * Uses delta time method to ensure game speed is consistent no matter what hardware the game is run on.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates based on the FPS (currently 60, so 60 times per second)
     * Need to call all entities that are animate so that they will determine what to do on each frame
     */
    private void update() {
        switch(currState) {
            case PLAY:
                player.update();

                for (int i = 0; i < mapM.getMap().farmers.length; i++)
                {
                    if (mapM.getMap().farmers[i] != null)
                    {
                        mapM.getMap().farmers[i].update();
                    }
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * Draws the games sprites onto the screen depending on the games current state.
     * 
     * @param graphic graphics object
     */
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphic2 = (Graphics2D) graphic;
        
        switch(currState) {
            case PAUSE:
            case PLAY:
                // Map
                mapM.draw(graphic2);
                
                // Player
                player.draw(graphic2);            
                
                // UI
                ui.draw(graphic2);
                break;
            case CREDITS:
            case TITLE:
            case LOSE:
            case SETTINGS:
            case WIN:
                ui.draw(graphic2);
                break;
        }

        graphic2.dispose();
    }
	
} 
