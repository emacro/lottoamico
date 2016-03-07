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
		String webRoot = ApplicationData.getInstance().getWebroot();
		String messagesDir = "WEB-INF/config/messages/";
		String extension = ".properties";
		String path = webRoot + messagesDir + language + extension;
		properties = PropertyLoader.getPropertiesOrEmpty(path);
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
