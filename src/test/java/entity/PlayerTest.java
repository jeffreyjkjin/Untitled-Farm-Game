package entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JFrame;

import app.GamePanel;
import app.StateManager.gameState;
import input.PlayInput;

public class PlayerTest {

    public class GamePanelTest extends GamePanel {
        public void setupGamePanel() {
            mapM.setupMap();
            setWindowScreen();
            startGameThread();
        }
    }
    
    public class App {
	
        public JFrame window;
        public GamePanelTest gp;
        
        public App() {
            window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Untitled Farm Game");
    
            gp = new GamePanelTest();
            window.add(gp);
    
            window.pack();
    
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            
            gp.setupGamePanel();
    
            System.out.println("Hello World!");
        }
    }
    
    App app;
    GamePanelTest gp;
    Player player;
    PlayInput input;

    @Before
    public void setupTests() {
        app = new App();
        gp = app.gp;

        gp.stateM.setCurrentState(gameState.PLAY);
        player = gp.player;
        player.setDefaultValues();

        player.worldX = 15 * gp.tileSize;
        player.worldY = 12 * gp.tileSize;
    }

    @Test
    public void playerHPGoesToZero() {
        gp.player.health = 0;
        gp.player.update();
        
        assertEquals(gameState.LOSE, gp.stateM.getCurrentState());
    }
    
    @Test
    public void playerMovementUp() {
        int y = player.worldY;
        int timer = 0;
        
        player.input.down = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.down = false;
        assertEquals(y + 20, player.worldY);
    }
    
    @Test
    public void playerMovementLeft() {
        int x = player.worldX;
        int timer = 0;
        
        player.input.left = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.left = false;
        assertEquals(x - 20, player.worldX);
    }

    @Test
    public void playerMovementDown() {
        int y = player.worldY;
        int timer = 0;
        
        player.input.up = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.up = false;
        assertEquals(y - 20, player.worldY);
    }

    @Test
    public void playerMovementRight() {
        int x = player.worldX;
        int timer = 0;
        
        player.input.right = true;
        while (timer < 5)  {
            timer++;
            player.update();
        }
        player.input.right = false;
        assertEquals(x + 20, player.worldX);

    }
}
