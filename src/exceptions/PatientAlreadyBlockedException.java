package exceptions;

public class PatientAlreadyBlockedException extends Exception {

	public PatientAlreadyBlockedException() {
		super("Dane wizyt pacjenta s¹ modyfikowane przez innego u¿ytkownika.");
	}
}
