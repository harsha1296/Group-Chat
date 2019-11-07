package org.app.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener//this interface has only one method actionPerformed
{
	public void actionPerformed(ActionEvent event)
	{
		Client.out.println(Client.textField.getText());
		Client.textField.setText("");
	}		
}