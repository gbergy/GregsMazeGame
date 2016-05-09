package gregsmazegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	private Player p1, p2;
	private boolean win = false; 
	private String message = " ";
	private Font font = new Font("Serif", Font.BOLD, 48);
	
	public Board() { 
		m = new Map();
		p1 = new Player(1); // player 1 (client)
		p2 = new Player(2); // player 2 (server) 
		addKeyListener(new Al());
		setFocusable(true);
		timer = new Timer(25, this); // performs actionPerformed every 25 milliseconds 
		timer.start();
	}// END Board constructor 

	/** 
	 * paint() creates maze board
	 * getMap() takes mapArray in Map.java and breaks it down into individual characters 
	 * paint iterates over array
	 * where ever there is: "g" puts a grass.png icon, "w" puts a wall.png icon", "f" puts a finish.png line
	 * 
	 **/
	public void paint(Graphics g) { 
		super.paint(g);
		
		if(!win){
			for (int y = 0; y < 20; y++) {
				for (int x = 0; x < 27; x++) {
					if (m.getMap(x, y).equals("f")) {
						g.drawImage(m.getFinish(), x * 32, y * 32, null);
					}
					if (m.getMap(x, y).equals("g")) {
						g.drawImage(m.getGrass(), x * 32, y * 32, null);
					}
	
					if (m.getMap(x, y).equals("w")) {
						g.drawImage(m.getWall(), x * 32, y * 32, null);
					}
	
				}
			}
			g.drawImage(p1.getPlayer(1), p1.getTileX() * 32, p1.getTileY() * 32 , null);
			g.drawImage(p2.getPlayer(2), p2.getTileX() * 32, p2.getTileY() * 32 , null);
		} 
		if (win) { // if player crosses finishline print winning message 
			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString(message, 150, 200);
		}
	}// END paint()
	
	
	// Message to print out when player crosses finish line
	public void actionPerformed(ActionEvent e) {
		if (m.getMap(p1.getTileX(), p1.getTileY()) == "f") {
			message = "Yellow Player 1 You WIN!";
			win = true; 
		}
		if (m.getMap(p2.getTileX(), p2.getTileY()) == "f") {
			message = "Blue Player 2 You WIN!";
			win = true; 
		}
		repaint();
	}// END actionPerformed()
	
	/** 
	 * Class Al (action listener) moves players' image icon around the board 
	 * Controls:
	 * Player 1 (client): up, down, left, right arrow keys 
	 * Player 2 (server): "W", "S", "A", "D" keys
	 * Doesn't allow icon to move when hits a wall 
	 * uses move() from Player.java 
	 * 
	 **/
	public class Al extends KeyAdapter { 
	
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
	
			if (keycode == KeyEvent.VK_UP) {
				if(!m.getMap(p1.getTileX(), p1.getTileY() - 1).equals("w"))
					p1.move(0, -1);
			}
	
			if (keycode == KeyEvent.VK_DOWN) {
				if(!m.getMap(p1.getTileX(), p1.getTileY() + 1).equals("w"))
					p1.move(0, 1);
			}
	
			if (keycode == KeyEvent.VK_LEFT) {
				if(!m.getMap(p1.getTileX() - 1, p1.getTileY()).equals("w"))
					p1.move(-1, 0);
			}
	
			if (keycode == KeyEvent.VK_RIGHT) {
				if(!m.getMap(p1.getTileX() + 1, p1.getTileY()).equals("w"))
					p1.move(1, 0);
			}
			
			if (keycode == KeyEvent.VK_W) {
				if(!m.getMap(p2.getTileX(), p2.getTileY() - 1).equals("w"))
					p2.move(0, -1);
			}
			
			if (keycode == KeyEvent.VK_S) {
				if(!m.getMap(p2.getTileX(), p2.getTileY() + 1).equals("w"))
					p2.move(0, 1);
			}
			
			if (keycode == KeyEvent.VK_A) {
				if(!m.getMap(p2.getTileX() - 1, p2.getTileY()).equals("w"))
					p2.move(-1, 0);
			}
			
			if (keycode == KeyEvent.VK_D) {
				if(!m.getMap(p2.getTileX() + 1, p2.getTileY()).equals("w"))
					p2.move(1, 0);
			}
		} // END keyPressed
	} // END Al (Action Listener) 
} // END Class Board.java 