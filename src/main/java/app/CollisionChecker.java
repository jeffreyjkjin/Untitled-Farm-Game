package app;

import entity.Entity;
import entity.Farmer;

/**
 * This class is used to check collisions between entities and objects
 * 
 * @author Long Nguyen (dln3)
 */
public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
	
    /**
	 * Checking the 2 tiles ahead for collision
     * @param entity
     */
    public void checkTileCollision(Entity entity) {
		// Get various values of Entity to help check for tile collision properly
		int entityLeftWorldX = entity.worldX + entity.hitbox.x;
		int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
		int entityTopWorldY = entity.worldY + entity.hitbox.y;
		int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tilenum1 = 0; 
		int tilenum2 = 0;
		// Check the possible tiles entity can be on next time it moves in its direction and see if any have collision on
		switch(entity.direction)
		{
			case"up":
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityTopRow][entityLeftCol];
				tilenum2 = gp.mapM.getTileMap()[entityTopRow][entityRightCol];
				break;
			case"down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityBottomRow][entityLeftCol];
				tilenum2 = gp.mapM.getTileMap()[entityBottomRow][entityRightCol];
				break;
			case"left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityTopRow][entityLeftCol];
				tilenum2 = gp.mapM.getTileMap()[entityBottomRow][entityLeftCol];
				break;
			case"right":
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityTopRow][entityRightCol];
				tilenum2 = gp.mapM.getTileMap()[entityBottomRow][entityRightCol];
				break;
		}

		if(gp.tileM.checkTileCollision(tilenum1) || gp.tileM.checkTileCollision(tilenum2)){
			entity.collisionOn = true;
		}

    }
    
    public int checkObjectCollision(Entity entity, boolean player) {
    	
    	int index = 999;
    	
    	for (int i = 0; i < gp.mapM.getMap().objects.length; i++) 
		{	
    		if(gp.mapM.getMap().objects[i] != null) {	
    			// get entity hitbox
    			entity.hitbox.x = entity.worldX + entity.hitbox.x; 
    			entity.hitbox.y = entity.worldY + entity.hitbox.y; 
    			// get object hitbox
    			gp.mapM.getObject(i).hitbox.x = gp.mapM.getObject(i).worldX + gp.mapM.getObject(i).hitbox.y;
    			gp.mapM.getObject(i).hitbox.y = gp.mapM.getObject(i).worldY + gp.mapM.getObject(i).hitbox.y;
    			// Temporarily move hitbox to check collision
    			switch(entity.direction) {
    			case "up":
    				entity.hitbox.y -= entity.speed;
    				break;
    				
    			case "down":
    				entity.hitbox.y += entity.speed;
    				break;
    				
    			case "left":
    				entity.hitbox.x -= entity.speed;
    				break;
    				
    			case "right":
    				entity.hitbox.x -= entity.speed;
    				break;
    			}
				// Check if player has collided with an object. If yes, turn collision on and return index if entity == player
				if (entity.hitbox.intersects(gp.mapM.getObject(i).hitbox)) 
				{
					if(gp.mapM.getObject(i).collision == true) 
					{
						entity.collisionOn = true;
					}
					if(player == true) 
					{
						index = i;
					}
				}
				// Reset all hitboxes back to their original locations
    			entity.hitbox.x = entity.hitboxDefaultX;
    			entity.hitbox.y = entity.hitboxDefaultY;
    			gp.mapM.getObject(i).hitbox.x = gp.mapM.getObject(i).hitboxDefaultX;
    			gp.mapM.getObject(i).hitbox.y = gp.mapM.getObject(i).hitboxDefaultY;
    		}
    			
    	}
    	
    	return index;
    } 

	/**
	 * Checks if entity will collide with any of the farmers in the array (the only enemies on the map currently)
	 * If collision will happen, turns on their collisionOn variable to stop movement and returns index of farmer collided with
	 * Checked by temporarily moving entities hitbox in the direction it is moving and seeing if collision happens
	 * All of these check_____Collision() functions are very similar just for specific scenarios
	 * 
	 * @param entity the entity that is calling this function to check if it will collide with an entity on next movement
	 * @param farmers the array of entities (currently farmers only) to check for possible collisions with
	 * @return index of the farmer entity collided with
	 */
	public int checkEntityCollision(Entity entity, Farmer[] farmers)
	{
		int index = 999;
		// Temporarily move the entitys hitbox to test collision
		switch(entity.direction) 
		{
			case "up":
				entity.hitbox.y -= entity.speed;
				break;
				
			case "down":
				entity.hitbox.y += entity.speed;
				break;
				
			case "left":
				entity.hitbox.x -= entity.speed;
				break;
				
			case "right":
				entity.hitbox.x -= entity.speed;
				break;
		}

		for (int i = 0; i < farmers.length; i++)
		{
			if (farmers[i] != null)
			{	
				// Get Entity's hitbox coords
				entity.hitbox.x = entity.worldX + entity.hitbox.x; 
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				// Get farmer's hitbox
				farmers[i].hitbox.x = farmers[i].worldX + farmers[i].hitbox.x;
    			farmers[i].hitbox.y = farmers[i].worldY + farmers[i].hitbox.y;
				// Check for hitbox intersection
				if (entity.hitbox.intersects(farmers[i].hitbox))
				{	// Check to see if entity is the farmer being checked. Otherwise it will collide with itself
					if (farmers[i] != entity)
					{	
						index = i;
						entity.collisionOn = true;
						farmers[i].collisionOn = true;
					}
				}
				// Reset all hitboxes back to their original coordinates
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				farmers[i].hitbox.x = farmers[i].hitboxDefaultX;
				farmers[i].hitbox.y = farmers[i].hitboxDefaultY;
			}
		}

		return index;
	}

	/**
	 * Checks if entity is colliding with the player by checking if on its next movement its hitbox will intersect with players
	 * Always called by entities other than the player character
	 * 
	 * @param entity the entity (currently always farmer) that may collide with the player during movement
	 * 
	 */
	public void checkPlayerCollision(Entity entity)
	{
		// Get Entity's hitbox
		entity.hitbox.x = entity.worldX + entity.hitbox.x; 
		entity.hitbox.y = entity.worldY + entity.hitbox.y;
		// Get Player's hitbox
		gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
		gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;

		// Temporarily move the Entity's hitbox to test collision
		switch(entity.direction) 
		{
			case "up":
				entity.hitbox.y -= entity.speed;
				break;
				
			case "down":
				entity.hitbox.y += entity.speed;
				break;
				
			case "left":
				entity.hitbox.x -= entity.speed;
				break;
				
			case "right":
				entity.hitbox.x -= entity.speed;
				break;
		}
		// If there is collision, enable the variable and call the farmerInteraction function
		if (entity.hitbox.intersects(gp.player.hitbox))
		{
			entity.collisionOn = true;
			gp.player.farmerInteraction(0); // Index does not matter in this case
		}
		// Reset all hitboxes back to their original coordinates
		entity.hitbox.x = entity.hitboxDefaultX;
		entity.hitbox.y = entity.hitboxDefaultY;
		gp.player.hitbox.x = gp.player.hitboxDefaultX;
		gp.player.hitbox.y = gp.player.hitboxDefaultY;
	}
}
