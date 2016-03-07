/**
 * 
 */
package it.emacro.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Emc
 * 
 */
public class ButtonListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

		String c = e.getActionCommand();

		if (c.compareTo("Avanti") == 0) {
			System.out.println("ActionListener: Premuto avanti");
		} else if (c.compareTo("Indietro") == 0) {

		}

	}

}
