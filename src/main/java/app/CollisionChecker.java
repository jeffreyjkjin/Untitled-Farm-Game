package app;

import entity.Entity;
import entity.Farmer;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
 
    public void checkTileCollision(Entity entity) {

		int entityLeftWorldX = entity.worldX + entity.hitbox.x;
		int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
		int entityTopWorldY = entity.worldY + entity.hitbox.y;
		int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tilenum1, tilenum2;

		switch(entity.direction){
			case"up":
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityTopRow][entityLeftCol];
				tilenum2 = gp.mapM.getTileMap()[entityTopRow][entityRightCol];
				if(gp.tileM.checkTileCollision(tilenum1) || gp.tileM.checkTileCollision(tilenum2)){
					entity.collisionOn = true;
				}
				break;
			case"down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityBottomRow][entityLeftCol];
				tilenum2 = gp.mapM.getTileMap()[entityBottomRow][entityRightCol];
				if(gp.tileM.checkTileCollision(tilenum1) || gp.tileM.checkTileCollision(tilenum2)){
					entity.collisionOn = true;
				}
				break;
			case"left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityTopRow][entityLeftCol];
				tilenum2 = gp.mapM.getTileMap()[entityBottomRow][entityLeftCol];
				if(gp.tileM.checkTileCollision(tilenum1) || gp.tileM.checkTileCollision(tilenum2)){
					entity.collisionOn = true;
				}
				break;
			case"right":
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tilenum1 = gp.mapM.getTileMap()[entityTopRow][entityRightCol];
				tilenum2 = gp.mapM.getTileMap()[entityBottomRow][entityRightCol];
				if(gp.tileM.checkTileCollision(tilenum1) || gp.tileM.checkTileCollision(tilenum2)){
					entity.collisionOn = true;
				}
				break;
		}

    }
    
    public int checkObjectCollision(Entity entity, boolean player) {
    	
    	int index = 999;
    	
    	for (int i = 0; i < gp.mapM.getMap().objects.length; i++) {
    		
    		if(gp.mapM.getMap().objects[i] != null) {
    			
    			// get entity hitbox
    			entity.hitbox.x = entity.worldX + entity.hitbox.x; 
    			entity.hitbox.y = entity.worldY + entity.hitbox.y; 
    			// get object hitbox
    			gp.mapM.getObject(i).hitbox.x = gp.mapM.getObject(i).worldX + gp.mapM.getObject(i).hitbox.y;
    			gp.mapM.getObject(i).hitbox.y = gp.mapM.getObject(i).worldY + gp.mapM.getObject(i).hitbox.y;
    			
    			switch(entity.direction) {
    			case "up":
    				entity.hitbox.y -= entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getObject(i).hitbox)) {
    					if(gp.mapM.getObject(i).collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}
    				}
    				break;
    				
    			case "down":
    				entity.hitbox.y += entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getObject(i).hitbox)) {
    					if(gp.mapM.getObject(i).collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    				
    			case "left":
    				entity.hitbox.x -= entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getObject(i).hitbox)) {
              if(gp.mapM.getObject(i).collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    				
    			case "right":
    				entity.hitbox.x -= entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getObject(i).hitbox)) {
              if(gp.mapM.getObject(i).collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    			}
    			entity.hitbox.x = entity.hitboxDefaultX;
    			entity.hitbox.y = entity.hitboxDefaultY;
    			gp.mapM.getObject(i).hitbox.x = gp.mapM.getObject(i).hitboxDefaultX;
    			gp.mapM.getObject(i).hitbox.y = gp.mapM.getObject(i).hitboxDefaultY;
    		}
    			
    	}
    	
    	return index;
    } 

	public int checkEntityCollision(Entity player, Farmer[] farmers)
	{
		int index = 999;
		// Get players hitbox coords
		player.hitbox.x = player.worldX + player.hitbox.x; 
		player.hitbox.y = player.worldY + player.hitbox.y;
		// Temporarily move the players hitbox to test collision
		switch(player.direction) 
		{
			case "up":
				player.hitbox.y -= player.speed;
				break;
				
			case "down":
				player.hitbox.y += player.speed;
				break;
				
			case "left":
				player.hitbox.x -= player.speed;
				break;
				
			case "right":
				player.hitbox.x -= player.speed;
				break;
		}

		for (int i = 0; i < farmers.length; i++)
		{
			if (farmers[i] != null)
			{	
				// Get farmer's hitbox
				farmers[i].hitbox.x = farmers[i].worldX + farmers[i].hitbox.x;
    			farmers[i].hitbox.y = farmers[i].worldY + farmers[i].hitbox.y;
				// Check for hitbox intersection
				if (player.hitbox.intersects(farmers[i].hitbox))
				{
					index = i;
					player.entityCollisionOn = true;
					farmers[i].entityCollisionOn = true;
				}

				farmers[i].hitbox.x = farmers[i].hitboxDefaultX;
				farmers[i].hitbox.y = farmers[i].hitboxDefaultY;
			}
		}

		player.hitbox.x = player.hitboxDefaultX;
		player.hitbox.y = player.hitboxDefaultY;

		return index;
	}

	public boolean checkFarmerCollision(Farmer toCheck, Farmer[] farmers)
	{
		if (toCheck.knownCollision)
		{
			return false;
		}
		// Get toCheck's hitbox
		toCheck.hitbox.x = toCheck.worldX + toCheck.hitbox.x; 
		toCheck.hitbox.y = toCheck.worldY + toCheck.hitbox.y;
		// Temporarily move the players hitbox to test collision
		switch(toCheck.direction) 
		{
			case "up":
				toCheck.hitbox.y -= toCheck.speed;
				break;
				
			case "down":
				toCheck.hitbox.y += toCheck.speed;
				break;
				
			case "left":
				toCheck.hitbox.x -= toCheck.speed;
				break;
				
			case "right":
				toCheck.hitbox.x -= toCheck.speed;
				break;
		}
		for (int i = 0; i < farmers.length; i++)
		{
			if (farmers[i] != null)
			{
				if (farmers[i] == toCheck)
				{
					continue;
				}
				// Get farmer's hitbox
				farmers[i].hitbox.x = farmers[i].worldX + farmers[i].hitbox.x;
    			farmers[i].hitbox.y = farmers[i].worldY + farmers[i].hitbox.y;

				if (toCheck.hitbox.intersects(farmers[i].hitbox))
				{
					toCheck.hitbox.x = toCheck.hitboxDefaultX;
					toCheck.hitbox.y = toCheck.hitboxDefaultY;
					farmers[i].hitbox.x = farmers[i].hitboxDefaultX;
					farmers[i].hitbox.y = farmers[i].hitboxDefaultY;

					toCheck.entityCollisionOn = true;
					farmers[i].knownCollision = true;
					return true;
				}

				farmers[i].hitbox.x = farmers[i].hitboxDefaultX;
				farmers[i].hitbox.y = farmers[i].hitboxDefaultY;
			}
		}

		toCheck.hitbox.x = toCheck.hitboxDefaultX;
		toCheck.hitbox.y = toCheck.hitboxDefaultY;

		return false;
	}
}
