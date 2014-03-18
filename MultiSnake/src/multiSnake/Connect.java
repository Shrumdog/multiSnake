package multiSnake;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Connect{
	private Dimension textFieldSize = new Dimension(150, 20);
	private JPanel infoNeeded;
	private JTextField ip;
	private String IPAddress;
	private ConcurrentHashMap<Player, String> players = new ConcurrentHashMap<Player, String>();
	private Player who;
	
	public Connect(Player me) throws IOException{
		infoNeeded = new JPanel();
		ip = new JTextField("");
		ip.setPreferredSize(textFieldSize);
		infoNeeded.add(new JLabel("IP Address: "));
		infoNeeded.add(ip);
		//System.out.println("Set " + who + " to " + me);
		who = me;
		System.out.println(who + " is " + me);

		
		JOptionPane.showMessageDialog(null, infoNeeded, "Connection Information", JOptionPane.QUESTION_MESSAGE);
		System.out.println(ip.getText());
		IPAddress = ip.getText();
		if(!players.contains(IPAddress)){
			players.put(me, IPAddress);
		}
		//Socket socket = new Socket(IPAddress, 8888);
		//UpdateSender sender = new UpdateSender(socket, me);
		//sender.start();
		//kick off updatesender thread with the above socket
	}
	
	public String getIP(){
		return IPAddress;
	}
	
	public ConcurrentHashMap<Player, String> getPlayers(){
		return players;
	}
	
	public Player getPlayer(){
		return this.who;
	}
}
