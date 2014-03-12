package multiSnake;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class SnakeOptions extends JPanel
{
	private JTextField nameField;
	private Dimension buttonSize = new Dimension(0, 0);
	private Player me;
	
	public SnakeOptions(Player player)
	{
		me = player;
		setLayout(new GridLayout(2, 1));
		add(setupNameContainer());
		add(setupColorSelector());
	}
	
	public String getName()
	{
		return nameField.getText();
	}
	
	private JPanel setupNameContainer()
	{
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(75, 20));
		
		JPanel nameContain = new JPanel();
		nameContain.add(new JLabel("Name:"));
		nameContain.add(nameField);
		
		return nameContain;
	}
	
	private JPanel setupColorSelector()
	{	
		JPanel colorSelector = new JPanel();
		colorSelector.setLayout(new GridLayout(2, 1));
		colorSelector.add(new JLabel("Worm Color: "));
		colorSelector.add(setupColorPalette());
		return colorSelector;
	}
	
	private JPanel setupColorPalette()
	{
		JPanel colorPalette = new JPanel();
		
		colorPalette.add(setupRed());
		colorPalette.add(setupBlue());
		colorPalette.add(setupGreen());
		colorPalette.add(setupOrange());
		colorPalette.add(setupCyan());
		colorPalette.add(setupMagenta());
		
		int size = (int) Math.sqrt(colorPalette.getComponentCount());
		colorPalette.setLayout(new GridLayout(size, size));
		
		return colorPalette;
	}
	
	private JButton setupRed()
	{
		JButton red = new JButton();
		red.setPreferredSize(buttonSize);
		red.setBackground(Color.RED);
		red.addActionListener(new SelectSnakeColor(Color.RED, me));
		return red;
	}
	
	private JButton setupBlue()
	{
		JButton blue = new JButton();
		blue.setBackground(Color.BLUE);
		blue.addActionListener(new SelectSnakeColor(Color.BLUE, me));
		return blue;
	}
	
	private JButton setupGreen()
	{
		JButton green = new JButton();
		green.setBackground(Color.GREEN);
		green.addActionListener(new SelectSnakeColor(Color.GREEN, me));
		return green;
	}
	
	private JButton setupOrange()
	{
		JButton orange = new JButton();
		orange.setBackground(Color.ORANGE);
		orange.addActionListener(new SelectSnakeColor(Color.ORANGE, me));
		return orange;
	}
	
	private JButton setupCyan()
	{
		JButton cyan = new JButton();
		cyan.setBackground(Color.CYAN);
		cyan.addActionListener(new SelectSnakeColor(Color.CYAN, me));
		return cyan;
	}
	
	private JButton setupMagenta()
	{
		JButton magenta = new JButton();
		magenta.setBackground(Color.MAGENTA);
		magenta.addActionListener(new SelectSnakeColor(Color.MAGENTA, me));
		return magenta;
	}
}
