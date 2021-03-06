package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Doctor;
import database.DBHandler;

public class DoctorDAO {

	PersonDAO personDAO;

	public DoctorDAO() {
		personDAO = new PersonDAO();
	}

	public ArrayList<Doctor> getDoctors() throws SQLException {

		// if (true)
		// throw new SQLException();
		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT IdOsoby, Imie, Nazwisko, PESEL FROM osoby WHERE IdOsoby IN (SELECT idOsoby FROM lekarze)";
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String name = "", surname = "", PESEL = "";
		int id = -1;
		Doctor doctor = null;

		while (rs.next()) {
			id = rs.getInt("IdOsoby");
			name = rs.getString("Imie");
			surname = rs.getString("Nazwisko");
			PESEL = rs.getString("PESEL");
			doctor = new Doctor(id, name, surname, PESEL);
			doctors.add(doctor);
		}

		rs.close();
		st.close();
		// if (true) throw new SQLException();

		return doctors;
	}

}
