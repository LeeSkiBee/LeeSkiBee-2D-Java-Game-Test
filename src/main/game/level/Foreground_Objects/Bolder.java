package main.game.level.Foreground_Objects;

import java.awt.Graphics;

import main.game.level.ForeObject;
import main.game.level.ForegroundObject;
import main.sys.Info;

public class Bolder implements ForeObject {
	
	private final static String MODEL_LOC = "/levels/foreground/bolder.png";
	
	private ForegroundObject core;
	
	public Bolder(int bX, int bY){
		this.core = new ForegroundObject(Info.getBufferedImageFromResource(MODEL_LOC), false, bX, bY);
	}

	@Override
	public void draw(Graphics g, int drawX, int drawY) {
		this.core.draw(g, drawX, drawY);
	}

	@Override
	public void update() {
		this.core.update();
		
	}

	@Override
	public int getX() {
		return this.core.getX();
	}

	@Override
	public int getY() {
		return this.core.getY();
	}

	@Override
	public boolean canWalk() {
		return this.core.canWalk();
	}

	@Override
	public boolean canExplode() {
		return this.core.canExplode();
	}

	@Override
	public void setCanWalkOn(boolean b) {
		this.core.setCanWalkOn(b);
		
	}

	@Override
	public void setCanExplode(boolean b) {
		this.core.setCanExplode(b);
		
	}

}
