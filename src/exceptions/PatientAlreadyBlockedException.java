package exceptions;

public class PatientAlreadyBlockedException extends Exception {

	public PatientAlreadyBlockedException() {
		super("Dane wizyt pacjenta s� modyfikowane przez innego u�ytkownika.");
	}
}
