package main.util;

import main.Defines;
import main.Global;
import main.game.gui.Positions;

public class Common {
	
	public final static float getPercentage(int a, int b){
		return ( ((float)a / (float)b) * 100);
	}
	
	public final static int invertInt(int n){
		return (n * -1);
	}
	
	public final static float invertFloat(float n){
		return (n * -1);
	}
	
	public static float convertWidthPixelsToPosition(int pixels){
		return (float)( (float)pixels / (float)Global.interfacePositions.get(Positions.gameBlockWidth) );
	}
	
	public static float convertHeightPixelsToPosition(int pixels){
		return (float)( (float)pixels / (float)Global.interfacePositions.get(Positions.gameBlockHeight) );
	}
	
	public static int getDrawXFromPosition(final float xPos){
		final int selfDrawX = Global.getPlayerDrawX();
		final float selfX = Global.self.getX();
		final int floorSelfX = (int) Math.floor(selfX);
		final float selfXaddition = (selfX - floorSelfX);
		final int blockWidth = Global.interfacePositions.get(Positions.gameBlockWidth);
		
		float additionX;
		int differenceX = 0;
		int pixelAdditionX = 0;
		
		differenceX = ( (int) Math.floor(xPos) ) - floorSelfX;
		differenceX = differenceX * blockWidth;
		
		additionX = (float) (xPos - Math.floor(xPos));
		additionX = additionX - selfXaddition;
		pixelAdditionX = Math.round(blockWidth * additionX);
		
		
		return (int)( selfDrawX + differenceX + pixelAdditionX );
	}
	
	public static int getDrawYFromPosition(final float yPos){
		final float selfY = Global.self.getY();
		final int floorSelfY = (int) Math.floor(selfY);
		final int blockHeight = Global.interfacePositions.get(Positions.gameBlockHeight);
		
		float additionY;
		int differenceY = 0;
		int pixelAdditionY = 0;
		
		differenceY = ( (int) Math.floor(yPos) ) - floorSelfY;
		differenceY = differenceY * blockHeight;
		
		additionY = (float) (yPos - Math.floor(yPos));
		additionY = additionY - (selfY - floorSelfY);
		pixelAdditionY = Math.round(blockHeight * additionY);
		
		
		return (int)( Global.getPlayerDrawY() + differenceY + pixelAdditionY );
	}
	
	public static boolean isInDrawZone(float currentX, float currentY){
		final float selfX = Global.self.getX();
		final float selfY = Global.self.getY();
		final int viewDistance = Defines.DRAW_DISTANCE;
		
		return isPointWithinArea(currentX, currentY, selfX - viewDistance, selfY - viewDistance, selfX + viewDistance, selfY + viewDistance);

	}
	
	public final static boolean isPointWithinArea(float pointX, float pointY, float boxStartX, float boxStartY, float boxEndX, float boxEndY){
		boolean is = false;

		if( (pointX >= boxStartX) && (pointX <= boxEndX) ){
			if( (pointY >= boxStartY) && (pointY <= boxEndY) ){
				is = true;
			}
		}
		
		return is;
	}
	
	public final static boolean isPointWithinArea(int pointX, int pointY, int boxStartX, int boxStartY, int boxEndX, int boxEndY){
		boolean is = false;

		if( (pointX >= boxStartX) && (pointX <= boxEndX) ){
			if( (pointY >= boxStartY) && (pointY <= boxEndY) ){
				is = true;
			}
		}
		
		return is;
	}

}
