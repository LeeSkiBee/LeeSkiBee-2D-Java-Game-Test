package main.game.entity;

public class AI {
	
	public final static int AGRESS_NON		= 0;
	public final static int AGRESS_LOW		= 1;
	public final static int AGRESS_MED		= 2;
	public final static int AGRESS_HIGH		= 3;
	
	public final static int STATE_IDEAL		= 0;
	public final static int STATE_COMBAT	= 1;
	public final static int STATE_MED		= 2;
	public final static int STATE_HIGH		= 3;
	
	
	public final static int R_NO_ACTION		= 0;
	
	public final static int R_MOVE_UP 		= 1; 
	public final static int R_MOVE_LEFT 	= 2;
	public final static int R_MOVE_RIGHT	= 3;
	public final static int R_MOVE_DOWN 	= 4;
	
	public final static int R_ATTACK_UP 	= 5;
	public final static int R_ATTACK_LEFT 	= 6;
	public final static int R_ATTACK_RIGHT	= 7;
	public final static int R_ATTACK_DOWN 	= 8;

	
	public static enum types{
		MELEE,
		RANGED;
	}
	
	public static void update(Entity e){
		final int aggress = e.getAggressiveness();
		final int state = e.getState();
		
		if(aggress == AGRESS_NON){
			
		} else {
			
		}
	}

}
