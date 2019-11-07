package org.app.chat;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server 
{
	static ArrayList<String> names=new ArrayList<String>();
	static ArrayList<PrintWriter> printWriters=new ArrayList<PrintWriter>();
	
	public static void main(String[] args) throws Exception
	{	
		ServerSocket serverSocket=new ServerSocket(8001);
		while(true)
		{
			Socket soc=serverSocket.accept();	
			ConversationHandler user=new ConversationHandler(soc);
			user.start();
		}
	}
}
