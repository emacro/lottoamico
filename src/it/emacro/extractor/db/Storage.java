/**
 * 
 */
package it.emacro.extractor.db;

import it.emacro.extractor.util.PropertyLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Emc
 * 
 */
public class Storage {

	/**
	 * 
	 */
	public Storage() {
		super();
	}

	public String[] getAllExtractionDates() {
		List<String> dates = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = ConnectionPool.getInstance().getConnection();

			ps = conn.prepareStatement(getQuery("sel.dates.extractions"));
			rs = ps.executeQuery();

			while (rs.next()) {
				dates.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}

		return dates.toArray(new String[dates.size()]);
	}

	public Ruota[] getRuote() {
		List<Ruota> list = new ArrayList<Ruota>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Ruota ruota;

		try {

			conn = ConnectionPool.getInstance().getConnection();

			ps = conn.prepareStatement(getQuery("sel.all.ruote"));
			rs = ps.executeQuery();

			while (rs.next()) {
				ruota = new Ruota(rs.getInt(1),rs.getString(2));
				list.add(ruota);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}

		return list.toArray(new Ruota[list.size()]);
	}

	public List<String[]> getExtractionByDate(String date) {
		Connection conn = null;
		String query;
		PreparedStatement ps = null;
		List<String[]> list = new ArrayList<String[]>();
		ResultSet rs = null;
		List<String> s;
		try {

			conn = ConnectionPool.getInstance().getConnection();

			query = getQuery("sel.number.extraction");
			ps = conn.prepareStatement(query);
			ps.setString(1, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				s = new ArrayList<String>();
				s.add(rs.getString(1));
				s.add(rs.getString(2));
				s.add(rs.getString(3));
				s.add(rs.getString(4));
				s.add(rs.getString(5));
				list.add(s.toArray(new String[s.size()]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}
		return list;
	}

	public Extraction[] getExtractions() {

		List<Extraction> list = new ArrayList<Extraction>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Extraction extraction;

		try {

			conn = ConnectionPool.getInstance().getConnection();

			ps = conn.prepareStatement(getQuery("sel.all.extractions"));
			rs = ps.executeQuery();

			while (rs.next()) {
				extraction = new Extraction(rs.getInt(1),rs.getString(2),rs.getInt(3));
				list.add(extraction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}

		return list.toArray(new Extraction[list.size()]);
	
	}

	public Extracts[] getExtracts() {

		List<Extracts> list = new ArrayList<Extracts>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Extracts extract;

		try {

			conn = ConnectionPool.getInstance().getConnection();

			ps = conn.prepareStatement(getQuery("sel.all.extracts"));
			rs = ps.executeQuery();

			while (rs.next()) {
				extract = new Extracts(rs.getInt(1),rs.getInt(2),rs.getInt(3));
				list.add(extract);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}

		return list.toArray(new Extracts[list.size()]);
	
	}
	
	public Number[] getNumbers() {

		List<Number> list = new ArrayList<Number>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Number number;

		try {

			conn = ConnectionPool.getInstance().getConnection();

			ps = conn.prepareStatement(getQuery("sel.all.numbers"));
			rs = ps.executeQuery();

			while (rs.next()) {
				number = new Number(rs.getInt(1),rs.getInt(2),rs.getInt(3));
				list.add(number);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}

		return list.toArray(new Number[list.size()]);
	
	}

	private String getQuery(String s) throws Exception {
		String path = it.emacro.services.ApplicationData.getInstance()
				.getWebroot()
				+ "WEB-INF/config/queries.properties";
		// String path =
		// "D:/workspace/lotto/WebContent/WEB-INF/config/queries.properties";
		Properties properties = PropertyLoader.getPropertiesOrEmpty(path);
		String res = properties.getProperty(s);

		return res;
	}

}
