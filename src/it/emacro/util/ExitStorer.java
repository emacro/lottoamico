/**
 * 
 */
package it.emacro.util;

import it.emacro.extractor.util.PropertyLoader;
import it.emacro.extractor.util.PropertyWriter;
import it.emacro.log.Log;
import it.emacro.services.Application;
import it.emacro.services.ApplicationData;

import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * @author Emc
 *
 */
public class ExitStorer {
	
	private static ExitStorer instance;

    private ExitStorer() {
        super();
    }

    public static ExitStorer getInstance() {
        if (instance == null) {
            synchronized (ExitStorer.class) {
                if (instance == null) {
                    instance = new ExitStorer();
                }
            }
        }
        return instance;
    }
	
	private Window window;
	private String path = ApplicationData.getInstance().getWebroot() + "WEB-INF/config/application.properties";
	private Properties properties = PropertyLoader.getPropertiesOrEmpty(path);

	public void store() {
		
		if(window != null){
			storeWindowDimension();
		}
		
		setLastApplicationUse();
		setForceExtractionReading();
		
		storeAll();
	}
	
	public void storeAndExit() {
		store();
		Application.getInstance().stop();
	}
	
// --------------- private methods
	
	private void storeWindowDimension() {
		
		properties.setProperty("main.window.width", String.valueOf(window.getWidth()));
		properties.setProperty("main.window.height", String.valueOf(window.getHeight()));
		
		try {
			PropertyWriter.setProperties(path, properties);

		} catch (Exception e) {
			Log.println("ExitStorer: cannot store aplication properties");
//			Log.println(Messenger.getInstance().getMessage(""));
			e.printStackTrace();
		}
	}
	
	private void setLastApplicationUse() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, d MMMMM yyyy HH.mm");
		Date date = Calendar.getInstance().getTime();
		properties.setProperty("last.appllication.use", dateFormat.format(date).replace('ì','i'));
		
	}
	
	private void setForceExtractionReading() {
		
		boolean force = ApplicationData.getInstance().isForceExtractionReading();
		properties.setProperty("force.extraction.read", String.valueOf(force));
		
	}
	
	public void setWindow(Window window) {
		this.window = window;
	}
	
	// this must be call always
	public void storeAll() {
		try {
			PropertyWriter.setProperties(path, properties);

		} catch (Exception e) {
			Log.println("ExitStorer: cannot store aplication properties");
//			Log.println(Messenger.getInstance().getMessage(""));
			e.printStackTrace();
		}
	}

}
