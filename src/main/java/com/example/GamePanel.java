package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import com.example.tile.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    InputHandler input = new InputHandler();
    Thread gameThread;

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
        if (input.up) {
            playerY -= playerSpeed;
        }
        else if (input.left) {
            playerX -= playerSpeed;
        }
        else if (input.down) {
            playerY += playerSpeed;
        }
        else if (input.right) {
            playerX += playerSpeed;
        }       
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphic2 = (Graphics2D)graphic;
        graphic2.setColor(Color.green);
        graphic2.fillRect(playerX, playerY, tileSize, tileSize);

        tileM.draw(graphic2);

        //player.draw(graphic2) goes here

        graphic2.dispose();
    }
}
