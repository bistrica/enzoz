package exceptions;

public class TodayException extends Exception {

	//private String errorString="Nie mo�na utworzy� podgl�du wizyty.";
	
	
	public TodayException() {
		super("Nie mo�na pobra� dzisiejszych wizyt.");
	}
	/*@Override
	public String getMessage() {
		return errorString;
	}*/
}
