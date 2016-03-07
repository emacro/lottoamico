/**
 * 
 */
package it.emacro.gui.panels;

import it.emacro.gui.components.NumbersTypeExtractor;
import it.emacro.gui.components.Row;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author Emc
 * 
 */
@SuppressWarnings("serial")
public class ExtractionPanel extends JPanel {
	
	public static final int BARI  		= 0;
	public static final int CAGLIARI	= 1; 
	public static final int FIRENZE		= 2;  
	public static final int GENOVA		= 3;   
	public static final int MILANO		= 4;   
	public static final int NAPOLI		= 5;   
	public static final int PALERMO		= 6;  
	public static final int ROMA		= 7;     
	public static final int TORINO		= 8;   
	public static final int VENEZIA		= 9;  
	public static final int NAZIONALE	= 10;

	private List<Row> rowList;

	public ExtractionPanel() {
		this((Row) null);

	}

	public ExtractionPanel(Row... rows) {
		super();
		setLayout(new BorderLayout());
		rowList = new ArrayList<Row>();

		int idx = 0;
		if (rows != null) {
			for (Row row : rows) {
				rowList.add(idx++, row);
			}
		}
//		add(new JPanel(), BorderLayout.NORTH);
		add(RuotePanel.getInstance(), BorderLayout.WEST);
		add(NumbersPanel.getInstance(), BorderLayout.CENTER);
	}
	
	public void setRowEnabled(int ruota, boolean e) {
		rowList.get(ruota).setEnabled(e);
	}
	
	public void setAllRowEnabled(boolean e) {
		for(int idx=0;idx<11;idx++){
			rowList.get(idx).setEnabled(e);
		}
	}
	
	public void setNumbersSelected(String[] numbers, String type) {
		String[] n = numbers;
		if(!type.equals(NumbersType.LIBERO)){
			n = NumbersTypeExtractor.getNumbers(type);
		}
		for(int idx=0;idx<11;idx++){
			rowList.get(idx).select(n);
		}
	}
	
//	public void findNumbersType(String type) {
//		setNumbersSelected(NumbersTypeExtractor.getNumbers(type));
//	}

	public void updateRows(List<String[]> extraction) {
		if (rowList != null) {

			int idx = 0;

			for (String[] r : extraction) {

				rowList.get(idx).getFirst().setText(r[0]);
				rowList.get(idx).getSecond().setText(r[1]);
				rowList.get(idx).getThird().setText(r[2]);
				rowList.get(idx).getFourth().setText(r[3]);
				rowList.get(idx).getFifth().setText(r[4]);

				// Utils.dump(rowList.get(0).getRuota().getText(),
				// rowList.get(0).getFirst().getText(),
				// rowList.get(0).getSecond().getText(),
				// rowList.get(0).getThird().getText(),
				// rowList.get(0).getFourth().getText(),
				// rowList.get(0).getFifth().getText());

				idx++;

			}
		}
	}

}
