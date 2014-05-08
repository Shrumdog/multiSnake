package multiSnake;

import gui.Joystick;
import gui.Screen;

import java.awt.Color;
import java.awt.event.KeyListener;

public class Player
{
	private String name;
	private Snake mySnake;
	private Screen fakeMap;

	public Player(Screen map)
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
		mySnake.isAlive = false;
		fakeMap.getTrueMap().changeSnakeMade(false);
	}

	public void resumePlay()
	{
		mySnake.isAlive = true;
	}

	public MasterMap getTrueMap()
	{
		return fakeMap.getTrueMap();
	}
	
	public Screen getScreen(){
		return fakeMap;
	}

	public Joystick connectListener()
	{
		return fakeMap.connectListener(this, mySnake);
	}
}