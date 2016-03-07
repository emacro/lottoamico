/**
 * 
 */
package it.emacro.distances;

import it.emacro.util.Utils;

import java.util.List;

/**
 * @author user
 *
 */
public class CouplesCreator {
	
	private static final String[] ruote = Utils.getRuote();

	/**
	 * 
	 */
	private CouplesCreator() {
	}

	public static CouplesContainer createCouples(List<String[]> exraction) {
		CouplesContainer container = new CouplesContainer();
		int index = 0; String ruota;
		for (String[] numbers : exraction) {
			ruota = ruote[index++];
			container.addCouple(createCouple(ruota, numbers[0], numbers[1]));
			container.addCouple(createCouple(ruota, numbers[1], numbers[2]));
			container.addCouple(createCouple(ruota, numbers[2], numbers[3]));
			container.addCouple(createCouple(ruota, numbers[3], numbers[4]));
		}
		return container;
	}
	
	private static Couple createCouple(String ruota, String firstNumber, String secondNumber) {
		return new Couple(ruota, Integer.parseInt(firstNumber), Integer.parseInt(secondNumber));
	}

}
