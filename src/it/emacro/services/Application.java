/**
 * 
 */
package it.emacro.services;

import it.emacro.downloader.FileDownloader;
import it.emacro.extractor.util.PropertyLoader;
import it.emacro.extractor.util.PropertyWriter;
import it.emacro.gui.components.MainWindow;
import it.emacro.log.Log;
import it.emacro.log.LogFile;
import it.emacro.util.Constants;
import it.emacro.util.ExtractionsDbLoader;
import it.emacro.util.Messenger;
import it.emacro.zip.Zipper;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Emacro
 * 
 */
public class Application implements Constants {

	private static final String PROP_DIR = "WEB-INF/config/application.properties";
	private static final String DB_DIR   = "../DB";
	/**
	 * @throws Exception
	 * 
	 */
	private Properties properties;
	private boolean started;
	private static Application instance;

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
	
	public void start(String applicationRoot, boolean startDownloadExtractionsFile, 
			boolean startDbLoader, boolean setSystemLookAndFeel) throws Exception {
		
		if(!started){
			properties = PropertyLoader.getPropertiesOrEmpty(applicationRoot + PROP_DIR);
			startDB();
			setApplicationRoot(applicationRoot);
			setApplicationLanguage(properties);
			printLastUse(properties);
			setForceExtractionReading(properties);
			setLastExtractionDate(properties);
			setExtractionsFileURL(properties);
			setExtractionsFilePath(properties);
			setExtractionsFileName(properties);
			loadNewExtractionsIntoDB(startDownloadExtractionsFile, startDbLoader);
			setMainWindowDimension(properties);
			startGui(setSystemLookAndFeel);
			started = true;
		}
	}
	
	public void stop() {
		if(started){
			LogFile.getInstance().close();
			System.exit(0);
		}
	}
	
	// ----------------- private methods ------------------
	
	private void startDB() {
		String[] args = {"-tcp", "-tcpPort", "9919", "-web", "-webPort", "8083", "-baseDir", DB_DIR};
		String message = "\nPosizione files del database: " + new File(DB_DIR).getAbsolutePath();
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

	private void loadNewExtractionsIntoDB(boolean startDownloadExtractionsFile,
			boolean startDbLoader) {
		
		
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
				Zipper.unzip(fn);
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
