package it.emacro.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class AboutListener  implements ActionListener{
	
	public void actionPerformed(ActionEvent e)
	{
		DlgAbout about = new DlgAbout(new JFrame());
		about.setVisible(true);
	}
}
