package exceptions;

public class BadDataException extends Exception {

	// private String
	// errorString="Wprowadzono b≥Ídne dane. Wprowadü dane ponownie.";

	public BadDataException() {
		super("Wprowadzono b≥Ídne dane. Wprowadü dane ponownie.");
	}
	/*
	 * @Override public String getMessage() {
	 * 
	 * return errorString;//super.getMessage(); }
	 */
}
