package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

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
        while(gameThread != null) {
            update();
            repaint();
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

        graphic2.dispose();
    }
}
