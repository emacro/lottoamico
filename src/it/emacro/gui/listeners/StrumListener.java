/**
 * 
 */
package it.emacro.gui.listeners;

import it.emacro.distances.CouplesHandler;
import it.emacro.extractor.util.FileUtils;
import it.emacro.gui.components.FrequencyWindow;
import it.emacro.log.Log;
import it.emacro.services.ApplicationData;
import it.emacro.util.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @author Emacro
 *
 */
public class StrumListener implements ActionListener {

	public StrumListener() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		String c = e.getActionCommand();
		
		if ( c.equals(Constants.CALC_PRES) ){
			FrequencyWindow fw = new FrequencyWindow();
			fw.setVisible(true);
		} else if( c.equals(Constants.FORCE_NEXT_DWL) ){
			ApplicationData.getInstance().setForceExtractionReading(true);
			
			String mess = "Al prossimo avvio dell'aplicazione partira' una nuova procedura di download delle estrazioni.";
			JOptionPane.showMessageDialog(null, mess, "Forzatura download", JOptionPane.INFORMATION_MESSAGE);
		}else if ( c.equals(Constants.CALC_DISTANCES) ){
			distancesAction();
		}else{
			noAction();
		}
		
	}
	
	private void distancesAction() {
		CouplesHandler ch =new CouplesHandler();
		String date = ch.getExtractionDateOfCalculation();
		String output = "L'ultimo numero delle quartine e' l'ambata principale.";
		output += "\nI numeri sono da giocare per massimo 9 estrazioni.\n\n";
		output += ch.getOutput();
		String title = "Calcolo distanze per l'estrazione del: " + date;
		String wantSave = "\nVuoi salvare l'esito?";
		int confirm = JOptionPane.showConfirmDialog(null, output + wantSave , title , JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			String mess;
			try {
				List<String> los = new ArrayList<String>();
				los.add(output);
				File file = new File("../distanze_metriche/" + date + ".txt");
				FileUtils.writeFile(file , los);
				mess = "L'esito e' stato salvato nel file: " + file.getCanonicalPath();
				JOptionPane.showMessageDialog(null, mess, "Esito salvato", JOptionPane.INFORMATION_MESSAGE);
				deleteOldDistanzeMetricheFiles();
			} catch (Exception e) {
				mess = "A causa di un'errore non e' stato possibile salvare l'esito del calcolo" +
				"\n controllare l'eccezione nella console.";
				JOptionPane.showMessageDialog(null, mess, "Errore", JOptionPane.INFORMATION_MESSAGE);
				Log.print(e);
			}
		}
	}
	
	private void noAction() {
		String mess = "Nessuna azione assegata a questo tasto.";
		JOptionPane.showMessageDialog(null, mess, "Errore", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void deleteOldDistanzeMetricheFiles() {
		List<File> files = FileUtils.getFiles("../distanze_metriche/", "txt");
		long month = 2592000000L;// one month
		long maxOld = Calendar.getInstance().getTimeInMillis() - month ;
		for (File file : files) {
			if (file.lastModified() < maxOld) {
				file.delete();
			}
		}
	}

}
