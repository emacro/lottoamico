/**
 * 
 */
package it.emacro.main;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import it.emacro.services.Application;
import it.emacro.services.ApplicationSettings;
import it.emacro.test.ApplicationContextAwareTEST;

/**
 * @author Emacro
 * 
 */
public class Main {

	@Autowired
	private ApplicationSettings applicationSettings;
	
	@Autowired
	private ApplicationContextAwareTEST  applicationContextAwareTEST;
	
	/***
	 * 
	 * on develop environment:
	 * 
	 * 1) Virtual Machine arguments: 
	 *    -Xmx500M -Xms256M 
	 *    -DdevelopPath=[application_path]/WebContent/ (example: C:/Users/Emanuele/Documents/lottoamico/WebContent/)
	 *    
	 * 2) Application arguments:
	 *    true (DOWNLOAD_EXTRACTIONS_FILE)
	 *    true (DB_LOADER)
	 *    false (SYSTEM_LOOK_AND_FEEL)
	 * 
	 */

	/**
	 * you could call this main method using, for example, 
	 * parameters: true true false
	 * 
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
			new Main().execute(startDownloadExtractionsFile, startDbLoader, setSystemLookAndFeel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute(boolean startDownloadExtractionsFile, boolean startDbLoader, boolean setSystemLookAndFeel) {

		
		try {
			
			
			if(applicationContextAwareTEST == null) {
				
				System.out.println("applicationContextAwareTEST is NULL");
				System.exit(-1);
			}
			
			System.out.println("applicationContextAwareTEST is GOOD");
			System.exit(0);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		try {

//			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
//			ApplicationSettings settings = (ApplicationSettings) context.getBean("settings");

			// settings.applicationRoot = getDevelopingRootPath();
			applicationSettings.applicationRoot = getRootPath();
			applicationSettings.startDownloadExtractionsFile = startDownloadExtractionsFile;
			applicationSettings.startDbLoader = startDbLoader;
			applicationSettings.setSystemLookAndFeel = setSystemLookAndFeel;
			Application.getInstance().start(applicationSettings);

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
	
	public void setApplicationSettings(ApplicationSettings applicationSettings) {
		this.applicationSettings = applicationSettings;
	}
	
	public ApplicationSettings getApplicationSettings() {
		return applicationSettings;
	}

	private String getDevelopingRootPath(){
		// lo script che lo lancia DEVE ESSERE in lotto/WebContent/WEB-INF/script
		// altrimenti l'applicazione da' un errore all'apertura
		return "D:/EC_WORK_ROOT/EC_PROGETTI/lotto_amico_20101218/WebContent/";
	}
	
	public void setApplicationContextAwareTEST(ApplicationContextAwareTEST applicationContextAwareTEST) {
		this.applicationContextAwareTEST = applicationContextAwareTEST;
	}

}
