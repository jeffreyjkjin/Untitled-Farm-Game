package app;

// import object.OBJ_Key;
import object.OBJ_Egg;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Egg();
		gp.obj[0].worldX = 7 * gp.tileSize;
		gp.obj[0].worldY = 7 * gp.tileSize;
		//set ? after choose a point on map
	}

}
