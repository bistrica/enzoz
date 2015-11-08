package login;

import java.sql.SQLException;

import people.Employee;
import daos.EmployeeDAO;
import database.DBHandler;
import exceptions.BadDataException;
import exceptions.ConnectionException;

public class LoginDBH {

	// private Connection conn = null;
	private EmployeeDAO employeeDAO;
	private String hashingFailedString = "Wyst¹pi³ b³¹d z szyfrowaniem. Skontaktuj siê z administratorem.";

	public Employee tryToLog(String login, String pass)
			throws ConnectionException, BadDataException {

		Employee user = null;
		employeeDAO = new EmployeeDAO();

		try {

			// conn =
			DBHandler.createAndGetDatabaseConnection(login, pass);

			if (employeeDAO.userExists(login))// , hashedPass))
				user = DBHandler.getCurrentUser();
			else
				throw new BadDataException();

		} catch (SQLException ex) {
			System.out.println(ex.getErrorCode() + " :: " + ex.getMessage());

			if (ex.getErrorCode() == 1045)
				throw new BadDataException();
			else
				throw new ConnectionException();

		}

		return user;
	}

	/*
	 * private String encrypt(String pass) {
	 * 
	 * MessageDigest md; try { md = MessageDigest.getInstance("SHA-512"); }
	 * catch (NoSuchAlgorithmException e) {
	 * 
	 * return null; }
	 * 
	 * byte[] b = pass.getBytes(Charset.forName("UTF-8")); md.update(b);
	 * 
	 * byte[] mdbytes = md.digest();
	 * 
	 * StringBuffer sb = new StringBuffer(); for (int i = 0; i < mdbytes.length;
	 * i++) { sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
	 * .substring(1)); }
	 * 
	 * return sb.toString(); }
	 */

}
