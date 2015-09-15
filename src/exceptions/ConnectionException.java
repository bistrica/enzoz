package exceptions;

public class ConnectionException extends Exception {

	// private String
	// errorString="Wyst¹pi³ b³¹d w po³¹czeniu. Spróbuj ponownie póŸniej.";

	public ConnectionException() {
		super("Wyst¹pi³ b³¹d w po³¹czeniu. Spróbuj ponownie póŸniej.");
	}

	/*
	 * @Override public String getMessage() {
	 * 
	 * return errorString;//super.getMessage(); }
	 */
}
