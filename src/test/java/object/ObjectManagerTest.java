package object;

import static org.junit.Assert.*;
import org.junit.Test;

public class ObjectManagerTest {
    
    @Test
    public void createOneOfEachObject() {
        // Create an array with a spot for each object + 1 spot to test an invalid object id
        SuperObject objects[] = new SuperObject[8];

        for (int i = 0; i < 8; i++) {
            objects[i] = ObjectManager.createObject(i);
        }

        // Invalid object id's should return null objects
        assertEquals(null, objects[0]);

        assertEquals("Egg", objects[1].name);
        assertEquals("Key", objects[2].name);
        assertEquals("Gate", objects[3].name);
        assertEquals("Trap", objects[5].name);
        assertEquals("Gate", objects[6].name);
    }

}
