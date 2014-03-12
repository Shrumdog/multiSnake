package multiSnake;
import java.net.*;
import java.io.*;


public class SnakeServer {
	private ServerSocket server;
	private static Player me;
	
	public SnakeServer(int port, Player who) throws IOException{
		server = new ServerSocket(port);
		me = who;
		System.out.println("Server IP address " + server.getInetAddress() + " has connected to OnlineSnake");
	}
	
	public void listen() throws IOException{
		for(;;){
			Socket s = server.accept();
			InetAddress address = s.getInetAddress();
			SnakeThread snakeThread = new SnakeThread(s, me);
			snakeThread.addAddress(address);
			
			snakeThread.start();
			}
	}
	
	public static void main(String[] args) throws IOException{
		SnakeServer s = new SnakeServer(Integer.parseInt(args[0]), me);
		s.listen();
	}
	
}
