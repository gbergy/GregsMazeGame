package gregsmazegame;

//to export to jar file 53:00

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.imageio.*;
import javax.swing.*;

//import mazeGame2.Board;
//import mazeGame2.Map;
//import mazeGame2.Player;
//import mazeGame2.Board.Al;

public class Maze implements Runnable {

	private String ip = "localhost";
	private int port = 22222;
	private Scanner scanner = new Scanner(System.in);
	private JFrame frame;
	//SIZE OF JFRAME 
	private final int WIDTH = 464;
	private final int HEIGHT = 485;
	private Thread thread;

	private Painter painter;
	private Socket socket; //end-point of communication between 2 streams 
	private DataOutputStream dos; //allows you to write primitive data types to output 
	private DataInputStream dis; //same with input 

	private ServerSocket serverSocket; //server side socket, waits for request to come in and does action based on request

	// images
	//private BufferedImage board;
	private BufferedImage grass;
	private BufferedImage wall;
	private BufferedImage finish;
	private BufferedImage player;

	//private String[] spaces = new String[9]; 

	private boolean yourTurn = false;
	private boolean player2 = true;
	private boolean accepted = false;
	private boolean unableToCommunicateWithOpponent = false;


//	private String waitingString = "Waiting for another player";
//	private String unableToCommunicateWithOpponentString = "Unable to communicate with opponent.";
//	private String wonString = "You won!"; // WIN STRING
//	private String enemyWonString = "Opponent won!"; // WIN STRING
	//private String tieString = "Game ended in a tie.";

	//private int[][] wins = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } }; //no need 

	/**
	 * <pre>
	 * 0, 1, 2 
	 * 3, 4, 5 
	 * 6, 7, 8
	 * </pre>
	 */

	public Maze() {
		//Print out request for IP Address  and Then Port Number 
		System.out.println("Please input the IP: ");
		ip = scanner.nextLine();
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
		while (port < 1 || port > 65535) { //port checking 
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}

		//loadImages(); 

//		painter = new Painter();
//		painter.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		if (!connect()) 
			initializeServer();

		frame = new JFrame();
		frame.setTitle("Greg's Maze Game");
		//frame.setContentPane(painter);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(new Board());


		thread = new Thread(this, "Maze");
		thread.start();
	}

	public void run() {
		//while (true) {
			//tick();
			//painter.repaint();

			if (!player2 && !accepted) {
				listenForServerRequest();
			} //20:10

		//}
	}

	//TCP - transmission control protcol -  does a handshake once gets sent waits for client to send something back, guarantees it will get there, in order that is was, and keeps its integrity (good for tictactoe and amze games because needs to follow up one move with another)
	//UDP  - user datagram protcol - no guarantee, better for games, keep u up-to-date, discard old things, everyone within a small latency with each other

//	//private void render(Graphics g) 

	// 28:00 may not need this 
//	private void tick() {

// check fors no need 46:00

	private void listenForServerRequest() { //method listens for request on the server and the server will accept once we get one
		Socket socket = null;
		try {
				socket = serverSocket.accept(); //game stops until another person is there to play 
				dos = new DataOutputStream(socket.getOutputStream());
				dos.flush();
				dis = new DataInputStream(socket.getInputStream());
				accepted = true;
				System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean connect() { //MAKE YOUR OUTPUT STREAM BEFORE YOUR INPUT STREAM And often times, you will do dos = ... then a dos.flush() then dis = ...  Do that. 19:50
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch (IOException e) {
			System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
			return false;
		}
		System.out.println("Successfully connected to the server.");
		return true;
	}

	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
		yourTurn = true;
		player2 = false;
	}

	//Load images from folder images 15:10 in video
//	private void loadImages() {
//		try { 
//			//board = ImageIO.read(getClass().getResourceAsStream("/board.png"));
//			grass = ImageIO.read(getClass().getResourceAsStream("/grass.png"));
//			wall = ImageIO.read(getClass().getResourceAsStream("/wall.png"));
//			finish = ImageIO.read(getClass().getResourceAsStream("/finish.png"));
//			player = ImageIO.read(getClass().getResourceAsStream("/player.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Maze maze = new Maze();
	} //end Main 
	
} //end class Maze
