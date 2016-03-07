/**
 * 
 */
package it.emacro.downloader;

/**
 * @author Emc
 *
 */
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;
import it.emacro.util.Messenger;
import it.emacro.util.Utils;

import java.io.*;
import java.net.*;

public class FileDownloader {
	
	public static boolean download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream  in = null;
		boolean result = true;
		
		try {
			URL url = new URL(address);
			OutputStream fos = new FileOutputStream(Utils.INPUT_FILE_FOLDER + localFileName);
			out = new BufferedOutputStream(fos);
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}

//			Log.println("download of " + localFileName + " from web - SUCCESSFULL");
			Log.print(Messenger.getInstance().getMessage("download.of") + localFileName);
			Log.print(Messenger.getInstance().getMessage("from.web"));
			Log.println(Messenger.getInstance().getMessage("successfull"));
		} catch (Exception exception) {
//			Log.println("download of " + localFileName+ " from web - NOT SUCCESSFULL");
			Log.print(Messenger.getInstance().getMessage("download.of") + localFileName);
			Log.print(Messenger.getInstance().getMessage("from.web"));
			Log.println(Messenger.getInstance().getMessage("not.successfull"));
			exception.getMessage();
			result = false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) { }
		}
		return result;
	}

	public static String download() {
		String address = ApplicationData.getInstance().getExtractionsFileURL();
		String fileName;
		
		int lastSlashIndex = address.lastIndexOf('/');
		if (lastSlashIndex >= 0 && lastSlashIndex < address.length() - 1) {
			fileName = address.substring(lastSlashIndex + 1);
			boolean downloaded = download(address, fileName);
			if( !downloaded ){
				fileName = null;
			}
		
		} else {
			System.err.println(Messenger.getInstance().getMessage("could.not.figure.out.local.file.name.for") + " " +  address);
			fileName = null;
		}
		return fileName;
	}

}
