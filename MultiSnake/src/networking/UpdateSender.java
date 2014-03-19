package networking;

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

import multiSnake.Player;
import multiSnake.Point;
import multiSnake.Snake;

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
			String snakeColor = getSnakeColor();
			infoToSend.append(stringMunchiePoint + "| ");
			infoToSend.append(stringSnake + "| ");
			infoToSend.append(snakeColor);
			writer.append(infoToSend);
			writer.flush();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String getSnakeColor(){
		String colorString = me.getTrueMap().getMySnake().color.toString();
		String colorNums = colorString.replaceAll("[^,0-9]", "");
		String[] colorNums2 = colorNums.split(",");
		String result = colorNums2[0] +" "+ colorNums2[1] +" "+ colorNums2[2];
		return result;
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
