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
    
    BufferedImage fall[] = new BufferedImage[10];
    int fallCounter = 0;
    int fallSpritePosition = 0;

    public int screenX, screenY, startingX, startingY;
    protected final static int frozen = 0; // This and below are for speed
    protected final static int normal = 2;
    protected int freezeTimer = 0;

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
    private void getFarmerImage()
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

            fall[0] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall1.png"));
            fall[1] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall2.png"));
            fall[2] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall3.png"));
            fall[3] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall4.png"));
            fall[4] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall5.png"));
            fall[5] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall6.png"));
            fall[6] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall7.png"));
            fall[7] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall8.png"));
            fall[8] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall9.png"));
            fall[9] = ImageIO.read(getClass().getResourceAsStream("/farmer/farmerfall10.png"));


        } catch(IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * This function is called each time the farmer updates (60 times per second)
     * Sets what the farmer will attempt to do which is currently just path towards the player and attempt to catch them
     */
    private void setAction()
    {
        int goalCol = (gamePanel.player.worldX + gamePanel.player.hitbox.x) / gamePanel.tileSize;
        int goalRow = (gamePanel.player.worldY + gamePanel.player.hitbox.y) / gamePanel.tileSize;
            
        gamePanel.pathFinder.searchPath(goalCol, goalRow, this);
    }

    /**
     * Main "brain" of the farmer. Controls what it does and how it acts
     * Finds most efficient path to the player by calling setAction() and then checks various collision factors
     * If there are no collisions, the farmer can move on this path towards the player
     * If there are collisions, farmer does not move
     */
    public void update()
    {
        // If farmer is frozen, do nothing until they are unfrozen
        if (freezeTimer > 0)
        {
            freezeTimer--;
            return;
        }
        else
        {
            speed = normal;
            fallCounter = 0;
            fallSpritePosition = 0;
        }

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

        // Draw farmer normally if they are not frozen
        if (freezeTimer == 0) {
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
        }
        else {
            // If farmer is frozen, play falling animation for farmer
            fallCounter++;
            if (fallCounter < 60) {
                // Farmer falling sprites are stored in an array
                // Every 6 frames, change the sprite of the farmer falling by incrementing fallPos which sets image to a different falling sprite
                if (fallCounter % 6 == 0) {
                    fallSpritePosition++;
                }
            }
            image = fall[fallSpritePosition];
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
     * Resets all of the farmers to their starting locations and turns collision off
     * Called when player and farmers collide and everything needs to be reset
     * 
     * @param farmers array of farmers stored in map
     */
    protected static void respawnFarmers(Farmer[] farmers)
    {
        for (int i = 0; i < farmers.length; i++)
        {
            if (farmers[i] != null)
            {
                farmers[i].worldX = farmers[i].startingX;
                farmers[i].worldY = farmers[i].startingY;
                farmers[i].collisionOn = false;
                farmers[i].freezeTimer = 0;
                farmers[i].speed = normal;
            }
        }
    }
}
