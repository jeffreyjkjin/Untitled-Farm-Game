package object;

import app.GamePanel;

public class OBJ_NonFuncStoneGate extends OBJ_StoneGate {
    
    /**
     * Calls the constructor of OBJ_StoneGate and changes the name of the object.
     */
    public OBJ_NonFuncStoneGate() {
        super();
        name = "NonFuncStoneGate";
    }

    /**
     * Overrides the update method of OBJ_Gate so that it is non-functioning.
     */
    public void update(GamePanel gp) {
    }
}
