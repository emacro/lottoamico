/**
 * 
 */
package it.emacro.log;


/**
 * @author Emacro
 *
 */
public class Log {
	
	private Log() {
		super();
	}
	
	public static void println(String message) {
		println(message, null);
	}
	
	public static void print(Throwable e) {
		println(null, e);
	}
	
	public static void print(String message) {
		if(message!=null){
			System.out.print(message);
			LogFile.getInstance().print(message);
		}
	}
	
	public static void println(String message, Throwable e) {
		if(message!=null){
			System.out.println(message);
			LogFile.getInstance().println(message);
		}
		if(e!=null){
			e.printStackTrace();
			LogFile.getInstance().print(e);
		}
	}
}
