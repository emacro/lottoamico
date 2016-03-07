package it.emacro.exceptions;

@SuppressWarnings("serial")
public class InvalidNumberException extends Exception {

	public InvalidNumberException() {
		super();
	}
	
	public InvalidNumberException(String message) {
		super(message);
	}
	
	public InvalidNumberException(String message, Throwable cause)  {
		super(message, cause);
	}
	
	public InvalidNumberException(Throwable cause)  {
		super(cause);
	}

}
