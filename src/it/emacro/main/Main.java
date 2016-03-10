/**
 * 
 */
package it.emacro.main;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.emacro.services.Application;
import it.emacro.services.ApplicationSettings;

/**
 * @author Emacro
 * 
 */
public class Main {
	
//	@Autowired
//	private ApplicationSettings settings;
	
	
	public Main(boolean startDownloadExtractionsFile, boolean startDbLoader, boolean setSystemLookAndFeel) {

		try {
			
			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
			ApplicationSettings settings = (ApplicationSettings) context.getBean("settings");
			
			// settings.applicationRoot = getDevelopingRootPath();
			settings.applicationRoot = getRootPath();
			settings.startDownloadExtractionsFile = startDownloadExtractionsFile;
			settings.startDbLoader = startDbLoader;
			settings.setSystemLookAndFeel = setSystemLookAndFeel;
			Application.getInstance().start(settings);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
			
			boolean startDownloadExtractionsFile = new Boolean(args[0]);
			boolean startDbLoader = new Boolean(args[1]);
			boolean setSystemLookAndFeel = new Boolean(args[2]);
			new Main(startDownloadExtractionsFile, startDbLoader, setSystemLookAndFeel);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String getRootPath(){

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
	
	private String getDevelopingRootPath(){
        // lo script che lo lancia DEVE ESSERE in lotto/WebContent/WEB-INF/script
		// altrimenti l'applicazione da' un errore all'apertura
		return "D:/EC_WORK_ROOT/EC_PROGETTI/lotto_amico_20101218/WebContent/";
	}

}
