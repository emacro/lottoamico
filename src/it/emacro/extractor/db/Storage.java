package it.emacro.extractor.db;

import it.emacro.extractor.util.PropertyLoader;
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;

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

	private Connection conn;
	private PreparedStatement psExtraction;
	private PreparedStatement psExtract;
	private PreparedStatement psNumbers;
	private PreparedStatement psRuote;
	private PreparedStatement psDates;
	private PreparedStatement psExtractionCount;

	private Properties properties;


	private List<Ruota> ruoteList = new ArrayList<Ruota>();
	private List<String> datesList = new ArrayList<String>();


	public Storage() {
		super();
		if (conn == null) {
			try {
				String path = ApplicationData.getInstance().getApplicationRoot() + "config/queries.properties";
				properties = PropertyLoader.getPropertiesOrEmpty(path);
				conn = ConnectionPool.getInstance().getConnection();

				psExtraction = conn.prepareStatement(getQuery("select.extraction"));
				psExtract = conn.prepareStatement(getQuery("select.extract"));
				psNumbers = conn.prepareStatement(getQuery("select.numbers"));
				psRuote = conn.prepareStatement(getQuery("sel.all.ruote"));
				psDates = conn.prepareStatement(getQuery("sel.dates.extractions"));
				//				psAllExtractions = conn.prepareStatement(getQuery("sel.all.extractions"));
				psExtractionCount = conn.prepareStatement(getQuery("extractions.count"));

			} catch (Exception e) {
				ConnectionPool.getInstance().closeConnection(conn);
				Log.print(e);
			} 
		}
	}

	public synchronized String[] getAllExtractionDates() {

		if (datesList.isEmpty()) {
			ResultSet rs = null;
			try {
				rs = psDates.executeQuery();
				while (rs.next()) {
					datesList.add(rs.getString(1));
				}
			} catch (Exception e) {
				Log.print(e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						Log.print(e);
					}
				}
			}
		}
		return datesList.toArray(new String[datesList.size()]);
	}

	public synchronized Ruota[] getRuote() {

		ResultSet rs = null;
		Ruota ruota;
		
		List<Ruota> res;

		if (ruoteList.isEmpty()) {
			try {

				rs = psRuote.executeQuery();

				while (rs.next()) {
					ruota = new Ruota(rs.getInt(1),rs.getString(2));
					ruoteList.add(ruota);
				}
			} catch (Exception e) {
				Log.print(e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						Log.print(e);
					}
				}
			}
			return ruoteList.toArray(new Ruota[ruoteList.size()]);
		}else{
			res = new ArrayList<Ruota>();
			for (Ruota r : ruoteList) {
				res.add(new Ruota(r.getId(), r.getName()));
			}
			return res.toArray(new Ruota[res.size()]);
		}
	}

	@Deprecated
	public synchronized List<String[]> getExtractionByDate(String date) {
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
			Log.print(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					Log.print(e);
				}
			}

			ConnectionPool.getInstance().closePreparedStatement(ps);
			ConnectionPool.getInstance().closeConnection(conn);
		}
		return list;
	}

	// --- new methods

	public synchronized int getNumberOfExtractions() {
		ResultSet rs = null;
		int res = 0;

		try {
			rs = psExtractionCount.executeQuery();
			rs.next();
			res = rs.getInt(1);

		} catch (Exception e) {
			Log.print(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					Log.print(e);
				}
			}
		}
		return res;
	}

	public synchronized Extraction getExtraction(String date) {
		ResultSet rs = null;
		Extraction extraction = null;

//		long now = System.currentTimeMillis();
//		long now2 = System.currentTimeMillis();

		try {

			psExtraction.setString(1, date);
			rs = psExtraction.executeQuery();
			
//			Log.println("query psExtraction eseguita in: " +  ((System.currentTimeMillis() - now) / 1000) + " seconds." );
//			now = System.currentTimeMillis();
			
			rs.next();
			extraction = new Extraction(rs.getInt(1), rs.getString(2), rs.getInt(3));
			Ruota[] ruote = getRuote();
			extraction.setRuote(ruote);
			Extracts[] extracts = new Extracts[11];
			psExtract.setInt(1, extraction.getId());
			rs = psExtract.executeQuery();
			
//			Log.println("query psExtract eseguita in: " +  ((System.currentTimeMillis() - now) / 1000) + " seconds." );
//			now = System.currentTimeMillis();
			
			
			int ruotaNumber = 0;
			while (rs.next()) {
				extracts[ruotaNumber] = new Extracts(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				ruote[ruotaNumber].setExtracts(extracts[ruotaNumber]);
				ruotaNumber++;
			}

			for (Extracts extr : extracts) {
				psNumbers.setInt(1, extr.getId());
				rs = psNumbers.executeQuery();
				
//				Log.println("query psNumbers eseguita in: " +  ((System.currentTimeMillis() - now) / 1000) + " seconds." );
//				now = System.currentTimeMillis();
				
				
				Number[] numbers = new Number[5];
				int idx = 0;
				while (rs.next()) {
					numbers[idx++] = new Number(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				}
				extr.setNumbers(numbers);
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					Log.print(e);
				}
			}
//			Log.println("Extraction found in DB in " + ((System.currentTimeMillis() - now2) / 1000) + " seconds.");
		} catch (Exception e) {
			Log.print(e);
		}

		return extraction;
	}

	private String getQuery(String s) throws Exception {
		String res = properties.getProperty(s);
		return res;
	}

}
