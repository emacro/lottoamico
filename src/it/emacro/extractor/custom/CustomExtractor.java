package it.emacro.extractor.custom;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.emacro.extractor.Extractor;
import it.emacro.extractor.db.ConnectionPool;
import it.emacro.extractor.db.QueryCreator;
import it.emacro.extractor.util.FileUtils;
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;
import it.emacro.util.Messenger;
import it.emacro.util.RuoteUtils;
import it.emacro.util.RuoteUtils.Ordinator;
import it.emacro.util.RuoteUtils.SimpleRuota;
import it.emacro.util.Utils;

public class CustomExtractor implements Extractor {

	private static Logger logger = Logger.getLogger(CustomExtractor.class.getSimpleName());
	private static final Map<String, String> MOTH_MAP = new HashMap<>(12);

	public CustomExtractor() {
		super();
		MOTH_MAP.put("gennaio", "01");
		MOTH_MAP.put("febbraio", "02");
		MOTH_MAP.put("marzo", "03");
		MOTH_MAP.put("aprile", "04");
		MOTH_MAP.put("maggio", "05");
		MOTH_MAP.put("giugno", "06");
		MOTH_MAP.put("luglio", "07");
		MOTH_MAP.put("agosto", "08");
		MOTH_MAP.put("settembre", "09");
		MOTH_MAP.put("ottobre", "10");
		MOTH_MAP.put("novembre", "11");
		MOTH_MAP.put("dicembre", "12");
	}
 
	public void extract(String source) throws Exception {
		extract(new File(source), null);
	}
 
	public void extract(File source) throws Exception {
		extract(source, null);
	}
 
	public void extract(String source, String destination) throws Exception {
		extract(new File(source), new File(destination));
	}
 
	public void extract(File source, File destination) throws Exception {
		List<String> unparsedLines = null;
		String date = null;
		List<String> extrList = null, list = null;
		Connection conn = null;
		Ordinator ordinator = null;
		long queryCounter = 0, time = System.currentTimeMillis();
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

			
			Map<String, Ordinator> packages = null;
			
			try {
				packages = getExtractionsAsPackages(unparsedLines);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Application has stopped the 'extrax action' due an error (maybe in file?)", e);
				throw new Exception();
			}
			
			int counter = 0;
			
			for (Entry<String, Ordinator> entry : packages.entrySet()) {
				
//				Log.print(Messenger.getInstance().getMessage("storing.year"));
				if( ((counter++)%100) == 0 ){
					Log.print("|");
				}
				
				date = entry.getKey();
				ordinator = entry.getValue();
				extrList = RuoteUtils.getNumbersFromOrdinator(ordinator);
			 
				String[] queries = QueryCreator.createInsertQueries(date, "0", extrList, conn);

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
 
	private Map<String, Ordinator> getExtractionsAsPackages(List<String> unparsedLines) throws Exception {
		
		Map<String, Ordinator> res = new LinkedHashMap<String, Ordinator>();
		SimpleRuota simpleRuota = null; Ordinator ordinator = null;
		String[] splitted; String date, ruota, first, second, third, fourth, fifth;
		
		for (String line : unparsedLines) {
			
			try {
				splitted = line.split("\\s");
				
				date = splitted[2] + "-" + convertMonthNameToNumber(splitted[1]) + "-" + splitted[0];
				
				List<String> ruote = new ArrayList<>();
				
				for (int ii = 3; ii < 13; ii++) {
					ruote.add(splitted[ii]);
				}
				
				for (int rr = 0; rr < ruote.size(); rr++) {
				 
					String[] _numbs = ruote.get(rr).split(".");

					first = _numbs[0];
					second = _numbs[1];
					third = _numbs[2];
					fourth = _numbs[3];
					fifth = _numbs[4];
					
					ruota = RuoteUtils.getNamesRuote().get(rr);
					
					simpleRuota = 
							RuoteUtils.getNewSimpleRuota(ruota, first, second, third, fourth, fifth);
					
				}

				if(!res.containsKey(date)){
					res.put(date, RuoteUtils.getNewOrdinator());
				}
				ordinator = res.get(date);
				ordinator.add(simpleRuota);

			} catch (Exception e) {
				//				Log.println("Cannot parse line: " + line, e);
				logger.log(Level.SEVERE, String.format("Cannot parse line: %s", line), e);
				throw new Exception();
			}
			
		}
		return res;
	}
	
	private String convertMonthNameToNumber(String monthName) {
		String _mpontAsName = monthName.trim().toLowerCase();
		return MOTH_MAP.get(_mpontAsName);
	}

	public static void main(String[] args) {
		int counter = 0;
		for (int i = 0; i < (5000 * 11 ); i++) {
			System.out.print( ( ((counter++)%5) == 0 ) ? ("|") : "" );
		}
	}

}
