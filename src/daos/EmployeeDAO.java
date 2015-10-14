package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import people.Doctor;
import people.Employee;
import people.Person;
import database.DBHandler;

public class EmployeeDAO {

	PersonDAO personDAO;

	// Connection conn;

	public EmployeeDAO() {
		personDAO = new PersonDAO();
		// conn = DBHandler.getDatabaseConnection();
	}

	public boolean userExists(String login, String hashedPass)
			throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		String queryString = "SELECT COUNT(*) AS ile FROM pracownicy WHERE login = ? and haslo = ?";// "SELECT idTypu FROM pracownicy WHERE login = ?";

		PreparedStatement st = conn.prepareStatement(queryString);
		st.setString(1, login);
		st.setString(2, hashedPass);
		ResultSet rs = st.executeQuery();// Update();
		int count = -1;
		while (rs.next()) {
			count = rs.getInt("ile");
			break;
		}

		return count == 1;

	}

	public Employee getEmployeeData(String login) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idOsoby, nazwa FROM pracownicy p JOIN typyPracownikow t ON t.idTypu=p.idTypu WHERE p.login = ?";// "SELECT idTypu FROM pracownicy WHERE login = ?";
		String type = "", doctor = "lekarz", receptionist = "rejestratorka", admin = "administrator";
		int personId = -1;

		Employee employee = null;
		Person person = null;

		// try {
		st = conn.prepareStatement(queryString);
		st.setString(1, login);
		ResultSet rs = st.executeQuery();// Update();
		while (rs.next()) {
			type = rs.getString("nazwa");
			personId = rs.getInt("idOsoby");
			break;
		}

		person = personDAO.getPersonData(personId);

		// sprawdü, czy to lekarz
		if (type.equals(doctor)) {

			int PWZ = -1;
			queryString = "SELECT PWZ FROM Lekarze WHERE idOsoby=?";
			st = conn.prepareStatement(queryString);
			st.setInt(1, personId);
			rs = st.executeQuery();
			while (rs.next()) {
				PWZ = rs.getInt("PWZ");
				break;
			}

			employee = new Doctor(person, PWZ);
		} else {
			// TODO or not todo
		}

		rs.close();
		st.close();

		return employee;
	}
}
