/**
 *	DlgAbout.java
 *	Scritta per ilsoftware.it
 *
 *  Commentiamo i punti salienti di questa classe, dato che non l'abbiamo fatto 
 *  nelle pagine web. Innanzitutto, si noti come abbiamo creato, a parte il 
 *  pulsante e le etichette (JLabel) anche due pannelli, che sono in effetti due
 *  containers, per meglio gestire il layout della finestra.
 *
 *  Per quanto riguarda il setLocation, quello che è stato utilizzato è semplicemente
 *  il metodo più semplice per ottenere la finestra al centro dello schermo, 
 *  per cui non spaventatevi se sembra lungo. Si tratta solo di recuperare la grandezza
 *  dello schermo tramite la classe Toolkit, che si rivela estremamente potente.
 *
 *  Si noti, infine, l'uso di BorderLayout che è uno dei principali gestori
 *  di layout di Java. Una conoscenza esaustiva di tutti i gestori di layout richiederebbe
 *  un intero libro, noi diremmo di volta in volta ciò che si serve. In questo caso,
 *  il lettore deve sapere che BorderLayout divide l'area della finestra in 5 sezioni,
 *  nord, sud, est, ovest e centro. Mediante le opportune costanti è abbastanza 
 *  semplice posizionare gli elementi con BorderLayout, che si rivela un'ottima
 *  scelta finchè gli elementi non sono troppo numerosi.  
 *
 *  Notate inoltre che al click di "ok" non chiamiamo exit, dato che non vogliamo
 *  che l'applicazione termini, ma dispose() sull'oggetto DlgAbout, in modo che  
 *  venga chiusa la finestra di about.
 *
 *	@author Davide Marche
 *  @author Emacro
 */ 

package it.emacro.gui.listeners;

import it.emacro.gui.components.CustomFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial") 
public class DlgAbout extends JDialog implements ActionListener{
	
	private JLabel appName = new JLabel("Lotto amico 2.0");
	private JLabel developer = new JLabel("realizzato da Emacro (emacro@email.it)");
	private JLabel date = new JLabel("ago'07 (rev. dic'07, gen'08, gen'09)");
	
	private JButton ok = new JButton("Ok");
	
	private JPanel up = new JPanel();
	private JPanel down = new JPanel();
	
	public DlgAbout(JFrame parent){
		
		super(parent, "Informazioni applicazione");
		setSize(290,180);
		setModal(true);
		// Centriamo la finestra sullo schermo
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2;
		setLocation(x ,y);
				    
		appName.setFont(CustomFont.BIG_BOLD);
		developer.setFont(CustomFont.MIDDLE_LITTLE_ITALIC);
		date.setFont(CustomFont.MIDDLE_LITTLE_PLAIN);
		
		up.add(appName);
		up.add(developer);
		up.add(date);
		
		down.add(ok);
		ok.addActionListener(this);
				    
		this.getContentPane().add(up, BorderLayout.CENTER);
		this.getContentPane().add(down, BorderLayout.SOUTH);
	}
	
	public void actionPerformed (ActionEvent e){
		this.dispose();
	}
}
