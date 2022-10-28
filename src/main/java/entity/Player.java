package entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.io.IOException;
import javax.imageio.ImageIO;

import app.GamePanel;
import app.GamePanel.gameState;
import app.InputHandler;

public class Player extends Entity{

    GamePanel gamePanel;
    InputHandler input;
    public final int screenX;
    public final int screenY;
    
    public int health = 3;
    public int score = 0;
    public int keyCount = 0;
    
    
    public Player(GamePanel gamePanel, InputHandler input) {
        this.gamePanel = gamePanel;
        this.input = input;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        
        hitbox = new Rectangle(10, 16, 28, 32); //2*10 +28 = 48 (tileSize), 16 +32 = 48
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.mapM.getMap().playerStartX; // starting position
        worldY = gamePanel.mapM.getMap().playerStartY;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenup1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenup2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickendown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickendown2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenleft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenleft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenright1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/chicken/chickenright2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (health == 0) {
            gamePanel.currState = gameState.LOSE;
        }

        if (input.up || input.left || input.down || input.right) {
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
        gamePanel.checker.checkCollision(this);
        
        int objIndex = gamePanel.checker.checkObjectCollision(this, true);
        objectInteraction(objIndex);

        // Check for collision with enemy
        int farmerIndex = gamePanel.checker.checkFarmerCollision(this, gamePanel.mapM.getMap().farmers);
        farmerInteraction(farmerIndex);

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

    public void objectInteraction(int index) {
        if (index != 999) {
            String objectName = gamePanel.mapM.getMap().objects[index].name;
            
            switch(objectName) {
                case "Egg":
                    gamePanel.playSoundE(4);
                    if (health >= 3) {
                        score += 100;
                    }
                    else {
                        health++;
                    }
                    gamePanel.mapM.getMap().objects[index] = null;
                    gamePanel.ui.showMessage("My Egg!");
                    break;
                case "Key":
                    gamePanel.mapM.getMap().objects[index] = null;
                    keyCount++;
                    if (keyCount == gamePanel.mapM.getMap().keyNum) {
                        gamePanel.mapM.getObject(gamePanel.mapM.getMap().gateIndex).update(gamePanel);
                    }
                    gamePanel.playSoundE(4);
                    break;
                case "Gate":
                    if (keyCount == gamePanel.mapM.getMap().keyNum) {
                        gamePanel.mapM.getObject(index).update(gamePanel);
                    }
                    break;
            }
        }
    }

    public void farmerInteraction(int index)
    {
        if (index != 999)
        {
            health--;
            gamePanel.ui.showMessage("TEST! HP minus 1");

            // Reset player to start coordinates
            worldX = gamePanel.mapM.getMap().playerStartX;
            worldY = gamePanel.mapM.getMap().playerStartY;

            gamePanel.playSoundE(5);
        }
    }

    public void draw(Graphics2D graphic2) {
        BufferedImage image = null;
        
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
        graphic2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
