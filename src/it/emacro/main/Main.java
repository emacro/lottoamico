/**
 * 
 */
package it.emacro.main;

import it.emacro.exceptions.SystemVeriableNotSetException;
import it.emacro.log.Log;
import it.emacro.services.Application;

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
			
			boolean startDownloadExtractionsFile = Boolean.parseBoolean(args[0]);
			boolean startDbLoader = Boolean.parseBoolean(args[1]);
			boolean setSystemLookAndFeel = Boolean.parseBoolean(args[2]);
			
			Application.getInstance().start(
					getRootPath(), 
					startDownloadExtractionsFile, 
					startDbLoader, 
					setSystemLookAndFeel
			);
			
		} catch (Exception e) {
			Log.print(e);
		}
	}

	private static String getRootPath() throws SystemVeriableNotSetException {
        // caller has to set the 'rootpath' as system variable
		String res = null;
		String rootpath = System.getProperty("rootpath");
		if (rootpath != null && !rootpath.isEmpty()) {
			res = rootpath;
		} else {
			String msg = "The 'rootpath' system variable is not set. (ex.: -Drootpath=C:/programs/ThisApplication/)";
			throw new SystemVeriableNotSetException(msg);
		}
		return res;
	}

}
