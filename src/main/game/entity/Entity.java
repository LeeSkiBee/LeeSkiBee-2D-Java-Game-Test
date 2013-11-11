package main.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Defines;

public interface Entity {
	
	public void update();
	public void draw(Graphics g, int drawX, int drawY);
	public void drawModel(Graphics g, int id, int drawX, int drawY);
	
	public void teleportTo(float tpX, float tpY);
	public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
	
	public void attackUp();
	public void attackDown();
	public void attackLeft();
	public void attackRight();
	
	public float getX();
	public float getY();
	public float getEndX();
	public float getEndY();
	public int getWidth();
	public int getHeight();
	
	public void show();
	public void hide();
	public void kill();
	public void damage(int amount, Entity source);
	public void heal(int amount);
	
	public void addModel(int id, String location);
	public BufferedImage getModel(int id);
	
	public boolean canBeAttacked();
	public boolean isVisible();
	
	public float getMovespeed();
	public void setMovespeed(float speed);
	
	public int getAggressiveness();
	public int getState();
	
	public Defines.Direction getFacingDirection();

}
