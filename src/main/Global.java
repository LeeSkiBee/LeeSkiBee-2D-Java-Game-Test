package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import main.game.GameUpdate;
import main.game.entity.Entity;
import main.game.gui.GameInterface;
import main.game.gui.Positions;
import main.game.level.Level;
import main.game.projectiles.Projectile;

public final class Global {

	private final static float scale = 2.00F;
	private final static int MAX_KEY_VALUE = 256;
	
	
	
	public static enum gameControls{
		UP(KeyEvent.VK_W),
		LEFT(KeyEvent.VK_A),
		DOWN(KeyEvent.VK_S),
		RIGHT(KeyEvent.VK_D),
		PAUSE(KeyEvent.VK_ESCAPE),
		ATTACK_UP(KeyEvent.VK_UP),
		ATTACK_LEFT(KeyEvent.VK_LEFT),
		ATTACK_DOWN(KeyEvent.VK_DOWN),
		ATTACK_RIGHT(KeyEvent.VK_RIGHT),
		;
		
		private int id;
		
		private gameControls(int keyID){
			this.id = keyID;
		}
		
		public int getKey(){
			return this.id;
		}
	}
	
	public static Positions interfacePositions = new Positions(scale);
	
	public static List<Entity> entities = new ArrayList<>();
	public static List<Projectile> projectiles = new ArrayList<>();
	public static Level mainLevel;
	public static GameUpdate updateThread = new GameUpdate();
	public static Entity self;
	
	private static boolean[] keyPressedArray = new boolean[MAX_KEY_VALUE];
	
	
	
	private static GameInterface display;
	
	
	private static boolean inGame;
	private static boolean isPaused;
	private static int cursorX = 0;
	private static int cursorY = 0;
	
	public static boolean isInGame(){
		return inGame;
	}
	
	public static boolean isGamePaused(){
		return isPaused;
	}
	
	public static void updateDisplay(Graphics g){
		updateThread.draw(g);
	}
	
	public static void repaint(){
		display.drawFrame();
	}
	
	public static void setDisplay(GameInterface setTo){
		display = setTo;
	}
	
	public static void showDisplay(){
		display.show();
	}
	
	public static void addEntity(Entity ent){
		entities.add(ent);
	}
	
	public static void startFPS(){
		updateThread.startDisplayThread();
	}
	
	public static void stopFPS(){
		updateThread.stopDisplayThread();
	}
	
	public static void keyPressed(int keyID){
		if(keyID >= 0 && keyID < keyPressedArray.length){
			keyPressedArray[keyID] = true;
		} else {
			//TODO handle wrong key id submit
		}
	}
	
	public static void keyReleased(int keyID){
		if(keyID >= 0 && keyID < keyPressedArray.length){
			keyPressedArray[keyID] = false;
		} else {
			//TODO handle wrong key id submit
		}
	}
	
	public static boolean isKeyPressed(int keyID){
		boolean is;
		if(keyID >= 0 && keyID < keyPressedArray.length){
			is = keyPressedArray[keyID];
		} else {
			is = false;	//if the key ID does not exist - return false
		}
		return is;
	}
	
	public static void setCursorPosition(int x, int y){
		cursorX = x;
		cursorY = y;
	}
	
	public static int getCursorX(){
		return cursorX;
	}
	
	public static int getCursorY(){
		return cursorY;
	}
	
	public static int maxLevelXPos(){
		return mainLevel.getLengthX();
	}
	
	public static int maxLevelYPos(){
		return mainLevel.getLengthY();
	}
	
	public static int getPlayerDrawX(){
		return (Global.interfacePositions.get(Positions.width) / 2) - (Global.self.getWidth() / 2);
	}
	
	public static int getPlayerDrawY(){
		return (Global.interfacePositions.get(Positions.height) / 2) - (Global.self.getHeight() / 2);
	}
}
