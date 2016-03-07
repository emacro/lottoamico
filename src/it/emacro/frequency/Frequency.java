/**
 * 
 */
package it.emacro.frequency;

import it.emacro.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emacro
 *
 */
public class Frequency {
	
	private int validExtractions = 0;
	private int numOfExtractions = Utils.getAllExtractionDates().length - 1;
	private List<int[]> ambi;
	
	/** 
	 * Constructor
	 * @param extractions
	 * @param frequencyData
	 */
	public Frequency() {
		super();
	}
	
	/**
	 * Torna il risultato dell'elaborazione come array di stringhe composto da 
	 * undici elementi.
	 * Prima di invocare questo metodo deve essere eseguito il metodo calculate, 
	 * altrimenti verra' lanciata un'eccezione.
	 * @return
	 * @throws Exception
	 */
	public String[] getResultAsStrings() throws Exception {
		List<String> list = new ArrayList<String>();
		StringBuffer sb;
		
		if(ambi==null){
			Exception e = new Exception("Lanciare il metodo calculate prima di stampare i risultati");
			throw e;
		}
		
		for(int[] ambo : ambi){
			
			sb = new StringBuffer();
			
			for (int ii = 0; ii < ambo.length; ii++) {
				if(ambo[ii] < 10) {
					sb.append(" ").append(ambo[ii]).append(" ");
				}else{
					sb.append(ambo[ii]).append(" ");
				}
				if(ii==0) sb.append("- ");
				if(ii==1) sb.append(" = ");
			}
			
			sb.append("%");
			
			list.add(sb.toString());
		}
		return list.toArray(new String[list.size()]);
	}
	
	public void calculate(FrequencyData frequencyData) throws Exception {
		ambi = createAmbi(frequencyData);
		ExtractionAndRuote[] extractionAndRuotes = findExtractionAndRuote(frequencyData);
		
		for(ExtractionAndRuote er : extractionAndRuotes){
			if(isValid(er)){
				validExtractions++;
				checkForAmbi(er);
			}
		}
		fillPercentage();
		
	}
	
	// -------------- private methods
	
	
	/**
	 * individua le estrazioni con almeno 2 ruote che contengono i tre 
	 * nuemeri al loro interno.
	 * @param num0
	 * @param num1
	 * @param num2
	 * @return
	 */
	private ExtractionAndRuote[] findExtractionAndRuote(FrequencyData data) {
		List<ExtractionAndRuote> list = new ArrayList<ExtractionAndRuote>();
		int[] numbers = data.getSpyNumbers(); // sempre 3 numeri
		boolean[] n = new boolean[3];  // check presenza numeri
		
		List<String[]> extraction;
		ExtractionAndRuote er;
		
		int ruotaIdx = 0;
		
		// ogni estrazione
		for(int count = numOfExtractions; count >= 0; count--){
			
			n[0] = n[1] = n[2] = false;
			
			extraction = Utils.getExtraction(count);
			er = new ExtractionAndRuote();
			ruotaIdx = 0;
			
			// estratti di ogni ruota 1 2 3 4 5 (gira 11 volte)
			for(String[] extracts : extraction){
				
				// ogni numero dei cinque estratti
				for(String number : extracts){
					
					if(Integer.parseInt(number)==numbers[0]){
						n[0] = true;
					}else if(Integer.parseInt(number)==numbers[1]){
						n[1] = true;
					}else if(Integer.parseInt(number)==numbers[2]){
						n[2] = true;
					}
					 
					if(n[0] == true || n[1] == true || n[2] == true){
						er.addRuota(ruotaIdx);
					}
					
				}
				
				ruotaIdx++;
			}
			
			if(er.getRuote().length > 1 && Utils.areAllTrue(n)){
				er.setExtrNumber(count);
				list.add(er);
			}
		}
		
		return list.toArray(new ExtractionAndRuote[list.size()]);
	}
	
	/**
	 * controlla la presenza degli ambi ed eventualmente incrementa 
	 * la variabile List<int[]> ambi terzo elemento
	 * @return
	 */
	private void checkForAmbi(ExtractionAndRuote er){
		
		List<String[]> extraction;
		Integer[] ruote = er.getRuote();
		int startExtraction = er.getExtrNumber();
		boolean[] founAmbo = new boolean[10];
		
		int countLimit = startExtraction - 13;
		
		if(countLimit < 0) {
			countLimit = 0;
		}
		
		int idx;
		
		// da numero estrazione fino ad estrazione min possibile 
		// il conteggio delle estrazioni diminuisce se le scorriamo in avanti
		// oggi = 200
		// 20 esrtazioni avanti = 180
		for(int count = startExtraction; count >= countLimit; count--){
			
			extraction = Utils.getExtraction(count);
			
			for(int ruota : ruote){
				
				idx=0;
				
				for(int[] ambo : ambi){
					
					if(!founAmbo[idx] && isAmboPresent(ambo, extraction.get(ruota))){
						ambo[2]++;
						// se un ambo viene trovato non deve essere piu' valutato
						founAmbo[idx]=true;
					}
					
					idx++;
				}
				
			}
			
		}
		
	}
	
	/**
	 * controlla che nelle 13 estrazioni precedenti a startExtraction non 
	 * siano presenti gli ambi
	 * @param ambi
	 * @param startExtraction
	 * @return se non sono presenti ambi (neanche uno)
	 */
	private boolean isValid(ExtractionAndRuote er) {
		List<String[]> extraction;
		Integer[] ruote = er.getRuote();
		int startExtraction = er.getExtrNumber();
		
		int countLimit = startExtraction + 13;
		int test = numOfExtractions - countLimit;
		
		if(test < 0) {
			countLimit = numOfExtractions;
		}
		
		// da numero estrazione fino ad estrazione max possibile 
		// il conteggio delle estrazioni aumenta se le scorriamo all'indietro
		// oggi = 0 
		// 200 estrazioni fa = 200
		for(int count = startExtraction; count <= countLimit; count++){
			
			extraction = Utils.getExtraction(count);
			
			for(int ruota : ruote){
				
				for(int[] ambo : ambi){
					
					if(isAmboPresent(ambo, extraction.get(ruota))){
						return false;
					}
					
				}
				
			}
			
		}
		return true;
	}
	
	/**
	 * crea le combinazioni tra i vari numeri cioe' gli ambi
	 * [primo numero] [secondo numero] [presenze]
	 * il terzo ele mento dall'array (presenze) parte da 0 e verra' 
	 * incrementato successivamente
	 * @param data
	 * @return
	 */
	private List<int[]> createAmbi(FrequencyData data) {  // testato ok
		List<int[]> ambi = new ArrayList<int[]>();
		int[] numbers = data.getPlayNumbers();
		
		for(int ii=0; ii < 5; ii++){
			for(int jj=ii+1; jj < 5; jj++){
				ambi.add(new int[]{numbers[ii], numbers[jj], 0});
			}
		}
		
		return ambi;
	}
	
	/**
	 * calcola la percentuale di presenza degli ambi e cambia il terzo elemento dell'array di 
	 * ogni ambo sostituendo le presenze con la percentuale delle presenze
	 * @param ambi
	 */
	private void fillPercentage(){
		int pres;
		
		for(int[] ambo : ambi){
			pres = ambo[2];
			ambo[2] = pres * 100 / validExtractions;  
		}
	}
	
//	private boolean areAllTrue(boolean[] arg){
//		boolean result = true;
//		
//		for(int ii=0;ii<arg.length;ii++){
//			if(arg[ii]==false){
//				result = false;
//				break;
//			}
//		}
//		
//		return result;
//	}
	
	/**
	 * Controlla se esiste l'ambo dato da l'array di int (2 elementi) all'interno
	 * dei numeri di un' un'array di stringhe
	 * @param a
	 * @param numbers
	 * @return
	 */
	private boolean isAmboPresent(int[] a, String[] numbers){
		boolean exsists1 = false;
		boolean exsists2 = false;
		
		for(int ii=0;ii<numbers.length;ii++){
			if(Integer.parseInt(numbers[ii]) == a[0]){
				exsists1 = true;
				break;
			}
		}
		
		if(exsists1){
			for(int ii=0;ii<numbers.length;ii++){
				if(Integer.parseInt(numbers[ii]) == a[1]){
					exsists2 = true;
					break;
				}
			}
		}
		
		return exsists1 && exsists2;
	}
	
}
