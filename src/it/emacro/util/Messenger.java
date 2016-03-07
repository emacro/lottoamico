/**
 * 
 */
package it.emacro.util;

import java.util.Properties;

import it.emacro.extractor.util.PropertyLoader;
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;

/**
 * Recupera i messaggi dal .properties caricato.
 * @author Emacro
 *
 */
public class Messenger {

	private static final String ERROR_MESSAGE = "ERROR: application is unable to find the language file.";
	private static Messenger instance;
	private Properties properties;
	
	private Messenger() {
		super();
		String language = ApplicationData.getInstance().getLanguage();
		load(language);
		if (properties == null || properties.isEmpty()) {
			Log.println(ERROR_MESSAGE);
			load("english");
		}
	}
	
	private void load(String language) {
		String appRoot = ApplicationData.getInstance().getApplicationRoot();
		StringBuffer path = new StringBuffer(appRoot);
		path.append("config/messages/");
		path.append(language);
		path.append(".properties");
		properties = PropertyLoader.getPropertiesOrEmpty(path.toString());
	}
	
	public static Messenger getInstance() {
		if (instance == null) {
			synchronized (Messenger.class) {
				if (instance == null) {
					instance = new Messenger();
				}
			}
		}
		return instance;
	}
	
	public String getMessage(String str){
		return properties.getProperty(str) ;
	}

}
