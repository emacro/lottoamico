/**
 * 
 */
package it.emacro.explorer;

import it.emacro.extractor.db.Extraction;
import it.emacro.extractor.db.Number;
import it.emacro.extractor.db.Ruota;
import it.emacro.manager.StorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
			for (Ruota r : extraction.getRuote()) {
				listN = new ArrayList<String>();

				for (Number n : r.getExtracts().getNumbers()) {
					listN.add(n.toString());
				}
				list.add(listN.toArray(new String[listN.size()]));
			}
		}
		catch(Exception e){
			logger.log(Level.SEVERE, String.format("Errore in fase di parsing per l'estrazione: %s ", extraction), e);
		}
 
		return list;
	}

}
