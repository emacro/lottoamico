/**
 * 
 */
package it.emacro.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class ToolBar extends JToolBar implements ActionListener {
	
	private JButton red = new JButton("Red");

	/**
	 * 
	 */
	public ToolBar() {
		super();
		setToolBar();
	}

	/**
	 * @param arg0
	 */
	public ToolBar(int arg0) {
		super(arg0);
		setToolBar();
	}

	/**
	 * @param arg0
	 */
	public ToolBar(String arg0) {
		super(arg0);
		setToolBar();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ToolBar(String arg0, int arg1) {
		super(arg0, arg1);
		setToolBar();
	}

	private void setToolBar() {
		// setting della toolbar e aggiunta alla gui
		setBorderPainted(true);
		add(red);
		red.addActionListener(this);
		setFloatable(false);
		setRollover(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		// if (e.getSource() == red) {
		// testo.setBackground(Color.RED);
		// }
	}

}
