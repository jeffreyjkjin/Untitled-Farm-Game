package object;

public class ObjectManager {

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
            default:
                return null;
        }

        return newObj;
    }
}
