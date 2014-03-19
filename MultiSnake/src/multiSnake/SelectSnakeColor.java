package multiSnake;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class SelectSnakeColor implements ActionListener
{
	private Player me;
	private Color colorSelect;
	
	public SelectSnakeColor(Color c, Player player)
	{
		super();
		colorSelect = c;
		me = player;
	}
	
	@Override
	public void actionPerformed(ActionEvent a)
	{
		if(available()) me.setColor(colorSelect);
		else JOptionPane.showMessageDialog(null, "That color is already selected");
	}
	
	private boolean available()
	{
		return true;
	}
	
}
