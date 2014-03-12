package multiSnake;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class VisualMap extends JPanel
{
	private MasterMap trueMap;
	private Snake focusSnake;
	
	public VisualMap(MasterMap map)
	{
		super();
		setLayout(new BorderLayout());
		setBackground(Color.white);
		setBorder(BorderFactory.createEtchedBorder());
		trueMap = map;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int coor = (int) getSize().getHeight()/2;
		if(focusSnake.getHead() != null) trueMap.draw(g, coor, focusSnake);
		requestFocusInWindow();
	}
	
	public void addSnake(Snake s)
	{
		trueMap.addSnake(s);
		repaint();
	}
	
	public void removeSnake(Snake s)
	{
		trueMap.removeSnake(s);
		repaint();
	}
	
	public MasterMap getTrueMap()
	{
		return trueMap;
	}
	
	public KeyListener connectListener(Snake s)
	{
		focusSnake = s;
		return new Joystick(trueMap, this, s);
	}
}
