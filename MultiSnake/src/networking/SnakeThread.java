package networking;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Array;
import java.net.*;
import java.awt.Color;
import java.io.*;

import multiSnake.MasterMap;
import multiSnake.Player;
import multiSnake.Point;
import multiSnake.Snake;
import multiSnake.SnakeSegment;

public class SnakeThread extends Thread{
	private ArrayList<InetAddress> IPAddresses;
	private Socket socket;
	private HashMap<Player, InetAddress> players;
	private Player me;
	private ArrayList<Point> freePoolPoints;
	private boolean firstTime;
	

	public SnakeThread(Socket s, Player who){
		socket = s;
		me = who;
		if(!players.containsKey(me)){
			players.put(me, socket.getInetAddress());
			IPAddresses.add(socket.getInetAddress());
		}
	}
	
	public String getMunchiePoint(){
		ConcurrentHashMap<Color, Point> munchies = me.getTrueMap().getMunchieOwners();
		Point p = munchies.get(me.getTrueMap().getMySnake().color);
		return p.toString();
	}
	
	public String getSnake(){
		Snake mySnake = me.getTrueMap().getMySnake();
		return mySnake.toString();
	}
	
	public void run(){
		try{
			Set<Player> playerSet = players.keySet();
			for(Player player:playerSet){
				MasterMap playerMap = player.getTrueMap();
				Update update = new Update(playerMap);
				String infoToSend = getMunchiePoint() + "|" +getSnake();
				//ServerSocket s = new ServerSocket()
				BufferedReader input = new BufferedReader (new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter (new OutputStreamWriter(socket.getOutputStream()));
				
			} 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Point parseInputForMunchie(String input){
		String[] inputs = input.split("|");
		String stringMunchiePoint = inputs[0];
		Point munchiePoint = toPoint(stringMunchiePoint);
		return munchiePoint;
		
	}
	
	public Snake parseInputForSnake(String input){
		String[] inputs = input.split("|");
		String stringSnake = inputs[1];
		Snake inputSnake = toSnake(stringSnake);
		return inputSnake;
	}
	
	public Snake toSnake(String snake){
		String[] inputs = snake.split("|");
		Snake snakeToReturn = new Snake();
		for(String s : inputs){
			Point snakePoint = toPoint(s);
			snakeToReturn.addToHead(new SnakeSegment(snakePoint));
		}
		return snakeToReturn;
	}
	
	public Point toPoint(String point){
		  String[] pointInfo = point.split("");
		  int row = Integer.parseInt(pointInfo[1]);
		  int col = Integer.parseInt(pointInfo[3]);
		  return new Point(row, col);
	  }
	
	public void addAddress(InetAddress address){
		IPAddresses.add(address);
	}
	
}
