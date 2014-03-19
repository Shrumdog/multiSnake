package gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import multiSnake.Player;
import networking.FirstResponder;


public class Connect{
	private Dimension textFieldSize = new Dimension(150, 20);
	private JPanel infoNeeded;
	private JTextField ip;
	private String IPAddress;
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
		
		Socket socket = new Socket("209.65.57.21", 8888);
		FirstResponder first = new FirstResponder(socket, IPAddress);
		System.out.println("sent a firstresponder thread to the server");
		first.start();
	}
	
	public String getIP(){
		return IPAddress;
	}
	
	public Player getPlayer(){
		return this.who;
	}
}
