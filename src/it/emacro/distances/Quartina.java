/**
 * 
 */
package it.emacro.distances;

import it.emacro.util.Utils;


/**
 * @author user
 *
 */
public class Quartina {
	
	private Couple[] couples = new Couple[2];
	private String[] ruote = new String[2];
	private int[] numbers = new int[4];
	private int linearDistance = 0;
	private int diagonalDistance = 0;
	/**
	 * 
	 */
	public Quartina(Couple c0, Couple c1, int linearDistance, int diagonalDistance) {
		this.couples[0] = c0;
		this.couples[1] = c1;
		this.linearDistance = linearDistance;
		this.diagonalDistance = diagonalDistance;
		fill();
	}
	
	private void fill() {
		int index = 0;
		for (int cc = 0; cc < couples.length; cc++) {
			ruote[cc] = couples[cc].getRuota();
			numbers[index++] = couples[cc].getFirstNumber();
			numbers[index++] = couples[cc].getSecondNumber();
		}
	}

	public String getRuote() {
		return Utils.firstLetterUpperCase(ruote[0]) + ", " + Utils.firstLetterUpperCase(ruote[1]);
	}
	
	public String getNumbers() {
		return numbers[0] + "." + numbers[1] + "." + numbers[2] + "." + numbers[3];
	}
	
	public String getDistances() {
		return linearDistance + " - " + diagonalDistance;
	}
	
	public String toString() {
		return getRuote() + "  " + getNumbers() + "   (" + getDistances() + ")";
	}
	
	

}
