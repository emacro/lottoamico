/**
 * 
 */
package it.emacro.distances;

import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 *
 */
public class CouplesContainer {
	
	private List<Couple> couples = new ArrayList<Couple>();

	public CouplesContainer() {
	}
	
	public boolean addCouple(Couple couple) {
		return couples.add(couple);
	}
	
	public boolean removeCouple(Couple couple) {
		return couples.remove(couple);
	}
	
	public List<Couple> getCouples() {
		return couples;
	}

}
