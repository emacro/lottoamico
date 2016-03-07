/**
 * 
 */
package it.emacro.util;

import it.emacro.extractor.Extractor;
import it.emacro.extractor.ExtractorTXT;
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @author Emc
 * 
 */
public class ExtractionsDbLoader {

	/**
	 * 
	 */
	public ExtractionsDbLoader() {
		super();
	}

	public void loadExtractions() {
		File in = null, out;
		String fName = ApplicationData.getInstance().getExtractionsFileName();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm");
		String today = formatter.format(System.currentTimeMillis());

		try {
			String filePath = ApplicationData.getInstance().getExtractionsFilePath();
			in = new File(filePath );
			out = new File(Utils.INPUT_FILE_FOLDER + "out/" + "out_" + today + ".txt");
			
			Extractor extractor = ExtractorProvider.getExtractor(filePath);
			extractor.extract(in, out);

			if (in.delete()) {
//				Log.println("\ndeleted: " + fName +"\n");
				Log.println(Messenger.getInstance().getMessage("deleted") + fName);
				Log.println("");
				trashFiles(Utils.INPUT_FILE_FOLDER, "exe");
			}
			
		} catch (Exception e) {
			in.renameTo(new File(fName + "." + today + ".ERR"));
			Log.println("\ncause errors: " + fName
					+ " has been renamed to " + fName + "." + today + ".ERR\n");
			Log.println("Exception in extraction loading");
			e.printStackTrace();
		}
	}

	private void trashFiles(String directory, String extension) {
		File dir = new File(directory);
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.getName().toLowerCase().endsWith(
					"." + extension.toLowerCase())) {
				f.delete();
			}
		}
	}

}
