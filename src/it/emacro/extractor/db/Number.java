/**
 * 
 */
package it.emacro.extractor.db;

/**
 * @author Emc
 *
 */
public class Number {

	private int extract, position, number;
	
	public Number() {
		super();
	}
	
	public Number(int extract, int position, int number) {
		super();
		this.extract = extract;
		this.position = position;
		this.number = number;
	}

	public int getExtract() {
		return extract;
	}

	public void setExtract(int extract) {
		this.extract = extract;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}
	
	

}
