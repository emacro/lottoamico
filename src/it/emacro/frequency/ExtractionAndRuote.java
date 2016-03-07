/**
 * 
 */
package it.emacro.frequency;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emacro
 *
 */
public class ExtractionAndRuote {

	private List<Integer> ruote;
	private int extrNumber;
	
	public ExtractionAndRuote() {
		super();
		ruote = new ArrayList<Integer>();
	}
	
	public int getExtrNumber() {
		return extrNumber;
	}
	public void setExtrNumber(int extrNumber) {
		this.extrNumber = extrNumber;
	}
	public Integer[] getRuote() {
		return ruote.toArray(new Integer[ruote.size()]);
	}
	public void addRuota(int ruota) {
		this.ruote.add(ruota);
	}
	


}
