package main.game.projectiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Defines;
import main.game.entity.Entity;

public interface Projectile {
	
	public void draw(Graphics g, int drawX, int drawY);
	public void update();
	public void performAOE();
	
	public float getX();
	public float getY();
	public float getEndX();
	public float getEndY();
	public int getWidth();
	public int getHeight();
	public int getDamage();
	public Entity getOwner();
	
	public void show();
	public void hide();
	public boolean isVisible();
	
	public void checkPosition();
	public void finishProjectile();
	
	public boolean isAOE();
	public float getAOE();
	public void setAOE(boolean b, float distance);
	
	public void setModel(BufferedImage model);
	
	public void setLevelCollision(boolean collide);
	public void setMaxTargets(int targetAmount);
	public void setTargetHitDamageMultipler(float multi);
	
	public void setX(float set);
	public void setY(float set);
	
	public void setDamage(int damage);
	
	public Defines.Direction getDirection();
	
	public void updatePosition(float newX, float newY);
	public void setUpdatePosition(boolean update);
	public boolean shouldUpdatePosition();
	
	public void setSpeed(float speed);
	public float getSpeed();
	
	public boolean isFinished();

}
