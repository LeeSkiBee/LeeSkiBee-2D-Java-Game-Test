package main.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Defines;
import main.Global;
import main.game.projectiles.ProjectileCore;

public class Elite implements Entity{
	
	private final static int M_FRONT_BODY_ID 			= 0;
	private final static int M_FRONT_FACE_ID 			= 1;
	
	private final static int M_LEFT_ARM_ID 				= 2;
	private final static int M_LEFT_LEG_STANDING_ID 	= 3;
	private final static int M_LEFT_LEG_WALKING_ID 		= 4;
	
	private final static int M_RIGHT_ARM_ID 			= 5;
	private final static int M_RIGHT_LEG_STANDING_ID 	= 6;
	private final static int M_RIGHT_LEG_WALKING_ID 	= 7;
	
	private final static int M_SIDE_BODY_ID 			= 8;
	private final static int M_SIDE_LEGS_ID 			= 9;
	private final static int M_LEFT_SIDE_FACE_ID 		= 10;
	private final static int M_RIGHT_SIDE_FACE_ID 		= 11;
	
	private final static int MODELS_AMOUNT 				= 12;
	
	
	private final static String M_FRONT_BODY_LOC = "/entityModels/elite/elite_front_body.png";
	private final static String M_FRONT_FACE_LOC = "/entityModels/elite/elite_front_face.png";
	
	private final static String M_LEFT_ARM_LOC = "/entityModels/elite/elite_arm.png";
	private final static String M_LEFT_LEG_STANDING_LOC = "/entityModels/elite/elite_standing_leg.png";
	private final static String M_LEFT_LEG_WALKING_LOC = "/entityModels/elite/elite_walking_leg.png";
	
	private final static String M_RIGHT_ARM_LOC = "/entityModels/elite/elite_arm_flipped.png";
	private final static String M_RIGHT_LEG_STANDING_LOC = "/entityModels/elite/elite_standing_leg_flipped.png";
	private final static String M_RIGHT_LEG_WALKING_LOC = "/entityModels/elite/elite_walking_leg_flipped.png";
	
	private final static String M_SIDE_BODY_LOC = "/entityModels/elite/elite_side_body.png";
	private final static String M_SIDE_LEGS_LOC = "/entityModels/elite/elite_side_legs.png";
	private final static String M_LEFT_SIDE_FACE_LOC = "/entityModels/elite/elite_side_face.png";
	private final static String M_RIGHT_SIDE_FACE_LOC = "/entityModels/elite/elite_side_face_flipped.png";
	
	private int legYaddition;
	private int legXaddition;
	private int armXaddition;
	private int faceXaddition;
	
	private Entity core;
	
	
	
	
	public Elite(){
		core = new EntityCore(Defines.entityList.player, MODELS_AMOUNT);
		core.addModel(M_FRONT_BODY_ID, M_FRONT_BODY_LOC);
		core.addModel(M_FRONT_FACE_ID, M_FRONT_FACE_LOC);
		
		core.addModel(M_LEFT_ARM_ID, M_LEFT_ARM_LOC);
		core.addModel(M_LEFT_LEG_STANDING_ID, M_LEFT_LEG_STANDING_LOC);
		core.addModel(M_LEFT_LEG_WALKING_ID, M_LEFT_LEG_WALKING_LOC);
		
		core.addModel(M_RIGHT_ARM_ID, M_RIGHT_ARM_LOC);
		core.addModel(M_RIGHT_LEG_STANDING_ID, M_RIGHT_LEG_STANDING_LOC);
		core.addModel(M_RIGHT_LEG_WALKING_ID, M_RIGHT_LEG_WALKING_LOC);
		
		core.addModel(M_SIDE_BODY_ID, M_SIDE_BODY_LOC);
		core.addModel(M_SIDE_LEGS_ID, M_SIDE_LEGS_LOC);
		core.addModel(M_LEFT_SIDE_FACE_ID, M_LEFT_SIDE_FACE_LOC);
		core.addModel(M_RIGHT_SIDE_FACE_ID, M_RIGHT_SIDE_FACE_LOC);

		
		this.legYaddition = core.getModel(M_FRONT_BODY_ID).getHeight();
		this.armXaddition = core.getModel(M_FRONT_BODY_ID).getWidth();
		this.legXaddition = core.getModel(M_FRONT_BODY_ID).getWidth() - core.getModel(M_RIGHT_LEG_STANDING_ID).getWidth();
		this.faceXaddition = this.armXaddition - core.getModel(M_RIGHT_SIDE_FACE_ID).getWidth();
		
	}
	
	@Override
	public void update(){
		this.core.update();
	}

	@Override
	public void draw(Graphics g, int drawX, int drawY) {
		this.core.draw(g, drawX, drawY);
		
		if(this.core.getFacingDirection() == Defines.Direction.DOWN || this.core.getFacingDirection() == Defines.Direction.UP){
			drawModel(g, M_FRONT_BODY_ID, drawX, drawY);
			drawModel(g, M_LEFT_ARM_ID, drawX, drawY);
			drawModel(g, M_RIGHT_ARM_ID, drawX + this.armXaddition, drawY);
			drawModel(g, M_LEFT_LEG_STANDING_ID, drawX, drawY + this.legYaddition);
			drawModel(g, M_RIGHT_LEG_STANDING_ID, drawX + this.legXaddition, drawY + this.legYaddition);
			if(this.core.getFacingDirection() == Defines.Direction.DOWN){
				drawModel(g, M_FRONT_FACE_ID, drawX, drawY);
			}
		}
		else {
			drawModel(g, M_SIDE_BODY_ID, drawX, drawY);
			drawModel(g, M_SIDE_LEGS_ID, drawX, drawY + this.legYaddition);
			if(this.core.getFacingDirection() == Defines.Direction.LEFT){
				drawModel(g, M_LEFT_SIDE_FACE_ID, drawX, drawY);
			} else {
				drawModel(g, M_RIGHT_SIDE_FACE_ID, drawX + this.faceXaddition, drawY);
			}
		}
		
	}
	
	@Override
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
		Global.projectiles.add(new ProjectileCore(
				Global.self, 
				null, 
				Global.self.getX(), 
				Global.self.getY(), 
				5, 
				0.40F, 
				Defines.Direction.UP));
		
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
	public Defines.Direction getFacingDirection(){
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
