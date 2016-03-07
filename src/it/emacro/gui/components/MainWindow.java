/**
 * 
 */
package it.emacro.gui.components;

/**
 * @author Emc
 * 
 */
import it.emacro.exceptions.InvalidNumberException;
import it.emacro.gui.listeners.Terminator;
import it.emacro.gui.panels.ButtonsPanel;
import it.emacro.gui.panels.DatePanel;
import it.emacro.gui.panels.ExtractionPanel;
import it.emacro.gui.panels.TextFieldPanel;
import it.emacro.manager.ExplorerManager;
import it.emacro.services.ApplicationData;
import it.emacro.util.Constants;
import it.emacro.util.ExitStorer;
import it.emacro.util.Finder;
import it.emacro.util.Utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener, ItemListener,
		AdjustmentListener, DocumentListener, Constants {

	private String[] dates;

	private String[] ruote;

	private List<String[]> extraction;

	private int count;

	private DatePanel datePanel;

	private ExtractionPanel extractionPanel;

	private JCheckBox[] checkBox;

	private SearchCombo combo = new SearchCombo();

	private JButton nextButton = new JButton(AVANTI);
	
	private JButton markButton = new JButton(MARKER);

	private JButton backButton = new JButton(INDIETRO);
	
	private JButton searchButton = new JButton(CERCA);
	
	private JButton returnButton = new JButton(TORNA_0);

	private SearchField search = new SearchField(22);

	private JButton emptyButton = new JButton(SVUOTA);
	
	private JScrollBar scroll;
	
	private JRadioButton radioMinTwo  = new JRadioButton("min 2",true);
	
	private JRadioButton radioAll = new JRadioButton("tutti");
	
	private ButtonGroup group = new ButtonGroup();
	  
	private boolean marked = false;
	
	private int extrMarked = 0;

	public MainWindow() throws Exception {
		super("Lotto");

		init();
		asignListenerToButtons();
		// addToolBar();

		count = 0;
		ruote = Utils.getRuote();
		dates = Utils.getAllExtractionDates();
		extraction = Utils.getExtraction(count);

		addPanels(getNorth(), getSouth(), getEast(), getWest(), getCenter());
		pack();

		resizeFrame(this);
		centerFrame(this);
	}

	// -------------- listeners methods ----------------

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == backButton) {

			if (count < dates.length - 1) {
//				datePanel.setDate(dates[++count]);
//				extraction = ExplorerManager.getInstance().getExtraction(count);
//				extractionPanel.updateRows(extraction);
//				checkForNumberPresent();
//				setMarkerButton();
				
				scroll.setValue(count + 1);
			}

		} else if (e.getSource() == nextButton) {

			if (count > 0) {
//				datePanel.setDate(dates[--count]);
//				extraction = ExplorerManager.getInstance().getExtraction(count);
//				extractionPanel.updateRows(extraction);
//				checkForNumberPresent();
//				setMarkerButton();
				
				scroll.setValue(count - 1);
			}
		} else if (e.getSource() == markButton) {
			
			marked = !marked;
			
			if (marked) {
				extrMarked = count;
				setMarkerButton();
				markButton.setFont(CustomFont.BUTTON_FONT_BOLD);
				returnButton.setEnabled(true);
			}else{
				markButton.setText(MARKER);
				datePanel.setHighlighted(false);
				markButton.setFont(CustomFont.BUTTON_FONT);
				returnButton.setEnabled(false);
			}
		} else if (e.getSource() == emptyButton) {
			search.setText("");
			checkForNumberPresent();
			search.requestFocus();

		} else if (e.getSource() == combo) {
			if (combo.getSelectedItem().equals(NumbersTypeExtractor.LIBERO)) {
//				searchButton.setEnabled(true);
				emptyButton.setEnabled(true);
				
				search.setEnabled(true);
				search.requestFocus();
			} else {
//				searchButton.setEnabled(false);
				emptyButton.setEnabled(false);
				search.setEnabled(false);
			}
			checkForNumberPresent();

		} else if (e.getSource() == searchButton) {

			int extrNumb;
			
			try {
				extrNumb = Finder.findExtractionNumber(scroll.getValue() + 1, search.splitContentNumbers(), radioMinTwo.isSelected());
				
				if(extrNumb == -1){
					Utils.showInformationMessage("Nessuna estrazione trovata", "Non e' atata trovata alcuna occorrenza.");
				} else {
					scroll.setValue(extrNumb);
				}
			} catch (InvalidNumberException e1) {
				Utils.showWarningMessage("Errore nell'inserimento", "E' stato inserito un numero non valido.");
			}
			
		} else if (e.getSource() == returnButton) {
			if(extrMarked != count){
				scroll.setValue(extrMarked);
			}
		}

	}

	// for checkbox - this method implements ItemListener interface
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();

		if (source == checkBox[0]) {
			for (int cbx = 1; cbx < 12; cbx++) {
				if (checkBox[cbx] != null) {
					checkBox[cbx].setSelected(checkBox[0].isSelected());
				}
			}
		}

		for (int ii = 0; ii < 11; ii++) {
			if (checkBox[ii + 1] != null && extractionPanel != null) {
				extractionPanel
						.setRowEnabled(ii, checkBox[ii + 1].isSelected());
			}
		}

		checkForNumberPresent();
		search.requestFocus();
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		count = e.getValue();
		datePanel.setDate(dates[count]);
		extraction = ExplorerManager.getInstance().getExtraction(count);
		extractionPanel.updateRows(extraction);

		checkForNumberPresent();
		setMarkerButton();
		
	}

	// textfield event
	public void insertUpdate(DocumentEvent e) {
		checkForNumberPresent();
	}

	public void removeUpdate(DocumentEvent e) {
		checkForNumberPresent();

	}

	public void changedUpdate(DocumentEvent e) {
	}

	// ------------- private methods ----------------

	private void init() {
		// setSize(800, 600);
		// setLocation(250, 100);
		setLayout(new BorderLayout());
		setJMenuBar(new Menu());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new Terminator());
		ExitStorer.getInstance().setWindow(this);
	}

	private void asignListenerToButtons() {
		nextButton.addActionListener(this);
		backButton.addActionListener(this);
		emptyButton.addActionListener(this);
		markButton.addActionListener(this);
		searchButton.addActionListener(this);
		returnButton.addActionListener(this);
	}

	@SuppressWarnings("unused")
	@Deprecated
	/**
	 * use getCell()
	 */
	private JLabel getLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		return label;
	}

	private Cell getCell(String text, Font font) {
		Cell c;

		if (text.length() > 3) {
			// is a ruota
			c = (font == null) ? new Cell(text + " ", Cell.LEFT) : new Cell(
					text + " ", font, Cell.LEFT);
		} else {
			// is a number
			c = (font == null) ? new Cell(text) : new Cell(text, font);
		}

		return c;
	}

	private Row[] getExtractionRows(List<String[]> extraction) {
		List<Row> list = new ArrayList<Row>();
		Row row;
		int idx = 0;

		for (String[] r : extraction) {
			row = new Row();

			row.setRuota(getCell(ruote[idx++], CustomFont.MIDDLE_BOLD_ITALIC));
			row.setFirst(getCell(r[0], CustomFont.MIDDLE_BOLD));
			row.setSecond(getCell(r[1], CustomFont.MIDDLE_BOLD));
			row.setThird(getCell(r[2], CustomFont.MIDDLE_BOLD));
			row.setFourth(getCell(r[3], CustomFont.MIDDLE_BOLD));
			row.setFifth(getCell(r[4], CustomFont.MIDDLE_BOLD));

			list.add(row);
		}
		return list.toArray(new Row[list.size()]);
	}

	private JPanel getNorth() {
		datePanel = new DatePanel(dates[count]);
		return datePanel;
	}

	private JPanel getSouth() {
		JPanel footPanel = new JPanel(new GridLayout(2, 1));
		ButtonsPanel commandsPanel = new ButtonsPanel();
		TextFieldPanel tfp = new TextFieldPanel();
		search.getDocument().addDocumentListener(this);
		search.setFont(CustomFont.SEARCH_FONT);

		combo.addActionListener(this);
		combo.setFont(CustomFont.COMBO_FONT);

		backButton.setFont(CustomFont.BUTTON_FONT);
		nextButton.setFont(CustomFont.BUTTON_FONT);
		emptyButton.setFont(CustomFont.BUTTON_FONT);
		markButton.setFont(CustomFont.BUTTON_FONT);
		searchButton.setFont(CustomFont.BUTTON_FONT);
		returnButton.setFont(CustomFont.BUTTON_FONT);
		
		returnButton.setEnabled(false);
		searchButton.setEnabled(false);
		
		group.add(radioMinTwo);
		group.add(radioAll); 
		
		tfp.add(combo);
		tfp.add(search);
		tfp.add(radioMinTwo);
		tfp.add(radioAll);
		tfp.add(emptyButton);
		
		footPanel.add(tfp);
		
		commandsPanel.add(backButton);
		commandsPanel.add(new Cell("    ",false));
		commandsPanel.add(searchButton);
		commandsPanel.add(new Cell("    ",false));
		commandsPanel.add(markButton);
		commandsPanel.add(new Cell("    ",false));
		commandsPanel.add(returnButton);
		commandsPanel.add(new Cell("    ",false));
		commandsPanel.add(nextButton);
		footPanel.add(commandsPanel);
		return footPanel;
	}

	private JPanel getEast() {
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(1, 3));
		scroll = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, Utils
				.getNumOfExtractions());
		scroll.addAdjustmentListener(this);
		eastPanel.add(new JLabel());
		eastPanel.add(scroll);
		eastPanel.add(new JLabel());
		return eastPanel;
	}

	private JPanel getWest() {
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(12, 3));
		checkBox = new JCheckBox[12];

		for (int ll = 0; ll < 12; ll++) {

			checkBox[ll] = new JCheckBox();
			checkBox[ll].addItemListener(this);
			checkBox[ll].setSelected(true);

			checkBoxPanel.add(new JLabel());
			checkBoxPanel.add(checkBox[ll]);
			checkBoxPanel.add(new JLabel());
		}
		return checkBoxPanel;
	}

	private JPanel getCenter() {
		extractionPanel = new ExtractionPanel(getExtractionRows(extraction));
		return extractionPanel;
	}

	private void addPanels(JPanel north, JPanel south, JPanel east, JPanel west, JPanel center) {
		getContentPane().add(BorderLayout.NORTH, north);
		getContentPane().add(BorderLayout.CENTER, center);
		getContentPane().add(BorderLayout.SOUTH, south);
		getContentPane().add(BorderLayout.EAST, east);
		getContentPane().add(BorderLayout.WEST, west);
	}

	private void checkForNumberPresent() {
		if (extractionPanel != null) {
			try {
				String[] numbs = search.splitContentNumbers();
				extractionPanel.setNumbersSelected(numbs, (String) combo.getSelectedItem());
				searchButton.setEnabled(numbs.length > 0 && combo.getSelectedItem().equals(NumbersTypeExtractor.LIBERO) );
			} catch (InvalidNumberException e) {
				Utils.showWarningMessage("Errore nell'inserimento", "E' stato inserito un numero non valido.");
			}
		}
	}
	
	private void resizeFrame(Window frame){
		Dimension frameSize = ApplicationData.getInstance().getMainWindowDimension();
		frame.setSize(frameSize);
	}

	private void centerFrame(Window frame) {
		Dimension frameSize = frame.getSize();
		frame.setLocation(
						(Toolkit.getDefaultToolkit().getScreenSize().width - frameSize.width) / 2,
						(Toolkit.getDefaultToolkit().getScreenSize().height - frameSize.height) / 2);
	}
	
	private void setMarkerButton(){
		if(marked){
			if(extrMarked==count){
				datePanel.setHighlighted(true);
				markButton.setText("0");
			}else{
				int diff = extrMarked - count;
				markButton.setText(String.valueOf(diff > 0 ? "+" + diff : diff));
				datePanel.setHighlighted(false);
			}
		}
	}

}