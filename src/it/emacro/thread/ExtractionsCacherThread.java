/**
 * 
 */
package it.emacro.thread;

import it.emacro.log.Log;

/**
 * @author Emc
 * 
 */
public class ExtractionsCacherThread extends Thread {

	private static ExtractionsCacherThread instance;

	private ExtractionsCacherThread() {
		super();
	}

	public static ExtractionsCacherThread getInstance() {
		if (instance == null) {
			synchronized (ExtractionsCacherThread.class) {
				if (instance == null) {
					instance = new ExtractionsCacherThread();
				}
			}
		}
		return instance;
	}

	public void run() {
		try {
			// ExplorerManager.getInstance().startAutoCaching();

		} catch (Exception e) {
			Log.println("Exception during the extractions caching");
			e.printStackTrace();
		}
	}

	public void cacheExtractions(int start, int stop) {
		// ExplorerManager.getInstance().cacheExtractionS(start,stop);
	}

}
