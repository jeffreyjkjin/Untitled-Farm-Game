package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import map.MapManager; 
import tile.TileManager;
import pathfinding.Pathfinding;
import app.CollisionChecker;

public class GamePanel extends JPanel implements Runnable {
    // Game States
    public enum gameState {
        PLAY,
        PAUSE,
        WIN,
        LOSE,
        TITLE
    }
    
    public gameState currState = gameState.PLAY;

    // Tiles
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // Screen
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
    // FPS
    final int FPS = 60;

    // System
    InputHandler input = new InputHandler(this);
    Sound sound = new Sound();
    Thread gameThread;
    public UI ui = new UI(this);
    boolean hitboxTest = true; // FOR TESTING. Draws hitbox
    
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
        
        playMusic(0);
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
                repaint();
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

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphic2 = (Graphics2D) graphic;
        
        // Map
        mapM.draw(graphic2);

        // Player
        player.draw(graphic2);            
        
        // UI
        ui.draw(graphic2);
        
        graphic2.dispose();
    } // The order inside is really important! It could hide your player or objects and etc.

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
