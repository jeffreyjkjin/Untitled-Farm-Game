import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import app.GamePanel;
import entity.Farmer;
import entity.Player;
import input.InputHandler;


class PlayerTest  {


	@Test 
	void testSetDefaultValues() {
		
		//given
		GamePanel gp = new GamePanel();
		InputHandler input = mock(InputHandler.class);
		Player p = new Player(gp,input);
		
		//when
		p.setDefaultValues();
		
		//then
		assertEquals(p.screenX, gp.screenWidth / 2 - (gp.tileSize / 2));
		assertEquals(p.screenY, gp.screenHeight / 2 - (gp.tileSize / 2));
		assertEquals(p.worldX, gp.mapM.getMap().playerStartX);
		assertEquals(p.worldY, gp.mapM.getMap().playerStartY);
		assertEquals(p.speed, 4);
		assertEquals(p.health, 3);
		assertEquals(p.score, 0);
		assertEquals(p.keyCount, 0);
		assertEquals(p.direction, "down");
	}

	@Test
	void testSpawnPlayer() {
		//given
		GamePanel gp = new GamePanel();
		InputHandler input = mock(InputHandler.class);
		Player p = new Player(gp,input);
		
		//when
		p.spawnPlayer();
		
		//then
		assertEquals(p.worldX, gp.mapM.getMap().playerStartX);
		assertEquals(p.worldY, gp.mapM.getMap().playerStartY);
	}

	@Test
	void testFarmerInteraction() {

		//given
		GamePanel gp = new GamePanel();
		InputHandler input = mock(InputHandler.class);
		Player p = new Player(gp,input);
		p.health = 3;
		
		//when
		p.farmerInteraction(0);
		
		//then
		assertEquals(2, p.health);
	}
}
