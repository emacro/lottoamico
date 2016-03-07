/**
 * 
 */
package it.emacro.extractor.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emc
 * 
 */
public class QueryCreator {

	private QueryCreator() {

	}

	public static String[] createInsertQueries(String date, String number,
			List<String> extracts, Connection conn) throws Exception {
		List<String> list = new ArrayList<String>();
		StringBuffer sb;
		
		ResultSet rs = conn.createStatement().executeQuery("select count(date) from extractions where date='" + date + "';");
		rs.next();
		
		int d = rs.getInt(1);
		
		if(d>0){
//			Log.println("extraction: " + date + " already in DB.");
			return null;
		}
		
		if (extracts.size() < 55) {
			throw new Exception("Insufficient number of extracts: "
					+ extracts.size() + "/55");
		}

		sb = new StringBuffer("insert into extractions (date,number) values (");
		sb.append("date'").append(date).append("'").append(",").append(number)
				.append(");");

		rs = conn.createStatement().executeQuery("select max(id) from extractions");
		
		if(!rs.next()){
			throw new Exception("No elements in table extractions");
		}
			
		int extractionId = rs.getInt(1);
		
//		if(extractionId==0){
//			extractionId=1;
//			insertQueryInDB(sb.toString(),conn);// the first
//		}else{
//			list.add(sb.toString());
//		}
		
		list.add(sb.toString());
		
		extractionId++;
		
		rs = conn.createStatement().executeQuery("select max(id) from extracts");
		
		if(!rs.next()){
			throw new Exception("No elements in table extracts");
		}

		int extractsId = rs.getInt(1); 
		
		extractsId++;
		
		int ruota = 1;
		int position = 1;

		for (String extract : extracts) {

			if (position > 5) {
				position = 1;
				extractsId++;
			}
			
			if (position==1){
				sb = new StringBuffer(
				"insert into extracts (ruota, extraction) values (");
				sb.append(ruota++).append(",").append(extractionId).append(");");

//				if(extractsId==0){
//					extractsId=1;
//					insertQueryInDB(sb.toString(),conn);
//				}else{
//					list.add(sb.toString());
//				}
				list.add(sb.toString());
			}

			sb = new StringBuffer(
					"insert into numbers (extract,position,number) values (");
			sb.append(extractsId).append(",").append(position++).append(",")
					.append(extract).append(");");

			list.add(sb.toString());

		}

		return list.toArray(new String[list.size()]);
	}
	
	public static int insertQueriesInDB(String[] queries, Connection conn) throws Exception {
		int count=0;
		for(String query: queries){
			conn.createStatement().executeUpdate(query);
		}
		return count;
	}
	
	public static int insertQueryInDB(String query, Connection conn) throws Exception {
		int count=0;
		conn.createStatement().executeUpdate(query);
		return count;
	}

}
