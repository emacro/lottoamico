/**
 * 
 */
package test;

import it.emacro.extractor.db.Storage;

import java.util.List;

/**
 * @author Emc
 * 
 */
public class Test {

	public static void main(String[] args) throws Exception {
		Storage storage = new Storage();
		List<String[]> list = storage.getExtractionByDate("2007-08-02");
				
		 for(String[] s: list){
			 System.out.println(s[0] + " " + s[1] + " " + s[2] + " " + s[3] + " "
					 + s[4] + " " + s[5] );
		 }
	}

}
