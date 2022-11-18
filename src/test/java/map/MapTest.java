package map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.GamePanel;
import entity.Farmer;
import object.SuperObject;

public class MapTest {
    public class ObjectData {
        String name;
        public int x, y;

        public ObjectData(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }

    GamePanel gp = new GamePanel();
    Map map;

    int[][] tileMap = {
        {0, 1, 2, 0, 2, 0, 1, 0},
        {0, 1, 0, 2, 0, 0, 0, 1},
        {1, 18, 19, 0, 2, 1, 0, 0},
        {0, 16, 17, 2, 0, 0, 1, 2},
        {1, 0, 1, 2, 0, 0, 2, 0},
        {0, 1, 0, 0, 2, 0, 1, 0},
        {0, 0, 1, 1, 0, 2, 0, 2},
        {1, 0, 1, 1, 2, 0, 1, 0},
    };

    int[][] objMap = {
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 5, 0},
        {0, 3, 0, 0, 99, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
    };

    ObjectData[] objData = {
        new ObjectData("Egg", 6 * gp.tileSize, 1 * gp.tileSize),
        new ObjectData("Key", 6 * gp.tileSize, 3 * gp.tileSize),
        new ObjectData("Trap", 6 * gp.tileSize, 5 * gp.tileSize),
        new ObjectData("Gate", 1 * gp.tileSize, 6 * gp.tileSize),
    };

    @Before
    public void loadMapFile() {
        map = new Map(gp, "/levels/test.txt");
    }

    @Test
    public void checkMapLevelData() {
        assertEquals("TEST", map.levelName);
        assertEquals(8, map.maxWorldCol);
        assertEquals(8, map.maxWorldRow);
        assertEquals(192, map.playerStartX);
        assertEquals(192, map.playerStartY);
        assertEquals(4, map.objectNum);
        assertEquals(1, map.entityNum);
        assertEquals(1, map.keyNum);
    }

    @Test
    public void checkTileMap() {

        int[][] testTileMap = map.tileMap;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(tileMap[i][j], testTileMap[i][j]);
            }
        }
    }

    @Test
    public void checkObjectMap() {
        int[][] testObjMap = map.objMap;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(objMap[i][j], testObjMap[i][j]);
            }
        }
    }

    @Test
    public void loadObjectsOntoMap() {
        map.setObject();

        SuperObject[] objects = map.objects;

        for (int i = 0; i < 4; i++) {
            // First object is an egg; only checks if the egg spawns
            if (i == 0) {
                if (objects[0] != null) {
                    assertEquals(objData[i].name, objects[i].name);
                    assertEquals(objData[i].x, objects[i].worldX);
                    assertEquals(objData[i].y, objects[i].worldY);
                }
            }
            else {
                assertEquals(objData[i].name, objects[i].name);
                assertEquals(objData[i].x, objects[i].worldX);
                assertEquals(objData[i].y, objects[i].worldY);
            }
        }
    }

    @Test
    public void loadFarmersOntoMap() {
        map.setFarmer();

        Farmer[] farmers = map.farmers;

        // Test level only 1 farmer to check
        assertEquals(192, farmers[0].worldX);
        assertEquals(288, farmers[0].worldY);
    }

}
