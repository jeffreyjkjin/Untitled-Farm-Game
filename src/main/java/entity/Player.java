package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import app.GamePanel;
import app.InputHandler;

public class Player extends Entity{

    GamePanel gamePanel;
    InputHandler input;
    
    public Player(GamePanel gamePanel, InputHandler input) {
        this.gamePanel = gamePanel;
        this.input = input;

        hitbox = new Rectangle(8,16,32,32);

        setDefaultValues();
        // getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    // TODO: find player image sprites
    // public void getPlayerImage() {
    //     try {
    //         up1 = ImageIO.read(getClass().getResourceAsStream(""));
    //         up2 = ImageIO.read(getClass().getResourceAsStream(""));
    //         down1 = ImageIO.read(getClass().getResourceAsStream(""));
    //         down2 = ImageIO.read(getClass().getResourceAsStream(""));
    //         left1 = ImageIO.read(getClass().getResourceAsStream(""));
    //         left2 = ImageIO.read(getClass().getResourceAsStream(""));
    //         right1 = ImageIO.read(getClass().getResourceAsStream(""));
    //         right2 = ImageIO.read(getClass().getResourceAsStream(""));
    //     } catch(IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void update() {
        if (input.up || input.left || input.down || input.right) {
            if (input.up) {
                y -= speed;
                direction = "up";
            }
            else if (input.left) {
                x -= speed;
                direction = "left";
            }
            else if (input.down) {
                y += speed;
                direction = "down";
            }
            else if (input.right) {
                x += speed;
                direction = "right";
            }
        
        collisionOn = false;
        gamePanel.checker.checkCollision(this);

    
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
    }

    public void draw(Graphics2D graphic2) {
        graphic2.setColor(Color.green);
        graphic2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        // TODO: remove above code when player sprites have been added
        // BufferedImage image = null;
        
        // switch (direction) {
        //     case "up":
        //         if (spriteNum == 1) {
        //             image = up1;
        //         }
        //         else {
        //             image = up2;
        //         }
        //         break;
        //     case "left":
        //         if (spriteNum == 1) {
        //             image = left1;
        //         }
        //         else {
        //             image = left2;
        //         }
        //         break;
        //     case "down":
        //         if (spriteNum == 1) {
        //             image = down1;
        //         }
        //         else {
        //             image = down2;
        //         }
        //         break;
        //     case "right":
        //         if (spriteNum == 1) {
        //             image = right1;
        //         }
        //         else {
        //             image = right2;
        //         }
        // }
        // graphic2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
