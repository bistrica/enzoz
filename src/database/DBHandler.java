package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import people.Pracownik;
import daos.PracownikDAO;

public class DBHandler {

	private Pracownik currentUser;
	private Connection conn;
	private String host = "192.168.56.1", login, password; // has³o? ??
	private static DBHandler dbh = null;
	private static int trialsNo = 0;
	private static int criticalNo = 5;

	public static int counter = 1;
	/*
	 * public DBHandler(Connection conn) { this.conn=conn; }
	 */
	private static boolean isClosed = false;

	public static void resetTrialsNo() {
		trialsNo = 0;
	}

	public static Connection createAndGetDatabaseConnection(String login,
			String pass) throws SQLException {
		if (dbh == null) {
			dbh = new DBHandler(login, pass);
		}
		return dbh.conn;
	}

	public static Connection getDatabaseConnection() {
		// return dbh.conn;
		// if (dbh.conn==null)
		// tryToConnect();
		try {
			System.out.println("**" + dbh.conn.isValid(0));
		} catch (SQLException e) {
			System.out.println(">>>Not valid");
		}
		return dbh.conn;

	}

	public static String getLogin() {
		return dbh.login;
	}

	public static boolean reconnect() {
		System.out.println("RECONNECT");
		boolean valid = false;
		try {
			valid = dbh.conn.isValid(0);
			System.out.println("VA: " + valid);
		} catch (SQLException e) {
			System.out.println(">>>Not valid");
		}
		int counter = 0;
		while (!valid && counter++ < 5) {
			try {
				Thread.sleep(1000);
				dbh.conn = DriverManager
						.getConnection(
								"jdbc:mysql://"
										+ dbh.host
										+ "/enzoz?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&autoReconnect=true",
								dbh.login, dbh.password);

				valid = dbh.conn.isValid(0);

			} catch (SQLException e) {
				System.out.println("SQL T");
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				System.out.println("INTER T");
				e.printStackTrace();
				break;
			}
		}
		setClosed(valid);
		return valid;// dbh.conn;

	}

	private DBHandler(String login, String pass) throws SQLException {

		this.login = login;
		this.password = pass; // ?
		this.conn = DriverManager
				.getConnection(
						"jdbc:mysql://"
								+ host
								+ "/enzoz?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&autoReconnect=true",
						login, pass);

		// tryToConnect();
	}

	/*
	 * private void tryToConnect() { this.conn=DriverManager.getConnection(
	 * "jdbc:mysql://"
	 * +host+"/enzoz?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true"
	 * , login, password);
	 * 
	 * }
	 */

	public static Pracownik getCurrentUser() throws SQLException {

		PracownikDAO employeeDAO = new PracownikDAO();
		if (dbh.currentUser == null)
			dbh.currentUser = employeeDAO.getEmployeeData(dbh.login);

		return dbh.currentUser;
	}

	public static boolean isCriticalNoExceeded() {
		return trialsNo == criticalNo;
	}

	public static void incrementTrialsNo() {
		trialsNo++;
	}

	public static boolean isClosed() {
		return isClosed;
	}

	public static void setClosed(boolean closed) {
		isClosed = closed;
	}

	public static void close() {
		setClosed(true);
		try {
			dbh.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
