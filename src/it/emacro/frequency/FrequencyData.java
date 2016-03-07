/**
 * 
 */
package it.emacro.frequency;

/**
 * @author Emc
 * 
 */
public class FrequencyData {

	private int[] spyNumbers = new int[3];
	private int[] playNumbers = new int[5];

	public FrequencyData() {
		super();
	}
	
	public FrequencyData(int[] spyNumbers, int[] playNumbers) {
		super();
		this.spyNumbers = spyNumbers;
		this.playNumbers = playNumbers;
	}

	public int[] getSpyNumbers() {
		return spyNumbers;
	}

	public void setSpyNumbers(int[] dateNumbers) {
		this.spyNumbers = dateNumbers;
	}
	
	public void setSpyNumbers(int num0, int num1, int num2) {
		setSpyNumbers(new int[]{num0,num1,num2});
	}

	public int[] getPlayNumbers() {
		return playNumbers;
	}

	public void setPlayNumbers(int[] playNumbers) {
		this.playNumbers = playNumbers;
	}
	
	public void setPlayNumbers(int num0, int num1, int num2, int num3, int num4) {
		setPlayNumbers(new int[]{num0,num1,num2,num3,num4});
	}

}
