package main.game.item;

import java.awt.Graphics;

import main.game.entity.Entity;

public interface Item {
	
	public void draw(Graphics g, int drawX, int drawY);
	public void update();
	
	public void use(Entity user);
	public Item getItem();
	
	public String getName();
	public int getMaxStackAmount();
	public boolean getReusable();

}
