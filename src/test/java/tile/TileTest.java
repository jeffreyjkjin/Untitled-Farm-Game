package tile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import app.GamePanel;

class TileTest {

	@Test
	void testTileLoading() {
		//given
		GamePanel gp = new GamePanel();
		TileManager tm;
		
		//when
		tm = TileManager.getInstance();
		
		//then
		assertNotNull(tm.getTileImage(0));
	}

}
