package multiSnake;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Connect implements ActionListener
{
	private Dimension textFieldSize = new Dimension(150, 20);
	private JPanel infoNeeded;
	private JTextField ip;
	
	public Connect()
	{
		super();
		infoNeeded = new JPanel();
		ip = new JTextField("");
		ip.setPreferredSize(textFieldSize);
		infoNeeded.add(new JLabel("IP Address: "));
		infoNeeded.add(ip);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JOptionPane.showMessageDialog(null, infoNeeded, "Connection Information", JOptionPane.QUESTION_MESSAGE);
		System.out.println(ip.getText());
	}
}
