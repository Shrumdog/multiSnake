package multiSnake;

import java.awt.Color;
import java.awt.event.KeyListener;

public class Player
{
	private String name;
	private Snake mySnake;
	private VisualMap fakeMap;
	
	public Player(VisualMap map)
	{
		fakeMap = map;
		mySnake = new Snake();
		name = new String("unnamed");
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public void setColor(Color c)
	{
		mySnake.color = c;
	}
	
	public void startPlaying()
	{
		mySnake.isAlive = true;
		fakeMap.addSnake(mySnake);
	}
	
	public void stopPlaying()
	{
		fakeMap.removeSnake(mySnake);
		mySnake = new Snake(mySnake.getColor());
		fakeMap.getTrueMap().changeSnakeMade(false);
	}
	
	public MasterMap getTrueMap()
	{
		return fakeMap.getTrueMap();
	}
	
	public KeyListener connectListener()
	{
		return fakeMap.connectListener(mySnake);
	}
}
