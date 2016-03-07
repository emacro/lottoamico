/**
 * 
 */
package it.emacro.distances;

import it.emacro.manager.DatesManager;
import it.emacro.manager.ExplorerManager;

import java.util.List;

/**
 * @author user
 *
 */
public class CouplesHandler {
	
	private List<Quartina> quartine;
	private String date;

	/**
	 * 
	 */
	public CouplesHandler() {
		int count = ExplorerManager.getInstance().getLastExtractionCount();
		date = DatesManager.getInstance().getDate(count);
		List<String[]> exraction = ExplorerManager.getInstance().getExtraction(count);
		CouplesContainer container = CouplesCreator.createCouples(exraction);
		quartine = CouplesAggregator.createQuartine(container);
	}
	
	public String getExtractionDateOfCalculation () {
		return date;
	}
	
	public String getOutput() {
		StringBuffer sb = new StringBuffer();
		for (Quartina quartina : quartine) {
			sb.append(quartina).append("\n");
		}
		return sb.toString();
	}

}
