/**
 * 
 */
package it.emacro.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.emacro.extractor.db.Extraction;
import it.emacro.extractor.db.Extracts;
import it.emacro.extractor.db.Number;
import it.emacro.extractor.db.Ruota;
import it.emacro.manager.StorageManager;

/**
 * @author Emc
 * 
 */
public class ExtractionsExplorer {

	private static ExtractionsExplorer instance = null;
	
	private static Logger logger = Logger.getLogger(ExtractionsExplorer.class.getName());

	private ExtractionsExplorer() {
		super();
	}

	public static ExtractionsExplorer getInstance() {
		if (instance == null) {
			synchronized (ExtractionsExplorer.class) {
				if (instance == null) {
					instance = new ExtractionsExplorer();
				}
			}
		}
		return instance;
	}

	public List<String[]> getExtraction(String date) {
		Extraction e = StorageManager.getInstance().getExtraction(date);
		return parseExtraction(e);
	}

	private List<String[]> parseExtraction(Extraction extraction) {
		List<String[]> list = new ArrayList<String[]>();
		List<String> listN;

		try{
			
			if(extraction.getRuote() == null || extraction.getRuote().length < 11) 
				throw new RuntimeException(String.format("Cannot feth Ruote by extraction: %s", extraction));
			
			for (Ruota r : extraction.getRuote()) {
				listN = new ArrayList<String>();
				
				Extracts extractsObj = r.getExtracts();

				if(null != extractsObj)
					for (Number n : extractsObj.getNumbers())  
							listN.add(n.toString());
				
				list.add(listN.toArray(new String[listN.size()]));
			}
		}
		catch(Exception e){
			logger.log(Level.SEVERE, 
					String.format("Error in parsing phase for extraction number: %s ", 
							extraction.getNumber()), e);
		}
 
		return list;
	}

}
