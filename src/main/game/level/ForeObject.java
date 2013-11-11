package main.game.level;

import java.awt.Graphics;

public interface ForeObject {

	public void draw(Graphics g, int drawX, int drawY);
	public void update();
	
	public int getX();
	public int getY();
	
	public boolean canWalk();
	public boolean canExplode();
	
	public void setCanWalkOn(boolean b);
	public void setCanExplode(boolean b);

	
}
