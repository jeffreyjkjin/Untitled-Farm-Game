package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import tile.*;

import object.SuperObject;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 16; // can change this and row
    public final int maxWorldRow = 16;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    InputHandler input = new InputHandler();
    public CollisionChecker checker = new CollisionChecker(this);
    Sound sound = new Sound();
    Thread gameThread;
    public Player player = new Player(this, input);
    public SuperObject obj[] = new SuperObject[10]; // 10 slots for object
    public AssetSetter aSetter = new AssetSetter(this);

    
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
    }
    
    public void setupGame() {
    	aSetter.setObject();

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
        long timer = 0;
        int FPSCount = 0;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                FPSCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + FPSCount);
                FPSCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphic2 = (Graphics2D) graphic;
        
        //Tile
        tileM.draw(graphic2);
        
        //Player
        player.draw(graphic2);

        //Object
        for(int i = 0; i < obj.length; i++) {
        	if(obj[i] != null)
        		obj[i].draw(graphic2, this);
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
