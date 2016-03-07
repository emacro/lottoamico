/**
 * 
 */
package it.emacro.main;

import it.emacro.services.Application;

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
			
			String applicationRoot = getRootPath();
			boolean startDownloadExtractionsFile = new Boolean(args[0]);
			boolean startDbLoader = new Boolean(args[1]);
			boolean setSystemLookAndFeel = new Boolean(args[2]);
			
			Application.getInstance().start(
					applicationRoot, 
					startDownloadExtractionsFile, 
					startDbLoader, 
					setSystemLookAndFeel
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getRootPath(){
        // lo script che lo lancia DEVE ESSERE in lotto/WebContent/WEB-INF/script
		// altrimenti l'applicazione da' un errore all'apertura
		File here = new File(".");
		String rootName = "WebContent";
		int idx = here.getAbsolutePath().indexOf(rootName) + rootName.length();
		return here.getAbsolutePath().substring(0, idx + 1);
	}

}
