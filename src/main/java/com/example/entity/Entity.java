package com.example.entity;

import java.awt.Rectangle;

public class Entity {
    public int x, y;
    public int speed;
    public String direction;
    
    public Rectangle hitbox;
    public int hitboxDefaultX, hitboxDefaultY;
    
    public boolean collisionOn;
}
