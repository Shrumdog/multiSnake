package multiSnake;
import java.net.*;
import java.io.*;


public class SnakeServer extends Thread {
	private ServerSocket server;
	private static Player me;
	
	public SnakeServer(Player who) throws IOException{
		server = new ServerSocket(8888);
		me = who;
		
		//this is not being set up properly.
		//should the server be set up as the application launches?
		//then the join game button should attempt to connect to the server? how?
		//me = who;
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
				InetAddress address = s.getInetAddress();
				UpdateReceiver update = new UpdateReceiver(s, me);
				update.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
