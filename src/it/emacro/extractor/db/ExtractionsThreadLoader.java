package it.emacro.extractor.db;

import it.emacro.log.Log;

import java.util.Map;

public class ExtractionsThreadLoader {
	
	private ExtractionsThreadLoader(){
		
	}
	
	public static void loadExtractionsInBackground(final Storage storage, final Map<String, Extraction> map) {
		
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				Extraction extraction;
				for (String date : storage.getAllExtractionDates()) {
					if (!map.containsKey(date)) {
						extraction = storage.getExtraction(date);
						map.put(extraction.getDate(), extraction);
					}
					
//					Log.println("loaded extraction with date " + date + " in background." );
				}
//				thread.interrupt();
			}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
//		thread.setDaemon(true);
		thread.start();
	}

}
