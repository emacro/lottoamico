/**
 * 
 */
package it.emacro.extractor.db;

import java.util.Arrays;

/**
 * @author Emc
 *
 */
public class Extracts {

	private int id, ruota, extraction;
	private Number[] numbers;
	
	

	public Number[] getNumbers() {
		return numbers;
	}

	public void setNumbers(Number[] numbers) {
		this.numbers = numbers;
	}

	public Extracts() {
		super();
	}
	
	public Extracts(int id) {
		super();
		this.id = id;
	}
	
	public Extracts(int id, int ruota, int extraction) {
		super();
		this.id = id;
		this.ruota = ruota;
		this.extraction = extraction;
	}

	public int getExtraction() {
		return extraction;
	}

	public void setExtraction(int extraction) {
		this.extraction = extraction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRuota() {
		return ruota;
	}

	public void setRuota(int ruota) {
		this.ruota = ruota;
	}

	@Override
	public String toString() {
		return "Extracts [extraction=" + extraction + ", id=" + id
				+ ", numbers=" + Arrays.toString(numbers) + ", ruota=" + ruota
				+ "]";
	}
	
	

}
