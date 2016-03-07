/**
 * 
 */
package it.emacro.gui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class Cell extends JLabel {

	private boolean highlighted = false;
	private boolean borded = true;


	public Cell(String text) {
		super();
		init();
		setText(text);
	}
	
	public Cell(String text, boolean borded) {
		super();
		init();
		setText(text);
		setBorded(borded);
	}

	public Cell(String text, Font font) {
		super();
		init();
		setText(text);
		setFont(font);
	}
	
	public Cell(String text, int alignment) {
		super();
		init();
		setText(text);
		setHorizontalAlignment(alignment);
	}

	public Cell(String text, Font font,int alignment) {
		super();
		init();
		setText(text);
		setFont(font);
		setHorizontalAlignment(alignment);
	}

	public Cell(ImageIcon icon) {
		super();
		init();
		setBackground(Color.BLUE);
		setIcon(icon);
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
		setBackground(highlighted ? Color.YELLOW : null);
	}

//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//		setForeground(enabled ? Color.GRAY : Color.BLACK);
//	}
//
//	public boolean isEnabled() {
//		return enabled;
//	}

	public boolean isHighlighted() {
		return highlighted;
	}
	
	public boolean isBorded() {
		return borded;
	}

	public void setBorded(boolean borded) {
		if(this.borded = borded){
			setBorder(new BevelBorder(BevelBorder.RAISED));
		}else{
			setBorder(null);
		}
		
	} 

	// ------------ private methods --------------

	private void init() {
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setOpaque(true); // lo rendo trasparente
		setHorizontalAlignment(JLabel.CENTER);
	}

	

}
