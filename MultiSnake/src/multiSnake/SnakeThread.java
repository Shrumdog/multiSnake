package multiSnake;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Array;
import java.net.*;
import java.awt.Color;
import java.io.*;

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
	
	//munchie point
	//what you put into the free pool
	//what you took out of the free pool
	//could I just send the snake's location anyway? and because
	//of that the other persons freepool would block those points
	//anyway? or just send each snake and put it in the free pool/remove it.
	//when we send a snake over the steam we can compare it to the snake
	//that the player receiving the snake already has for that color
	//thus, when decoding we can then decide what to add/remove from
	//free pool with the snake/snakes that we receive.
	
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
		//if(input!=null){
		//update data structures
		//}
		catch (IOException e) {
			// TODO Auto-generated catch block
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
