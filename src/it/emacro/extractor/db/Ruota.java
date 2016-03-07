/**
 * 
 */
package it.emacro.extractor.db;

/**
 * @author Emc
 *
 */
public class Ruota {
	private int id;
	private String name; 
	private Extracts extracts;

	public Extracts getExtracts() {
		return extracts;
	}

	public void setExtracts(Extracts extracts) {
		this.extracts = extracts;
	}

	public Ruota() {
		super();
	}
	
	public Ruota(int id) {
		super();
		this.id = id;
	}
	
	public Ruota(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
