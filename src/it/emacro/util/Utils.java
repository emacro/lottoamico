/**
 * 
 */
package it.emacro.util;

import it.emacro.manager.DatesManager;
import it.emacro.manager.ExplorerManager;
import it.emacro.manager.StorageManager;
import it.emacro.services.ApplicationData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @author Emc
 * 
 */
public class Utils implements Constants {

	/**
	 * 
	 */
	private Utils() {
		super();
	}

	public static String parseDate(String date) {
		String result = date;
		Calendar c = Calendar.getInstance();
		String[] val = date.split("-");

		c.set(Calendar.DAY_OF_MONTH, new Integer(val[2]));
		c.set(Calendar.MONTH, new Integer(val[1]) - 1);
		c.set(Calendar.YEAR, new Integer(val[0]));

		SimpleDateFormat formatter = new SimpleDateFormat(
				"EEEEEEEE d MMMMMMM yyyy");

		result = formatter.format(c.getTime());

		return result;
	}

	public static boolean contains(String[] array, String str) {
		if (array.length > 0) {
			for (int kk = 0; kk < array.length; kk++) {
				if (array[kk].equals(str)) {
					return true;
				}
			}
		}
		return false;
	}

//	public static int getNewCount(HttpServletRequest req) {
//		int count = 0;
//		String oldC = req.getParameter(COUNT);
//
//		if (oldC != null) {
//			count = new Integer(oldC);
//
//			if (req.getParameter(PREVIOUS) == null) {
//				if (req.getParameter(NEXT) == null) {
//					if (req.getParameter(APPLY) == null) {
//						int jump = 0;
//						if (req.getParameter(JUMP) != null) {
//							try {
//								jump = Integer.parseInt(req.getParameter(JUMP));
//							} catch (NumberFormatException ignore) {
//							}
//						}
//						if (req.getParameter(AVANTI) != null) {
//							count = new Integer(oldC) - jump;
//							if (count < 0)
//								count = 0;
//						} else {
//							if (req.getParameter(INDIETRO) != null) {
//								count = new Integer(oldC) + jump;
//								if (count >= getAllExtractionDates().length)
//									count = getAllExtractionDates().length - 1;
//								;
//							}
//						}
//					}
//				} else {
//					count = new Integer(oldC) - 1;
//					if (count < 0)
//						count = 0;
//				}
//			} else {
//				count = new Integer(oldC) + 1;
//				if (count >= getAllExtractionDates().length)
//					count--;
//			}
//		}
//		return count;
//	}

//	public static String[] getSerchValues(HttpServletRequest request,
//			int numOfSerched) {
//		List<String> list = new ArrayList<String>();
//		String value;
//
//		for (int jj = 0; jj < numOfSerched; jj++) {
//			value = request.getParameter("n" + (jj));
//			value = (value == null) ? "" : value.trim();
//			list.add(value);
//		}
//
//		return list.toArray(new String[list.size()]);
//	}

	public static String[] getAllExtractionDates() {
		return DatesManager.getInstance().getAllExtractionDates();
	}

	public static String[] getRuote() {
		return StorageManager.getInstance().getRuoteNames();
	}

	public static String getDate(int count) {
		return DatesManager.getInstance().getDate(count);
	}

	public static String getParsedDate(int count) {
		return parseDate(getDate(count));
	}

	public static int getCountOnCheck(int count) {
		int datesLength = getAllExtractionDates().length;
		return (count < datesLength) ? count : datesLength - 1;
	}

//	public static List<String[]> getExtraction(String date) {
//		return ExplorerManager.getInstance().getExtractionByDate(date);
//	}

	public static List<String[]> getExtraction(int count) {
		return ExplorerManager.getInstance().getExtraction(count);
	}

//	public static List<String[]> getExtractionByNumber(int extrNumber) {
//		return ExplorerManager.getInstance().getExtractionByNum(extrNumber);
//	}

//	public static String[] getDatesOrNull(HttpSession session) {
//		return (String[]) session.getAttribute(ALL_DATE);
//	}
//
//	public static boolean mustWriteNumbers(HttpServletRequest request,
//			String checkBoxName) {
//		return request.getParameter(IS_FIRST_RUN) == null
//				|| request.getParameter(checkBoxName) != null
//				&& !request.getParameter(checkBoxName).trim().isEmpty();
//	}

//	public static String getCheckboxState(HttpServletRequest request,
//			String name) {
//		String state = "";
//
//		// first time run
//		if (request.getParameter(Utils.IS_FIRST_RUN) == null) {
//			state = "checked=checked";
//		} else if (request.getParameter(name) != null) {
//			state = "checked=" + request.getParameter(name);
//		}
//
//		return state;
//	}

	public static int getNumOfExtractions() {
		return StorageManager.getInstance().getNumberOfExtractions();
	}

//	public static String getValue(HttpServletRequest request, String name) {
//		String result = request.getParameter(name);
//		return result == null ? "" : result;
//	}

	public static void dump(Object... elems) {
		for (Object s : elems) {
			System.out.print(s + " ");
		}
		System.out.print("\n");
	}

	public static boolean isNumber(String n) {
		try {
			new Integer(n);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String getLastExtractionDate() {
		return ApplicationData.getInstance().getLastExtractionDate();

	}

	public static String getTimeAsString(long millis) {
		long hours = millis / 3600000;
		long minutes = (millis - hours * 3600000) / 60000;
		long seconds = ((millis - hours * 3600000) % 60000) / 1000;
		String mm, ss;
		mm = minutes < 10 ? "0" + String.valueOf(minutes) : String
				.valueOf(minutes);
		ss = seconds < 10 ? "0" + String.valueOf(seconds) : String
				.valueOf(seconds);
		return new StringBuffer(String.valueOf(hours)).append(":").append(mm)
				.append(":").append(ss).toString();
	}
	
	public static boolean areAllTrue(boolean[] arg){
		boolean result = true;
		
		for(int ii=0;ii<arg.length;ii++){
			if(arg[ii]==false){
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	public static boolean areElementsTrue(boolean[] arg, int numbOfElements){
		boolean result = false;
		int test = 0;
		
		for(int ii=0;ii<arg.length; ii++){
			if(arg[ii]==true){
				test++;
			}
			
			if(test >= numbOfElements){
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public static String firstLetterUpperCase(String s) {
		String res;
		if (s.length() > 1) {
			res = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}else{
			res = s.toUpperCase();
		}
		return res;
	}
	
	public static void showWarningMessage(String title, String message){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showErrorMessage(String title, String message){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showInformationMessage(String title, String message){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
