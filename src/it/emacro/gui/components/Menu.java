/**
 * 
 */
package it.emacro.gui.components;

import it.emacro.gui.listeners.AboutListener;
import it.emacro.gui.listeners.ExitListener;
import it.emacro.gui.listeners.StrumListener;
import it.emacro.util.Constants;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Emacro
 *
 */
@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	
	private JMenu file = new JMenu("File");
	
	private JMenu strum = new JMenu("Strumenti");

	private JMenu help = new JMenu("Help");

	private JMenuItem esci = new JMenuItem("Esci");
	
	private JMenuItem perc = new JMenuItem(Constants.CALC_PRES);
	
	private JMenuItem distances = new JMenuItem(Constants.CALC_DISTANCES);
	
	private JMenuItem force = new JMenuItem(Constants.FORCE_NEXT_DWL);

	private JMenuItem about = new JMenuItem("Informazioni applicazione");

	/**
	 * 
	 */
	public Menu() {
		super();
		file.add(esci);
		strum.add(perc);
		strum.add(distances);
		strum.addSeparator();
		strum.add(force);
		help.add(about);
		
		add(file);
		add(strum);
		add(help);

		esci.addActionListener(new ExitListener());
		perc.addActionListener(new StrumListener());
		distances.addActionListener(new StrumListener());
		force.addActionListener(new StrumListener());
		about.addActionListener(new AboutListener());
	}

}
