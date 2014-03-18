package multiSnake;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Array;
import java.net.*;
import java.awt.Color;
import java.io.*;

public class UpdateReceiver extends Thread{
	private Socket socket;
	private ConcurrentHashMap<Player, InetAddress> players = new ConcurrentHashMap<Player, InetAddress>();
	private Player me;
	private ArrayList<Point> freePoolPoints;
	private boolean firstTime;
	

	public UpdateReceiver(Socket s, Player who){
		socket = s;
		me = who;
		//System.out.println(me.toString());
		//if(!players.containsKey(me)){
			//players.put(me, socket.getInetAddress());
		//}
	}
	
	//move concurrentHashMap to where ever the ticks are done so that and updateSender thread is created every tick for 
	//every player connected to the game.
	//we need to make the data structures in masterMap thread safe.
	
	//for every player in playerset, only send the one update from my application to every player.
	//when do sockets open and how does stuff get read in?
	
	public void run(){
		try{
			BufferedReader input = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			String infoToParse = "";
			infoToParse += input.readLine();
			
			System.out.println("input from the input stream: " + infoToParse);
			Point munchiePoint = parseInputForMunchie(infoToParse);
			Snake snake = parseInputForSnake(infoToParse);
			MasterMap map = me.getTrueMap();
			
			map.addSnake(snake);
			map.swapMunchie(snake, munchiePoint);
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
}
