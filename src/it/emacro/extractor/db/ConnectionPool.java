/*
 * i-service 5
 *
 * Copyright (c) 1999-2007 Ingenium Technology Srl
 * All rights reserved.
 *
 * Proprietary and confidential of Ingenium Technology Srl
 * Use is subject to license terms.
 */
package it.emacro.extractor.db;

// import it.ingeniumtech.forumnet.util.ApplicationData;
// import it.ingeniumtech.forumnet.util.LogManager;
// import it.ingeniumtech.forumnet.util.PropertyLoader;

import it.emacro.extractor.util.PropertyLoader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 
 * @author Marco Ghezzi
 * @since 5.6
 */
public class ConnectionPool {

	private static ConnectionPool engine;

	private Properties properties;

	private BasicDataSource dataSource;

	private int sqlStateType;

	private static final String DRIVER = "database.driver";

	private static final String USERNAME = "database.user";

	private static final String PASSWORD = "database.passwd";

	private static final String URL = "database.url";

	private ConnectionPool() {
		super();
	}

	public static ConnectionPool getInstance() {
		if (engine == null) {
			synchronized (ConnectionPool.class) {
				if (engine == null) {
					engine = new ConnectionPool();
				}
			}
		}
		return engine;
	}

	private void setupDataSource() throws SQLException {
		// String path = "WebContent/WEB-INF/config/database.properties";
		String path = it.emacro.services.ApplicationData.getInstance().getApplicationRoot() + "config/database.properties";
		// String path =
		// "D:\\workspace\\lotto\\WebContent\\WEB-INF\\config\\database.properties";
		// properties = PropertyLoader.getPropertiesOrEmpty(path);

		// path = "D:\\workspace\\lotto\\WebContent\\WEB-INF\\config\\database.properties";
		properties = PropertyLoader.getPropertiesOrEmpty(path);

		String driver = properties.getProperty(DRIVER);
		String url = properties.getProperty(URL);
		String username = properties.getProperty(USERNAME);
		String password = properties.getProperty(PASSWORD);
		// log.debug("Database username: " + username);
		// log.debug("Database password: " + password);
		// log.debug("Database driver: " + driver);
		// log.debug("Database url: " + url);

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Can not load driver");
		}

		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
	}

	// private void dumpDBMDInfo() {
	// Logger log = LogManager.getLogger();
	// log.info("Database Metadata Information...");
	// log.info("SqlStateType: " + sqlStateType);
	// log.info("isSQL99: " + isSQL99());
	// log.info("isXOPEN: " + isXOPEN());
	// }

	public boolean isXOPEN() {
		return (sqlStateType == DatabaseMetaData.sqlStateXOpen);
	}

	public boolean isSQL99() {
		return (sqlStateType == DatabaseMetaData.sqlStateSQL99);
	}

	public void inspect() throws SQLException {
		Connection conn = getConnection();
		try {
			DatabaseMetaData dmd = conn.getMetaData();
			sqlStateType = dmd.getSQLStateType();
			// dumpDBMDInfo();
		} finally {
			closeConnection(conn);
		}
	}

	public Connection getConnection() throws SQLException {
		if (dataSource == null) {
			setupDataSource();
		}
		return dataSource.getConnection();
	}

	protected void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException ignore) {
		}
	}

	public void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.close();
		} catch (SQLException ignore) {
		}
	}

	public void closePreparedStatement(PreparedStatement ps) {
		if (ps == null) {
			return;
		}
		try {
			ps.close();
		} catch (SQLException ignore) {
		}
	}

}
