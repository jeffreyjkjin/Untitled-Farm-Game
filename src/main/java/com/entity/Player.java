package com.entity;

import com.example.GamePanel;
import com.example.InputHandler;

public class Player extends Entity{

    GamePanel gamePanel;
    InputHandler input;
    
    public Player(GamePanel gamePanel, InputHandler input) {
        this.gamePanel = gamePanel;
        this.input = input;
    }
}
