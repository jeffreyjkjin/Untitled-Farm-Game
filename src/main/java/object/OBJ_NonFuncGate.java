package object;

import app.GamePanel;

/**
 * Gate object that does not function.
 * Used to denote start location for the player. 
 * 
 * @author Jeffrey Jin (jjj9)
 * @see object.OBJ_Gate
 * @see object.ObjectManager
 * @see object.SuperObject
 */
public class OBJ_NonFuncGate extends OBJ_Gate {

    /**
     * Calls the constructor of OBJ_Gate and changes the name of the object.
     */
    public OBJ_NonFuncGate() {
        super();
        name = "NonFuncGate";
    }

    /**
     * Overrides the update method of OBJ_Gate so that it is non-functioning.
     */
    public void update(GamePanel gp) {
    }
}
