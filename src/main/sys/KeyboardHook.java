package main.sys;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import main.Global;

public class KeyboardHook implements KeyEventDispatcher{

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() == KeyEvent.KEY_PRESSED){
			Global.keyPressed(e.getKeyCode());
		}
		else if(e.getID() == KeyEvent.KEY_RELEASED){
			Global.keyReleased(e.getKeyCode());
		}

		
		return false;
	}

}
