package gregsmazegame;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class Map {
	
	//private Scanner m;
	private BufferedReader br = null;
	private FileInputStream fis = null; 
	private String[] Map = new String[14];
	private Image grass, wall, finish;
	private String index, line;
	
	public Map(){
			
		loadMapImages();
		openFile();
		readFile();
		closeFile();
	}
	
	public Image getGrass() {
		return grass;
	}
	
	public Image getWall() {
		return wall;
	}
	
	public Image getFinish() {
		return finish;
	}
	public void loadMapImages() {
		try 
		{
			grass = ImageIO.read(getClass().getResourceAsStream("/grass.png"));
			wall = ImageIO.read(getClass().getResourceAsStream("/wall.png"));
			finish = ImageIO.read(getClass().getResourceAsStream("/finish.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMap(int x, int y) { //video 3 at 3:45
			if (index != null)
				index = Map[y].substring(x, x + 1);
			return index;
	}
	
	public void openFile() {
		try
			{
				//m = new Scanner(new File("//Users/gbergy/Documents/workspace/GregsMazeGame/res/maze.txt"));
				fis = new FileInputStream("/Users/gbergy/Documents/workspace/GregsMazeGame/res/maze.txt");
				br = new BufferedReader(new InputStreamReader(fis));   
				line = br.readLine();
			} catch (IOException ex) {
		        System.out.println("error loading map OpenFile");
		    } 
	} // end open file 
	
	public void readFile() {
		try
		{
			while( (line = br.readLine() )  != null){
                //System.out.println(line);
				for(int i = 0; i < 13; i++) {
					Map[i] = line;
					System.out.println(line);
					line = br.readLine();
				}  
			}
		} catch (IOException ex) {
		       System.out.println("error loading map readFile"); 
		}
	} //end readFile 

	public void closeFile() {
//		if (m != null)
		try
		{
            fis.close();
			br.close();
		} catch (IOException ex) {
		    System.out.println("error loading map closeFile"); 
		}
	}
} //end class Map 