package main.game.gui;


public class Positions {
	
	private float scale;
	
	
	
	public final static int width 				= 0;
	public final static int height 				= 1;
	
	public final static int gameBlockWidth 		= 2;
	public final static int gameBlockHeight 	= 3;
	
	public final static int HPwidth 			= 4;
	public final static int HPheight 			= 5;
	public final static int HPpadding 			= 6;
	public final static int HPborder 			= 7;
	
	private final static int[] anchors = {
		
		320,		//width
		240,		//height
		
		16,			//block width
		16,			//block height
		
		16,			//HP bar width
		4,			//HP bar height
		8,			//HP bar padding
		1,			//HP bar border size
		
	};
	
	private int[] pos = new int[anchors.length];
	
	public Positions(float posScale){
		this.scale = posScale;
		updateSizes();
	}
	
	
	public void updateSizes(){
		for(int i = anchors.length - 1; i >= 0; i--){
			this.pos[i] = getScaledSize(anchors[i]);
		}
	}
	
	public void updateScale(float updatedScale){
		this.scale = updatedScale;
		updateSizes();
	}
	
	
	
	private int getScaledSize(int size){
		return Math.round(size * scale);
	}
	
	public int get(int index){
		if(index >= 0 && index < this.pos.length){
			return this.pos[index];
		} else {
			return 0;
		}
		
	}

}
