/**
 * 
 */
package it.emacro.extractor;

import it.emacro.log.Log;

/**
 * @author Emc
 *
 */
public class Main {
	
	public static final String INPUT_FILE_FOLDER = "../extractions/";
	//public static final String INPUT_FILE_NAME = "Estratti.txt";
	// public static final String OUTPUT_FILE_PATH = 
	/**
	 * 
	 */
	public Main() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length==0){
				throw new Exception("Missing input file name");
			}
			String fileName = args[0];
			
//			new Extractor().extract("D:/workspace/lotto_extractor/extractions/20070707.txt",
//									"D:/workspace/lotto_extractor/extractions/out_20070707.txt");
			
			new Extractor().extract(INPUT_FILE_FOLDER + fileName,
									INPUT_FILE_FOLDER + "out_" + fileName);
		} catch (Exception e) {
			Log.print(e);
		}
	}

}
