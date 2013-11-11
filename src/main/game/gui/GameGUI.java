package main.game.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Defines;
import main.Global;
import main.sys.KeyboardHook;

public class GameGUI implements GameInterface, MouseListener, MouseMotionListener {
	
	private JFrame frame;
	private JPanel interfacePanel;
	

	public GameGUI(String title, int width, int height){
		this.frame = new JFrame(title);
		
		this.interfacePanel = new JPanel(new FlowLayout()) {
			private static final long serialVersionUID = 73162623806022979L;

			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Global.updateDisplay(g);
			}
		};
		
		interfacePanel.setBackground(Defines.GAME_BACKGROUND_COLOR.getColor());
		interfacePanel.setPreferredSize(new Dimension(width, height));
		interfacePanel.addMouseMotionListener(this);
		interfacePanel.addMouseListener(this);
		
		this.frame.setContentPane(interfacePanel);
		this.frame.setLayout(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setPreferredSize(new Dimension(width, height));
		this.frame.setLocation(0, 0);
		this.frame.setResizable(false);
		this.frame.setVisible(false);
		this.frame.pack();
		
		KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		keyManager.addKeyEventDispatcher(new KeyboardHook());
		
		System.out.println("running at: " + width + "x" + height);
	}
	
	@Override
	public void drawFrame() {
		this.interfacePanel.repaint();
	}


	@Override
	public void setSize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		this.frame.setVisible(true);
		
	}


	@Override
	public void hide() {
		this.frame.setVisible(false);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//mouseMoved does not update the cursor position if the user is dragging the mouse, resulting in the cursor position failing to
		//update. update the position if they are dragging the cursor to ensure the position stays up to date
		Global.setCursorPosition(e.getX(), e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Global.setCursorPosition(e.getX(), e.getY());
	}
}
