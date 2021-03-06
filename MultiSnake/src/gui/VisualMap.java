package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import multiSnake.MasterMap;
import multiSnake.Player;
import multiSnake.Snake;


@SuppressWarnings("serial")
public class VisualMap extends JPanel
{
	private MasterMap trueMap;
	private Snake focusSnake;
	private Joystick jStick;

	public VisualMap(MasterMap map)
	{
		super();
		setLayout(new BorderLayout());
		setBackground(Color.getHSBColor(0.1f, 0.5f, 0.9f));
		setBorder(BorderFactory.createEtchedBorder());
		trueMap = map;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int coor = (int) getSize().getHeight()/2;
		if(focusSnake.getHead() != null) trueMap.drawVisualMap(g, coor, focusSnake);
		requestFocusInWindow();
	}

	public void addSnake(Snake s)
	{
		trueMap.addSnake(s);
		repaint();
	}

	public void removeSnake(Snake s){
		trueMap.removeSnake(s);
		repaint();
	}

	public MasterMap getTrueMap(){
		return trueMap;
	}

	public Joystick connectListener(Player me, Snake s){
		focusSnake = s;
		jStick = new Joystick(trueMap, this, me, s);
		return jStick;
	}
	
	public Joystick getJoystick(){
		return jStick;
	}
}