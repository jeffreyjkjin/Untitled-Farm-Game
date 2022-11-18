package tile;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;


import app.GamePanel;

public class TileTest {

	@Test
	public void testTileLoading() {
		//given
		GamePanel gp = new GamePanel();
		TileManager tm;
		
		//when
		tm = TileManager.getInstance();
		
		//then
		assertNotNull(tm.getTileImage(0));
	}

}
