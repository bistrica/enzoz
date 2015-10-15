package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import people.Employee;
import daos.EmployeeDAO;

public class DBHandler {

	private Employee currentUser;
	private Connection conn;
	private String host = "192.168.56.1"// "mysql.serversfree.com"//
										// "192.168.56.1",
			,
			login, password, dbname = "enzoz";//
												// "89.72.71.58",
	// login,
	// password;//
	// "192.168.0.14",
	// login, password;
	// // has�o? ?? 56.1
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
			dbh = new DBHandler();// login, pass);
		}
		dbh.login = login;
		// password = pass;
		return dbh.conn;
	}

	public static Connection getDatabaseConnection() {
		// return dbh.conn;
		// if (dbh.conn==null)
		// tryToConnect();
		/*
		 * try { System.out.println("**" + dbh.conn.isValid(0)); } catch
		 * (SQLException e) { System.out.println(">>>Not valid"); }
		 */
		return dbh.conn;

	}

	public static String getLogin() {
		return dbh.login;
	}

	public static boolean reconnect() {
		System.out.println("RECONNECT");
		// String login;
		String pass = "adolega";// "3nz0z_b^7a";
		String login = "adolega";// "u710003583_enzoz"; // to remove?

		boolean valid = false;
		try {
			valid = dbh.conn.isValid(0);
			System.out.println("VA: " + valid);
		} catch (SQLException e) {
			System.out.println(">>>Not valid");
		}
		valid = false; // hmhmhm
		int counter = 0;
		while (!valid && counter++ < 5) {
			try {
				Thread.sleep(1000);
				dbh.conn = DriverManager
						.getConnection(
								"jdbc:mysql://"
										+ dbh.host
										+ "/"
										+ dbh.dbname
										+ "?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&autoReconnect=true",
								// dbh.login, dbh.password);
								login, pass);
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

	private DBHandler() throws SQLException { // String login, String pass)
												// throws SQLException {

		// login = "a8323487_adolega";
		// pass = "adolega1";
		// this.login = login;
		// this.password = pass; // ?

		String login;
		String pass = "adolega";
		login = pass;// tochange/?

		this.conn = DriverManager
				.getConnection(
						"jdbc:mysql://"
								+ host
								+ "/"
								+ dbname
								+ "?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&autoReconnect=true"
								+ "&verifyServerCertificate=false&useSSL=true", // TODO
																				// ask
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

	// TODO: zdecydowa� si� z poni�szymi 2ma metodami
	public static Employee getCurrentUser() throws SQLException {

		EmployeeDAO employeeDAO = new EmployeeDAO();
		// if (dbh.currentUser == null)
		dbh.currentUser = employeeDAO.getEmployeeData(dbh.login);

		return dbh.currentUser;
	}

	public static Employee getUser() {
		return dbh.currentUser;
	}

	public static boolean isCriticalNoExceeded() {
		return trialsNo == criticalNo;
	}

	public static void incrementTrialsNo() {
		trialsNo++;
	}

	public static boolean isClosed() {
		return dbh == null || isClosed;
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
