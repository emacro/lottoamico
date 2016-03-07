package it.emacro.util;

import it.emacro.extractor.Extractor;
import it.emacro.extractor.ExtractorCSV;
import it.emacro.extractor.ExtractorTXT;

public class ExtractorProvider {

	public static synchronized Extractor getExtractor(String filePath) throws Exception {
		Extractor extractor;
		if (filePath.endsWith(".csv")) {
			extractor = new ExtractorCSV();
		}else if (filePath.endsWith(".txt")) {
			extractor = new ExtractorTXT();
		}else{
			throw new Exception("Impossibile creare un'istanza per il file Extractor");
		}
		return extractor;
	}
	
	

}
