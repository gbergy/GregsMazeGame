package gregsmazegame;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class Map {
	private BufferedReader br = null;
	private FileInputStream fis = null; 
	private String[] mapArray = new String[20]; // hold text file characters used in the board creation 
	private Image grass, wall, finish; // image icons 
	private String index, line;
	
	public Map() {
			
		loadMapImages();
		openFile();
		readFile();
		closeFile();
	} // END Map constructor 
	
	public Image getGrass() {
		return grass;
	}
	
	public Image getWall() {
		return wall;
	}
	
	public Image getFinish() {
		return finish;
	}
		
	public void loadMapImages() { //load images that create board from res file
		try 
		{
			grass = ImageIO.read(getClass().getResourceAsStream("/grass.png"));
			wall = ImageIO.read(getClass().getResourceAsStream("/wall.png"));
			finish = ImageIO.read(getClass().getResourceAsStream("/finish.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // END loadMapImages()
	
	public void openFile() { // gets a text file of lines with g's and w's that will make up the maze board
		try
		{
			fis = new FileInputStream("/Users/gbergy/Documents/workspace/GregsMazeGame/res/maze.txt"); // Text File 
			br = new BufferedReader(new InputStreamReader(fis));   
			line = br.readLine();
		} catch (IOException ex) {
	        System.out.println("error loading map OpenFile");
	    } 
	} // END openFile() 
	
	public void readFile() { // read line of text file and add into mapArray
		try
		{
			while( (line = br.readLine() )  != null) {
				for(int i = 0; i < 20; i++) {
					mapArray[i] = line;
					System.out.println(line); // check to see if file reads correctly 
					line = br.readLine();
				}  
			}
		} catch (IOException ex) {
		       System.out.println("error loading map readFile"); 
		}
	} // END readFile() 

	public void closeFile() {
		try
		{
            fis.close();
			br.close();
		} catch (IOException ex) {
		    System.out.println("error loading map closeFile"); 
		}
	} // END closeFile()
	
	public String getMap(int x, int y) { // takes full array and breaks down into individual characters, Board.java will use to the paint the maze board  
		if (index != null)
			index = mapArray[y].substring(x, x + 1);
		return index;
	} //END getMap()
} // END Class Map.java  