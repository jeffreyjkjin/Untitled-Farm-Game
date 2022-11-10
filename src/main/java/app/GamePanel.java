package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

import entity.Player;
import input.InputManager;
import map.MapManager; 
import pathfinding.Pathfinding;
import ui.UIManager;

/**
 * Manages and controls all the systems involved with the game.
 * Other classes should have access to this object so that it can interact with other game systems.
 * 
 * @author Jeffrey Jin (jjj9)
 */
public class GamePanel extends JPanel implements Runnable {
    // Tiles
    private final int originalTileSize = 16;
    public int scale = 3; 
    public int tileSize = originalTileSize * scale;

    // Screen
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 960 pix.
    public int screenHeight = tileSize * maxScreenRow; // 576 pix.

    // FPS
    private final int FPS = 60;

    // System
    InputManager inputM = new InputManager(this);
    Thread gameThread;
    public UIManager uiM = new UIManager(this);
    public StateManager stateM = new StateManager(this);
    
    // Maps
    public MapManager mapM = new MapManager(this);
    
    // Entity & Object
    public CollisionChecker checker = new CollisionChecker(this);
    public Player player = new Player(this, inputM.getPlayInput());
    public Pathfinding pathFinder = new Pathfinding(this);

    /**
     * Creates new GamePanel object and sets default attributes.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputM.getCurrentInput());
        this.setFocusable(true);
    }
    
    /**
     * Loads the game map.
     * Also sets fullscreen or window mode depending on what was saved in the settings file.
     */
    protected void setupGame() {
        mapM.setupMap();
        
        if (uiM.getFullScreen()) {
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
    public void setFullScreen() {
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
    public void setWindowScreen() {
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
        stateM.update();
    }
    
    /**
     * Draws the games sprites onto the screen depending on the games current state.
     * 
     * @param graphic graphics object
     */
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphic2 = (Graphics2D) graphic;
        
        stateM.draw(graphic2);

        graphic2.dispose();
    }
	
} 
