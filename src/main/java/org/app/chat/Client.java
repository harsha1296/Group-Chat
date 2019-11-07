package org.app.chat;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client 
{
	static JFrame chatWindow=new JFrame("WhatsApp");
	static JTextArea chatArea=new JTextArea(22,40);//(columns,rows)
	static JScrollPane jsp=new JScrollPane(chatArea);
	static JTextField textField=new JTextField(40);//(columns)
	static JButton button=new JButton("Send");
	
	static JLabel nameLabel=new JLabel("              ");
	static BufferedReader in;
	static PrintWriter out;
	
	public Client()
	{
		chatWindow.setLayout(new FlowLayout());
		//elements are added one after the other in a flow
		chatWindow.setSize(475,500);//(width,height)
		//sets the size of the window
		
		chatWindow.add(nameLabel);
		
		chatWindow.add(jsp);
		chatArea.setEditable(false);
		//adds chatArea along with a scroll bar and prevents the user from typing on it
		chatWindow.add(textField);
		textField.setEditable(false);
		//adds textField and prevents the user from typing on it
		chatWindow.add(button);
		//adds the send button
		chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//on default it is JFrame.HIDE_ON_CLOSE which hides the application but keeps it running in the background
		chatWindow.setVisible(true);
		//must be written to display the window
		
		button.addActionListener(new Listener());
		textField.addActionListener(new Listener());		
	}	
	
	void startChat() throws Exception
	{
		String ipAddress=JOptionPane.showInputDialog(chatWindow,"Enter IP Address: ","IP Address Required!!!",JOptionPane.PLAIN_MESSAGE);
		Socket socket=new Socket(ipAddress,8001);
		in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(socket.getOutputStream(),true);
		while(true)
		{
			String response=in.readLine();
			if(response.equals("NAMEREQUIRED"))
			{
				String name=JOptionPane.showInputDialog(chatWindow,"Enter your name: ","Name Required!!!",JOptionPane.PLAIN_MESSAGE);
				out.println(name);
			}
			else if(response.equals("NAMEALREADYEXISTS"))
			{
				String name=JOptionPane.showInputDialog(chatWindow,"Enter another name: ","Name already exists!!!",JOptionPane.WARNING_MESSAGE);
				out.println(name);
			}
			else if(response.startsWith("NAMEACCEPTED"))
			{
				textField.setEditable(true);
				nameLabel.setText("You are logged in as: "+response.substring(12));
			}
			else
			{
				chatArea.append(response+"\n");
			}			
		}
	}

	public static void main(String[] args) throws Exception 
	{
		Client client=new Client();
		client.startChat();
	}
}
