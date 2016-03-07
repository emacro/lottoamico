package it.emacro.manager;

import it.emacro.cache.DatesCacher;

/**
 * @author Emc
 *
 */
public class DatesManager extends AbstractManager {

	private static DatesManager instance;

	private DatesManager() {
		super();
		cacheDates();
	}

	public static DatesManager getInstance() {
		if (instance == null) {
			synchronized (DatesManager.class) {
				if (instance == null) {
					instance = new DatesManager();
				}
			}
		}
		return instance;
	}
	
	public String getDate(int count) {
		return DatesCacher.getInstance().getDate(count);
	}
	
	public int getDatesLen() {
		return DatesCacher.getInstance().getNumberOfCashed();
	}
	
	public  String[] getAllExtractionDates() {
		return DatesCacher.getInstance().getAllDate();
	}
	
	private void cacheDates(){
		if (DatesCacher.getInstance().getNumberOfCashed() == 0) {
			String[] dates = StorageManager.getInstance().getStorage().getAllExtractionDates();
			for (int ii = 0; ii < dates.length; ii++) {
				DatesCacher.getInstance().addDate(ii, dates[ii]);
			}
		}
	}

}
