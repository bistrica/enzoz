package exceptions;

public class PreviewCannotBeCreatedException extends Exception {

//	private String errorString="Nie mo�na utworzy� podgl�du wizyty.";
	
	public PreviewCannotBeCreatedException() {
		super("Nie mo�na utworzy� podgl�du wizyty.");
	}
	/*@Override
	public String getMessage() {
		return errorString;
	}*/
}
