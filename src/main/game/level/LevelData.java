package main.game.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.Defines;
import main.Global;
import main.game.gui.Positions;
import main.game.level.Foreground_Objects.Bolder;
import main.sys.Info;
import main.util.Common;

public class LevelData implements Level {

	private static boolean loadedCache = false;
	private byte[][] data;
	private List<ForeObject> foreData;
	private static BufferedImage[] cache;
	
	private int centerX;
	private int centerY;
	private int width;
	private int height;
	private int xPixelsForDrawDistance;
	private int yPixelsForDrawDistance;
	private Color hoverBlockOutline;
	
	public LevelData() {
		this.data = new byte[5000][5000];
		this.foreData = new ArrayList<>();
		this.foreData.add(new Bolder(15, 15));
		
		this.width = Global.interfacePositions.get(Positions.gameBlockWidth);
		this.height = Global.interfacePositions.get(Positions.gameBlockHeight);
		this.centerX = Global.getPlayerDrawX();
		this.centerY = Global.getPlayerDrawY();
		this.xPixelsForDrawDistance = (this.width * Defines.DRAW_DISTANCE);
		this.yPixelsForDrawDistance = (this.height * Defines.DRAW_DISTANCE);
		this.hoverBlockOutline = Defines.HOVER_BLOCK_BORDER_COLOR.getColor();
		
		for(int i = 0; i < 5000; i++){
			for(int j = 0; i < 5000; i++){
				this.data[i][j] = 0;
			}
		}
		loadCache();
	}
	
	public static void loadCache(){
		if(!loadedCache){
			cache = new BufferedImage[5];
			cache[0] = Info.getBufferedImageFromResource("/levels/grass.png");
			loadedCache = true;
		} else {
			
		}

	}
	
	@Override
	public void draw(Graphics g, int startCol, int startRow, int distance, float additionX, float additionY) {
		
		final int pixelAdditionX = Math.round(this.width * additionX);
		final int pixelAdditionY = Math.round(this.height * additionY);
		
		final int defaultDrawX = this.centerX + this.xPixelsForDrawDistance - pixelAdditionX;
		final int defaultDrawY = this.centerY + this.yPixelsForDrawDistance - pixelAdditionY;
		
		int drawX = defaultDrawX;
		int drawY = defaultDrawY;
		
		final int cursorX = Global.getCursorX();
		final int cursorY = Global.getCursorY();
		final boolean drawCursorBlockOutline = Defines.drawGridHoverOutline;
		boolean drawnCursorBlock = false;
		
		for(int j = (startRow + distance); j >= startRow - distance; j--){			//backwards for loop because it is suppose to be quicker due to compiler opt.
			for(int i = (startCol + distance); i >= startCol - distance; i--){
				if(doesPositionExist(i, j)){
					g.drawImage(cache[this.data[i][j]], drawX, drawY, this.width, this.height, null);
					if(Defines.drawGridOutline){
						g.setColor(Color.white);
						g.drawRect(drawX, drawY, this.width, this.height);
					}
					
					
					if(drawCursorBlockOutline && !drawnCursorBlock){
						if(Common.isPointWithinArea(cursorX, cursorY, drawX, drawY, drawX + this.width, drawY + this.height) ){
							g.setColor(this.hoverBlockOutline);
							g.drawRect(drawX, drawY, this.width - 1, this.height- 1);
							drawnCursorBlock = true;
						}
					}
				}

				drawX -= this.width;
			}
			drawX = defaultDrawX;
			drawY -= this.height;
		}	
		
		drawForeground(g);
	}
	
	private void drawForeground(Graphics g){
		int drawX = 0;
		int drawY = 0;
		for(int i = 0; i < this.foreData.size(); i++){
			drawX = Common.getDrawXFromPosition((float)this.foreData.get(i).getX());
			drawY = Common.getDrawYFromPosition((float)this.foreData.get(i).getY());
			this.foreData.get(i).draw(g, drawX, drawY);
		}
	}
	
	private final boolean doesPositionExist(int col, int row){
		boolean exists = false;
		if(col >= 0 && row >= 0){
			if( (col < this.data.length) && (row < this.data[0].length) ){
				exists = true;
			} else {
				exists = false;
			}
		} else {
			exists = false;
		}
		
		return exists;
	}

	@Override
	public int getLengthX() {
		return this.data[0].length - 1;	//all lengths are the same; so just return the first indexes length
	}

	@Override
	public int getLengthY() {
		return this.data.length - 1;
	}

	@Override
	public boolean isBlockFree(int col, int row) {
		boolean free = false;
		if(doesPositionExist(col, row)){
			if(hasForeObject(col, row)){
				free = true;
			}
		}
		
		return free;
	}
	
	private boolean hasForeObject(int col, int row){
		boolean free = true;
		for(int i = 0; i < this.foreData.size(); i++){
			if( (this.foreData.get(i).getX() == col) && (this.foreData.get(i).getY() == row)){
				free = false;
			}
		}
		
		return free;
	}
}
