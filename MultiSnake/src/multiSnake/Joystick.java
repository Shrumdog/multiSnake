package multiSnake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class Joystick implements KeyListener, ActionListener
{
	private MasterMap mastermap;
	private VisualMap visualmap;
	private Snake snake;
	private Timer timer;
	private int direction;
	
	public Joystick(MasterMap mmap, VisualMap vmap, Snake s)
	{
		mastermap = mmap;
		visualmap = vmap;
		snake = s;
		
		mastermap.setPlayerSnake(snake);
		timer = new Timer(40, this);
		System.out.println("Joystick created");
	}
	
	public void keyPressed( KeyEvent ke )
	{
		if(!(snake.isAlive))
		{
			System.out.println("Snake is not alive");
		}
		else
		{
			if(!timer.isRunning()) timer.start();
			int kc = ke.getKeyCode();
			if( kc == KeyEvent.VK_UP ) direction = mastermap.UP;
			else if( kc == KeyEvent.VK_DOWN ) direction = mastermap.DOWN;
			else if( kc == KeyEvent.VK_LEFT ) direction = mastermap.LEFT;
			else if( kc == KeyEvent.VK_RIGHT ) direction = mastermap.RIGHT;
			
			snake.isAlive = mastermap.move(direction); 
			visualmap.repaint();
		}
	}
	public void keyReleased( KeyEvent ke ) {}
	public void keyTyped( KeyEvent ke ) {}

	@Override
	public void actionPerformed( ActionEvent ae )
	{
		if( ae.getSource() == timer )
		{
			snake.isAlive = mastermap.move(direction);
			if(!snake.isAlive && timer.isRunning())
				timer.stop();			
			visualmap.repaint();
		}
	}
}
