package main.game;

import java.awt.Color;
import java.awt.Graphics;

import main.Defines;
import main.Global;
import main.sys.Info;
import main.util.Common;

public class GameUpdate implements Runnable {
	
	private final int DEBUG_DISPLAY_LINES_AMOUNT = 3;
	private final int DEBUG_LINE_Y_PIXELS = 20;
	private final static int runsPerAverageTimeOutput = 1000; 
	
	private boolean drawFrames;
	private int drawnFrames;
	private Thread updateDisplay;
	
	private int[] debugOutputPositions;
	private Color debugColor;
	private StringBuilder[] debugText;
	
	public GameUpdate(){

		this.drawFrames = false;
		this.drawnFrames = 0;
		this.updateDisplay = new Thread(this, "Update display");
		
		this.debugColor = Defines.DEBUG_INFO_COLOR.getColor();
		this.debugOutputPositions = new int[DEBUG_DISPLAY_LINES_AMOUNT];
		for(int i = 0; i < DEBUG_DISPLAY_LINES_AMOUNT; i++){
			this.debugOutputPositions[i] = (i + 1) * DEBUG_LINE_Y_PIXELS;
		}
		
		this.debugText = new StringBuilder[DEBUG_DISPLAY_LINES_AMOUNT];
		for(int i = 0; i < this.debugText.length; i++){
			this.debugText[i] = new StringBuilder();
		}
	}
	
	public void updateGame(){
		if(Global.isKeyPressed(Global.gameControls.UP.getKey())){				//move up pressed
			Global.self.moveUp();
		}
		if(Global.isKeyPressed(Global.gameControls.LEFT.getKey())){				//move left pressed
			Global.self.moveLeft();
		}
		if(Global.isKeyPressed(Global.gameControls.DOWN.getKey())){				//move down pressed
			Global.self.moveDown();
		}
		if(Global.isKeyPressed(Global.gameControls.RIGHT.getKey())){			//move right pressed
			Global.self.moveRight();
		}
		
		
		if(Global.isKeyPressed(Global.gameControls.ATTACK_UP.getKey())){		//attack up pressed
			Global.self.attackUp();
		}
		else if(Global.isKeyPressed(Global.gameControls.ATTACK_LEFT.getKey())){		//attack left pressed
			Global.self.attackLeft();
		}
		else if(Global.isKeyPressed(Global.gameControls.ATTACK_DOWN.getKey())){		//attack down pressed
			Global.self.attackDown();
		}
		else if(Global.isKeyPressed(Global.gameControls.ATTACK_RIGHT.getKey())){		//attack right pressed
			Global.self.attackRight();
		}
		
		for(int i = 0; i < Global.entities.size(); i++){
			Global.entities.get(i).update();
		}
		
		for(int i = 0; i < Global.projectiles.size(); i++){
			if(!Global.projectiles.get(i).isFinished()){
				Global.projectiles.get(i).update();
			} else {
				Global.projectiles.remove(i);		//finished projectile - remove it
			}
		}
	}
	
	public void draw(Graphics g){
		final int selfX = (int)Math.floor(Global.self.getX());
		final int selfY = (int)Math.floor(Global.self.getY());
		final float selfX_add = Global.self.getX() - selfX;
		final float selfY_add = Global.self.getY() - selfY;
		
		
		//Level
		Global.mainLevel.draw(g, selfX, selfY, Defines.DRAW_DISTANCE, selfX_add, selfY_add);
		
		
		//projectiles
		drawProjectiles(g);
		
		
		//entities
		drawEntities(g);
		
		
		
		//debug info
		drawDebugOutput(g);
	}
	
	public void startDisplayThread(){
		drawFrames = true;
		updateDisplay.start();
	}
	
	public void stopDisplayThread(){
		drawFrames = false;
	}
	
	public void drawEntities(Graphics g){
		int drawX = 0;
		int drawY = 0;
		float currentX;
		float currentY;
		
		for(int i = 0; i < Global.entities.size(); i++){
			if(Global.entities.get(i).isVisible()){						//only check the position of the entity if it is visible
				currentX = Global.entities.get(i).getX();
				currentY = Global.entities.get(i).getY();
				if( Common.isInDrawZone(currentX, currentY) ){
					drawX = Common.getDrawXFromPosition(currentX);
					drawY = Common.getDrawYFromPosition(currentY);
					Global.entities.get(i).draw(g, drawX, drawY);
				}
						
			} else {
				continue;		//skip the entity if it is not visible
			}
		}
		
		
		final int selfDrawX = Global.getPlayerDrawX();
		final int selfDrawY = Global.getPlayerDrawY();
		Global.self.draw(g, selfDrawX, selfDrawY);
	}
	
	public void drawProjectiles(Graphics g){		
		int drawX = 0;
		int drawY = 0;
		float currentX;
		float currentY;
		
		for(int i = 0; i < Global.projectiles.size(); i++){
			try{
				if(Global.projectiles.get(i).isVisible()){						//only check the position of the entity if it is visible
					currentX = Global.projectiles.get(i).getX();
					currentY = Global.projectiles.get(i).getY();
					if( Common.isInDrawZone(currentX, currentY) ){
						drawX = Common.getDrawXFromPosition(currentX);
						drawY = Common.getDrawYFromPosition(currentY);
						Global.projectiles.get(i).draw(g, drawX, drawY);
					}
							
				} else {
					continue;		//skip the entity if it is not visible
				}
			} catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void drawDebugOutput(Graphics g){
		
		for(int i = 0; i < this.debugText.length; i++){
			this.debugText[i].setLength(0);
		}
		
		this.debugText[0].append("FPS: ").append(String.valueOf(drawnFrames));
		this.debugText[1].append("POS: ").append(String.valueOf(Global.self.getX())).append(" , ").append(String.valueOf(Global.self.getY()));
		this.debugText[2].append("E POS: ").append(String.valueOf(Global.self.getEndX())).append(" , ").append(String.valueOf(Global.self.getEndY()));
		
		g.setColor(this.debugColor);
		for(int i = 0; i < this.debugText.length; i++){
			g.drawString(this.debugText[i].toString(), 10, this.debugOutputPositions[i]);
		}
		
	}
	
	@Override
	public void run() {
		try {
			long currentTime =  Info.getTime();
			long nextFrameRun = currentTime;
			long nextDebugInfoupdate = currentTime;
			long nextGameUpdate = currentTime;
			
			final long frameAddition = Defines.NEXT_FPS_TIMER;
			final long FPSAddition = Defines.DISPLAY_STATS_TIMER;
			final long sleepTime = Defines.DISPLAY_THREAD_SLEEP_TIME;
			final long gameUpdateAddition = Defines.NEXT_GS_TIMER;
			final boolean monitorLoopPerformance = Defines.monitorPerformance;
			int frames = 0;
			
			long loopStart;
			long lastLoop;
			long loopsDone;


			if(monitorLoopPerformance){
				loopStart = Info.getPreciseTime();
				lastLoop = loopStart;
				loopsDone = 0;
			} 
			
			while(drawFrames){
				currentTime = Info.getTime();
				
				
				if(!Global.isGamePaused()){
					if(currentTime >= nextGameUpdate){
						nextGameUpdate = currentTime + gameUpdateAddition;
						updateGame();
					}
				}
				
				
				if(currentTime >= nextFrameRun){
					if(currentTime >= nextDebugInfoupdate){
						nextDebugInfoupdate = currentTime + FPSAddition;
						drawnFrames = frames;
						frames = 0;
					}
					nextFrameRun = currentTime + frameAddition;
					Global.repaint();
					frames += 1;
				}
				
				if(monitorLoopPerformance){
					loopsDone += 1;
					if( (loopsDone % runsPerAverageTimeOutput) == 0){
						lastLoop = Info.getPreciseTime();
						System.out.println(Math.round((lastLoop - loopStart) / loopsDone));
					}
				}
				
				currentTime = Info.getTime();
				if(currentTime >= nextFrameRun){	//if this is true, then we should already be drawing the next frame - don't sleep
					//@empty-block
				} else {
					Thread.sleep(sleepTime);
				}
				
			}	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
