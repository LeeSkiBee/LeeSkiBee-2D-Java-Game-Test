package main;

import java.awt.Color;

import main.sys.Info;

public final class Defines {
	
	public final static int GS = 30;
	public final static int FPS_TARGET = 30;					//anything over 1000 will be "unlimited FPS"
	public final static int DISPLAY_THREAD_SLEEP_TIME = 5;
	public final static int DRAW_DISTANCE = 11;
	
	public final static boolean monitorPerformance = false;
	public final static boolean drawGridOutline = false;
	public final static boolean drawGridHoverOutline = true;
	
	public final static int MIN_X_POS = 0;
	public final static int MIN_Y_POS = 0;
	
	public final static String corePath = Info.getClassPath().replaceAll("bin", "src");
	public final static String modelsPath = corePath + "/resources/models";
	
	public static enum Direction{
		NONE, UP, RIGHT, DOWN, LEFT;
	}
	
	public static enum gameColors{
		shadow(0,0,0,100),
		
		white(255,255,255,255),
		black(0,0,0,255),
		red(255,0,0,255),
		green(0,255,0,255),
		blue(0,0,255,255);
		;
		
		final int r;
		final int g;
		final int b;
		final int a;
		
		private gameColors(int red, int green, int blue, int alpha){
			this.r = red;
			this.g = green;
			this.b = blue;
			this.a = alpha;
		}
		
		public int getRed(){
			return this.r;
		}
		
		public int getGreen(){
			return this.g;
		}
		
		public int getBlue(){
			return this.b;
		}
		
		public int getAlpha(){
			return this.a;
		}
		
		public Color getColor(){
			return new Color(this.r, this.g, this.b, this.a);
		}
	}
	
	public static enum renderModes{
		swing;
	}
	
	public static enum contentPaths{
		models("resources/models");
		
		private String path;
		private contentPaths(String filePath){
			this.path = filePath;
		}
		
		public String getPath(){
			return this.path;
		}

	}
	
	
	public static enum entityTypes{
		none, 
		playerControlled, 
		clientPlayer, 
		nonCombatNPC, 
		combatNPC;
	}
	
	public static enum entityList{
		player, 
		zombie;
		
		// TODO add more and fix
		private entityTypes type;
		private int maxHealth;
		private float movespeed;
		private boolean attackable;
		
		private entityList(){
			
		}
		
		
	}
	
	
	public static enum items{
		none
	}
	
	public final static renderModes defaultRenderMode = renderModes.swing;
	
	public final static gameColors HOVER_BLOCK_BORDER_COLOR = gameColors.red;
	public final static gameColors GAME_BACKGROUND_COLOR = gameColors.black;
	public final static gameColors UNDEFINED_MODEL_COLOR = gameColors.blue;
	public final static gameColors SHADOW_COLOR = gameColors.shadow;
	
	public final static gameColors DEBUG_INFO_COLOR = gameColors.white;
	
	public final static gameColors HP_BACKGROUND_COLOR = gameColors.black;
	public final static gameColors HP_FOREGROUND_COLOR = gameColors.green;
	public final static int MIN_HP_SIZE = 1;

	
	
	public final static int ONE_SECOND = 1000;
	public final static int NEXT_FPS_TIMER = Math.round(ONE_SECOND / FPS_TARGET);
	public final static int NEXT_GS_TIMER = Math.round(ONE_SECOND / GS);
	public final static int DISPLAY_STATS_TIMER = 1000;
	
	public final static int MAX_ENTITIES = 128;
	public final static int MAX_PROJECTILES = 256;
	public final static int MAX_ITEM_STACK = 64;
	
}
