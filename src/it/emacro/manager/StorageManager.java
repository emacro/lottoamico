/**
 * 
 */
package it.emacro.manager;

import it.emacro.extractor.db.Extraction;
import it.emacro.extractor.db.ExtractionsThreadLoader;
import it.emacro.extractor.db.Ruota;
import it.emacro.extractor.db.Storage;
import it.emacro.log.Log;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Emc
 * 
 */
public class StorageManager extends AbstractManager {

	private static StorageManager instance;

	private Storage storage = new Storage();

	private Map<String, Extraction> map = new Hashtable<String, Extraction>();

	private int numberOfExtractions;
	
	private StorageManager() {
		super();
		initialize();
		ExtractionsThreadLoader.loadExtractionsInBackground(storage, map);
	}

	/** load extractions into the database */
	private void initialize() {
		numberOfExtractions = storage.getNumberOfExtractions();
	}

	public static StorageManager getInstance() {
		if (instance == null) {
			synchronized (StorageManager.class) {
				if (instance == null) {
					instance = new StorageManager();
				}
			}
		}
		return instance;
	}
	
	public Storage getStorage() {
		return storage;
	}

	/**
	 * 
	 * @param date
	 *            the extraction date in format yyyy-MM-dd
	 * @return Extraction object
	 */
	public synchronized Extraction getExtraction(String date) {
		if (!map.containsKey(date)) {
			insertNewExtractionIntoMap(date);
//			Log.println(date + " inserita.");
		}else{
//			Log.println(date + " gia' esistente in memoria.");
		}
		return map.get(date);
	}
	
	private void insertNewExtractionIntoMap(String date) {
		Extraction extraction = storage.getExtraction(date);
		map.put(extraction.getDate(), extraction);
	}

	public synchronized int getNumberOfExtractions() {
		return numberOfExtractions; // map.size();
	}
	
	public synchronized boolean contains(Extraction extraction) {
		return map.containsValue(extraction);
	}
	
	public synchronized boolean containDate(String date) {
		return map.containsKey(date);
	}

	public synchronized String[] getRuoteNames() {
		List<String> list = new ArrayList<String>();
		for (Ruota r : storage.getRuote()) {
			list.add(r.getName());
		}
		return list.toArray(new String[list.size()]);
	}

}
