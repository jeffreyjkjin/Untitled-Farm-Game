package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * Abstract class Entity is the super class of farmer (enemies) and player
 * This class has some general stats for enemies and player such as X and Y coordinates, speed, hitbox, etc.
 */
public abstract class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    public Rectangle hitbox;
    public int hitboxDefaultX, hitboxDefaultY;
    public boolean collisionOn;
}
