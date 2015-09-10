package exceptions;

public class TodayException extends Exception {

	//private String errorString="Nie mo¿na utworzyæ podgl¹du wizyty.";
	
	
	public TodayException() {
		super("Nie mo¿na pobraæ dzisiejszych wizyt.");
	}
	/*@Override
	public String getMessage() {
		return errorString;
	}*/
}
