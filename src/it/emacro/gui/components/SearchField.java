/**
 * 
 */
package it.emacro.gui.components;

import it.emacro.exceptions.InvalidNumberException;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Emc
 *
 */
@SuppressWarnings("serial")
public class SearchField extends JTextField {
	
	public SearchField() {
		super();
	}
	
	public SearchField(int n) {
		super(n);
	}
	
	public String[] splitContentNumbers() throws InvalidNumberException {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(getText(),"."); 
		String n;
		
		while(st.hasMoreTokens()){
			n = st.nextToken();
			if(Integer.parseInt(n)>90 || Integer.parseInt(n)<1){
				throw new InvalidNumberException();
			}
			list.add(n);
		}
		return list.toArray(new String[list.size()]);
	}
	
	public void setEnabled(boolean e){
//		setText("");
		setBackground(e ? Color.WHITE : new Color(222,222,222));
		setEditable(e);
	}
	
	protected Document createDefaultModel() {
		return new MaxLengthDocument();
	}

//	Il Document che gestisce l'inserimento del testo
	class MaxLengthDocument extends PlainDocument {

		// controlla che il testo non sia diverso da un numero o un punto
		public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {

			if (str == null)
				return;
			
			if( !isNumberOrPoint(str) ){
				str = str.substring(0,str.length()-1);
			}

			super.insertString(offs, str, a);
		}
	}
	
	private boolean isNumberOrPoint(String n){
		if(n.equals(".")) return true;
		try {
			new Integer(n);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
