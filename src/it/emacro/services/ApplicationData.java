/*
 * i-service 5
 *
 * Copyright (c) 1999-2007 Ingenium Technology Srl
 * All rights reserved.
 *
 * Proprietary and confidential of Ingenium Technology Srl
 * Use is subject to license terms.
 */
package it.emacro.services;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 
 * @author Emc
 *
 */
public class ApplicationData {

    private static ApplicationData instance;

    private String webroot;
    private String extractionsFileName;
    private String extractionsFilePath;
    private String extractionsFileURL;
    private String lastExtractionDate;
    private String language;
    private boolean forceExtractionReading;
    private Dimension mainWindowDimension;

    //private User user;

    private ApplicationData() {
        super();
    }

    public static ApplicationData getInstance() {
        if (instance == null) {
            synchronized (ApplicationData.class) {
                if (instance == null) {
                    instance = new ApplicationData();
                }
            }
        }
        return instance;
    }

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

	public String getExtractionsFileName() {
		return extractionsFileName;
	}

	public void setExtractionsFileName(String extractionsFileName) {
		this.extractionsFileName = extractionsFileName;
	}

//	public int getExtractionsToCache() {
//		return extractionsToCache;
//	}
//
//	public void setExtractionsToCache(int extractionsToCache) {
//		this.extractionsToCache = extractionsToCache;
//	}

	public String getExtractionsFilePath() {
		return extractionsFilePath;
	}

	public void setExtractionsFilePath(String extractionsFilePath) {
		this.extractionsFilePath = extractionsFilePath;
	}

	public String getExtractionsFileURL() {
		return extractionsFileURL;
	}

	public void setExtractionsFileURL(String extractionsFileURL) {
		this.extractionsFileURL = extractionsFileURL;
	}

	public String getLastExtractionDate() {
		return lastExtractionDate;
	}

	public void setLastExtractionDate(String lastExtractionDate) {
		if(lastExtractionDate.trim().isEmpty() || lastExtractionDate == null){
			lastExtractionDate = "1900-01-01";
		}
		this.lastExtractionDate = lastExtractionDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		if(language.trim().isEmpty() || language == null){
			language = "english";
		}
		this.language = language;
	}

	public Dimension getMainWindowDimension() {
		return mainWindowDimension;
	}

	public void setMainWindowDimension(int width, int height) {
		if(width > Toolkit.getDefaultToolkit().getScreenSize().width || 
				height > Toolkit.getDefaultToolkit().getScreenSize().height){
			width = 540;
			height = 480;
		}
		
		if(width < 540 ||  height < 480){
			width = 540;
			height = 480;
		}
		
		this.mainWindowDimension = new Dimension(width, height);
	}

	public boolean isForceExtractionReading() {
		return forceExtractionReading;
	}

	public void setForceExtractionReading(boolean forceExtractionReading) {
		this.forceExtractionReading = forceExtractionReading;
	}


}
