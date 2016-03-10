/**
 * 
 */
package it.emacro.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import it.emacro.downloader.FileDownloader;
import it.emacro.extractor.db.ConnectionPool;
import it.emacro.extractor.util.PropertyLoader;
import it.emacro.extractor.util.PropertyWriter;
import it.emacro.gui.components.MainWindow;
import it.emacro.log.Log;
import it.emacro.log.LogFile;
import it.emacro.util.Constants;
import it.emacro.util.ExtractionsDbLoader;
import it.emacro.util.Messenger;
import it.emacro.zip.Zipper;

/**
 * @author Emacro
 * 
 */
public class Application implements Constants {
	
	
	private Logger logger = Logger.getLogger("Application");

	private static final String PROP_DIR = "WEB-INF/config/application.properties";
	private static final String DB_DIR   = "/DB";
	/**
	 * @throws Exception
	 * 
	 */
	private Properties properties;
	private static Application instance;
	private String applicationStatus;
	private Long time;

	private Application() {
		super();
	}
	
	public static Application getInstance() {
        if (instance == null) {
            synchronized (Application.class) {
                if (instance == null) {
                    instance = new Application();
                }
            }
        }
        return instance;
    }
	
	public void start(ApplicationSettings settings) throws Exception {

		properties = PropertyLoader.getPropertiesOrEmpty(settings.applicationRoot + PROP_DIR);
		setApplicationRoot(settings.applicationRoot);
		setApplicationLanguage(properties);
		startDB();
		
		final Connection conn = ConnectionPool.getInstance().getConnection();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SETTINGS WHERE NAME = 'APPLICATION_STATUS'");
		if(rs.next()) {
			applicationStatus = rs.getString("VALUE");
			time = rs.getLong("TIME");
		}
		
		boolean started = applicationStatus != null && "RUNNING".equals(applicationStatus) && 
				(System.currentTimeMillis() - time) < 30000;
		
		if(!started){
			printLastUse(properties);
			setForceExtractionReading(properties);
			setLastExtractionDate(properties);
			setExtractionsFileURL(properties);
			setExtractionsFilePath(properties);
			setExtractionsFileName(properties);
			setExtractorImplementation(properties);
			loadNewExtractionsIntoDB(settings.startDownloadExtractionsFile, settings.startDbLoader);
			setMainWindowDimension(properties);
			startGui(settings.setSystemLookAndFeel);
			
			boolean done = false;
			if(applicationStatus != null){
				done = conn.createStatement()
						.executeUpdate("UPDATE SETTINGS SET VALUE = 'RUNNING' WHERE NAME = 'APPLICATION_STATUS'") > 0;
						
				if (done) {
					
					new Timer().schedule(new TimerTask() {
						
						@Override
						public void run() {
							try {
								conn.createStatement()
								.executeUpdate("UPDATE SETTINGS SET TIME = " + System.currentTimeMillis() + 
										" WHERE NAME = 'APPLICATION_STATUS'");
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}, 10000, 10000);
				}
			}else{
				done = conn.createStatement().execute("INSERT INTO SETTINGS (NAME,VALUE,TIME,EXTRA) VALUES ('APPLICATION_STATUS','RUNNING'," + System.currentTimeMillis() + ",'application status info')");
			}
			
		}else{
			this.logger.severe("PROJECT IS ALREADY RUNNING, GIVE UP!");
			LogFile.getInstance().close();
			System.exit(0);
		}
	}
	
	public void stop() {
		
		Connection conn = null;
		
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT VALUE FROM SETTINGS WHERE NAME = 'APPLICATION_STATUS'");
			if(rs.next()) applicationStatus = rs.getString("VALUE");

			boolean started = applicationStatus != null && "RUNNING".equals(applicationStatus);
			if(started){
				conn = ConnectionPool.getInstance().getConnection();
				conn.createStatement()
				.executeUpdate("UPDATE SETTINGS SET VALUE = 'STOPPED' WHERE NAME = 'APPLICATION_STATUS'");

				LogFile.getInstance().close();
				System.exit(0);
			}

		} catch (SQLException e) {
				 
				try {
					if(conn != null) conn.createStatement()
					.executeUpdate("UPDATE SETTINGS SET VALUE = 'ERROR' WHERE NAME = 'APPLICATION_STATUS'");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
		}
	
	// ----------------- private methods ------------------
	
	private void startDB() {
		
		String developPath = System.getProperty("developPath", null);
		String dbDirectory = developPath != null? (developPath + ".." + DB_DIR) : (".." + DB_DIR); 
		String[] args = {"-tcp", "-tcpPort", "9919", "-web", "-webPort", "8083", "-baseDir", dbDirectory};
		String message = "\nPosizione files del database: " + dbDirectory;
		try {
			org.h2.tools.Server.main(args);
			System.out.println(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setApplicationRoot(String applicationRoot) throws Exception {

		// could be d:/workspace/lotto/webcontent/

		if (applicationRoot == null || applicationRoot.isEmpty()) {
			throw new Exception("Missing aplication root");
		}
		ApplicationData.getInstance().setWebroot(applicationRoot);

		Log.println("application root: " + applicationRoot);
//		Log.println(Messenger.getInstance().getMessage("application.root.is")  + applicationRoot);
	}

	private void loadNewExtractionsIntoDB(boolean startDownloadExtractionsFile, boolean startDbLoader) {
		
		
		boolean canDo;

		// extraction file download and reading can be forced only for one time
		// if it is the last extracion date will be ignored
		if(ApplicationData.getInstance().isForceExtractionReading()){
			ApplicationData.getInstance().setForceExtractionReading(false);
			canDo = true;
		}else{
			canDo = (Calendar.getInstance().getTimeInMillis() - getLastExtraction().getTimeInMillis()) >= getMillisToWait();
		}
		
		// only on sunday, wednesday and friday
		if (canDo && startDownloadExtractionsFile) {
			Log.println(Messenger.getInstance().getMessage("starting.to.download.extractions.file.from.web"));

			// download extractions file from web and unzip that
			// this is NOT A THREAD
			String fn = FileDownloader.download();

			if (fn != null) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(fn != null && fn.endsWith(".zip")) Zipper.unzip(fn);
			}

		}

		if (canDo && startDbLoader) {
			String oldDate = ApplicationData.getInstance()
					.getLastExtractionDate();
			String newDate = null;

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Log.println(Messenger.getInstance().getMessage("loading.extractions.from.file.to.db"));

			// load extractions in DB if file exists
			// this is the NON THREAD version (that is
			// ExtractionsDbLoaderThread)
			new ExtractionsDbLoader().loadExtractions();

			newDate = ApplicationData.getInstance().getLastExtractionDate();

			if (!newDate.equals(oldDate)) {
				setProperty("last.extraction.date", newDate);
			}

		}

	}

	private void startGui(boolean setSystemLookAndFeel) throws Exception {
		try {
			if (setSystemLookAndFeel) {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			}

			MainWindow mw = new MainWindow();
			mw.setVisible(true);

			Log.println(Messenger.getInstance().getMessage("starting.gui"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void setExtractionsFileName(Properties properties) {
		String fn = properties.getProperty("extractions.file.name");
		if (fn == null || fn.trim().isEmpty()) {
			fn = DEFAULT_EXTRACTIONS_FILE_NAME;
		}
		ApplicationData.getInstance().setExtractionsFileName(fn);

		Log.println(Messenger.getInstance().getMessage("extractions.file.name") + fn);
	}

	private void setExtractionsFilePath(Properties properties) {
		String fp = INPUT_FILE_FOLDER
				+ properties.getProperty("extractions.file.name");
		if (fp == null || fp.trim().isEmpty()) {
			fp = INPUT_FILE_FOLDER + DEFAULT_EXTRACTIONS_FILE_NAME;
		}
		ApplicationData.getInstance().setExtractionsFilePath(fp);

		Log.println(Messenger.getInstance().getMessage("extractions.file.path") + fp);
	}

	private void setExtractionsFileURL(Properties properties) {
		String fp = properties.getProperty("extraction.file.url");
		ApplicationData.getInstance().setExtractionsFileURL(fp);

		Log.println(Messenger.getInstance().getMessage("extractions.file.url") + fp);
	}

	private void setLastExtractionDate(Properties properties) {
		String fp = properties.getProperty("last.extraction.date");
		ApplicationData.getInstance().setLastExtractionDate(fp);

		Log.println(Messenger.getInstance().getMessage("last.extraction.date")  + fp);
	}
	
	private void setForceExtractionReading(Properties properties) {
		boolean force = Boolean.valueOf(properties.getProperty("force.extraction.read").trim());
		ApplicationData.getInstance().setForceExtractionReading(force);
		
		if(force){
			Log.println(Messenger.getInstance().getMessage("download.forcing.required"));
		}
	}
	
	private void setExtractorImplementation(Properties properties) {
		String extractorImplementation = properties.getProperty("extractor.imlementation").trim();
		ApplicationData.getInstance().setExtractorImplementation(extractorImplementation);
		 
	}

	private Calendar getLastExtraction() {
		Calendar c = Calendar.getInstance();
		String lastDate = ApplicationData.getInstance().getLastExtractionDate();
		int year = new Integer(lastDate.substring(0, 4));
		int month = new Integer(lastDate.substring(5, 7)) - 1;
		int date = new Integer(lastDate.substring(8));
		c.set(year, month, date, 20, 0);

		return c;
	}

	private void setProperty(String key, String value) {
		String path = ApplicationData.getInstance().getWebroot() + "WEB-INF/config/application.properties";

		properties.setProperty(key, value);
		try {
			PropertyWriter.setProperties(path, properties);

		} catch (Exception e) {
			Log.println(Messenger.getInstance().getMessage("cannot.store.new.last.extractions.date"));
			e.printStackTrace();
		}
	}
	
	private long getMillisToWait() {
		/*
		 * this gets the time waiting for new search (and load) of a next extraction 
		 * 
		 * if last extraction was on Tuesday or Thursday result is 2 days and 1 hours
		 * else last extraction was on Saturday result is 3 days and 1 hours
		 * 
		 * 86400000 millis is a day
		 */
		long result = 176400000L;// 2gg + 1h     // prec 187200000;
		
		if( getLastExtraction().get(Calendar.DAY_OF_WEEK) == 7){
			// if last was on saturday
			result += 86400000L;
		}
		
		return result;
	}
	
	private void setApplicationLanguage(Properties properties) {
		String lang = properties.getProperty("application.language");
		ApplicationData.getInstance().setLanguage(lang);

		Log.println(Messenger.getInstance().getMessage("application.language.is") + lang);
	}
	
	private void setMainWindowDimension(Properties properties) {
		int width = Integer.parseInt(properties.getProperty("main.window.width"));
		int height = Integer.parseInt(properties.getProperty("main.window.height"));
		
		ApplicationData.getInstance().setMainWindowDimension(width, height);
	}
	
	private void printLastUse(Properties properties) {
		String lu = properties.getProperty("last.appllication.use");
		Log.println(Messenger.getInstance().getMessage("last.application.use.date")  + lu);
	}

}
