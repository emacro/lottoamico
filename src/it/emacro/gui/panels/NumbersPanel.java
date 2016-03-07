/**
 * 
 */
package it.emacro.gui.panels;

import it.emacro.gui.components.Cell;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class NumbersPanel extends JPanel {

	private static NumbersPanel instance;

	private NumbersPanel() {
		super();
		setLayout(new GridLayout(12, 5));
		Cell[] roman = new Cell[5];
//		Cell[] empty = new Cell[5];
		
		roman[0] = new Cell("I");
		roman[1] = new Cell("II");
		roman[2] = new Cell("III");
		roman[3] = new Cell("IV");
		roman[4] = new Cell("V");
		
//		for(int rr=0;rr<5;rr++){
//			empty[rr] = new Cell("");
//			empty[rr].setBorded(false);
//			add(empty[rr]);
//		}
		
		for(int rr=0;rr<5;rr++){
			roman[rr].setBorded(false);
			add(roman[rr]);
		}
		
	}

	public static NumbersPanel getInstance() {
		if (instance == null) {
			synchronized (NumbersPanel.class) {
				if (instance == null) {
					instance = new NumbersPanel();
				}
			}
		}
		return instance;
	}

}
