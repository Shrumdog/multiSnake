package multiSnake;

import gui.Joystick;
import gui.VisualMap;

import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class Player
{
	private String name;
	private Snake mySnake;
	private VisualMap fakeMap;
	private boolean firstAdd = true;

	public Player(VisualMap map)
	{
		fakeMap = map;
		mySnake = new Snake();
		mySnake.isAlive = false;
		name = new String("unnamed");
	}

	public void setName(String n)
	{
		name = n;
	}

	public void setColor(Color c)
	{
		if(firstAdd){
			mySnake.color = c;
		}
		firstAdd = false;
	}

	public void startPlaying()
	{
		mySnake.isAlive = true;
		fakeMap.addSnake(mySnake);
	}
	
	public boolean readyToJoin(){
		return !mySnake.getColor().equals(Color.gray);
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
	
	public VisualMap getVisualMap(){
		return fakeMap;
	}

	public Joystick connectListener()
	{
		return fakeMap.connectListener(this, mySnake);
	}
}