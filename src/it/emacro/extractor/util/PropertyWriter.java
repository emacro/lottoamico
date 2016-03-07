/**
 * 
 */
package it.emacro.extractor.util;

import it.emacro.log.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Emc
 * 
 */
public class PropertyWriter {

	// public static void setProperty(String path, String key, String value)
	// throws Exception {
	// Properties properties;
	// FileInputStream fin = null;
	// FileOutputStream fos = null;
	// try {
	// fin = new FileInputStream(path);
	// fos = new FileOutputStream(path);
	// properties = new Properties();
	// properties.load(fin);
	// properties.setProperty(key,value);
	// properties.store(fos,"Application properties");
	// } finally {
	// if (fin != null) {
	// try {
	// fin.close();
	// fos.close();
	// } catch (IOException e) {
	// Log.print(e);
	// }
	// }
	// }
	// }

	public static void setProperties(String path, Properties properties)
			throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			properties.store(fos, "Application properties");
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					Log.print(e);
				}
			}
		}
	}

}
