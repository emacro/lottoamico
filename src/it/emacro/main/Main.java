/**
 * 
 */
package it.emacro.main;

import it.emacro.services.Application;
import it.emacro.services.ApplicationSettings;

import java.io.File;

/**
 * @author Emacro
 * 
 */
public class Main {
	
	// could launch with: true true true

	/**
	 * @param args
	 */
	public static void main(String...args) {
		
		try {
			int missingPars = 3 - args.length;
			if( missingPars > 0 ){
				System.out.println("Missing " + missingPars + " parameter(s).\n");
				return;
			}
			
			ApplicationSettings settings = new ApplicationSettings();
			// settings.applicationRoot = getDevelopingRootPath();
			settings.applicationRoot = getRootPath();
			settings.startDownloadExtractionsFile = new Boolean(args[0]);
			settings.startDbLoader = new Boolean(args[1]);
			settings.setSystemLookAndFeel = new Boolean(args[2]);
			Application.getInstance().start(settings);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getRootPath(){

		String developPath = System.getProperty("developPath", null);
		if(developPath != null){
			return developPath;
		}
		else{
			// lo script che lo lancia DEVE ESSERE in lotto/WebContent/WEB-INF/script
			// altrimenti l'applicazione da' un errore all'apertura
			File here = new File(".");
			String rootName = "WebContent";
			int idx = here.getAbsolutePath().indexOf(rootName) + rootName.length();
			return here.getAbsolutePath().substring(0, idx + 1);
		}
	}
	
	private static String getDevelopingRootPath(){
        // lo script che lo lancia DEVE ESSERE in lotto/WebContent/WEB-INF/script
		// altrimenti l'applicazione da' un errore all'apertura
		return "D:/EC_WORK_ROOT/EC_PROGETTI/lotto_amico_20101218/WebContent/";
	}

}
