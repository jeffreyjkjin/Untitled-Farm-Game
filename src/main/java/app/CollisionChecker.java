package app;

import entity.Entity;
public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
 
    public void checkCollision(Entity entity) {

		int entityLeftWorldX = entity.worldx + entity.hitbox.x;
		int entityRightWorldX = entity.worldx + entity.hitbox.x + entity.hitbox.width;
		int entityTopWorldY = entity.worldy + entity.hitbox.y;
		int entityBottomWorldY = entity.worldy + entity.hitbox.y + entity.hitbox.height;

		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;

		int tilenum1, tilenum2;

		switch(entity.direction){
			case"up":
				entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
				tilenum1 = gp.tileM.levelTileNum[entityLeftCol][entityTopRow];
				tilenum2 = gp.tileM.levelTileNum[entityRightCol][entityTopRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
			case"down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
				tilenum1 = gp.tileM.levelTileNum[entityLeftCol][entityBottomRow];
				tilenum1 = gp.tileM.levelTileNum[entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
			case"left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
				tilenum1 = gp.tileM.levelTileNum[entityLeftCol][entityTopRow];
				tilenum1 = gp.tileM.levelTileNum[entityLeftCol][entityBottomRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
			case"right":
				entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
				tilenum1 = gp.tileM.levelTileNum[entityRightCol][entityTopRow];
				tilenum1 = gp.tileM.levelTileNum[entityRightCol][entityBottomRow];
				if(gp.tileM.tile[tilenum1].collision == true || gp.tileM.tile[tilenum2].collision == true){
					entity.collisionOn = true;
				}
				break;
		}

    }
    
    // public int checkObjectCollision(Entity entity, boolean player) {
    	
    // 	int index = 999;
    	
    // 	for (int i = 0; i < gp.obj.length; i++) {
    		
    // 		if(gp.obj[i] != null) {
    			
    // 			// get entitiy hitbox
    // 			//entity.hitbox.x = entity.? + entity.hitbox.x; 
    // 			//entity.hitbox.y = entity.? + entity.hitbox.y; 
    // 			// get object hitbox
    // 			gp.obj[i].hitbox.x = gp.obj[i].x + gp.obj[i].hitbox.y;
    // 			gp.obj[i].hitbox.y = gp.obj[i].y + gp.obj[i].hitbox.y;
    			
    // 			switch(entity.direction) { // need to put direction
    			
    // 			//'intersect' method is really comfortable but using it on tileCollisionCheck would be heavy
    			
    // 			case "up":
    // 				entity.hitbox.y -= entity.speed;
    // 				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    // 					if(gp.obj[i].collision == true) {
    // 						entity.collisionOn = true;
    // 					}
    // 					if(player == true) {
    // 						index = i;
    // 					} // NPC can't do anything
    // 				}
    // 				break;
    				
    // 			case "down":
    // 				entity.hitbox.y += entity.speed;
    // 				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    // 					if(gp.obj[i].collision == true) {
    // 						entity.collisionOn = true;
    // 					}
    // 					if(player == true) {
    // 						index = i;
    // 					}}
    // 				break;
    				
    // 			case "left":
    // 				entity.hitbox.x -= entity.speed;
    // 				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    // 					if(gp.obj[i].collision == true) {
    // 						entity.collisionOn = true;
    // 					}
    // 					if(player == true) {
    // 						index = i;
    // 					}}
    // 				break;
    				
    // 			case "right":
    // 				entity.hitbox.x -= entity.speed;
    // 				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    // 					if(gp.obj[i].collision == true) {
    // 						entity.collisionOn = true;
    // 					}
    // 					if(player == true) {
    // 						index = i;
    // 					}}
    // 				break;
    // 			}
    // 			entity.hitbox.x = entity.hitboxDefaultX;
    // 			entity.hitbox.y = entity.hitboxDefaultY;
    // 			gp.obj[i].hitbox.x = gp.obj[i].hitboxDefaultX;
    // 			gp.obj[i].hitbox.y = gp.obj[i].hitboxDefaultY;
    // 		}
    			
    // 	}
    	
    // 	return index;
    // } 
    // To use checkObjectCollision, need to put objIndex = returning index at Player move method 
}
