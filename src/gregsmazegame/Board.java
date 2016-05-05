package gregsmazegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L; //default action 
	private Timer timer;
	private Map m;
	private Player p;
	private boolean win = false; 
	private String message = " ";
	private Font font = new Font("Serif", Font.BOLD, 48);
	public Board() {

		m = new Map();
		p = new Player();
		addKeyListener(new Al());
		setFocusable(true);
		//setBackground(Color.GREEN);
		timer = new Timer(25, this);
		timer.start();

	}

	public void actionPerformed(ActionEvent e) {
		if (m.getMap(p.getTileX(), p.getTileY()) == "f") {
			message = "You WIN!";
			win = true; 
		}
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);		    
		Toolkit.getDefaultToolkit().sync(); //the sync method simply syncs the graphics state with that of the window, it is a failsafe in case the frame is not up to date with the window.
		
		if(!win){
			for (int y = 0; y < 14; y++) {
				for (int x = 0; x < 14; x++) {
					if (m.getMap(x, y) == "f" ) {
						g.drawImage(m.getFinish(), x * 32, y * 32, null);
					}
					if (m.getMap(x, y) == "g") {
						g.drawImage(m.getGrass(), x * 32, y * 32, null);
					}
	
					if (m.getMap(x, y) == "w") {
						g.drawImage(m.getWall(), x * 32, y * 32, null);
					}
	
				}
			}
			g.drawImage(p.getPlayer(), p.getTileX() * 32, p.getTileY() * 32 , null);
		} 
		if (win){
			g.setColor(Color.ORANGE);
			g.setFont(font);
			g.drawString(message, 150, 200);
		}
	} //end paint
	
	private class Al extends KeyAdapter { 
	
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
	
			if (keycode == KeyEvent.VK_W) {
				if(!m.getMap(p.getTileX(), p.getTileY() - 1).equals("w"))
					p.move(0, -1);
			}
	
			if (keycode == KeyEvent.VK_S) {
				if(!m.getMap(p.getTileX(), p.getTileY() + 1).equals("w"))
					p.move(0, 1);
			}
	
			if (keycode == KeyEvent.VK_A) {
				if(!m.getMap(p.getTileX() - 1, p.getTileY()).equals("w"))
					p.move(-1, 0);
			}
	
			if (keycode == KeyEvent.VK_D) {
				if(!m.getMap(p.getTileX() + 1, p.getTileY()).equals("w"))
					p.move(1, 0);
			}
		} //end keyPressed
	} //end Al (Action Listener) 
} // end class Board
