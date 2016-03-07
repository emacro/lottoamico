/**
 * 
 */
package it.emacro.gui.listeners;

import it.emacro.util.ExitStorer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author Emc
 * 
 */
public class ExitListener implements ActionListener {

	public ExitListener() {
		super();
		// esci = jmi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// if (e.getSource() == esci) {
		int a = JOptionPane.showConfirmDialog(null, "Vuoi veramente uscire?",
				"Conferma uscita", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (a == JOptionPane.OK_OPTION) {
			ExitStorer.getInstance().storeAndExit();
		}
		// }

	}

}
