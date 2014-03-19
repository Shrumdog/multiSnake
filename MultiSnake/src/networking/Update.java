package networking;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import multiSnake.MasterMap;
import multiSnake.Point;
import multiSnake.Snake;

public class Update {
	private ArrayList<Snake> snakes;
	private HashMap<Color, Point> munchieOwners;
	private String nullPoints, munchiePoints;
	private String update;
	
	public Update(MasterMap map){
		update = getStringUpdate(map);	
	}
	
	public Update(String munchiePoint, String snake){
		update = munchiePoint + snake;
	}
	
	public String getUpdate(){
		return update;
	}
	
	public String getStringUpdate(MasterMap map){
		snakes = map.getSnakes();
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
