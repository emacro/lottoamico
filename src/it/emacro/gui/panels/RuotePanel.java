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
public class RuotePanel extends JPanel {

	private static RuotePanel instance;

	private RuotePanel() {
		super();
		setLayout(new GridLayout(12, 1));
		Cell cell = new Cell("");
		cell.setBorded(false);
		add(cell);
	}

	public static RuotePanel getInstance() {
		if (instance == null) {
			synchronized (RuotePanel.class) {
				if (instance == null) {
					instance = new RuotePanel();
				}
			}
		}

		return instance;
	}

}
