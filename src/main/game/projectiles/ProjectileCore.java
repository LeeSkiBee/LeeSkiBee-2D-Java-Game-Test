package main.game.projectiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Defines;
import main.Global;
import main.game.entity.Entity;
import main.util.Common;

public class ProjectileCore implements Projectile {
	
	//TODO knock back on hit
	
	private final static float MAX_TRAVEL_DISTANCE = (float) Defines.DRAW_DISTANCE;
	
	private Entity owner;
	private Defines.Direction travelDirection;
	private boolean visible;
	
	private float startX;
	private float startY;
	
	private float x;
	private float y;
	private float endX;
	private float endY;
	private int width;
	private int height;
	
	private boolean finished;
	
	private float speed;
	
	private BufferedImage model;
	
	private int damage;
	private boolean AOE;
	private float AOE_distance;
	
	private int targetsHit;
	private int maxTargets;
	private float damageMultiplerPerTarget;
	
	private boolean collideWithLevel;
	private boolean collideWithEntities;
	
	private boolean updateStartPositon;
	
	public ProjectileCore(Entity owner, BufferedImage project_model, float startX, float startY, int damage, float speed, Defines.Direction dir){
		this.travelDirection = dir;
		this.owner = owner;
		this.visible = true;
		
		this.startX = startX;
		this.startY = startY;

		this.x = startX;
		this.y = startY;
		
		this.finished = false;
		
		this.speed = speed;

		this.model = project_model;
		
		this.damage = damage;
		this.AOE = false;
		this.AOE_distance = 0.0F;
		
		this.targetsHit = 0;
		this.maxTargets = 1;
		this.damageMultiplerPerTarget = 1.0F;
		
		this.collideWithLevel = true;
		this.collideWithEntities = true;
		
		this.updateStartPositon = false;
		
		if(this.model != null){
			this.width = this.model.getWidth();
			this.height = this.model.getHeight();
		} else {
			this.width = 4;
			this.height = 4;
		}
		
		this.endX = Common.convertWidthPixelsToPosition(this.width);
		this.endY = Common.convertHeightPixelsToPosition(this.height);
	}

	@Override
	public void draw(Graphics g, int drawX, int drawY) {
		if(this.finished)
			return;
		
		if(this.model != null){
			g.drawImage(this.model, drawX, drawY, getWidth(), getHeight(), null);
		} else {
			g.setColor(Defines.UNDEFINED_MODEL_COLOR.getColor());
			g.fillOval(drawX, drawY, getWidth(), getHeight());
		}
		
	}

	@Override
	public void update() {
		
		if(this.finished){
			return;
		}
		
		if(this.travelDirection == Defines.Direction.NONE){
			//@empty-block
			//no travel for this projectile - do nothing
		}
		else if(this.travelDirection == Defines.Direction.UP){
			this.y -= this.speed;
		}
		else if(this.travelDirection == Defines.Direction.LEFT){
			this.x -= this.speed;
		}
		else if(this.travelDirection == Defines.Direction.DOWN){
			this.y += this.speed;
		}
		else if(this.travelDirection == Defines.Direction.RIGHT){
			this.x += this.speed;
		}
		else {
			//@empty-block
			//should never happen
		}
		
		checkPosition();
	}
	
	@Override
	public void performAOE(){
		
	}
	
	public void checkPosition(){			//checks the position for moving projectiles
		boolean endProjectile = false;
		final int floorX = (int)Math.floor(this.x);
		final int floorY = (int)Math.floor(this.y);
		
		if( hasReachedMaxDistance() ){
			endProjectile = true;
		}
		else if( (this.collideWithLevel) && (!Global.mainLevel.isBlockFree(floorX, floorY)) ){		//projectile hit a wall - end it
			endProjectile = true;
			
		} 
		else if( this.collideWithEntities ){
			
			final float proX = this.x;
			final float proY = this.y;							//projectile hit box
			final float proX_end = proX + this.endX;
			final float proY_end = proY + this.endY;
			
			
			float entX;
			float entY;										//entity hit box
			float entX_end;
			float entY_end;
			
			boolean hit = false;
			
			for(int i = 0; i < Global.entities.size(); i++){
				
				if(Global.entities.get(i).canBeAttacked()){			//ignore them if we cant attack them
					entX = Global.entities.get(i).getX();
					entY = Global.entities.get(i).getY();
					entX_end = Global.entities.get(i).getEndX();
					entY_end = Global.entities.get(i).getEndY();
					
					if(Common.isPointWithinArea(proX, proY, entX, entY, entX_end, entY_end)){	//top left corner inside entity
						hit = true;
					}
					else if(Common.isPointWithinArea(proX, proY_end, entX, entY, entX_end, entY_end)){	//top right corner inside entity
						hit = true;
					}
					else if(Common.isPointWithinArea(proX, proY_end, entX, entY, entX_end, entY_end)){	//bottom left corner inside entity
						hit = true;
					}
					else if(Common.isPointWithinArea(proX_end, proY_end, entX, entY, entX_end, entY_end)){	//bottom right corner inside entity
						hit = true;
					}
					else {
						hit = false;
					}
					
					if(hit){
						if(this.AOE){
							performAOE();
							endProjectile = true;
							break;
						} else {
							Global.entities.get(i).damage(this.damage, owner);		//entity hit
							this.targetsHit += 1;
							this.damage = Math.round( (float)this.damage * this.damageMultiplerPerTarget );
							if(this.targetsHit >= this.maxTargets){
								endProjectile = true;
								break;
							}
						}
					}
				}
			}
		}
		
		
		if(endProjectile){
			finishProjectile();
		}
	}
	
	private boolean hasReachedMaxDistance(){
		boolean has = false;
		
		float x_travel_dis = this.x - this.startX;
		float y_travel_dis = this.y - this.startY;
		
		if( x_travel_dis < 0){
			 x_travel_dis = Common.invertFloat(x_travel_dis);
		}
		
		if( y_travel_dis < 0){
			 y_travel_dis = Common.invertFloat(y_travel_dis);
		}
		
		
		if( x_travel_dis >= MAX_TRAVEL_DISTANCE ){
			has = true;
		}
		else if( y_travel_dis >= MAX_TRAVEL_DISTANCE ){
			has = true;
		}
		else {
			has = false;
		}
		
		return has;
	}
	
	public float getEndX(){
		return (this.x + this.endX);
	}
	
	public float getEndY(){
		return (this.y + this.endY);
	}
	
	@Override
	public void finishProjectile(){
		this.visible = false;
		this.finished = true;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getDamage() {
		return this.damage;
	}

	@Override
	public boolean isAOE() {
		return this.AOE;
	}

	@Override
	public float getAOE() {
		return this.AOE_distance;
	}

	@Override
	public void setAOE(boolean b, float distance) {
		this.AOE = true;
		this.AOE_distance = distance;
	}

	@Override
	public void setModel(BufferedImage model) {
		this.model = model;
		this.width = model.getWidth();
		this.height = model.getHeight();
		this.endX = Common.convertWidthPixelsToPosition(this.width);
		this.endY = Common.convertHeightPixelsToPosition(this.height);
		
	}

	@Override
	public void setLevelCollision(boolean collide) {
		this.collideWithLevel = collide;
		
	}

	@Override
	public void setMaxTargets(int targetAmount) {
		this.maxTargets = targetAmount;
		
	}

	@Override
	public void setTargetHitDamageMultipler(float multi) {
		this.damageMultiplerPerTarget = multi;
		
	}

	@Override
	public void setX(float set) {
		this.x = set;
		
	}

	@Override
	public void setY(float set) {
		this.y = set;
		
	}

	@Override
	public void setDamage(int damage) {
		this.damage = damage;
		
	}

	@Override
	public Defines.Direction getDirection() {
		return this.travelDirection;
	}

	@Override
	public void setUpdatePosition(boolean update) {
		this.updateStartPositon = update;
		
	}

	@Override
	public boolean shouldUpdatePosition() {
		return this.updateStartPositon;
	}

	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
		
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public boolean isFinished() {
		return this.finished;
	}

	@Override
	public void updatePosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
		
	}

	@Override
	public Entity getOwner() {
		return this.owner;
	}

	@Override
	public void show() {
		this.visible = true;
		
	}

	@Override
	public void hide() {
		this.visible = false;
		
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}
	

}
