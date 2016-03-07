/**
 * 
 */
package it.emacro.log;

import it.emacro.util.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 * @author Emacro
 *
 */
public class LogFile {
	
	private static LogFile instance;
	private File file;
	private PrintWriter out;
	private FileWriter outFile;
	

	private LogFile() {
		super();
		createLog();
	}
	
	public static LogFile getInstance() {
		if (instance == null) {
			synchronized (LogFile.class) {
				if (instance == null) {
					instance = new LogFile();
				}
			}
		}
		return instance;
	}
	
	public void println(String content) {
		if(out!=null){
			out.println(content);
		}
	}
	
	public void print(String content) {
		if(out!=null){
			out.print(content);
		}
	}
	
	public void print(Throwable t) {
		if(out!=null){
			t.printStackTrace(out);
		}
	}
	
	public void close() {
		println("");
		println("");
		println("*** application closed at: " + new Date());
		try {
			out.close();
		} catch (Exception ignore) {
		}
		try {
			outFile.close();
		} catch (Exception ignore) {
		}
	}
	
	private void createLog() {
		
		try {
			File dir = new File(Utils.LOG_FILE_FOLDER);
			if(!dir.exists()){
				dir.mkdir();
			}
			
			file = new File(dir, Utils.LOG_FILE_NAME);
			if(!file.exists()){
				file.createNewFile();
			}
			
			outFile = new FileWriter(file);
			out = new PrintWriter(outFile);
			
			println("*** application started at: " + new Date());
			println("");
			println("");
		} catch (IOException e) {
			System.out.println("Impossibile creare il file di log");
			Log.print(e);
		}
		
	}
	
}
