package it.emacro.cache;
 
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emc
 * 
 */
public class DatesCacher {

	private static DatesCacher instance = null;

	private List<String> list;

	private DatesCacher() {
		list = new ArrayList<String>();
	}

	public static DatesCacher getInstance() {
		if (instance == null) {
			synchronized (DatesCacher.class) {
				if (instance == null) {
					instance = new DatesCacher();
				}
			}
		}
		return instance;
	}

	public String getDate(int count) {
		return list.get(count);
	}

	public void addDate(int count, String date) {
		list.add(count, date);
	}

	public String[] getAllDate() {
		return list.toArray(new String[list.size()]);
	}

	public int getNumberOfCashed() {
		return list.size();
	}

	public boolean contains(Object date) {
		return list.contains(date);
	}

}
