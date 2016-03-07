/**
 * 
 */
package it.emacro.util;

import it.emacro.services.ApplicationData;


/**
 * @author Emc
 *
 */
public interface Constants {
//	public static final int DEFAULT_EXTRACTIONS_TO_CACHE 		= 100;
	public static final String DEFAULT_EXTRACTIONS_FILE_NAME 	= "Estratti.txt";
	public static final String LOG_FILE_NAME 			= "log.txt";
	
	public static final String COUNT        			= "count";
	public static final String PREVIOUS   				= "precedente"; 
	public static final String NEXT   					= "successivo";
	public static final String APPLY	    			= "applica";
	public static final String AVANTI	    			= " avanti ";
	public static final String INDIETRO	    			= "indietro";
	public static final String CERCA	    			= "<< cerca";
	public static final String TORNA_0	    			= "torna zero";
	public static final String MARKER	    			= "marker";
	public static final String SVUOTA	    			= "svuota";
	public static final String ALL_DATE	    			= "allDate";
	public static final String IS_FIRST_RUN 			= "isFirstRun";
	public static final String JUMP 					= "jump";
	public static final String CALC_PRES				= "Calcola presenze";
	public static final String CALC_DISTANCES			= "Calcola distanze metriche";
	public static final String FORCE_NEXT_DWL			= "Forza prossimo download";
	public static final String INPUT_FILE_FOLDER 		= ApplicationData.getInstance().getWebroot() + "WEB-INF/extractions/";
	public static final String LOG_FILE_FOLDER 		  	= ApplicationData.getInstance().getWebroot() + "WEB-INF/log/";
	
}
