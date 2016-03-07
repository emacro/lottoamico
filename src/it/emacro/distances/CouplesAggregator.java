/**
 * 
 */
package it.emacro.distances;

import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 *
 */
public class CouplesAggregator {

	/**
	 * 
	 */
	public CouplesAggregator() {
	}

	public static List<Quartina> createQuartine(CouplesContainer container) {
		List<Quartina> quartine = new ArrayList<Quartina>();
		List<Couple> couples = container.getCouples();
		Quartina quartina; int[] distances;
		for (int ii = 0; ii < couples.size() - 1; ii++) {
			for (int kk = ii+1; kk < couples.size(); kk++) {
				distances = getDistances(couples.get(ii), couples.get(kk));
				if (distances != null) {
					quartina = new Quartina(couples.get(ii), couples.get(kk), distances[0], distances[1]);
					quartine.add(quartina);
				}
			}
		}
		
		return quartine;
	}
	
	/** @return ritorna le due distanze solo se sono uguali linearmente e 
	 * diagonalmente tra le due coppie altrimenti null. */
	private static int[] getDistances(Couple couple0, Couple couple1) {
		int[] res = null;
		int linear0, linear1, diagonal0, diagonal1;
		linear0 = getDistance(couple0.getFirstNumber(), couple0.getSecondNumber());
		linear1 = getDistance(couple1.getFirstNumber(), couple1.getSecondNumber());
		if (!couple0.getRuota().equals(couple1.getRuota())) {// non sulla stessa ruota
			if (linear0 == linear1) {
				diagonal0 = getDistance(couple0.getFirstNumber(), couple1.getSecondNumber());
				diagonal1 = getDistance(couple0.getSecondNumber(), couple1.getFirstNumber());
				if (diagonal0 == diagonal1) {
					res = new int[]{linear0, diagonal0};
				}
			}
		}
		return res	;
	}
	
	public static int getDistance(int firstNumber, int secondNumber) {
		int res = 0;
		if (firstNumber > secondNumber) {
			res = firstNumber - secondNumber;
		}else if(secondNumber > firstNumber){
			res = secondNumber - firstNumber;
		}
//		else{
//			throw new RuntimeException("Errore: i numeri passati sono anomali: " + 
//					firstNumber + " - " + secondNumber);
//		}
		return normlize(res);
	}
	
	private static int normlize(int n) {
		return (n > 45) ? 90 - n : n;
	}

}
