package entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.io.IOException;
import javax.imageio.ImageIO;

import app.GamePanel;
import app.StateManager.gameState;
import app.InputHandler;
import audio.SoundEffects;
import settings.Settings;

/**
 * Manages the player's stats, sprites and interactions.
 * Needs to be instantiated in the GamePanel object so that its attributes can be accessed by other classes.
 * 
 * @author Jeffrey Jin (jjj9)
 */
public class Player extends Entity{
    Settings settings;
    SoundEffects sound;
    GamePanel gamePanel;
    InputHandler input;

    public int screenX;
    public int screenY;
    
    public int health = 3;
    public int score = 0;
    public int keyCount = 0;
    public int freezeCooldown = 0;
    
    
    /**
     * Constructs a new Player object and creates its hitbox and area of sight on the game map.
     * Links Settings and Sound singleton to this object.
     * Also sets the default values of the player and loads its sprite images.
     * 
     * @param gamePanel GamePanel object that is used to the game
     * @param input InputHandler object that manages the players keyboard inputs
     */
    public Player(GamePanel gamePanel, InputHandler input) {
        this.gamePanel = gamePanel;
        this.input = input;

        settings = Settings.getInstance();
        sound = SoundEffects.getInstance();

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        
        hitbox = new Rectangle(12, 12, 24, 24); //2*10 +28 = 48 (tileSize), 16 +32 = 48
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the default values of the player.
     */
    public void setDefaultValues() {
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        worldX = gamePanel.mapM.getMap().playerStartX; // starting position
        worldY = gamePanel.mapM.getMap().playerStartY;
        speed = 4;
        health = 3;
        score = 0;
        keyCount = 0;
        direction = "down";
    }

    /**
     * Sets the players current location to the start position of the current map.
     */
    public void spawnPlayer() {
        worldX = gamePanel.mapM.getMap().playerStartX;
        worldY = gamePanel.mapM.getMap().playerStartY;
    }

    /**
     * Loads the players sprites.
     */
    private void getPlayerImage() {
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

    /**
     * Updates the Player's stats depending on what is happening in the game.
     * If Player's health points are zero, the user is sent to the gameover screen.
     * Checks if using is pressing movement keys and moves the Player in the corresponding direction.
     * Changes the players sprite depending on which direction the user is moving in.
     * Also checks if player is colliding with an object or entity.
     */
    public void update() {
        if (health == 0) {
            if (gamePanel.player.score > settings.getHighScore()) {
                settings.setHighScore(gamePanel.player.score);
                settings.saveConfigFile();
            }
            
            gamePanel.stateM.setCurrentState(gameState.LOSE);
        }

        if (freezeCooldown > 0)
        {
            if (gamePanel.player.freezeCooldown == 1)
            {
                gamePanel.uiM.showMessage("Cluck is ready!");
            }
            freezeCooldown--;
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
            gamePanel.checker.checkTileCollision(this);
            
            int objIndex = gamePanel.checker.checkObjectCollision(this, true);
            objectInteraction(objIndex);

            // Check for collision with enemy whie moving
            if (gamePanel.stateM.getCurrentState() == gameState.PLAY) {
                int farmerIndex = gamePanel.checker.checkEntityCollision(this, gamePanel.mapM.getMap().farmers);
                farmerInteraction(farmerIndex);
            }

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
        else
        {
            // Check for collision with enemy while standing still
            if (gamePanel.stateM.getCurrentState() == gameState.PLAY) {
                int farmerIndex = gamePanel.checker.checkEntityCollision(this, gamePanel.mapM.getMap().farmers);
                farmerInteraction(farmerIndex);
            }
        }
    }

    /**
     * Determines the type of object the player is interacting with and calls the corresponding event.
     * 
     * @param index index of the object in the object array that the Player is interating with
     */
    private void objectInteraction(int index) {
        if (index != 999) {
            String objectName = gamePanel.mapM.getMap().objects[index].name;
            
            switch(objectName) {
                case "Egg":
                    sound.play(2);
                    if (health >= 3) {
                        score += 100;
                    }
                    else {
                        health++;
                    }
                    gamePanel.mapM.getMap().objects[index] = null;
                    gamePanel.uiM.showMessage("MY EGG!");
                    break;
                case "Key":
                    gamePanel.mapM.getMap().objects[index] = null;
                    keyCount++;
                    if (keyCount == gamePanel.mapM.getMap().keyNum) {
                        gamePanel.mapM.getObject(gamePanel.mapM.getMap().gateIndex).update(gamePanel);
                        sound.play(8);
                        gamePanel.uiM.showMessage("DOOR IS OPENED!");
                    }
                    sound.play(2);
                    break;
                case "Gate":
                    if (keyCount == gamePanel.mapM.getMap().keyNum) {
                        gamePanel.mapM.getObject(index).update(gamePanel);
                    }
                    break;
                case "Trap":
                    health--;
                    gamePanel.mapM.getMap().objects[index] = null;
                    respawnPlayer();
                    Farmer.respawnFarmers(gamePanel.mapM.getMap().farmers);
                    sound.play(3);
                    sound.play(7);
                    gamePanel.uiM.showMessage("OUCH!");
                    break;
            }
        }
    }

    /**
     * Called when Player and Farmer collide with each other.
     * Player loses one life and both Player and Farmer get reset back to their starting locations.
     * Also plays a sound and displays an appropriate mesage.
     * 
     * @param index index of the farmer in the farmer array that the Player is interacting with
     */
    public void farmerInteraction(int index)
    {
        if (index != 999)
        {
            health--;
            gamePanel.uiM.showMessage("You were caught!");

            respawnPlayer();
            Farmer.respawnFarmers(gamePanel.mapM.getMap().farmers);

            sound.play(3);
        }
    }

    /**
     * Sets the Player's current position to start position of the current map.
     * Also sets Player and all Farmers collision status to false.
     */
    private void respawnPlayer() {
        worldX = gamePanel.mapM.getMap().playerStartX;
        worldY = gamePanel.mapM.getMap().playerStartY;
        freezeCooldown = 0;

        this.collisionOn = false;
        for (int i = 0; i < gamePanel.mapM.getMap().farmers.length; i++)
        {
            if (gamePanel.mapM.getMap().farmers[i] != null)
            {
                gamePanel.mapM.getMap().farmers[i].collisionOn = false;
            }
        }
    }

    /**
     * Draws Player with the appropriate image based on the direction Player is moving in.
     * 
     * @param graphic2 main graphic used by gamePanel to draw the maps sprites and tiles
     */
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

    /**
     * An ability that freezes the farmers whenever the H key is pressed
     * If any farmers are in the freezeArea, they are frozen for 1 second
     * This ability has a 5 second cooldown
    */
    public void freezeFarmers() 
    {
        /*
         * TODO - decide if this needs changing as a group
         * Potentially makes the game too easy since you can freeze at most difficult parts
         * To fix we could either disable it, make the CD longer, or limit it to once per round
         * Let me know what you think after you play a bit
        */
        // Get the array of farmers for the current map
        Farmer[] f = gamePanel.mapM.getMap().farmers;
        // Create a new rectangle for the freeze area
        // Get coordinates of the rectangle and width/height
        int freezeAreaSize = 7; // Can be any ODD number
        int freezeWidth = gamePanel.tileSize * freezeAreaSize;
        int freezeHeight = gamePanel.tileSize * freezeAreaSize;
        int tilesToShiftBy = freezeAreaSize / 2;
        int freezeX = worldX - tilesToShiftBy * gamePanel.tileSize;
        int freezeY = worldY - tilesToShiftBy * gamePanel.tileSize;
        Rectangle freezeArea = new Rectangle(freezeX, freezeY, freezeWidth, freezeHeight);
        // Set the farmers movement speed to frozen if they are within the freezeArea. Also set a 3 second timer to become unfrozen
        for (int i = 0; i < f.length; i++)
        {
            // Get farmer's hitbox
            f[i].hitbox.x = f[i].worldX + f[i].hitbox.x;
            f[i].hitbox.y = f[i].worldY + f[i].hitbox.y;
            // Determine if farmer is in stun range
            if (f[i].hitbox.intersects(freezeArea))
            {
                f[i].speed = Farmer.frozen;
                f[i].freezeTimer = 60; // 60 frames per second. Ex. freezeTimer = 60 is a 1 second freeze
                freezeCooldown = 300; // 60 frames per second. Ex. freezeCooldown = 300 is a 5 second CD
            }
            // Reset farmers hitbox
            f[i].hitbox.x = f[i].hitboxDefaultX;
			f[i].hitbox.y = f[i].hitboxDefaultY;
        }
    }
}
