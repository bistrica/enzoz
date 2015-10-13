package daos;

import items.Clinic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class ClinicDAO {

	// Connection conn;

	public ClinicDAO() {
		// conn = DBHandler.getDatabaseConnection();
	}

	public ArrayList<Clinic> getAllClinics() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idPoradni, nazwa FROM poradnie";
		ArrayList<Clinic> clinics = new ArrayList<Clinic>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String name = "";
		int id = -1;
		Clinic clinic = null;

		while (rs.next()) {
			id = rs.getInt("idPoradni");
			name = rs.getString("nazwa");

			clinic = new Clinic(id, name);
			clinics.add(clinic);
		}

		rs.close();
		st.close();
		return clinics;

	}

	public Clinic getClinic(int id) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		// if (true) throw new SQLException();

		PreparedStatement st;
		String queryString = "SELECT nazwa FROM poradnie WHERE idPoradni = ?";
		Clinic clinic = null;
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		String name = "";

		while (rs.next()) {
			name = rs.getString("nazwa");

			clinic = new Clinic(id, name);
			break;
		}

		rs.close();
		st.close();
		return clinic;

	}

}
