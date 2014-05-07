package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;

import multiSnake.MasterMap;
import multiSnake.Player;
import networking.SnakeServer;


@SuppressWarnings("serial")
public class GUI extends JFrame
{
	private VisualMap playScreen;
	private Dimension screenSize;
	private static Player me;
	private MasterMap trueMap;
	private Joystick jstick;
	
	public GUI(){
		super("Multiplayer Snake");
		screenSize = new Dimension(500, 500);
		trueMap = new MasterMap();
		playScreen = new VisualMap(trueMap);
		me = new Player(playScreen);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1350, 550);
		setResizable(false);
		setLayout(new FlowLayout());
		
		playScreen.setPreferredSize(screenSize);
		jstick = me.connectListener();
		playScreen.addKeyListener(jstick);
		
		add(playScreen);
		add(new ControlPanel(me, trueMap, jstick));
		
	}
	
	public static void main(String[] args) throws IOException{
		GUI gui = new GUI();
		gui.setVisible(true);
		SnakeServer snakeServer = new SnakeServer(me, gui.jstick);
		snakeServer.start();

	}
}
