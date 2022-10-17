package com.example.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.GamePanel;
import com.example.InputHandler;

public class Player extends Entity{

    GamePanel gamePanel;
    InputHandler input;
    int hasKey = 0;
    
    
    public Player(GamePanel gamePanel, InputHandler input) {
        this.gamePanel = gamePanel;
        this.input = input;
        
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        hitbox = new Rectangle(8,16,32,32);
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (input.up) {
            direction = "up";
        }
        else if (input.left) {
            direction = "left";
        }
        else if (input.down) {
            direction = "down";
        }
        else if (input.right) {
            direction = "right";
        }    
        
        collisionOn = false;
        if(collisionOn == false) {
            switch(direction) {
                case "up": y -= speed; break;
                case "down": x -= speed; break;
                case "left": y += speed; break;
                case "right": x += speed; break;
            }
        }
    }
    
    public void pickUpObject(int index) {
    	
    	if(index != 999) {
    		if(gamePanel.obj[index].name == "Key")
    		{
    			hasKey++;
    			gamePanel.obj[index] = null;
    		}
    		
    	}
    }

    public void draw(Graphics2D graphic2) {
        graphic2.setColor(Color.green);
        graphic2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
    }
}
