package exceptions;

public class DataCannotBeEditedException extends Exception {

	public DataCannotBeEditedException() {
		super("Dane nie mog� by� edytowane.");
	}
}
