package main.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Defines.Direction;

public class Player implements Entity{
	
	private Entity core;
	
	public Player(){
		core = new Elite();
	}

	@Override
	public void update(){
		this.core.update();
	}
	
	@Override
	public void draw(Graphics g, int drawX, int drawY) {
		this.core.draw(g, drawX, drawY);
	}
	
	public void drawModel(Graphics g, int id, int drawX, int drawY){
		g.drawImage(this.core.getModel(id), drawX, drawY, this.core.getModel(id).getWidth(), this.core.getModel(id).getHeight(), null, null);
	}

	@Override
	public void teleportTo(float tpX, float tpY) {
		this.core.teleportTo(tpX, tpY);
	}

	@Override
	public void moveUp() {
		this.core.moveUp();
		
	}

	@Override
	public void moveDown() {
		this.core.moveDown();
		
	}

	@Override
	public void moveLeft() {
		this.core.moveLeft();
		
	}

	@Override
	public void moveRight() {
		this.core.moveRight();
		
	}
	
	@Override
	public void attackUp(){
		this.core.attackUp();
	}
	
	@Override
	public void attackDown(){
		this.core.attackDown();
	}
	
	@Override
	public void attackLeft(){
		this.core.attackLeft();
	}
	
	@Override
	public void attackRight(){
		this.core.attackRight();
	}

	@Override
	public float getX() {
		return this.core.getX();
	}

	@Override
	public float getY() {
		return this.core.getY();
	}
	
	public float getEndX(){
		return this.core.getEndX();
	}
	
	public float getEndY(){
		return this.core.getEndY();
	}

	@Override
	public void show() {
		this.core.show();
		
	}

	@Override
	public void hide() {
		this.core.hide();
		
	}

	@Override
	public void kill() {
		this.core.kill();
		
	}

	@Override
	public void damage(int amount, Entity source) {
		this.core.damage(amount, source);
		
	}

	@Override
	public void heal(int amount) {
		this.core.heal(amount);
		
	}

	@Override
	public boolean canBeAttacked() {
		return this.core.canBeAttacked();
	}

	@Override
	public float getMovespeed() {
		return this.core.getMovespeed();
	}

	@Override
	public void setMovespeed(float speed) {
		this.core.setMovespeed(speed);
	}

	@Override
	public void addModel(int id, String location) {
		this.core.addModel(id, location);
	}

	@Override
	public BufferedImage getModel(int id) {
		return this.core.getModel(id);
	}

	@Override
	public int getWidth() {
		return this.core.getWidth();
	}

	@Override
	public int getHeight() {
		return this.core.getHeight();
	}

	@Override
	public boolean isVisible() {
		return this.core.isVisible();
	}

	@Override
	public Direction getFacingDirection() {
		return this.core.getFacingDirection();
	}

	@Override
	public int getAggressiveness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return 0;
	}

}
