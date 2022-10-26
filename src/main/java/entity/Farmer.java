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
    public boolean collision = true;

    public Farmer(GamePanel gp)
    {
        this.gamePanel = gp;

        speed = 1;
        hitboxDefaultX = 14;
        hitboxDefaultY = 16;
        hitbox = new Rectangle(hitboxDefaultX, hitboxDefaultY, 32, 40);
        direction = "down";
        onPath = true;

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
        // Need to change it so they are only onPath when they can see the chicken?
        if(onPath)
        {
            int goalCol = (gamePanel.player.worldX + gamePanel.player.hitbox.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.hitbox.y) / gamePanel.tileSize;
            
            searchPath(goalCol, goalRow);
        }
    }

    public void update()
    {
        collisionOn = false;
        gamePanel.checker.checkCollision(this);

        setAction();
        gamePanel.checker.checkCollision(this);

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

    public void searchPath(int goalCol, int goalRow)
    {
        int currCol = (worldX + hitbox.x) / gamePanel.tileSize;
        int currRow = (worldY + hitbox.y) / gamePanel.tileSize;
        gamePanel.pathFinder.setNodes(currCol, currRow, goalCol, goalRow);
        boolean goalReached = gamePanel.pathFinder.search();

        if (goalReached)
        {
            // Next worldX and Y
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;
            // Next col and row
            int nextCol = gamePanel.pathFinder.pathList.get(0).col;
            int nextRow = gamePanel.pathFinder.pathList.get(0).row;
            // Entity's hitbox
            int farmerLeftX = worldX + hitbox.x;
            int farmerRightX = worldX + hitbox.x + hitbox.width;
            int farmerTopY = worldY + hitbox.y;
            int farmerBotY = worldY + hitbox.y + hitbox.height;
            // Find which direction to go next
            if (farmerTopY > nextY && farmerLeftX >= nextX && farmerRightX < nextX + gamePanel.tileSize)
            {
                direction = "up";
            }
            else if (farmerTopY < nextY && farmerLeftX >= nextX && farmerRightX < nextX + gamePanel.tileSize)
            {
                direction = "down";
            }
            else if (farmerTopY >= nextY && farmerBotY < nextY + gamePanel.tileSize)
            {
                // Can go left or right so have to figure out which
                if (farmerLeftX > nextX)
                {
                    direction = "left";
                }
                if (farmerLeftX < nextX)
                {
                    direction = "right";
                }

            }
            else if (farmerTopY > nextY && farmerLeftX > nextX)
            {
                // Can go up or left, have to figoure out wich
                direction = "up";

                gamePanel.checker.checkCollision(this);

                if (collisionOn)
                {
                    direction = "left";
                }
            }
            else if(farmerTopY > nextY && farmerLeftX < nextX)
            {
                // Can go up or right
                direction = "up";

                gamePanel.checker.checkCollision(this);

                if (collisionOn)
                {
                    direction = "right";
                }
            }
            else if (farmerTopY < nextY && farmerLeftX > nextX)
            {
                // down or left
                direction = "down";

                gamePanel.checker.checkCollision(this);

                if (collisionOn)
                {
                    direction = "left";
                }

            }
            else if (farmerTopY < nextY && farmerLeftX < nextX)
            {
                // down or left
                direction = "down";

                gamePanel.checker.checkCollision(this);

                if (collisionOn)
                {
                    direction = "right";
                }
            }

            if (nextCol == goalCol && nextRow == goalRow)
            {
                onPath = false;
            } 
        }

    }
}
