package it.emacro.util;

import it.emacro.extractor.Extractor;
import it.emacro.extractor.ExtractorCSV;
import it.emacro.extractor.ExtractorTXT;
import it.emacro.services.ApplicationData;
import it.emacro.services.ApplicationSettings;

public class ExtractorProvider {

	@SuppressWarnings("unchecked")
	public static synchronized Extractor getExtractor(String filePath) throws Exception {
		
		try {
			String extractorImplementation = 
					ApplicationData.getInstance().getExtractorImplementation();
			
			Class<Extractor> clazz = (Class<Extractor>) Class.forName(extractorImplementation);
			
			Extractor extractor = clazz.newInstance();
			 
			return extractor;
			
		} catch (Exception e) {
			throw new Exception("Impossibile creare un'istanza per il file Extractor");
		}
	}
	
	

}
