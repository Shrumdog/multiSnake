package multiSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MiniMap extends JPanel implements ActionListener
{
	private MasterMap trueMap;
	private Timer timer;
	
	public MiniMap(MasterMap map)
	{
		super();
		setBackground(Color.white);
		setBorder(BorderFactory.createEtchedBorder());
		trueMap = map;
		timer = new Timer(40, this);
		timer.start();
	}
	
	public MiniMap()
	{
		super();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int length = (int) getSize().getHeight();
		trueMap.draw(g, length);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == timer )
		{
			repaint();
		}
	}
}
