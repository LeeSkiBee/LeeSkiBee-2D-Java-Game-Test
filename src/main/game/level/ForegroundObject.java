package main.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ForegroundObject implements ForeObject {
	
	BufferedImage model;
	
	private int x;
	private int y;
	private boolean canWalkOn;
	private boolean canBlowUp;
	
	public ForegroundObject(BufferedImage objectModel, boolean walkOn, int x, int y){
		this.canWalkOn = walkOn;
		this.canBlowUp = true;
		this.model = objectModel;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics g, int drawX, int drawY){
		g.drawImage(this.model, drawX, drawY, null);
	}
	
	@Override
	public void update(){
		
	}
	
	@Override
	public int getX(){
		return this.x;
	}
	
	@Override
	public int getY(){
		return this.y;
	}
	
	@Override
	public boolean canWalk(){
		return this.canWalkOn;
	}
	
	@Override
	public boolean canExplode(){
		return this.canBlowUp;
	}
	
	@Override
	public void setCanWalkOn(boolean b){
		this.canWalkOn = b;
	}
	
	@Override
	public void setCanExplode(boolean b){
		this.canBlowUp = b;
	}

}
