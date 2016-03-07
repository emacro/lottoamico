/**
 * 
 */
package it.emacro.manager;

import it.emacro.explorer.ExtractionsExplorer;

import java.util.List;

/**
 * @author Emc
 * 
 */
public class ExplorerManager extends AbstractManager {

	private static ExplorerManager instance;
	private int actualExtractionCount = -1;;

	private ExplorerManager() {
		super();
	}

	public static ExplorerManager getInstance() {
		if (instance == null) {
			synchronized (ExplorerManager.class) {
				if (instance == null) {
					instance = new ExplorerManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * @param count
	 *            the index of returned List
	 * @return a list of 11 elements (ruote) containing an array of 5 (numbers)
	 */
	public List<String[]> getExtraction(int count) {
		actualExtractionCount = count;
		String date = DatesManager.getInstance().getDate(actualExtractionCount);
		return getExtractionByDate(date);
	}
	
	public int getLastExtractionCount() {
		if (actualExtractionCount < 0) {
			throw new RuntimeException("Il number count passato non e' valido (-1)");
		}
		return actualExtractionCount;
	}
	
	public List<String[]> getLastExtraction() {
		if (actualExtractionCount < 0) {
			throw new RuntimeException("Non riesco ad estrarre l'estrazione: count number non valido (-1)");
		}
		return getExtraction(actualExtractionCount);
	}

	/**
	 * 
	 * @param date
	 *            the extraction date in format yyyy-MM-dd
	 * @return a list of 11 elements (ruote) containing an array of 5 (numbers)
	 */
	private List<String[]> getExtractionByDate(String date) {
		return ExtractionsExplorer.getInstance().getExtraction(date);
	}

	/**
	 * 
	 * @param extrNum
	 *            the extraction number from 1 to N
	 * @return a list of 11 elements (ruote) containing an array of 5 (numbers)
	 */
	private List<String[]> getExtractionByNum(int extrNum) {
//		int n = DatesManager.getInstance().getDatesLen() - extrNum;
		int n = StorageManager.getInstance().getNumberOfExtractions() - extrNum;
		String date = DatesManager.getInstance().getDate(n);
		return ExtractionsExplorer.getInstance().getExtraction(date);
	}

}
