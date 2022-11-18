package tile;

import static org.junit.Assert.*;
import org.junit.Test;

public class TileManagerTest
{
    TileManager t = TileManager.getInstance();
    int numTiles = 179;

    @Test
    // Make sure every tile is linked to an image
    public void tileImageTest()
    {

        for (int i = 0; i < numTiles; i++)
        {
            assertNotNull(t.getTileImage(i));
        }
    }

    @Test
    // Check if each tile has a collision status assigned to it
    public void tileCollisionTest()
    {
        for (int i = 0; i < numTiles; i++)
        {
            assertNotNull(t.checkTileCollision(i));
        }
    }

}
