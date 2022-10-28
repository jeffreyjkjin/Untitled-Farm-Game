package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entity.Player;
import map.MapManager; 
import tile.TileManager;
import pathfinding.Pathfinding;

public class GamePanel extends JPanel implements Runnable {
    // Game States
    public enum gameState {
        PLAY,
        PAUSE,
        WIN,
        LOSE,
        TITLE
    }
    
    public gameState currState = gameState.TITLE;

    // Tiles
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // Screen
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pix.
    public final int screenHeight = tileSize * maxScreenRow; // 576 pix.
    // Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D full_g2;
    
    
    // FPS
    final int FPS = 60;

    // System
    InputHandler input = new InputHandler(this);
    Sound sound = new Sound();
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
    
    public void setupGame() {
        mapM.setupMap();
        
        // playMusic(0);
        currState = gameState.TITLE;
        
        // For FullScreen: draw on tempscreen(g2) and resize it
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        full_g2 = (Graphics2D)tempScreen.getGraphics();
        
        //setFullScreen();
    }
    
    public void setFullScreen() {
    	
    	// Get Local Screen Device
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gd = ge.getDefaultScreenDevice();
    	gd.setFullScreenWindow(App.window);
    	
    	// Get Full Screen width & height
    	screenWidth2 = App.window.getWidth();
    	screenHeight2 = App.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

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
                repaint(); //-> changed it to drawToTempScreen/drawToScreen for FullScreen
                //drawToTempScreen(); // draw to the BufferedImage
                //drawToScreen(); // draw BufferImage to the screen
                delta--;
            }
        }
    }

    public void update() {
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
            case PAUSE:
                break;
            case WIN:
                break;
            case LOSE:
                break;
            case TITLE:
                break;
        }
    }
    
    public void drawToTempScreen() {
    	
    	switch(currState) {
        case PAUSE:
        case PLAY:
            // Map
            mapM.draw(full_g2);
            
            // Player
            player.draw(full_g2);            
            
            // UI
            ui.draw(full_g2);
            break;
        case TITLE:
            ui.draw(full_g2);
            break;
        case LOSE:
        case WIN:
            ui.draw(full_g2);
            break;
    } // The order inside is really important! It could hide your player or objects and etc.
    }
    
    public void drawToScreen() {
    	
    	Graphics g = getGraphics();
    	g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
    	g.dispose();
    }
    
    
     //* This Method was used to draw things directly which can't use on FullScreen
     
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
            case TITLE:
                ui.draw(graphic2);
                break;
            case LOSE:
                ui.draw(graphic2);
                break;
            case WIN:
                ui.draw(graphic2);
                break;
        }

        graphic2.dispose();
    } 
	
    
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }

    public void playSoundE(int i) {
        sound.setFile(i);
        sound.play();
    }
} 
