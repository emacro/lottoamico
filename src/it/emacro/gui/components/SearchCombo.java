/**
 * 
 */
package it.emacro.gui.components;

import it.emacro.gui.panels.NumbersType;

import javax.swing.JComboBox;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class SearchCombo extends JComboBox implements NumbersType{

	public SearchCombo() {
		super();
		init();
	}

	private void init() {
		setEditable(false);
		addItem(LIBERO);
		addItem(GEMELLI);
		addItem(PARI);
		addItem(DISPARI);
		addItem(N_1_30);
		addItem(N_31_60);
		addItem(N_61_90);

		for (int ii = 0; ii < 10; ii++) {
			addItem(CADENZA + " " + ii);
		}
		for (int ii = 0; ii < 9; ii++) {
			addItem(DECINA + " " + ii);
		}
		for (int ii = 1; ii < 10; ii++) {
			addItem(FIGURA + " " + ii);
		}
		for (int ii = 0; ii < 10; ii++) {
			addItem(CIFRA + " " + ii);
		}
	}

}
