package com.example;

import com.example.entity.Entity;
public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
 
    public void checkCollision(Entity entity) {

    }
    
    public int checkObjectCollision(Entity entity, boolean player) {
    	
    	int index = 999;
    	
    	for (int i = 0; i < gp.obj.length; i++) {
    		
    		if(gp.obj[i] != null) {
    			
    			// get entitiy hitbox
    			//entity.hitbox.x = entity.? + entity.hitbox.x; 
    			//entity.hitbox.y = entity.? + entity.hitbox.y; 
    			// get object hitbox
    			gp.obj[i].hitbox.x = gp.obj[i].x + gp.obj[i].hitbox.y;
    			gp.obj[i].hitbox.y = gp.obj[i].y + gp.obj[i].hitbox.y;
    			
    			switch(entity.direction) { // need to put direction
    			
    			//'intersect' method is really comfortable but using it on tileCollisionCheck would be heavy
    			
    			case "up":
    				entity.hitbox.y -= entity.speed;
    				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    					if(gp.obj[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					} // NPC can't do anything
    				}
    				break;
    				
    			case "down":
    				entity.hitbox.y += entity.speed;
    				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    					if(gp.obj[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    				
    			case "left":
    				entity.hitbox.x -= entity.speed;
    				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    					if(gp.obj[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    				
    			case "right":
    				entity.hitbox.x -= entity.speed;
    				if (entity.hitbox.intersects(gp.obj[i].hitbox)) {
    					if(gp.obj[i].collision == true) {
    						entity.collisionOn = true;
    					}
    					if(player == true) {
    						index = i;
    					}}
    				break;
    			}
    			entity.hitbox.x = entity.hitboxDefaultX;
    			entity.hitbox.y = entity.hitboxDefaultY;
    			gp.obj[i].hitbox.x = gp.obj[i].hitboxDefaultX;
    			gp.obj[i].hitbox.y = gp.obj[i].hitboxDefaultY;
    		}
    			
    	}
    	
    	return index;
    } 
    // To use checkObjectCollision, need to put objIndex = returning index at Player move method 
}
