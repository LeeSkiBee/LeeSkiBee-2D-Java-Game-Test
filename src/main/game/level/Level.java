package main.game.level;

import java.awt.Graphics;

public interface Level {
	
	public void draw(Graphics g, int x, int y, int distance, float additionX, float additionY);
	public int getLengthX();
	public int getLengthY();
	
	public boolean isBlockFree(int col, int row);

}
