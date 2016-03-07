/**
 * 
 */
package it.emacro.gui.components;

import it.emacro.gui.panels.NumbersPanel;
import it.emacro.gui.panels.RuotePanel;

import java.awt.Color;
import java.awt.Component;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class Row extends Component {

	private Cell ruota;

	private Cell first;

	private Cell second;

	private Cell third;

	private Cell fourth;

	private Cell fifth;
	
	private boolean enabled = true;

	/**
	 * 
	 */
	public Row() {
		super();
		// setLayout(new GridLayout(1, 6));
		// setLayout(new BorderLayout());
	}

	public Row(Cell ruota, Cell first, Cell second, Cell third, Cell fourth,
			Cell fifth) {
		super();
		this.ruota = ruota;
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		
		this.ruota.setBackground(new Color(232, 232, 232));
		deSelect();

		RuotePanel.getInstance().add(this.ruota);
		NumbersPanel.getInstance().add(this.first);
		NumbersPanel.getInstance().add(this.second);
		NumbersPanel.getInstance().add(this.third);
		NumbersPanel.getInstance().add(this.fourth);
		NumbersPanel.getInstance().add(this.fifth);

	}
	
	public void setEnabled(boolean e) {
		this.enabled = e;
		
		this.ruota.setEnabled(e);
		this.first.setEnabled(e);
		this.second.setEnabled(e);
		this.third.setEnabled(e);
		this.fourth.setEnabled(e);
		this.fifth.setEnabled(e);
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void select(String[] numbers) {
		deSelect();
		
		if(isEnabled()){
			for(String n: numbers){
				if(this.first.getText().equals(n)){
					this.first.setBackground(new Color(238, 238, 0));
				}
				if(this.second.getText().equals(n)){
					this.second.setBackground(new Color(238, 238, 0));
				}
				if(this.third.getText().equals(n)){
					this.third.setBackground(new Color(238, 238, 0));
				}
				if(this.fourth.getText().equals(n)){
					this.fourth.setBackground(new Color(238, 238, 0));
				}
				if(this.fifth.getText().equals(n)){
					this.fifth.setBackground(new Color(238, 238, 0));
				}
				
			}
		}
		
	}
	
	public void deSelect() {
		this.first.setBackground(new Color(248, 248, 255));
		this.second.setBackground(new Color(248, 248, 255));
		this.third.setBackground(new Color(248, 248, 255));
		this.fourth.setBackground(new Color(248, 248, 255));
		this.fifth.setBackground(new Color(248, 248, 255));
	}

	public Cell getFifth() {
		return fifth;
	}

	public void setFifth(Cell fifth) {
		this.fifth = fifth;
		this.fifth.setBackground(new Color(245, 245, 245));
		NumbersPanel.getInstance().add(this.fifth);
	}

	public Cell getFirst() {
		return first;
	}

	public void setFirst(Cell first) {
		this.first = first;
		this.first.setBackground(new Color(245, 245, 245));
		NumbersPanel.getInstance().add(this.first);
	}

	public Cell getFourth() {
		return fourth;
	}

	public void setFourth(Cell fourth) {
		this.fourth = fourth;
		this.fourth.setBackground(new Color(245, 245, 245));
		NumbersPanel.getInstance().add(this.fourth);
	}

	public Cell getRuota() {
		return ruota;
	}

	public void setRuota(Cell ruota) {
		this.ruota = ruota;
		this.ruota.setBackground(new Color(232, 232, 232));
		RuotePanel.getInstance().add(this.ruota);
	}

	public Cell getSecond() {
		return second;
	}

	public void setSecond(Cell second) {
		this.second = second;
		this.second.setBackground(new Color(245, 245, 245));
		NumbersPanel.getInstance().add(this.second);
	}

	public Cell getThird() {
		return third;
	}

	public void setThird(Cell third) {
		this.third = third;
		this.third.setBackground(new Color(245, 245, 245));
		NumbersPanel.getInstance().add(this.third);
	}

}
