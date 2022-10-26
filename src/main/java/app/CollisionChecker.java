package app;

import entity.Entity;
public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
 
    public void checkCollision(Entity entity) {

		int entityLeftWorldX = entity.worldX + entity.hitbox.x;
		int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
		int entityTopWorldY = entity.worldY + entity.hitbox.y;
		int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;

		int tilenum1, tilenum2;

		switch(entity.direction){
			case"up":
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
				tilenum1 = gp.mapM.getCurrentMap().tileMap[entityLeftCol][entityTopRow];
				tilenum2 = gp.mapM.getCurrentMap().tileMap[entityRightCol][entityTopRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
			case"down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
				tilenum1 = gp.mapM.getCurrentMap().tileMap[entityLeftCol][entityBottomRow];
				tilenum2 = gp.mapM.getCurrentMap().tileMap[entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
			case"left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
				tilenum1 = gp.mapM.getCurrentMap().tileMap[entityLeftCol][entityTopRow];
				tilenum2 = gp.mapM.getCurrentMap().tileMap[entityLeftCol][entityBottomRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
			case"right":
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tilenum1 = gp.mapM.getCurrentMap().tileMap[entityRightCol][entityTopRow];
				tilenum2 = gp.mapM.getCurrentMap().tileMap[entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
		}

    }
    
    public int checkObjectCollision(Entity entity, boolean player) {
    	
    	int index = 999;
    	
    	for (int i = 0; i < gp.mapM.getCurrentMap().objects.length; i++) {
    		
    		if(gp.mapM.getCurrentMap().objects[i] != null) {
    			
    			// get entity hitbox
    			entity.hitbox.x = entity.worldX + entity.hitbox.x; 
    			entity.hitbox.y = entity.worldY + entity.hitbox.y; 
    			// get object hitbox
    			gp.mapM.getCurrentMap().objects[i].hitbox.x = gp.mapM.getCurrentMap().objects[i].worldX + gp.mapM.getCurrentMap().objects[i].hitbox.y;
    			gp.mapM.getCurrentMap().objects[i].hitbox.y = gp.mapM.getCurrentMap().objects[i].worldY + gp.mapM.getCurrentMap().objects[i].hitbox.y;
    			
    			switch(entity.direction) {
    			case "up":
    				entity.hitbox.y -= entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getCurrentMap().objects[i].hitbox)) {
    					if(gp.mapM.getCurrentMap().objects[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}
    				}
    				break;
    				
    			case "down":
    				entity.hitbox.y += entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getCurrentMap().objects[i].hitbox)) {
    					if(gp.mapM.getCurrentMap().objects[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    				
    			case "left":
    				entity.hitbox.x -= entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getCurrentMap().objects[i].hitbox)) {
              if(gp.mapM.getCurrentMap().objects[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    				
    			case "right":
    				entity.hitbox.x -= entity.speed;
    				if (entity.hitbox.intersects(gp.mapM.getCurrentMap().objects[i].hitbox)) {
              if(gp.mapM.getCurrentMap().objects[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    			}
    			entity.hitbox.x = entity.hitboxDefaultX;
    			entity.hitbox.y = entity.hitboxDefaultY;
    			gp.mapM.getCurrentMap().objects[i].hitbox.x = gp.mapM.getCurrentMap().objects[i].hitboxDefaultX;
    			gp.mapM.getCurrentMap().objects[i].hitbox.y = gp.mapM.getCurrentMap().objects[i].hitboxDefaultY;
    		}
    			
    	}
    	
    	return index;
    } 
}
