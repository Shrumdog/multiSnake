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
			String stringSnake = getSnake();
			infoToSend.append(stringMunchiePoint);
			infoToSend.append(stringSnake);
			writer.flush();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public String getMunchiePoint(){
		ConcurrentHashMap<Color, Point> munchieOwners = me.getTrueMap().getMunchieOwners();
		System.out.println("the color of the point is: " + me.getTrueMap().getMySnake().color.toString());
		Point p = munchieOwners.get(me.getTrueMap().getMySnake().color.toString());
		System.out.println("The Point I'm about to put into the outputstream is: " + p);
		return p.toString();
	}
	
	public String getSnake(){
		Snake mySnake = me.getTrueMap().getMySnake();
		System.out.println("The snake I'm about to put into the outputstream is: " + me);
		return mySnake.toString();
	}
	
}
