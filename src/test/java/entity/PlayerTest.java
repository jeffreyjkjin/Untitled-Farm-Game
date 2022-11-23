package entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javax.swing.JFrame;

import app.GamePanel;
import app.StateManager.gameState;
import input.PlayInput;
import object.SuperObject;

public class PlayerTest {

    public class GamePanelHelper extends GamePanel {
        public void setupGamePanel() {
            mapM.setupMap();
            setWindowScreen();
            startGameThread();
        }
    }
    
    public class App {
	
        public JFrame window;
        public GamePanelHelper gp;
        
        public App() {
            window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Untitled Farm Game");
    
            gp = new GamePanelHelper();
            window.add(gp);
    
            window.pack();
    
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            
            gp.setupGamePanel();
    
            System.out.println("Hello World!");
        }
    }
    
    App app;
    GamePanelHelper gp;
    Player player;
    PlayInput input;

    @Before
    public void setupTests() {
        // Creates an instance of the game
        app = new App();
        gp = app.gp;

        gp.stateM.setCurrentState(gameState.PLAY);
        player = gp.player;
        player.setDefaultValues();

        // Set player spawn point on barn level
        player.worldX = 15 * gp.tileSize;
        player.worldY = 12 * gp.tileSize;
        
        // Set default values
        player.score = 0;
        player.health = 3;
    }

    @Test
    public void playerHPGoesToZero() {
        player.health = 0;
        player.update();
        
        assertEquals(gameState.LOSE, gp.stateM.getCurrentState());
    }
    
    @Test
    public void playerMovementDown() {
        int y = player.worldY;
        int timer = 0;
        
        // Moves player down for 5 frames
        player.input.down = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.down = false;
        assertTrue(y < player.worldY);
    }
    
    @Test
    public void playerMovementLeft() {
        int x = player.worldX;
        int timer = 0;
        
        // Moves player left for 5 frames
        player.input.left = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.left = false;
        assertTrue(x > player.worldX);
    }

    @Test
    public void playerMovementUp() {
        int y = player.worldY;
        int timer = 0;
        
        // Moves player up for 5 frames
        player.input.up = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.up = false;
        assertTrue(y > player.worldY);
    }

    @Test
    public void playerMovementRight() {
        int x = player.worldX;
        int timer = 0;
        
        // Moves player right for 5 frames
        player.input.right = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.right = false;
        assertTrue(x < player.worldX);

    }

    @Test
    public void playerCollectsEggWithFullHP() {
        int score = player.score + 100;
        
        SuperObject[] objects = gp.mapM.getMap().objects;

        // Searches for first egg that spawns on the map and sets players location to it
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].name == "Egg") {
                player.worldX = objects[i].worldX;
                player.worldY = objects[i].worldY;
                break;
            }
        }

        // Moves player down for one frame to trigger egg pickup
        player.input.down = true;
        player.update();
        player.input.down = false;

        assertEquals(3, player.health);
        assertEquals(score, player.score);
    }

    @Test
    public void playerCollectsEggWithoutFullHP() {
        int score = player.score;
        
        SuperObject[] objects = gp.mapM.getMap().objects;

        // Searches for first egg that spawns on the map and sets players location to it
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].name == "Egg") {
                player.worldX = objects[i].worldX;
                player.worldY = objects[i].worldY;
                break;
            }
        }

        player.health = 1;
        int health = player.health + 1;

        // Moves player down for one frame to trigger egg pickup
        player.input.down = true;
        player.update();
        player.input.down = false;

        assertEquals(health, player.health);
        assertEquals(score, player.score);
    }

    @Test
    public void playerCollectsKey() {
        int keyCount = player.keyCount + 1;
        
        SuperObject[] objects = gp.mapM.getMap().objects;

        // Searches for first key on the map and sets players location to it
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].name == "Key") {
                player.worldX = objects[i].worldX;
                player.worldY = objects[i].worldY;
                break;
            }
        }

        // Moves player down for one frame to trigger key pickup
        player.input.down = true;
        player.update();
        player.input.down = false;

        assertEquals(keyCount, player.keyCount);
        
    }

    @Test
    public void playerStepsOnTrapWithFullHP() {
        int health = player.health - 1;
        
        SuperObject[] objects = gp.mapM.getMap().objects;

        // Searches for first trap on the map and sets players location to it
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].name == "Trap") {
                player.worldX = objects[i].worldX;
                player.worldY = objects[i].worldY;
                break;
            }
        }

        // Moves player down for one frame to trigger trap interaction
        player.input.down = true;
        player.update();
        player.input.down = false;

        assertEquals(health, player.health);
        assertEquals(gp.mapM.getMap().playerStartX, player.worldX);
    }

    @Test
    public void playerEntersGateWithRequiredKeys() {
        int score = player.score + 1000;
        
        SuperObject[] objects = gp.mapM.getMap().objects;
        int gateIndex = gp.mapM.getMap().gateIndex;

        player.worldX = objects[gateIndex].worldX;
        player.worldY = objects[gateIndex].worldY - 48;

        player.keyCount = gp.mapM.getMap().keyNum;

        // Moves player down for five frame to trigger gate interaction
        int timer = 0;
        player.input.down = true;
        while (timer < 5) {
            timer++;
            player.update();
        }
        player.input.down = false;

        assertEquals(score, player.score);
        assertEquals(gp.mapM.getMap().playerStartX, player.worldX);
        assertEquals(gp.mapM.getMap().playerStartY, player.worldY);
    }

    @Test
    public void playerTouchesFarmerWithFullHP() {
        int health = player.health - 1;
        
        Farmer farmer = gp.mapM.getMap().farmers[0];

        player.worldX = farmer.worldX;
        player.worldY = farmer.worldY - 48;

        // Moves player down for 10 frames to collide with farmer
        int timer = 0;
        player.input.down = true;
        while (timer < 10) {
            timer++;
            player.update();
        }
        player.input.down = false;

        assertEquals(health, player.health);
        assertEquals(gp.mapM.getMap().playerStartX, player.worldX);
    }

    @Test
    public void playerFreezesFarmerWhileInRange() {
        Farmer farmer = gp.mapM.getMap().farmers[0];

        player.worldX = farmer.worldX;
        player.worldY = farmer.worldY - 48;

        player.freezeFarmers();

        assertTrue(farmer.freezeTimer >= 60);
    }

    @Test
    public void playerFreezesFarmerWhileNotInRange() {
        Farmer farmer = gp.mapM.getMap().farmers[0];

        // Set player to be more than 5 tiles away from farmer
        player.worldX = farmer.worldX;
        player.worldY = farmer.worldY - 241;

        player.freezeFarmers();

        assertTrue(farmer.freezeTimer == 0);
    }
}
