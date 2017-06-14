/**
 * 
 */
package it.emacro.downloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Emc
 *
 */
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;
import it.emacro.util.Messenger;
import it.emacro.util.Utils;

public class FileDownloader {
	
	public static boolean download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream  in = null;
		boolean result = true;
		
		try {
			URL url = new URL(address);
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			
			File directory = new File(Utils.INPUT_FILE_FOLDER);
			File file = new File(directory, localFileName);
			if(!file.exists()){
				if(!directory.exists()){
					directory.mkdirs();
				}
				file.createNewFile();
			}
			out = new BufferedOutputStream(new FileOutputStream(file));
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
		if (lastSlashIndex >= 0 &&
		    lastSlashIndex < address.length() - 1) {
			fileName = address.substring(lastSlashIndex + 1);
			if(!download(address,fileName)){
				fileName = null;
			}
		
		} else {
			System.err.println(Messenger.getInstance().getMessage("could.not.figure.out.local.file.name.for") + " " +  address);
			fileName = null;
		}
		return fileName;
	}

}
