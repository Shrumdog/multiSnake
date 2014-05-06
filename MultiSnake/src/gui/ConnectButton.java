package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import multiSnake.Player;
import networking.FirstResponder;

@SuppressWarnings("serial")
public class ConnectButton extends JButton
{
	private Dimension textFieldSize = new Dimension(150, 20);
	private JPanel infoNeeded;
	private ArrayList<JTextField> IPFields;
	private ArrayList<String> IPAddresses;
	private Player player;

	public ConnectButton(String label, Player me)
	{
		super(label);
		// System.out.println("Set " + who + " to " + me);
		player = me;
		System.out.println(player + " is " + me);
		infoNeeded = new JPanel();
		infoNeeded.setLayout(new GridLayout(10, 0));
		IPAddresses = new ArrayList<String>();
		initializeIPFields();
		addActionListener(new Display());
	}
	
	public void startConnection()
	{
		for(String ip: IPAddresses)
		{
			try
			{
				createFirstResponse(ip);
			} catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	public ArrayList<String> getIPs()
	{
		return new ArrayList<String>(IPAddresses);
	}

	public Player getPlayer()
	{
		return player;
	}
	
	private void initializeIPFields()
	{
		JTextField textField = newIPField();
		IPFields = new ArrayList<JTextField>();
		IPFields.add(textField);
		addTextListener(textField);
	}
	
	private JTextField newIPField()
	{
		JTextField textField = new JTextField("");
		textField.setPreferredSize(textFieldSize);
		return textField;
	}
	
	private void addTextListener(JTextField field)
	{
		Document doc = field.getDocument();
		doc.addDocumentListener(new DocListener(IPFields, infoNeeded));
	}
	
	private void createFirstResponse(String IPAddress) throws IOException
	{
		Socket socket = new Socket("209.65.57.21", 8888);
		FirstResponder first = new FirstResponder(socket, IPAddress);
		System.out.println("sent a firstresponder thread to the server");
		first.start();
	}
	
	private void displayPrompt()
	{
		infoNeeded.add(new JLabel("IP Address: "));
		for(String ip: IPAddresses)
		{
			JLabel IPLabel = new JLabel(ip);
			infoNeeded.add(IPLabel);
		}
		for(JTextField tf: IPFields)
		{
			infoNeeded.add(tf);
		}
		
		JOptionPane.showMessageDialog(null, infoNeeded, "Connection Information", JOptionPane.QUESTION_MESSAGE);
		for(JTextField tf: IPFields)
		{
			String str = tf.getText();
			System.out.println(str);
			if(!str.equals(""))
			{
				IPAddresses.add(str);
			}
		}
		
		infoNeeded.removeAll();
	}
	
	private class Display implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			displayPrompt();
		}
	}
}
