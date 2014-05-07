package networking;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import multiSnake.MasterMap;
import multiSnake.Point;
import multiSnake.Snake;

public class FirstResponder extends Thread{
	private ArrayList<Snake> snakes;
	private HashMap<Color, Point> munchieOwners;
	private String nullPoints, munchiePoints;
	private String update;
	private String IPAddress;
	private Socket socket;
	
	public FirstResponder(Socket s, String address){
		IPAddress = address;
		socket = s;
	}
	
	public FirstResponder(String munchiePoint, String snake){
		update = munchiePoint + snake;
	}
	
	public void run(){
		PrintWriter writer;
		try {
			writer = new PrintWriter (new OutputStreamWriter(socket.getOutputStream()));
			StringBuilder infoToSend = new StringBuilder();
			infoToSend.append(IPAddress);
			writer.append(infoToSend);
			writer.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public String getUpdate(){
		return update;
	}
	
	public String getStringUpdate(MasterMap map){
		snakes = map.getSnakes();
		//munchieOwners = map.getMunchieOwners();
		for(Snake s : snakes){
			nullPoints += s.toString() + " ";
		}
		Set<Color> colors = munchieOwners.keySet();
		for(Color c:colors){
			munchiePoints += c.toString() + " " + munchieOwners.get(c).toString() + " ";
		}
		String result = "";
		result += nullPoints + munchiePoints;
		return result;
	}
}
