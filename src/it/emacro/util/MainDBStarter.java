package it.emacro.util;

import java.io.File;
import java.sql.SQLException;

public class MainDBStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String DB_DIR   = "DB";

		String[] dbArgs = {"-tcp", "-tcpPort", "9919", "-web", "-webPort", "8083", "-baseDir", DB_DIR};
		String message = "\nPosizione files del database: " + new File(DB_DIR).getAbsolutePath();
		try {
			org.h2.tools.Server.main(dbArgs);
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
