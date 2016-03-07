package it.emacro.util;

import it.emacro.extractor.util.FileUtils;
import it.emacro.log.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Make a file in path passed as first parameter of the main method and fill it 
 * using the other parameters first parameter is the first line and so on
 * @author Emacro
 *
 */
public class MakeFile {

	public static void main(String...args) {
		MakeFile makeFile = new MakeFile();
		if(args.length < 2) {
			makeFile.showErrorMessage();
		}else{
			makeFile.makeFile(args);
		}
	}
	
	// --- private methods
	
	/**
	 * Shows error message
	 */
	private void showErrorMessage() {
		System.out.println("Error: missing parameter(s)");
		System.out.println("\nYou must insert these perameters");
		System.out.println("1) the file path, for example C:/myfolder/myfile.txt (mandatory)");
		System.out.println("2) content of first line (mandatory)");
		System.out.println("3) content of second line line (not mandatory)");
		System.out.println("etc...");
	}
	
	/**
	 * Make a file in path passed as first parameter and content as lines for 
	 * each other passed parameter
	 * @param args first is the file path others as content as lines
	 */
	private void makeFile(String...args) {
		List<String> lines = new ArrayList<String>();
		String path = args[0];
		File file = new File(path);
		try {
			file.createNewFile();
			int index = 0;
			for (String line : args) {
				if (index == 0) {
					index++;
					continue;
				}
				lines.add(line);
				index++;
			}
			FileUtils.writeFile(file, lines);
		} catch (IOException e) {
			Log.print(e);
		}
	}
}