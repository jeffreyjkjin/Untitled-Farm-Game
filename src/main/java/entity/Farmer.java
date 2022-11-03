package entity;

import app.GamePanel;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Math;

public class Farmer extends Entity {

    GamePanel gamePanel;

    public int screenX, screenY, startingX, startingY;
    public boolean collision = true;
    public static int frozen = 0; // This and below are for speed
    public static int normal = 2;
    public boolean knownCollision = false;

    public Farmer(GamePanel gp)
    {
        this.gamePanel = gp;

        speed = normal;
        hitboxDefaultX = 10;
        hitboxDefaultY = 10;
        hitbox = new Rectangle(hitboxDefaultX, hitboxDefaultY, 28, 28); //2*10 +28 = 48 (tileSize), 16 +32 = 48
        direction = "down";

        getFarmerImage();
    }

    public void getFarmerImage()
    {
        try 
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerup1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerup2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerdown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerdown2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerleft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerleft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerright1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerright2.png"));
        } catch(IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void setAction()
    {
        int goalCol = (gamePanel.player.worldX + gamePanel.player.hitbox.x) / gamePanel.tileSize;
        int goalRow = (gamePanel.player.worldY + gamePanel.player.hitbox.y) / gamePanel.tileSize;
            
        searchPath(goalCol, goalRow);
    }

    public void update()
    {
        speed = normal;
        setAction();
        collisionOn = false;
        //gamePanel.checker.checkTileCollision(this);
        if (!gamePanel.checker.checkFarmerCollision(this, gamePanel.mapM.getMap().farmers))
        {
            entityCollisionOn = false;
        }

        knownCollision = false;

        int middleOfPlayerX = gamePanel.player.worldX + gamePanel.player.hitbox.x + (gamePanel.player.hitbox.width / 2);
        int distanceToPlayer = Math.abs(worldX - middleOfPlayerX);
        boolean goalRow = false;

        if (worldY / gamePanel.tileSize == gamePanel.player.worldY / gamePanel.tileSize)
        {
            goalRow = true;
        }
        
        if (distanceToPlayer < 48 && goalRow)
        {
            if (worldX > middleOfPlayerX)
            {
                direction = "left";
                worldX -= speed;
            }
            else if (worldX < middleOfPlayerX)
            {
                direction = "right";
                worldX += speed;
            }
        }
        
         /*else*/ if(!collisionOn && !entityCollisionOn) {
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

        gamePanel.checker.checkTileCollision(this);
        gamePanel.checker.checkFarmerCollision(this, gamePanel.mapM.getMap().farmers);

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
                // Can go up or left, have to figoure out which
                direction = "up";

                gamePanel.checker.checkTileCollision(this);

                if (collisionOn)
                {
                    direction = "left";
                }
            }
            else if(farmerTopY > nextY && farmerLeftX < nextX)
            {
                // Can go up or right
                direction = "up";

                gamePanel.checker.checkTileCollision(this);

                if (collisionOn)
                {
                    direction = "right";
                }
            }
            else if (farmerTopY < nextY && farmerLeftX > nextX)
            {
                // down or left
                direction = "down";

                gamePanel.checker.checkTileCollision(this);

                if (collisionOn)
                {
                    direction = "left";
                }

            }
            else if (farmerTopY < nextY && farmerLeftX < nextX)
            {
                // down or left
                direction = "down";

                gamePanel.checker.checkTileCollision(this);

                if (collisionOn)
                {
                    direction = "right";
                }
            }
        }
    }

    public static void respawnFarmers(Farmer[] farmers)
    {
        for (int i = 0; i < farmers.length; i++)
        {
            if (farmers[i] != null)
            {
                farmers[i].worldX = farmers[i].startingX;
                farmers[i].worldY = farmers[i].startingY;
                farmers[i].collisionOn = false;
                farmers[i].entityCollisionOn = false;
            }
        }
    }
}
