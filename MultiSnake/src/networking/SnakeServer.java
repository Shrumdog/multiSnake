package networking;
import gui.Joystick;

import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

import multiSnake.Player;


public class SnakeServer extends Thread {
	private ServerSocket server;
	private static Player me;
	private Joystick js;

	public SnakeServer(Player who, Joystick js) throws IOException{
		this.js = js;
		server = new ServerSocket(8888);
		me = who;

		System.out.println("Server IP address " + server.getInetAddress() + " has connected to OnlineSnake");
	}


	@Override
	public void run(){
		for(;;){
			Socket s;
			try {
				s = server.accept();
				UpdateReceiver update = new UpdateReceiver(s, me);
				update.start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Well, whatever...");
			}
		}
	}

}