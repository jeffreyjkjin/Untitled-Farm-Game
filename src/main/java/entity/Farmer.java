package entity;

import app.GamePanel;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Math;

/** 
 * An entity called Farmer which is the main enemy of the game. These are stored in an array in the map
 * Has a different image for each direction it moves in
 * Constantly tries to path towards the player character. If successful, player loses a life and is reset to spawn location
 * 
 * @author Andrew Hein (ach17)
*/
public class Farmer extends Entity {

    GamePanel gamePanel;

    public int screenX, screenY, startingX, startingY;
    public static int frozen = 0; // This and below are for speed
    public static int normal = 2;

    /**
     *  Constructs the farmer and sets some default values like speed, creating hitbox, and getting images for later
     * 
     * @param gp
     */
    public Farmer(GamePanel gp)
    {
        this.gamePanel = gp;

        speed = normal;
        hitboxDefaultX = 10;
        hitboxDefaultY = 16;
        hitbox = new Rectangle(hitboxDefaultX, hitboxDefaultY, 28, 32); //2*10 +28 = 48 (tileSize), 16 +32 = 48
        direction = "down";

        getFarmerImage();
    }

    /**
     * Reads in the farmers direction-based images and saves them in variables for later uses when farmer is drawn and moving
     */
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

    /**
     * This function is called each time the farmer updates (60 times per second)
     * Sets what the farmer will attempt to do which is currently just path towards the player and attempt to catch them
     */
    public void setAction()
    {
        int goalCol = (gamePanel.player.worldX + gamePanel.player.hitbox.x) / gamePanel.tileSize;
        int goalRow = (gamePanel.player.worldY + gamePanel.player.hitbox.y) / gamePanel.tileSize;
            
        searchPath(goalCol, goalRow);
    }

    /**
     * Main "brain" of the farmer. Controls what it does and how it acts
     * Finds most efficient path to the player by calling setAction() and then checks various collision factors
     * If there are no collisions, the farmer can move on this path towards the player
     * If there are collisions, farmer does not move
     */
    public void update()
    {
        setAction();
        collisionOn = false;
        gamePanel.checker.checkPlayerCollision(this);
        // gamePanel.checker.checkEntityCollision(this, gamePanel.mapM.getMap().farmers);
        // Removing the above line as it is causing a lot of bugs while not adding much to the game
        // May re-add later but currently not a priority until rest of the game is completed
        
         if(!collisionOn) 
         {
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

        // Set up variables to determine the distance this farmer and the player are apart
        // Using Pythagorean theorem
        double midPX = gamePanel.player.worldX + gamePanel.player.hitbox.x + (gamePanel.player.hitbox.width / 2);
        double midPY = gamePanel.player.worldY + gamePanel.player.hitbox.y + (gamePanel.player.hitbox.height / 2);
        double midFX = this.worldX + this.hitbox.x + (this.hitbox.width / 2);
        double midFY = this.worldY + this.hitbox.y + (this.hitbox.height / 2);

        double ac = Math.abs(midPY - midFY);
        double cb = Math.abs(midPX - midFX);

        double distanceApart = Math.hypot(ac, cb);
        // This is the only reliable fix I could come up with. Play around with this number to see what feels best
        // Can realistically change this number to 32 and nobody but the developers would notice a bug
        // It would just introduce a scenario where the farmers can not catch you if you stand still in a certain way
        // 45 Feels a bit annoying tbh so probably 32 for the final game
        if (distanceApart <= 45)
        {
            gamePanel.player.farmerInteraction(0);
        }

        gamePanel.checker.checkTileCollision(this);
        // gamePanel.checker.checkEntityCollision(this, gamePanel.mapM.getMap().farmers);
        // Removing the above line as it is causing a lot of bugs while not adding much to the game
        // May re-add later but currently not a priority until rest of the game is completed

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

    /**
     * Finds out where the farmer should be on the screen (if anywhere)
     * Also sets the image of the farmer based on the direction it is going
     * If the farmer should be on the players screen, this function draws them
     * 
     * @param graphic2 main graphic used to draw everything to the screen
     */
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

    /**
     * Uses the pathfinding class to find the most efficient path to the player
     * Once path is found, sets the appropriate direction farmer needs to go to avoid collisions based on next tile in path
     * 
     * @param goalCol goal column farmer attempts to reach. Currently the players current column
     * @param goalRow goal row farmer attempts to reach. Currently the players current row
     */
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

    /**
     * Resets all of the farmers to their starting locations and turns collision off
     * Called when player and farmers collide and everything needs to be reset
     * 
     * @param farmers array of farmers stored in map
     */
    public static void respawnFarmers(Farmer[] farmers)
    {
        for (int i = 0; i < farmers.length; i++)
        {
            if (farmers[i] != null)
            {
                farmers[i].worldX = farmers[i].startingX;
                farmers[i].worldY = farmers[i].startingY;
                farmers[i].collisionOn = false;
            }
        }
    }
}
