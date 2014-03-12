package multiSnake;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GUI extends JFrame
{
	private VisualMap playScreen;
	private Dimension screenSize;
	private Player me;
	private MasterMap trueMap;
	
	public GUI()
	{
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
		playScreen.addKeyListener(me.connectListener());
		
		add(playScreen);
		add(new ControlPanel(me, trueMap));
	}
	
	public static void main(String[] args)
	{
		new GUI().setVisible(true);
	}
}
