/**
 * 
 */
package it.emacro.extractor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emc
 * 
 */
public class FileUtils {

	/**
	 * 
	 */
	private FileUtils() {

	}

	public static void writeFile(File file, List<String> lines) {
		PrintWriter out = null;
		FileWriter outFile = null;

		try {
			outFile = new FileWriter(file);
			out = new PrintWriter(outFile);

			// Also could be written as follows on one line
			// Printwriter out = new PrintWriter(new FileWriter(args[0]));

			// Write text to file
			for (String line : lines) {
				out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception ignore) {
			}
			try {
				outFile.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static List<String> readFile(File file) {
		List<String> list = new ArrayList<String>();
		BufferedReader bufRead = null;
		FileReader input = null;

		try {

			/*
			 * Sets up a file reader to read the file passed on the command line
			 * one character at a time
			 */
			if(file.exists()){
				input = new FileReader(file);
			}else{
				return null;
			}

			/*
			 * Filter FileReader through a Buffered read to read a line at a
			 * time
			 */
			bufRead = new BufferedReader(input);

			String line; // String that holds current file line

			// Read first line
			line = bufRead.readLine();

			// Read through file one line at time. Print line # and line
			while (line != null) {
				list.add(line);
				line = bufRead.readLine();
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			/*
			 * If no file was passed on the command line, this expception is
			 * generated. A message indicating how to the class should be called
			 * is displayed
			 */
			Utils.println("Usage: java ReadFile filename\n");

		} catch (IOException e) {
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		} finally {
			try {
				bufRead.close();
			} catch (Exception ignore) {
			}
			try {
				input.close();
			} catch (Exception ignore) {
			}
		}

		return list;
	}

	public static List<File> getFiles(String dirname, final String extension) {
		List<File> files = new ArrayList<File>();
		File f1 = new File(dirname); 
		FilenameFilter only = new FilenameFilter() { 
			public boolean accept(File dir, String name) { 
				return name.endsWith(extension); 
			} 
		}; 
		String s[] = f1.list(only); 
		for (int i=0; i < s.length; i++) { 
			files.add(new File(dirname + s[i])); 
		}
		return files;
	}
}
