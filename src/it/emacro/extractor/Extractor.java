package it.emacro.extractor;

import java.io.File;

public interface Extractor {

	/**
	 * 
	 * @param source
	 *            source path file
	 * @throws Exception
	 */
	public abstract void extract(String source) throws Exception;

	/**
	 * 
	 * @param source
	 *            source path file
	 * @throws Exception
	 */
	public abstract void extract(File source) throws Exception;

	/**
	 * 
	 * @param source
	 *            source path file
	 * @param destination
	 *            dest path file
	 * @throws Exception
	 */
	public abstract void extract(String source, String destination)
			throws Exception;

	/**
	 * 
	 * @param source
	 *            source path file
	 * @param destination
	 *            dest path file
	 * @throws Exception
	 */
	public abstract void extract(File source, File destination) throws Exception;

}