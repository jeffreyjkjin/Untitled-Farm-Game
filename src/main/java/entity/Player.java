package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.Random; 

import app.GamePanel;
import app.StateManager.gameState;
import audio.SoundEffects;
import input.InputHandler;
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

    Random randGen = new Random();

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

        freezeCooldown = 0;
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
        checkHealth();

        checkFreezeCooldown();

        if (input.up || input.left || input.down || input.right) {
            updatePlayerDirection();
        
            collisionOn = false;
            gamePanel.checker.checkTileCollision(this);
            
            int objIndex = gamePanel.checker.checkObjectCollision(this, true);
            objectInteraction(objIndex);

            updatePlayerPosition();

            updatePlayerSprite();
        }

        checkPlayerCollisonWithEntity();
    }

    /**
     * If player's health is 0 it changes the game state to lose.
     * Else, it does nothing.
     */
    private void checkHealth() {
        if (health == 0) {
            gamePanel.stateM.setCurrentState(gameState.LOSE);
        }
    }

    /**
     * If the player is in the play state and the player has a clucking cooldown, decrement the timer for the cooldown.
     * Tell the player cool down is over when its over.
     */
    private void checkFreezeCooldown() {
        if (gamePanel.stateM.getCurrentState() == gameState.PLAY) {
            if (freezeCooldown > 0)
            {
                if (gamePanel.player.freezeCooldown == 1)
                {
                    gamePanel.uiM.showMessage("CLUCK RECHARGED");
                    sound.play(0);
                }
                freezeCooldown--;
            }
        }
    }

    /**
     * Updates the player's direction depending on what input is being pressed.
     */
    private void updatePlayerDirection() {
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
    }

    /**
     * Changes the player sprite.
     */
    private void updatePlayerSprite() {
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
     * Changes the player's x and y coordinates on the map depending on what input is being pressed.
     */
    private void updatePlayerPosition() {
        if(!collisionOn) {
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
    }

    /**
     * If the player is in the play state, check if an entity has collided with the player.
     */
    private void checkPlayerCollisonWithEntity() {
        if (gamePanel.stateM.getCurrentState() == gameState.PLAY) {
            int farmerIndex = gamePanel.checker.checkEntityCollision(this, gamePanel.mapM.getMap().farmers);
            farmerInteraction(farmerIndex);
        }
    }

    /**
     * Determines the type of object the player is interacting with and calls the corresponding event.
     * 
     * @param index index of the object in the object array that the Player is interating with
     */
    public void objectInteraction(int index) {
        if (index != 999) {
            String objectName = gamePanel.mapM.getMap().objects[index].name;
            
            switch(objectName) {
                case "Egg":
                    gamePanel.mapM.getMap().objects[index].update(gamePanel);
                    gamePanel.mapM.getMap().objects[index] = null;
                    break;
                case "Key":
                    gamePanel.mapM.getMap().objects[index].update(gamePanel);
                    gamePanel.mapM.getMap().objects[index] = null;
                    break;
                case "Gate":
                    if (keyCount == gamePanel.mapM.getMap().keyNum) {
                        gamePanel.mapM.getObject(index).update(gamePanel);
                    }
                    break;
                case "Trap":
                    gamePanel.mapM.getMap().objects[index].update(gamePanel);
                    gamePanel.mapM.getMap().objects[index] = null;
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
            gamePanel.uiM.showMessage("-1 HP");

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
        // Get the array of farmers for the current map
        Farmer[] f = gamePanel.mapM.getMap().farmers;
        // Create a new rectangle for the freeze area
        // Get coordinates of the rectangle and width/height
        int freezeAreaSize = 5; // Can be any ODD number
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
                f[i].freezeTimer = 60 + randGen.nextInt(240); // freezes farmer for 60-300 frames (1-5 seconds)
            }
            // Reset farmers hitbox
            f[i].hitbox.x = f[i].hitboxDefaultX;
			f[i].hitbox.y = f[i].hitboxDefaultY;
        }
    }
}
