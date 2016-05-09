/**
 * Greg Goldberg 								Maze.java
 * Parallel and Distributed Computing CS 610 
 * 
 * Description:
 * Distributed Maze Game
 * 2 Players (Client and Server) compete against each other to win the game
 * THe players move throughout the maze avoid the wall and eventually cross the finish line
 * 
 * To Run:
 * Save Jar file on computer
 * open command prompt/terminal and type: Java -Jar jar file path
 * Type in IP
 * Type in Port #
**/

package gregsmazegame;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;

public class Maze implements Runnable {

	private String ip = "localhost";
	private static int port = 9032;
	private Scanner scanner = new Scanner(System.in);
	private JFrame frame;
	//SIZE OF JFRAME 
	private final int WIDTH = 864;
	private final int HEIGHT = 685;
	private Thread thread;

	private Socket socket; //end-point of communication between 2 streams 
	private DataOutputStream dos; //allows you to write primitive data types to output 
	@SuppressWarnings("unused")
	private DataInputStream dis; //same with input 

	private ServerSocket serverSocket; //server side socket, waits for request to come in and does action based on request
	//private String[] spaces = new String[9]; 

	private boolean player2 = true;
	private boolean accepted = false;

	public Maze() {
		//Print out request for IP Address  and Then Port Number 
		System.out.println("Please input the IP: ");
		ip = scanner.nextLine();
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
		while ((port < 1 || port > 65535) && port == 9032) { //valid port checking 
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}

		if (!connect()) 
			initializeServer();

		frame = new JFrame();
		frame.setTitle("Greg's Maze Game");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(new Board());
		
		thread = new Thread(this, "Maze");
		thread.start();
	} // END Maze constructor 

	public void run() {
		if (!player2 && !accepted) {
			listenForServerRequest();
		} 
	}
	//TCP - transmission control protocol -  does a handshake once gets sent waits for client to send something back, guarantees it will get there, in order that is was, and keeps its integrity (good for tictactoe and amze games because needs to follow up one move with another)
	//UDP - user datagram protocol - no guarantee, better for games, keep u up-to-date, discard old things, everyone within a small latency with each other

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
	} // END listenForServerRequest()

	private boolean connect() { //connects to server 
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch (IOException e) {
			System.out.println("Starting a server, address: " + ip + ":" + " port " + port);
			return false;
		}
		System.out.println("Successfully connected to the server.");
		return true;
	} // END connect()

	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
		player2 = false;
	} // END initializeServer()

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Maze maze = new Maze();
	} //end Main 
} //end class Maze.java