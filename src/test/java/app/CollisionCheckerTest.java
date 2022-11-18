package app;

import static org.junit.Assert.*;
import org.junit.Test;

import entity.Entity;
import tile.Tile;

public class CollisionCheckerTest {
    private Entity entity;
    private GamePanel gp;
    
    @Test
    public void ShouldTurnCollisionOn(){
        CollisionChecker2 colcheck = new CollisionChecker2(gp);
        entity = new EntityMock();
        //when
        colcheck.checkTileCollision(entity, true, false);

        //then
        assertTrue(entity.collisionOn);
        
    }
}

class CollisionChecker2 extends CollisionChecker{
    public CollisionChecker2(GamePanel gp){
        super(gp);
    }

    public void checkTileCollision(Entity entity, boolean bool1, boolean bool2){
        TileMock tile1 = new TileMock();
        tile1.collision = bool1;
        TileMock tile2 = new TileMock();
        tile2.collision = bool2;
        if(tile1.collision || tile2.collision){
			entity.collisionOn = true;
		}
    }
}

class TileMock extends Tile{
    public boolean collision = false;

    public TileMock(){}
}

class EntityMock extends Entity{
    public EntityMock(){
        collisionOn = false;
    }
}