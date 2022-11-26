package object;

/**
 * Manages the spawning of game objects.
 * 
 * @author Jeffrey Jin (jjj9)
 */
public class ObjectManager {

    /**
     * Creates a different object depending on the provided index.
     * Used by other classes to spawn objects on the map.
     * Index 0 should not be used as it denotes locations in the object map without any objects.
     * 
     * @param index of the object
     * @return the object at the specified index
     */
    public static SuperObject createObject(int index) {
        SuperObject newObj;
        
        switch(index) {
            // do not use 0 as it is used to denote tiles without objects
            case 1:
                newObj = new OBJ_Egg();
                break;
            case 2:
                newObj = new OBJ_Key();
                break;
            case 3:
                newObj = new OBJ_Gate();
                break;
            case 4:
                newObj = new OBJ_NonFuncGate();
                break;
            case 5:
                newObj = new OBJ_Trap();
                break;
            case 6:
                newObj = new OBJ_StoneGate();
                break;
            case 7:
                newObj = new OBJ_NonFuncStoneGate();
                break;
            default:
                return null;
        }

        return newObj;
    }
}
