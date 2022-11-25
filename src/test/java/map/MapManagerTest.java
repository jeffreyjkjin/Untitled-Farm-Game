package map;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import app.GamePanel;
import app.StateManager.gameState;

public class MapManagerTest {
    

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
    GamePanel gp;
    MapManager mapM;

    @Before
    public void setupTests() {
        // Creates an instance of the game
        app = new App();
        gp = app.gp;
        mapM = gp.mapM;

        gp.stateM.setCurrentState(gameState.PLAY);
    }

    @Test
    public void movePlayerToNextLevel() {
        int score = gp.player.score + 1000;
        
        mapM.nextMap();

        assertEquals(score, gp.player.score);
        assertEquals("2", mapM.getMap().levelName);
    }

    @Test
    public void playerFinishesLastLevel() {
        int score = gp.player.score + 6000;
        
        // Move player through every map
        for (int i = 0; i < 6; i++) {
            mapM.nextMap();
        }

        score *= (double) 2 - ((double) gp.uiM.getPlayTime()/600);

        assertEquals(gameState.WIN, gp.stateM.getCurrentState());
        assertEquals(score, gp.player.score);
    }

    @Test
    public void resetPlayerToFirstLevel() {
        // Move player to second map
        mapM.nextMap();

        mapM.resetMap();
        assertEquals("1", mapM.getMap().levelName);
    }

}
