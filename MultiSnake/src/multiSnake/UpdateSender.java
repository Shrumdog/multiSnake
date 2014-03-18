package multiSnake;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class UpdateSender extends Thread {
	private Socket socket;
	private Player me;
	
	public UpdateSender(Socket s, Player who){
		this.me = who;
		this.socket = s;
	}
	
	public void run(){
		try {
			PrintWriter writer = new PrintWriter (new OutputStreamWriter(socket.getOutputStream()));
			StringBuilder infoToSend = new StringBuilder();
			String stringMunchiePoint = getMunchiePoint();
			System.out.println("calling getSnake()");
			String stringSnake = getSnake();
			System.out.println("My stringy snake equals: " + stringSnake);
			infoToSend.append(stringMunchiePoint);
			infoToSend.append(stringSnake);
			writer.append(infoToSend);
			writer.flush();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public String getMunchiePoint(){
		ConcurrentHashMap<Color, Point> munchieOwners = me.getTrueMap().getMunchieOwners();
		Point p = munchieOwners.get(me.getTrueMap().getMySnake().color);
		return p.toString();
	}
	
	public String getSnake(){
		Snake mySnake = me.getTrueMap().getMySnake();
		return mySnake.toString();
	}
	
}
