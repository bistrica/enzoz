package daos;

import items.Poradnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class PoradniaDAO {

	// Connection conn;

	public PoradniaDAO() {
		// conn = DBHandler.getDatabaseConnection();
	}

	public ArrayList<Poradnia> getAllClinics() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idPoradni, nazwa FROM poradnie";
		ArrayList<Poradnia> clinics = new ArrayList<Poradnia>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String name = "";
		int id = -1;
		Poradnia clinic = null;

		while (rs.next()) {
			id = rs.getInt("idPoradni");
			name = rs.getString("nazwa");

			clinic = new Poradnia(id, name);
			clinics.add(clinic);
		}

		rs.close();
		st.close();
		return clinics;

	}

	public Poradnia getClinic(int id) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		// if (true) throw new SQLException();

		PreparedStatement st;
		String queryString = "SELECT nazwa FROM poradnie WHERE idPoradni = ?";
		Poradnia clinic = null;
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		String name = "";

		while (rs.next()) {
			name = rs.getString("nazwa");

			clinic = new Poradnia(id, name);
			break;
		}

		rs.close();
		st.close();
		return clinic;

	}

}
