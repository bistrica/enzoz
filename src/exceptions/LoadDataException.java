package exceptions;

public class LoadDataException extends Exception {

	public LoadDataException() {
		super("Wyst¹pi³ b³¹d z pobraniem danych.");
	}
	
	public LoadDataException(String info) {
		super("Wyst¹pi³ b³¹d z pobraniem danych: "+info);
	}
}
