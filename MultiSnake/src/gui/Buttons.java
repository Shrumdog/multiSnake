package gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import multiSnake.Player;
import multiSnake.SnakeOptions;

@SuppressWarnings("serial")
public class Buttons extends JPanel
{
	private Player me;
	private SnakeOptions options;
	private ConnectButton connect;
	private JButton play;
	private Joystick jstick;

	public Buttons(Player player, SnakeOptions o, Joystick js)
	{
		setLayout(new GridLayout(2, 1));
		me = player;
		options = o;
		jstick = js;

		connect = new ConnectButton("Connect", player);
		play = new JButton("Join Game");

//		connect.addActionListener(new Spectate());
		play.addActionListener(new JoinGame());

		add(connect);
		add(play);
	}

	private class JoinGame implements ActionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String cur = play.getText();
			if(cur.equals("Rejoin Game"))
			{
				me.resumePlay();
				play.setText("Leave Game");
			}
			else if(cur.equals("Leave Game"))
			{
				me.stopPlaying();
				play.setText("Rejoin Game");
			}
			else
			{
				connect.startConnection();
				me.setName(options.getName());
				me.startPlaying();
				play.setText("Leave Game");
			}
		}
	}
}