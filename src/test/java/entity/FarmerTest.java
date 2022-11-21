package entity;

import static org.junit.Assert.*;

import org.junit.Test;

import app.GamePanel;

public class FarmerTest
{
    Farmer[] farmersTest;

    public class FarmerTestHelper extends Farmer
    {
        public FarmerTestHelper(GamePanel gp)
        {
            super(gp);
        }
    }


    @Test
    public void testRespawnFarmers()
    {
        GamePanel gp = null;
        farmersTest = new Farmer[1];
        farmersTest[0] = new FarmerTestHelper(gp);
        // Set starting coordinates
        farmersTest[0].startingX = 0;
        farmersTest[0].startingY = 0;
        // Move farmer to a random location
        farmersTest[0].worldX = 20;
        farmersTest[0].worldY = 20;
        // Set attributes to non-default values
        farmersTest[0].collisionOn = true;
        farmersTest[0].freezeTimer = 500;
        farmersTest[0].speed = Farmer.frozen;
        // Test that the farmer is not at starting position
        assertNotEquals(farmersTest[0].startingX, farmersTest[0].worldX);
        assertNotEquals(farmersTest[0].startingY, farmersTest[0].worldY);
        // Call the function we are testing
        Farmer.respawnFarmers(farmersTest);
        // See if it respawned the farmers to the proper coordinates with the correct attributes
        assertEquals(farmersTest[0].worldX, farmersTest[0].startingX);
        assertEquals(farmersTest[0].worldY, farmersTest[0].startingY);
        assertFalse(farmersTest[0].collisionOn);
        assertEquals(farmersTest[0].freezeTimer, 0);
        assertEquals(farmersTest[0].speed, Farmer.normal);
    }
}
