package multiSnake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Update {
	private ArrayList<Point> makeFree, freePoolPoints;
	private ArrayList<Snake> occupy, snakes;
	private HashMap<Color, Point> munchieOwners;
	private String points, nullPoints, munchiePoints;
	private String update;
	
	public Update(MasterMap map){
		//new points to be added to free pool
		//what points need to be taken out of the free pool;
		//new munchie locations
		update = getStringUpdate(map);	
	}
	
	public Update(String munchiePoint, String addedPool, String outPool){
		update = munchiePoint + addedPool + outPool;
	
	}
	
	public String getUpdate(){
		return update;
	}
	
	
	
	
	public String getStringUpdate(MasterMap map){
		
		makeFree = new ArrayList<Point>();
		occupy = new ArrayList<Snake>();
		freePoolPoints = map.getFreePool();
		snakes = map.getSnakes();
		munchieOwners = map.getMunchieOwners();
		
		//for(Point p:freePoolPoints){
			//points += p.toString() + " ";
		//}
		
		for(Snake s : occupy){
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