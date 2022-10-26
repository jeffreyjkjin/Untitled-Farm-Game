package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Farmer;
import entity.Player;
import object.SuperObject;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable {
    // Game States
    public enum gameState {
        PLAY,
        PAUSE,
        WIN,
        LOSE
    }

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
    public TileManager tileM = new TileManager(this);
    public Map map = new Map(this, "/levels/levelTest1.txt");
    InputHandler input = new InputHandler(this);
    public CollisionChecker checker = new CollisionChecker(this);
    Sound sound = new Sound();
    Thread gameThread;
    public UI ui = new UI(this);
    
    // Entity & Object
    public Player player = new Player(this, input);
    public SuperObject obj[] = new SuperObject[10]; // 10 slots for object
    public Farmer farmers[] = new Farmer[10];
    
    // Game State
    public gameState currState;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
    }
    
    public void setupGame() {
        map.setObject();
        map.setFarmer();
        
        currState = gameState.PLAY;

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

                for (int i = 0; i < farmers.length; i++)
                {
                    if (farmers[i] != null)
                    {
                        farmers[i].update();
                    }
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

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphic2 = (Graphics2D) graphic;
        
        //Tile
        map.drawTiles(graphic2);
        
        //Object
        map.drawObjects(graphic2);

        //Farmers
        map.drawFarmers(graphic2);

        //Player
        player.draw(graphic2);        
        
        //UI
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
