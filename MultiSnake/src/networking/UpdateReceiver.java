package networking;
import gui.Joystick;

import java.net.*;
import java.awt.Color;
import java.io.*;

import multiSnake.MasterMap;
import multiSnake.Player;
import multiSnake.Point;
import multiSnake.Snake;
import multiSnake.SnakeSegment;

public class UpdateReceiver extends Thread{
	private Socket socket;
	private Player me;
	private int indexForColors = 0;
	
	public UpdateReceiver(Socket s, Player who){
		socket = s;
		me = who;
	}
	
	public void run(){
		try{
			BufferedReader input = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			String infoToParse = "";
			infoToParse += input.readLine();
			if(infoToParse == null){}
			else {
				Point munchiePoint = parseInputForMunchie(infoToParse);
				Snake snake = parseInputForSnake(infoToParse);
				Color snakeColor = parseInputforColor(infoToParse);
				MasterMap map = me.getTrueMap();
				snake.color = snakeColor;
				map.addSnake(snake);
				map.swapMunchie(snake, munchiePoint);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Color parseInputforColor(String input){
		String[] inputs = input.split(" ");
		int color1 = 0;
		int color2 = 0;
		int color3 = 0;
		for(int k = indexForColors; k < inputs.length; k++){
			
		}
		color1 = Integer.parseInt(inputs[indexForColors+1]);
		color2 = Integer.parseInt(inputs[indexForColors+2]);
		color3 = Integer.parseInt(inputs[indexForColors+3]);
		return new Color(color1, color2, color3);
	}

	@SuppressWarnings("static-access")
	public Point parseInputForMunchie(String input){
		String[] inputs = input.split(" ");
		String stringMunchiePoint = inputs[0];
		Point munchiePoint = toPoint(stringMunchiePoint);
		return munchiePoint;
	}
	
	public Snake parseInputForSnake(String input){
		String[] inputs = input.split(" ");
		String stringSnake = "";
		
		for(int k = 2 ; k<inputs.length; k++){
			if(!(inputs[k].equals("|")) && indexForColors ==0){
				stringSnake += inputs[k] + " ";
			}
			else if(inputs[k].equals("|")){
				indexForColors = k;
			}
		}
		Snake inputSnake = toSnake(stringSnake);
		return inputSnake;
	}
	
	@SuppressWarnings("static-access")
	public Snake toSnake(String snake){
		String[] inputs = snake.split(" ");
		Snake snakeToReturn = new Snake();
		for(String s : inputs){
			Point snakePoint = toPoint(s);
			snakeToReturn.addToHead(new SnakeSegment(snakePoint));
		}
		return snakeToReturn;
	}
	
	public static Point toPoint(String point){
		point = point.trim();
		String[] pointInfo = point.split(",");
		String temp = pointInfo[0].replace("(", "");
		int row = Integer.parseInt(temp);
		temp = pointInfo[1];
		temp = (String) temp.subSequence(0, 2);
		int col = Integer.parseInt(temp);
		return new Point(row, col);
	}
}
