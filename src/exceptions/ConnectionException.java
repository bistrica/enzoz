package exceptions;

public class ConnectionException extends Exception {

	// private String
	// errorString="Wyst�pi� b��d w po��czeniu. Spr�buj ponownie p�niej.";

	public ConnectionException() {
		super("Wyst�pi� b��d w po��czeniu. Spr�buj ponownie p�niej.");
	}

	/*
	 * @Override public String getMessage() {
	 * 
	 * return errorString;//super.getMessage(); }
	 */
}
