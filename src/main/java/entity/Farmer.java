package entity;

import app.GamePanel;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Farmer extends Entity {

    GamePanel gamePanel;

    public int screenX, screenY;

    public Farmer(GamePanel gp)
    {
        this.gamePanel = gp;

        speed = 4;
        hitboxDefaultX = 14;
        hitboxDefaultY = 16;
        hitbox = new Rectangle(hitboxDefaultX, hitboxDefaultY, 32, 40);
        direction = "down";

        getFarmerImage();
    }

    public void getFarmerImage()
    {
        try 
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/enemy/chickendown1.png"));
        } catch(IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void setAction()
    {
        // Movement AI
    }

    public void update()
    {
        // TODO determine direction with pathfinding algo and change direction string var

        collisionOn = false;
        gamePanel.checker.checkCollision(this);
        /*
        if(collisionOn == false) {
            switch(direction){
                case"up":
                    worldY -= speed;
                    break;
                case"down":
                    worldY += speed;
                    break;
                case"left":
                    worldX -= speed;
                    break;
                case"right":
                    worldX += speed;
                    break;
            }
        }
        */

        spriteCounter++;
    
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphic2) {
        BufferedImage image = null;

        screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                else {
                    image = up2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                else {
                    image = left2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                else {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                else {
                    image = right2;
                }
        }
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
        worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
        worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
        worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY)
            {
            graphic2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
    }
}
