package multiSnake;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel
{
	private MiniMap miniMap;
	private SnakeOptions options;
	
	public ControlPanel(Player player, MasterMap trueMap, Joystick jstick)
	{
		setLayout(new GridLayout(2,1));
		options = new SnakeOptions(player);
		
		JPanel container = new JPanel();
		container.add(new Buttons(player, options, jstick));
		container.add(options);
		add(container);
		
		miniMap = new MiniMap(trueMap);
		miniMap.setPreferredSize(new Dimension(250, 250));
		add(miniMap);
	}
}
