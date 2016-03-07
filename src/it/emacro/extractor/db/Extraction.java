/**
 * 
 */
package it.emacro.extractor.db;

/**
 * @author Emc
 * 
 */
public class Extraction {

	private int id, number;

	private String date;

	private Ruota[] ruote;

	public Extraction() {
		super();
	}

	public Extraction(int id) {
		super();
		this.id = id;
	}

	public Extraction(int id, String date, int number) {
		super();
		this.id = id;
		this.date = date;
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Ruota[] getRuote() {
		return ruote;
	}

	public void setRuote(Ruota[] ruote) {
		this.ruote = ruote;
	}

}
