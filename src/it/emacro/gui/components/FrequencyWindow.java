/**
 * 
 */
package it.emacro.gui.components;

import it.emacro.frequency.Frequency;
import it.emacro.frequency.FrequencyData;
import it.emacro.log.Log;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 * @author Emacro
 *
 */
@SuppressWarnings("serial")
public class FrequencyWindow  extends JFrame implements ActionListener {


	private JButton calc = new JButton("Calcola");
	private JButton exit = new JButton("Esci");
	
	private JPanel fields = new JPanel();
	private JPanel results = new JPanel(new GridLayout(10,1));
	private JPanel fieldsTwoLine = new JPanel(new GridLayout(2,1));
	private JPanel buttons = new JPanel();
	
	private JTextField[] spy = new JTextField[3];
	private JTextField[] play = new JTextField[5];
	
	private Cell[] resLine = new Cell[10]; 
	
	public FrequencyWindow() {
		super("Calcola presenze");
		setSize(300,400);
		setResizable(false);
		setLayout(new BorderLayout());
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2 ,
			    (Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);
		
		for (int ii = 0; ii < spy.length; ii++) {
			spy[ii] = new JTextField(2);
			fields.add(spy[ii]);
		}
		
		fields.add(new Cell("-", false));
		
		for (int ii = 0; ii < play.length; ii++) {
			play[ii] = new JTextField(2);
			fields.add(play[ii]);
		}
		
		for (int ii = 0; ii < resLine.length; ii++) {
			resLine[ii] = new Cell("", CustomFont.BUTTON_FONT_BOLD);
			resLine[ii].setBorded(false);
			results.add(resLine[ii]);
		}
		
		results.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JPanel comment = new JPanel(new GridLayout(3,1));
		comment.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		comment.add(new JLabel(" Inserire i numeri spia nei tre campi a sinistra"));
		comment.add(new JLabel(" e i numeri da giocare nei cinque campi a destra"));
		comment.add(new JLabel(" "));
		
		fields.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		fieldsTwoLine.add(comment);
		fieldsTwoLine.add(fields);
		
		buttons.add(calc);
		buttons.add(exit);
		buttons.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		calc.addActionListener(this);
		exit.addActionListener(this);
		
		getContentPane().add(fieldsTwoLine, BorderLayout.NORTH);
		getContentPane().add(results, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
//		pack();
	}
	
	private String[] go(int[] spyNumbers, int[] playNumbers){

		FrequencyData fd = new FrequencyData(spyNumbers, playNumbers);
		Frequency frequency = new Frequency();
		
		String[] result = null;
		
		try {
			frequency.calculate(fd);
			result = frequency.getResultAsStrings();
		} catch (Exception ex) {
			Log.print(ex);
		}
		
		return result == null ? new String[]{"Errore nel calcolo"} : result;
	}
	
	private boolean isValidNumber(String str){
		boolean result = true;
		
		try {
			int n = Integer.parseInt(str);
			if(n<1 || n>90){
				result = false;
			}
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == calc) {
			int[] spyNumbers = new int[3];
			int[] playNumbers = new int[5];
			
			for (int ii = 0; ii < spy.length; ii++) {
				String s = spy[ii].getText();
				if(isValidNumber(s)){
					spyNumbers[ii] = Integer.parseInt(s);
				}else{
					JOptionPane.showMessageDialog(null, "Uno o piu' numeri inseriti non sono corretti",
							"Errore inserimento", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			for (int ii = 0; ii < play.length; ii++) {
				String s = play[ii].getText();
				if(isValidNumber(s)){
					playNumbers[ii] = Integer.parseInt(s);
				}else{
					JOptionPane.showMessageDialog(null, "Uno o piu' numeri inseriti non sono corretti",
							"Errore inserimento", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			String[] lines = go(spyNumbers, playNumbers);
			
			for (int ii = 0; ii < lines.length; ii++) {
				resLine[ii].setText(lines[ii]);
			}
			
		} else if (e.getSource() == exit) {
			setVisible(false);
		} 

	}

}
