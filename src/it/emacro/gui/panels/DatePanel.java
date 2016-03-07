/**
 * 
 */
package it.emacro.gui.panels;

import it.emacro.gui.components.Cell;
import it.emacro.gui.components.CustomFont;
import it.emacro.gui.components.Row;
import it.emacro.util.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class DatePanel extends JPanel {

	private String date;
	private Cell cell;

	public DatePanel(String date) {
		super();
		this.date = date;

		setLayout(new FlowLayout(FlowLayout.CENTER));
		cell = new Cell(" " + Utils.parseDate(date) + " ", CustomFont.BIG_BOLD);
		cell.setBackground(new Color(219,219,219));
		cell.setForeground(new Color(255, 0, 0));
		add(cell);
	}

	public void addRow(Row row) {
		add(BorderLayout.CENTER, row);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
		((Cell) this.getComponent(0))
				.setText(" " + Utils.parseDate(date) + " ");
	}

	public String getFormattedDate() {
		return Utils.parseDate(date);
	}
	
	public void setHighlighted(boolean highlighted){
		if(highlighted){
			cell.setBackground(new Color(238, 238, 0));
		}else{
			cell.setBackground(new Color(219,219,219));
		}
	}

}
