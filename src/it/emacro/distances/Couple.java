/**
 * 
 */
package it.emacro.distances;

/**
 * @author user
 *
 */
public class Couple {
	
	private String ruota;
	private int firstNumber, secondNumber;

	/**
	 * 
	 */
	public Couple(String ruota, int firstNumber, int secondNumber) {
		this.ruota = ruota;
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
	}
	
	public String getRuota() {
		return ruota;
	}
	
	public int getFirstNumber() {
		return firstNumber;
	}
	
	public int getSecondNumber() {
		return secondNumber;
	}
	
	@Override
	public String toString() {
		return ruota + " "  + firstNumber + " - " + secondNumber;
	}

}
