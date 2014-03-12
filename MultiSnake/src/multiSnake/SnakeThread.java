package multiSnake;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.net.*;
import java.awt.Color;
import java.io.*;

public class SnakeThread extends Thread{
	private ArrayList<InetAddress> IPAddresses;
	private Socket socket;
	private HashMap<Player, InetAddress> players;
	private Player me;
	

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
	
	public String getMunchiePoint(){
		HashMap<Color, Point> munchies = me.getTrueMap().getMunchieOwners();
		Point p = munchies.get(me.getTrueMap().getMySnake().color);
		return p.toString();
	}
	
	//connectionless - blast a message then close the socket.
	//keep track of all the IP addresses of computers that are connected to the game
	//each time an update is sent from one client, the updates have to be sent to ALL the other servers. and visa versa.
	//create a thread class that will keep track of all of the IP addresses connected to the game.
	//whenever there is a socket created in listen, it can kick of this thread (or just go to it, ie. add that sockets IP address to the data structure in that thread to iterate
	//over the new IP adress as well)
	//That thread will iterate through all of the IP addresses that are connected to the game and create sockets with each one.
	//each of these sockets will open an input and output stream that will send and receive updates to each of the players connected to the game.
	//after the sockets are done with that iteration in the for loop, they all close 
	
	
	//send an update every time a heading changes otherwise assume it goes the same direction and send the corresponding updates

	
	public void run(){
		Set<Player> playerSet = players.keySet();
		for(Player player:playerSet){
			MasterMap playerMap = player.getTrueMap();
			Update update = new Update(playerMap);
			//InputStream input = socket.get
			//if(input!=null){
				//update data structures
			//}
		}
	}
	
	public void addAddress(InetAddress address){
		IPAddresses.add(address);
	}
	
}
