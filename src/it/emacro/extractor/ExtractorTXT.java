/**
 * 
 */
package it.emacro.extractor;

import it.emacro.extractor.db.ConnectionPool;
import it.emacro.extractor.db.QueryCreator;
import it.emacro.extractor.util.FileUtils;
//import it.emacro.extractor.util.Utils;
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;
import it.emacro.util.Messenger;
import it.emacro.util.Utils;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emc
 * 
 */
public class ExtractorTXT implements Extractor {

	private String anno = "1900";

	public ExtractorTXT() {
		super();
	}

	/* (non-Javadoc)
	 * @see it.emacro.extractor.Extractor#extract(java.lang.String)
	 */
	public void extract(String source) throws Exception {
		extract(new File(source), null);
	}

	/* (non-Javadoc)
	 * @see it.emacro.extractor.Extractor#extract(java.io.File)
	 */
	public void extract(File source) throws Exception {
		extract(source, null);
	}

	/* (non-Javadoc)
	 * @see it.emacro.extractor.Extractor#extract(java.lang.String, java.lang.String)
	 */
	public void extract(String source, String destination) throws Exception {
		extract(new File(source), new File(destination));
	}

	/* (non-Javadoc)
	 * @see it.emacro.extractor.Extractor#extract(java.io.File, java.io.File)
	 */
	public void extract(File source, File destination) throws Exception {
		List<String> unparsedLines = null;
		String[] parsedLines = null;
		String date = null, number;
		List<String> extrList;
		List<String> list = null;
		Connection conn = null;
		long queryCounter = 0;
		long time = System.currentTimeMillis();
		boolean writeInFile = destination != null;

		try {

			if (writeInFile) {
				list = new ArrayList<String>();
			}

			conn = ConnectionPool.getInstance().getConnection();

			// all lines of file
			unparsedLines = FileUtils.readFile(source);

			// no file found
			if (unparsedLines == null) {
//				Log.println("no file found or no data in " + source.getName());
				Log.println(Messenger.getInstance().getMessage("no.file.found.or.no.data.in") + source.getName());
				return;
			}

			for (String line : unparsedLines) {
				extrList = new ArrayList<String>();

				if (line == null || line.isEmpty()) {
					continue;
				}

				parsedLines = splitAndParseLine(line);

				date = parsedLines[0];
				number = parsedLines[1];
				for (int ii = 2; ii < 57; ii++) {

					extrList.add(parsedLines[ii]);
				}

				String[] queries = QueryCreator.createInsertQueries(date,
						number, extrList, conn);

				if (queries == null || queries.length == 0)
					continue;

				QueryCreator.insertQueriesInDB(queries, conn);

				queryCounter += queries.length;

				if (writeInFile) {
					for (String query : queries) {
						list.add(query);
					}
				}

			}

			ApplicationData.getInstance().setLastExtractionDate(date);

//			Log.println("\nInserted " + (queryCounter / 67) + " new extraction(s) in database");
			
			int inserted = (int) queryCounter / 67;
			
			Log.println("");
			
			if(inserted != 1){
				Log.print(Messenger.getInstance().getMessage("inserted.2"));
				Log.print(String.valueOf(inserted));
				Log.println(Messenger.getInstance().getMessage("new.extractions.in.database"));
			}else{
				Log.print(Messenger.getInstance().getMessage("inserted"));
				Log.print(String.valueOf(inserted));
				Log.println(Messenger.getInstance().getMessage("new.extraction.in.database"));
			}
			
			Log.print(Messenger.getInstance().getMessage("elapsed.time"));

//			Log.println("\nelapsed time: " + Utils.getTimeAsString(System .currentTimeMillis() - time));
			
			Log.println(Utils.getTimeAsString(System .currentTimeMillis() - time));

		} finally {
			ConnectionPool.getInstance().closeConnection(conn);
		}

		if (writeInFile) {
			if (list.size() > 0) {
				FileUtils.writeFile(destination, list);
			}
		}

//		Log.println("\nDB loading is termined ok");
		Log.println(Messenger.getInstance().getMessage("db.loading.is.termined.ok"));
	}

	private String[] splitAndParseLine(String line) {

		String[] result = line.split(" ");
		result[0] = parseDate(result[0]);

		if (!anno.equals(result[0].substring(0, 4))) {
			Log.print(Messenger.getInstance().getMessage("storing.year"));
			Log.println(result[0].substring(0, 4));
			anno = result[0].substring(0, 4);
		}

		return result;
	}

	private String parseDate(String date) {
		String year;
		String month;
		String day;

		day = date.substring(0, 2);
		month = date.substring(3, 5);
		year = date.substring(6);

		return new StringBuffer(year).append("-").append(month).append("-")
				.append(day).toString();
	}

}
