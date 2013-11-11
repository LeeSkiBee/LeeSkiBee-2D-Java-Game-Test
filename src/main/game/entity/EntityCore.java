package main.game.entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import main.Defines;
import main.Global;
import main.Settings;
import main.game.gui.Positions;
import main.sys.Info;
import main.util.Common;

public class EntityCore implements Entity {
	
	private Defines.entityTypes genericType;
	private Defines.entityList  type;
	private BufferedImage[] models;
	private Defines.Direction facingDirection;
	
	private float x;
	private float y;
	private float endX;
	private float endY;
	private int width;
	private int height;
	
	private float movespeed;
	private int maxHealth;
	private int health;
	
	private final int aggressiveness;
	
	private boolean visible;
	private boolean attackable;
	
	private float sightDistance;
	//items...
	//equipment 
	
	
	
	// TODO read values from a table with the type???
	public EntityCore(Defines.entityList type, int maxModels){
		this.x = 10.00F;
		this.y = 10.00F;
		
		this.facingDirection = Defines.Direction.DOWN;
		this.aggressiveness = AI.AGRESS_HIGH;
		
		this.width = 32;
		this.height = 32;
		this.movespeed = 0.15F;
		this.sightDistance = 3.00F;
		
		this.endX = Common.convertWidthPixelsToPosition(this.width);
		this.endY = Common.convertHeightPixelsToPosition(this.height);
		
		this.visible = true;
		this.type = type;
		this.health = 100;
		this.maxHealth = 100;
		
		this.visible = true;
		this.attackable = true;
		
		this.models = new BufferedImage[maxModels];
		this.genericType = getGenericEntityType(type);
	}
	
	public static Defines.entityTypes getGenericEntityType(Defines.entityList listType){
		Defines.entityTypes returnType = Defines.entityTypes.none; //default
		
		if(listType == Defines.entityList.player){
			returnType = Defines.entityTypes.playerControlled;
		}
		else if(listType == Defines.entityList.zombie){
			returnType = Defines.entityTypes.combatNPC;
		}
		else {
			//@empty-block
			//this should never occur
		}
		
		
		return returnType;
	}
	
	@Override
	public void update(){
		final float selfX = Global.self.getX();
		final float selfY = Global.self.getY();
		
		if(selfX == this.x){
			
		}	
		else if(selfX < this.x){
			moveLeft();
		} else {
			moveRight();
		}
	}
	
 
	@Override
	public void draw(Graphics g, int drawX, int drawY) {
		drawShadow(g, drawX, drawY);
		boolean drawHPbar = true;
		
		if( Settings.getSetting(Settings.S_SELECTIVE_HP_BAR_DISPLAY) == Settings.V_TRUE ){
			if(this.health == this.maxHealth){
				drawHPbar = false;
			}
		}
		
		if(drawHPbar){
			drawHP(g, drawX, drawY);
		}

	}
	
	public void drawModel(Graphics g, int id, int drawX, int drawY){
		g.drawImage(this.getModel(id), drawX, drawY, this.getModel(id).getWidth(), this.getModel(id).getHeight(), null, null);
	}
	
	private void drawShadow(Graphics g, int drawX, int drawY){
		final int halfHeight = (this.height / 2);
		
		g.setColor(Defines.SHADOW_COLOR.getColor());
		g.fillOval(drawX, drawY + halfHeight + (halfHeight / 3) , this.width, halfHeight);
	}

	private void drawHP(Graphics g, int drawX, int drawY){
		final int barWidth = Global.interfacePositions.get(Positions.HPwidth);
		final int barHeight = Global.interfacePositions.get(Positions.HPheight);
		final int barBorder = Global.interfacePositions.get(Positions.HPborder);
		final int maxBarSize = barWidth - (barBorder * 2);
		
		g.setColor(Defines.HP_BACKGROUND_COLOR.getColor());
		g.fillRect(
				drawX, 
				drawY - Global.interfacePositions.get(Positions.HPpadding), 
				barWidth, 
				barHeight
			);
		
		if(this.health > 0){
			int healthWidth = maxBarSize;
			if(this.health != this.maxHealth){
				final int healthPercent = Math.round( Common.getPercentage(this.health, this.maxHealth) );
				healthWidth = Math.round( (((float)healthPercent / 100) * barWidth) );
				if(healthWidth < Defines.MIN_HP_SIZE){
					healthWidth = Defines.MIN_HP_SIZE;
				}
				else if(healthWidth > maxBarSize){
					healthWidth = maxBarSize;
				}
			}
			
			g.setColor(Defines.HP_FOREGROUND_COLOR.getColor());
			g.fillRect(
					drawX + barBorder, 
					drawY - Global.interfacePositions.get(Positions.HPpadding) + barBorder, 
					healthWidth, 
					barHeight - (barBorder * 2)
				);
		}
		
	}
	
	@Override
	public void teleportTo(float tpX, float tpY) {
		this.x = tpX;
		this.y = tpY;
		
	}

	@Override
	public void moveUp() {
		final float nextPos = this.y - this.movespeed;
		final boolean can = canMoveTo(this.x, nextPos);
		
		if(can){
			this.y = nextPos;
			this.facingDirection = Defines.Direction.UP;
		}
	}

	@Override
	public void moveDown() {
		final float nextPos = this.y + this.movespeed;
		final boolean can = canMoveTo(this.x, nextPos);
		
		if(can){
			this.y = nextPos;
			this.facingDirection = Defines.Direction.DOWN;
		}
	}

	@Override
	public void moveLeft() {
		final float nextPos = this.x - this.movespeed;
		final boolean can = canMoveTo(nextPos, this.y);
		
		if(can){
			this.x = nextPos;
			this.facingDirection = Defines.Direction.LEFT;
		}
	}

	@Override
	public void moveRight() {
		final float nextPos = this.x + this.movespeed;
		final boolean can = canMoveTo(nextPos, this.y);
		
		if(can){
			this.x = nextPos;
			this.facingDirection = Defines.Direction.RIGHT;
		}
	}
	
	private boolean canMoveTo(float xPos, float yPos) {
		boolean can = false;
		if( (xPos >= Defines.MIN_X_POS) && (xPos <= Global.maxLevelXPos()) ){
			if( (yPos >= Defines.MIN_Y_POS) && (yPos <= Global.maxLevelYPos()) ){
				int startX = (int)Math.floor(xPos);
				int endX = (int)Math.floor(xPos + this.endX);
				float xAdd = (float) (xPos - Math.floor(xPos));
				if(xAdd == (float) 0){
					endX -= 1;
				}
				
				int startY = (int)Math.floor(yPos);
				int endY = (int)Math.floor(yPos + this.endY);
				float yAdd = (float) (yPos - Math.floor(yPos));
				if(yAdd == (float) 0){
					endY -= 1;
				}
				boolean topLeftIn = Global.mainLevel.isBlockFree(startX, startY);
				boolean topRightIn = Global.mainLevel.isBlockFree(endX, startY);
				boolean botLeftIn = Global.mainLevel.isBlockFree(startX, endY);
				boolean botRightIn = Global.mainLevel.isBlockFree(endX, endY);
				
				
				if( (topLeftIn) && (topRightIn) && (botLeftIn) && (botRightIn) ){
					can = true;
				}
				
				
			}
		}
		return can;
	}
	
	@Override
	public void attackUp(){
		this.facingDirection = Defines.Direction.UP;
	}
	
	@Override
	public void attackDown(){
		this.facingDirection = Defines.Direction.DOWN;
	}
	
	@Override
	public void attackLeft(){
		this.facingDirection = Defines.Direction.LEFT;
	}
	
	@Override
	public void attackRight(){
		this.facingDirection = Defines.Direction.RIGHT;
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
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
	public boolean canBeAttacked() {
		return this.attackable;
	}

	@Override
	public void damage(int amount, Entity source) {
		this.health -= amount;
	}

	@Override
	public void heal(int amount) {
		if(this.health != this.maxHealth){
			if( (this.health + amount) > this.maxHealth) {
				this.health = this.maxHealth;
			} else {
				this.health += amount;
			}
		}
		
		
	}

	@Override
	public float getMovespeed() {
		return this.movespeed;
	}

	@Override
	public void setMovespeed(float speed) {
		this.movespeed = speed;
		
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}
	
	public float getEndX(){
		return this.x + this.endX;
	}
	
	public float getEndY(){
		return this.y + this.endY;
	}

	@Override
	public void addModel(int id, String location) {
		this.models[id] = Info.getBufferedImageFromResource(location);
	}

	@Override
	public BufferedImage getModel(int id) {
		return this.models[id];
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
	public boolean isVisible() {
		return this.visible;
	}
	
	@Override
	public int getAggressiveness(){
		return this.aggressiveness;
	}
	
	@Override
	public Defines.Direction getFacingDirection(){
		return this.facingDirection;
	}

	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return 0;
	}
}
