package com.example.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.GamePanel;
import com.example.InputHandler;

public class Player extends Entity{

    GamePanel gamePanel;
    InputHandler input;
    
    public Player(GamePanel gamePanel, InputHandler input) {
        this.gamePanel = gamePanel;
        this.input = input;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {
        if (input.up) {
            y -= speed;
        }
        else if (input.left) {
            x -= speed;
        }
        else if (input.down) {
            y += speed;
        }
        else if (input.right) {
            x += speed;
        }       
    }

    public void draw(Graphics2D graphic2) {
        graphic2.setColor(Color.green);
        graphic2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }
}
