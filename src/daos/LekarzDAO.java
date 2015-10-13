package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Lekarz;
import database.DBHandler;

public class LekarzDAO {

	OsobaDAO personDAO;

	public LekarzDAO() {
		personDAO = new OsobaDAO();
	}

	public ArrayList<Lekarz> getDoctors() throws SQLException {

		// if (true)
		// throw new SQLException();
		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idOsoby, imie, nazwisko, PESEL FROM Osoby WHERE idOsoby IN (SELECT IdOsoby FROM Lekarze)";
		ArrayList<Lekarz> doctors = new ArrayList<Lekarz>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String name = "", surname = "", PESEL = "";
		int id = -1;
		Lekarz doctor = null;

		while (rs.next()) {
			id = rs.getInt("idOsoby");
			name = rs.getString("imie");
			surname = rs.getString("nazwisko");
			PESEL = rs.getString("PESEL");
			doctor = new Lekarz(id, name, surname, PESEL);
			doctors.add(doctor);
		}

		rs.close();
		st.close();
		// if (true) throw new SQLException();

		return doctors;
	}

}
