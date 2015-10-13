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
		String queryString = "SELECT idOsoby, imie, nazwisko, PESEL FROM Osoby WHERE idOsoby IN (SELECT IdOsoby FROM Lekarze)";
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String name = "", surname = "", PESEL = "";
		int id = -1;
		Doctor doctor = null;

		while (rs.next()) {
			id = rs.getInt("idOsoby");
			name = rs.getString("imie");
			surname = rs.getString("nazwisko");
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
