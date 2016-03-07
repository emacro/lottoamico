/**
 * 
 */
package it.emacro.cache;

import it.emacro.extractor.db.Extraction;
import it.emacro.extractor.db.Extracts;
import it.emacro.extractor.db.Number;
import it.emacro.extractor.db.Ruota;

/**
 * @author Emc
 * 
 */
public class DbCacher {

	private Extraction[] extractions;

	private Extracts[] extracts;

	private Ruota[] ruote;

	private Number[] numbers;

	// private Map<String,Extraction> map;

	private static DbCacher instance = null;

	private DbCacher() {
		// map = new HashMap<Integer, String>();
	}

	public static DbCacher getInstance() {
		if (instance == null) {
			synchronized (DbCacher.class) {
				if (instance == null) {
					instance = new DbCacher();
				}
			}
		}
		return instance;
	}

	public Extraction[] getExtractions() {
		return extractions;
	}

	public void setExtractions(Extraction[] extractions) {
		this.extractions = extractions;
	}

	public Extracts[] getExtracts() {
		return extracts;
	}

	public void setExtracts(Extracts[] extracts) {
		this.extracts = extracts;
	}

	public Number[] getNumbers() {
		return numbers;
	}

	public void setNumbers(Number[] numbers) {
		this.numbers = numbers;
	}

	public Ruota[] getRuote() {
		return ruote;
	}

	public void setRuote(Ruota[] ruote) {
		this.ruote = ruote;
	}

}
