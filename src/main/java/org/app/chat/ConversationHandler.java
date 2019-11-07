package org.app.chat;

import java.io.*;
import java.net.Socket;

public class ConversationHandler extends Thread
{
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	String name;
	PrintWriter pw;
	static FileWriter fw;
	static BufferedWriter bw;

	public ConversationHandler(Socket socket) throws Exception
	{
		this.socket=socket;
		fw=new FileWriter("C:\\Users\\ramini\\Desktop\\org.app.chat.Server-Logs.txt",true);
		//if true is not written it clears the existing file and writes this, if true is written it just appends this to the existing file
		//FileWriter writes only one character at a time
		bw=new BufferedWriter(fw);
		//it is capable of writing an entire string at a time
		pw=new PrintWriter(bw,true);
		//it is the one which actually writes into the file
		//true is written for auto-flush
	}
	public void run()
	{	
		try
		{
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream(),true);
			int count=0;
			while(true)
			{
				if(count>0)
				{
					out.println("NAMEALREADYEXISTS");
				}
				else
				{
					out.println("NAMEREQUIRED");
				}
				
				name=in.readLine();			
				if(name==null)
				{
					return;
				}
				if(!Server.names.contains(name))
				{
					Server.names.add(name);
					out.println("NAMEACCEPTED"+name);
					break;
				}
				count++;
			}
			Server.printWriters.add(out);
		
		
			while(true)
			{
				String message=in.readLine();
				if(message==null)
				{
					return;
				}
				pw.println(name+": "+message);
				for(PrintWriter writer : Server.printWriters)
				{
					writer.println(name+": "+message);
				}
			}
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
	}
}