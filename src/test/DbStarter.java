package test;

import java.sql.SQLException;

import org.h2.tools.Server;

public class DbStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String dbDir = args[0];
		try {
			Server.main(new String[]{"-tcp", "-tcpPort", "9919", "-web", "-webPort", "8083", "-baseDir", dbDir });
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
