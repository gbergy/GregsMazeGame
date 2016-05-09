package gregsmazegame;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
		
	private int tileX, tileY; //Coordinates of images icons
	
	private Image player1, player2;
	
	public Player(int x) { 	
		getPlayer(1);
		getPlayer(2);
	
		tileX = 1;
		tileY = 1;
	} // END Player constructor	
		
	public void loadPlayerImage() { //load images for the player image icons 
		try 
		{
			player1 = ImageIO.read(getClass().getResourceAsStream("/player1.png"));
			player2 = ImageIO.read(getClass().getResourceAsStream("/player2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // END loadPlayerImage()
		
	public Image getPlayer(int x) { // assign proper image icon to players 
		if (x == 1)
			return player1;
		else
			return player2; 
	} // END getPlayer()
		
	public int getTileX(){ // starting coordinate on x-axis of player 
		return tileX;
	}
		
	public int getTileY(){ // starting coordinate on y-axis of player 
		return tileY; 
	}
		
	public void move(int dx, int dy) { // changes coordinates of player when moves 
		tileX += dx;
		tileY += dy;
	}
} // END Class Player.java  