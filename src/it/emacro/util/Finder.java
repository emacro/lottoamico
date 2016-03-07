/**
 * 
 */
package it.emacro.util;

import java.util.List;

/**
 * @author Emacro
 *
 */
public class Finder {

	/**
	 * 
	 */
	private Finder() {
		super();
	}
	
	public static int findExtractionNumber(int startExtr, String[] searchNumbers, boolean minTwo) {
		int numOfExtractions = Utils.getAllExtractionDates().length - 1;
		boolean[] foundNumbers = new boolean[searchNumbers.length];
		int result = -1;
		

		List<String[]> extraction;
		
		// da numero estrazione fino ad estrazione max possibile 
		// il conteggio delle estrazioni aumenta se le scorriamo all'indietro
		// oggi = 0 
		// 200 esrtazioni fa = 200
		for(int count = startExtr; count <= numOfExtractions; count++){
			
			extraction = Utils.getExtraction(count);
			
			for(int ruota=0; ruota<Utils.getRuote().length; ruota++){
				
				for(int ii=0; ii<searchNumbers.length; ii++){
					
					// se gia' trovato non viene piu' controllato
					if(!foundNumbers[ii]){
						foundNumbers[ii] = isNumberPresent(searchNumbers[ii], extraction.get(ruota));
					}
					
				}
				
			}
			
			if(minTwo){
				// bastano 2 numeri
				if(Utils.areElementsTrue(foundNumbers, 2)) {
					result = count;
					break;
				}
			} else {
				// ci devono essere tutti
				if(Utils.areAllTrue(foundNumbers)) {
					result = count;
					break;
				}
			}
			
			for(int jj=0; jj<foundNumbers.length; jj++){
				foundNumbers[jj] = false;
			}
			
		}
		return result;
	
	}
	
	/**
	 * Controlla se esiste il numero cercato all'interno
	 * dei numeri di un' un'array di stringhe
	 * @param a
	 * @param numbers
	 * @return
	 */
	private static boolean isNumberPresent(int number, String[] numbers){
		boolean exsists = false;
		
		for(int ii=0;ii<numbers.length;ii++){
			if(Integer.parseInt(numbers[ii]) == number){
				exsists = true;
				break;
			}
		}
		
		return exsists;
	}
	
	private static boolean isNumberPresent(String number, String[] numbers){
		return isNumberPresent(Integer.parseInt(number), numbers);
	}

}
