package app;

import javax.swing.JFrame;

/**
 * Main driver object of this game.
 * 
 * @author Jeffrey Jin (jjj9)
 */
public final class App {
	
	public static JFrame window;
	
    /**
     * Creates a new game window and instantiates an instance of GamePanel.
     * Runs the game loop.
     * 
     * @param args default parameter
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Untitled Farm Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();

        System.out.println("Hello World!");
    }
}
