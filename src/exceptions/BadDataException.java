package exceptions;

public class BadDataException extends Exception {

	// private String
	// errorString="Wprowadzono b��dne dane. Wprowad� dane ponownie.";

	public BadDataException() {
		super("Wprowadzono b��dne dane. Wprowad� dane ponownie.");
	}
	/*
	 * @Override public String getMessage() {
	 * 
	 * return errorString;//super.getMessage(); }
	 */
}
