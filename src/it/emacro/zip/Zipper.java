package it.emacro.zip;

import it.emacro.log.Log;
import it.emacro.util.Messenger;
import it.emacro.util.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author Emc
 * 
 */
public class Zipper {
	static final int BUFFER = 2048;

	@SuppressWarnings("unchecked")
	public static void unzip(String fileName) {
		BufferedOutputStream dest = null;
		BufferedInputStream is = null;
		ZipFile zipfile = null;

		try {
			dest = null;
			is = null;
			ZipEntry entry;
			zipfile = new ZipFile(Utils.INPUT_FILE_FOLDER + fileName);
			Enumeration e = zipfile.entries();

			while (e.hasMoreElements()) {
				entry = (ZipEntry) e.nextElement();

				is = new BufferedInputStream(zipfile.getInputStream(entry));
				int count;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(
						Utils.INPUT_FILE_FOLDER + entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				is.close();
				Log.println(Messenger.getInstance().getMessage("unzipped") + entry);
			}
		} catch (Exception e) {
			Log.print(e);
		} finally {
			try {
				zipfile.close();
				dest.close();
				is.close();
			} catch (Exception ignore) {}

		}
	}

	// non testato!!
	public static void zip(String fileDestName) {
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(fileDestName);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			// out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			// get a list of files from current directory
			// File f = new File(".");
			File f = new File(/* directory */".");
			String files[] = f.list();

			for (int i = 0; i < files.length; i++) {
				Log.println("Adding: " + files[i]);
				FileInputStream fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i]);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			Log.print(e);
		}
	}

}
