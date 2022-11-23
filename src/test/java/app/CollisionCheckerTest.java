package app;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Test;
import org.junit.Before;

import entity.*;

import javax.swing.JFrame;

import app.StateManager.gameState;
import object.SuperObject;

public class CollisionCheckerTest {
    App app;
    GamePanelHelper gp;
    Player player;
    CollisionChecker colcheck;
    
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

    @Before
    public void setupTests() {
        // Creates an instance of the game
        app = new App();
        gp = app.gp;
        colcheck = new CollisionChecker(gp);

        gp.stateM.setCurrentState(gameState.PLAY);
        player = gp.player;
        player.setDefaultValues();

        player.hitbox = new Rectangle(0,0,48,48); //set player's hitbox to be the whole tile

    }

    @Test
    public void CheckTileCollisionEqualsTrue() {

        //when (the tile above spawn in barn map is solid)
        player.direction = "up";
        colcheck.checkTileCollision(player);

        //then
        assertTrue(player.collisionOn);

    }

    @Test
    public void CheckTileCollisionEqualsFalse() {
        //the tiles in these 3 direction are not solid
        player.direction = "left";
        colcheck.checkTileCollision(player);
        boolean leftcol = player.collisionOn;

        player.direction = "right";
        colcheck.checkTileCollision(player);
        boolean rightcol = player.collisionOn;

        player.direction = "down";
        colcheck.checkTileCollision(player);
        boolean downcol = player.collisionOn;

        //then
        assertFalse(rightcol||leftcol||downcol);

    }
    
    @Test
    public void playerCollideWithKey() {
        SuperObject[] objects = gp.mapM.getMap().objects;

        // Searches for first key on the map and sets players location to it
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].name == "Key") {
                player.worldX = objects[i].worldX;
                player.worldY = objects[i].worldY;
                break;
            }
        }

        int i = colcheck.checkObjectCollision(player, true);
        //confirms that it collides with the object and returns and int
        assertNotEquals(999, i);
    }

    @Test
    public void playerCollideWithEgg() {
        SuperObject[] objects = gp.mapM.getMap().objects;

        // Searches for first key on the map and sets players location to it
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].name == "Egg") {
                player.worldX = objects[i].worldX;
                player.worldY = objects[i].worldY;
                break;
            }
        }

        int i = colcheck.checkObjectCollision(player, true);
        //confirms that it collides with the object and returns and int
        assertNotEquals(999, i);

    }

    @Test
    public void playerCollideWithFarmer() {
        Farmer farmer = gp.mapM.getMap().farmers[0];
        Farmer[] farmers = gp.mapM.getMap().farmers;

        player.worldX = farmer.worldX;
        player.worldY = farmer.worldY;

        int i = colcheck.checkEntityCollision(player, farmers);
        //confirms that it collides with the farmer and returns and int
        assertNotEquals(999, i);
    }
}

