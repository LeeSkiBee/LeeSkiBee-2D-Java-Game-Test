package main;

import main.game.entity.Player;
import main.game.gui.GameGUI;
import main.game.gui.Positions;
import main.game.level.LevelData;
import main.sys.Info;

public class Start {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl","True");
	   // System.setProperty("sun.java2d.accthreshold", "0");
	    System.setProperty("sun.java2d.translaccel", "true"); 
		System.out.println(Info.getScreenImageType());
		
		Settings.initSettings();
		Global.self = new Player();
		Global.mainLevel = new LevelData();
		Global.entities.add(new Player());
		
		Global.setDisplay(new GameGUI("Test", Global.interfacePositions.get(Positions.width), Global.interfacePositions.get(Positions.height)));
		Global.showDisplay();
		Global.startFPS();
	}

}
