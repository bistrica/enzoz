package exceptions;

public class PreviewCannotBeCreatedException extends Exception {

//	private String errorString="Nie mo¿na utworzyæ podgl¹du wizyty.";
	
	public PreviewCannotBeCreatedException() {
		super("Nie mo¿na utworzyæ podgl¹du wizyty.");
	}
	/*@Override
	public String getMessage() {
		return errorString;
	}*/
}
