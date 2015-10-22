package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import people.Employee;
import daos.EmployeeDAO;

public class DBHandler {

	private Employee currentUser;
	private Connection conn;
	private String host = // "alexx.linuxpl.eu"// "185.25.148.252"//
							// "virt1452.wirt-03.netdc.pl"//
			"192.168.56.1",
			login, password, dbname = "enzoz", prefix = "";// "alexx_";//
															// "virt1452_";//

	private static DBHandler dbh = null;
	private static int trialsNo = 0;
	private static int criticalNo = 5;

	public static int counter = 1;

	private static boolean isClosed = false;

	public static void resetTrialsNo() {
		trialsNo = 0;
	}

	public static Connection createAndGetDatabaseConnection(String login,
			String pass) throws SQLException {
		if (dbh == null) {
			dbh = new DBHandler(login, pass);
		}
		dbh.login = login;
		dbh.password = pass;
		return dbh.conn;
	}

	public static Connection getDatabaseConnection() {
		return dbh.conn;

	}

	public static String getLogin() {
		return dbh.prefix + dbh.login;
	}

	public static boolean reconnect() {

		boolean valid = false;

		int counter = 0;
		while (!valid && counter++ < 5) {
			try {
				Thread.sleep(1000);
				dbh.conn = DriverManager
						.getConnection(
								"jdbc:mysql://"
										+ dbh.host
										+ "/"
										+ dbh.prefix
										+ dbh.dbname
										+ "?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&autoReconnect=true",

								dbh.prefix + dbh.login, dbh.password);
				valid = dbh.conn.isValid(0);

			} catch (SQLException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		setClosed(!valid); // ?
		return valid;// dbh.conn;

	}

	private DBHandler(String login, String pass) throws SQLException {

		this.login = login;
		this.password = pass;

		this.conn = DriverManager
				.getConnection(
						"jdbc:mysql://"
								+ host
								+ "/"
								+ prefix
								+ dbname
								+ "?useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&autoReconnect=true"
								+ "&verifyServerCertificate=false&useSSL=true",
						prefix + login, pass);

	}

	// TODO: zdecydowaæ siê z poni¿szymi 2ma metodami
	public static Employee getCurrentUser() throws SQLException {

		EmployeeDAO employeeDAO = new EmployeeDAO();
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
			e.printStackTrace();
		}
	}

}
