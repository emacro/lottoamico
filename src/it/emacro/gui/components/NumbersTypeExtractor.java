/**
 * 
 */
package it.emacro.gui.components;

import it.emacro.gui.panels.NumbersType;
import it.emacro.log.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emc
 * 
 */
public class NumbersTypeExtractor implements NumbersType {

	/**
	 * 
	 */
	private NumbersTypeExtractor() {
		super();
	}

	public static String[] getNumbers(String type) {
		String[] t = getType(type);
		String[] result = null;

		try {
			Method m = NumbersTypeExtractor.class.getMethod("get" + t[0],
					Integer.class);
			result = (String[]) m.invoke(null, new Integer(t[1]));
		} catch (Exception e) {
			Log.print(e);
		}
		return result;
	}

	// ------------ methods called via reflection -------------

	public static String[] getGemelli(Integer idx) {
		String[] g = { "11", "22", "33", "44", "55", "66", "77", "88" };
		return g;
	}

	public static String[] getPari(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 1; nn <= 90; nn++) {
			if (nn % 2 == 0)
				list.add(String.valueOf(nn));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] getDispari(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 1; nn <= 90; nn++) {
			if (nn % 2 != 0)
				list.add(String.valueOf(nn));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] get1_30(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 1; nn <= 30; nn++) {
			list.add(String.valueOf(nn));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] get31_60(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 31; nn <= 60; nn++) {
			list.add(String.valueOf(nn));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] get61_90(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 61; nn <= 90; nn++) {
			list.add(String.valueOf(nn));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] getCadenza(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 1; nn <= 90; nn++) {
			if (String.valueOf(nn).endsWith(String.valueOf(idx))) {
				list.add(String.valueOf(nn));
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] getDecina(Integer idx) {
		List<String> list = new ArrayList<String>();
		for (int nn = 1; nn <= 90; nn++) {
			if (idx == 0 && nn < 10) {
				list.add(String.valueOf(nn));

			} else if (String.valueOf(nn).length()>1 && 
					String.valueOf(nn).startsWith(String.valueOf(idx))) {
				
				list.add(String.valueOf(nn));
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] getFigura(Integer idx) {
		List<String> list = new ArrayList<String>();
		int n1, n2;
		for (int nn = 1; nn <= 90; nn++) {
			if (nn > 9) {
				n1 = new Integer(String.valueOf(nn).substring(0, 1));
				n2 = new Integer(String.valueOf(nn).substring(1));
				if (getFiguraOfNumber(n1 + n2) == idx) {
					list.add(String.valueOf(nn));
				}
			} else {
				if (nn == idx) {
					list.add(String.valueOf(nn));
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] getCifra(Integer idx) {
		List<String> list = new ArrayList<String>();
		int n1, n2;
		for (int nn = 1; nn <= 90; nn++) {
			if (nn > 9) {
				n1 = new Integer(String.valueOf(nn).substring(0, 1));
				n2 = new Integer(String.valueOf(nn).substring(1));
				if (n1 == idx || n2 == idx) {
					list.add(String.valueOf(nn));
				}
			} else {
				if (nn == idx) {
					list.add(String.valueOf(nn));
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}

	// ------------------ private methods ----------------

	private static String[] getType(String type) {
		if (type.equals(N_1_30) || type.equals(N_31_60) || type.equals(N_61_90)) {
			type = type.replaceAll("-", "_");
		}

		String[] result = new String[2];
		String[] t = type.split(" ");
		result[0] = t[0];
		result[1] = t.length < 2 ? "0" : t[1];

		return result;
	}

	private static int getFiguraOfNumber(int nn) {
		int n1, n2;

		if (nn > 9) {
			n1 = new Integer(String.valueOf(nn).substring(0, 1));
			n2 = new Integer(String.valueOf(nn).substring(1));
			nn = getFiguraOfNumber(n1 + n2);
		}

		return nn;
	}

}
