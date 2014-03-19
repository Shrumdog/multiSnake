package networking;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

import multiSnake.Player;


public class SnakeServer extends Thread {
	private ServerSocket server;
	private static Player me;

	public SnakeServer(Player who) throws IOException{
		server = new ServerSocket(8888);
		me = who;
		
		System.out.println("Server IP address " + server.getInetAddress() + " has connected to OnlineSnake");
	}
	
	
	@Override
	public void run(){
		for(;;){
			System.out.println("Server listening....");
			Socket s;
			try {
				s = server.accept();
				System.out.println("I have accepted a request");
				System.out.println("Processing...");
				UpdateReceiver update = new UpdateReceiver(s, me);
				update.start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Well, whatever...");
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		SnakeServer s = new SnakeServer(me);
		s.start();
	}
	
}
