/**
 * 
 */
package it.emacro.manager;

import it.emacro.cache.DbCacher;
import it.emacro.extractor.db.Extraction;
import it.emacro.extractor.db.Extracts;
import it.emacro.extractor.db.Number;
import it.emacro.extractor.db.Ruota;
import it.emacro.extractor.db.Storage;
import it.emacro.log.Log;
import it.emacro.util.Messenger;

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

	private Storage storage;

	private DbCacher cacher;
	
	private Map<String,Extraction> map;

	private StorageManager() {
		super();
		map = new Hashtable<String,Extraction>();
		loadCacher();
		synchronizeDbElems();
		mapExtractions();
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

	/**
	 * 
	 * @param date
	 *            the extraction date in format yyyy-MM-dd
	 * @return Extraction object
	 */
	public Extraction getExtraction(String date) {
		return map.get(date);
	}
	
	public int getNumberOfExtractions() {
		return map.size();
	}
	
	public boolean contains(Extraction extraction) {
		return map.containsValue(extraction);
	}
	
	public boolean containDate(String date) {
		return map.containsKey(date);
	}

	public String[] getRuoteNames() {
		List<String> list = new ArrayList<String>();
		for (Ruota r : cacher.getRuote()) {
			list.add(r.getName());
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * synchronize all DB objects data
	 * 
	 */
	private void synchronizeDbElems() {

		List<Number> listN = new ArrayList<Number>();
		List<Ruota> listR = new ArrayList<Ruota>();
		Ruota r;

//		Log.println("starting DB objects synchronize...");
		Log.println(Messenger.getInstance().getMessage("starting.db.objects.synchronize"));

		int idx = 0;
		for (Number number : cacher.getNumbers()) {
			listN.add(number);
			idx++;
			if (idx % 5 == 0) {
				cacher.getExtracts()[idx / 5 - 1].setNumbers(listN
						.toArray(new Number[listN.size()]));
				listN.clear();
			}
		}

		idx = 0;
		for (Extracts extractS : cacher.getExtracts()) {
			r = new Ruota();
			r.setExtracts(extractS);
			listR.add(r);
			idx++;
			if (idx % 11 == 0) {
				cacher.getExtractions()[idx / 11 - 1].setRuote(listR
						.toArray(new Ruota[listR.size()]));
				listR.clear();
			}
		}
//		Log.println("DB objects are synchronized");
		Log.println(Messenger.getInstance().getMessage("db.objects.are.synchronized"));
	}

	/**
	 * create a map with key: the date of the extraction and value: the
	 * extraction object
	 * 
	 */
	private void mapExtractions() {

//		Log.println("mapping extractions...");
		Log.println(Messenger.getInstance().getMessage("mapping.extractions"));

		for (Extraction e : cacher.getExtractions()) {
			map.put(e.getDate(), e);
		}

//		Log.println("extractions are mapped");
		Log.println(Messenger.getInstance().getMessage("extractions.are.mapped"));
		
	}

	private void loadCacher() {
		storage = new Storage();
		cacher = DbCacher.getInstance();
//		Log.println("start to load DB data into memory");
		Log.println(Messenger.getInstance().getMessage("start.to.load.db.data.into.memory"));

//		Log.println("loading table extractions...");
		Log.println(Messenger.getInstance().getMessage("loading.table.extractions"));
		cacher.setExtractions(storage.getExtractions());
//		Log.println("loading table ruote...");
		Log.println(Messenger.getInstance().getMessage("loading.table.ruote"));
		cacher.setRuote(storage.getRuote());
//		Log.println("loading table extracts...");
		Log.println(Messenger.getInstance().getMessage("loading.table.extracts"));
		cacher.setExtracts(storage.getExtracts());
//		Log.println("loading table numbers...");
		Log.println(Messenger.getInstance().getMessage("loading.table.numbers"));
		cacher.setNumbers(storage.getNumbers());

//		Log.println("DB data has been right loaded");
		Log.println(Messenger.getInstance().getMessage("db.data.has.been.right.loaded"));
	}

}
