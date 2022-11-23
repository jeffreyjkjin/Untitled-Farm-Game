package tile;

import static org.junit.Assert.*;
import org.junit.Test;

public class TileTest {

	@Test
	public void testTileLoading() {
		//given
		TileManager tm;
		
		//when
		tm = TileManager.getInstance();
		
		//then
		assertNotNull(tm.getTileImage(0));
	}

}
