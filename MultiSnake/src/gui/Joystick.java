package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.Timer;

import multiSnake.Direction;
import multiSnake.MasterMap;
import multiSnake.Player;
import multiSnake.Snake;
import networking.UpdateSender;

public class Joystick implements KeyListener, ActionListener{
	private MasterMap mastermap;
	private VisualMap visualmap;
	private Player me;
	private Snake snake;
	private Timer timer;
	private Direction direction;
	private ArrayList<String> playerAddresses;

	public Joystick(MasterMap mmap, VisualMap vmap, Player p, Snake s){
		mastermap = mmap;
		visualmap = vmap;
		me = p;
		snake = s;
		mastermap.setPlayerSnake(snake);
		timer = new Timer(40, this);
		System.out.println("Joystick created");
		playerAddresses = new ArrayList<String>();
		//addAddress("209.65.57.21"); //<--------YOUR COMPUTER'S IP ADDRESS
	}

	// public void addConnect(Connect c)
	// {
	// this.c = c;
	// }

	public ArrayList<String> getPlayerAddresses() {
		return playerAddresses;
	}

	public void updateAddresses(ArrayList<String> list){
		playerAddresses = list;
		System.out.println("player addresses size: " + playerAddresses.size());
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
				direction = mastermap.setDirection("n");
			else if (kc == KeyEvent.VK_DOWN)
				direction = mastermap.setDirection("s");
			else if (kc == KeyEvent.VK_LEFT)
				direction = mastermap.setDirection("w");
			else if (kc == KeyEvent.VK_RIGHT)
				direction = mastermap.setDirection("e");

			snake.isAlive = mastermap.move();
			visualmap.repaint();
		}
	}

	public void keyReleased(KeyEvent ke){}

	public void keyTyped(KeyEvent ke){}

	@Override
	public void actionPerformed(ActionEvent ae){
		System.out.println("player set size: " + playerAddresses.size());
		for(int i= 1; i<playerAddresses.size(); i++){
			if(playerAddresses.size()>1){
				Socket socket;
				try {
					//send in the IP address of the game that is already running
					System.out.println("creating socket to address: " + playerAddresses.get(i));
					socket = new Socket(playerAddresses.get(i), 8888);
					UpdateSender sender = new UpdateSender(socket, me);
					sender.start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (ae.getSource() == timer){
			snake.isAlive = mastermap.move();
			if (!snake.isAlive && timer.isRunning())
				timer.stop();
			visualmap.repaint();
		}
	}

	public void addAddress(String address){
		playerAddresses.add(address);
	}
}