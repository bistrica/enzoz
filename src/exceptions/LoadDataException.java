package exceptions;

public class LoadDataException extends Exception {

	public LoadDataException() {
		super("Wyst�pi� b��d z pobraniem danych.");
	}
	
	public LoadDataException(String info) {
		super("Wyst�pi� b��d z pobraniem danych: "+info);
	}
}
