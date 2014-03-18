package multiSnake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Timer;

public class Joystick implements KeyListener, ActionListener{
	private MasterMap mastermap;
	private VisualMap visualmap;
	private Player me;
	private Snake snake;
	private Timer timer;
	private int direction;
	private Connect c;

	public Joystick(MasterMap mmap, VisualMap vmap, Player p, Snake s){
		mastermap = mmap;
		visualmap = vmap;
		me = p;
		snake = s;
		//c = new Connect(me);
		mastermap.setPlayerSnake(snake);
		timer = new Timer(40, this);
		System.out.println("Joystick created");
	}
	
	public void addConnect(Connect c)
	{
		this.c = c;
	}

	public void keyPressed(KeyEvent ke)
	{
		if (!(snake.isAlive))
		{
			System.out.println("Snake is not alive");
		} else
		{
			if (!timer.isRunning())
				timer.start();
			int kc = ke.getKeyCode();
			if (kc == KeyEvent.VK_UP)
				direction = mastermap.UP;
			else if (kc == KeyEvent.VK_DOWN)
				direction = mastermap.DOWN;
			else if (kc == KeyEvent.VK_LEFT)
				direction = mastermap.LEFT;
			else if (kc == KeyEvent.VK_RIGHT)
				direction = mastermap.RIGHT;

			snake.isAlive = mastermap.move(direction);
			visualmap.repaint();
		}
	}

	public void keyReleased(KeyEvent ke){}

	public void keyTyped(KeyEvent ke){}

	@Override
	public void actionPerformed(ActionEvent ae){
		ConcurrentHashMap<Player, String> players = c.getPlayers();
		Set<Player> playerSet = players.keySet();
		for(Player play:playerSet){
			Socket socket;
			try {
				socket = new Socket(players.get(play), 8888);
				UpdateSender sender = new UpdateSender(socket, me);
				sender.start();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (ae.getSource() == timer){
			snake.isAlive = mastermap.move(direction);
			if (!snake.isAlive && timer.isRunning())
				timer.stop();
			visualmap.repaint();
		}
	}
}