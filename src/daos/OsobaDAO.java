package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import people.Osoba;
import database.DBHandler;

public class OsobaDAO {

	private Connection conn;

	public OsobaDAO() {
		conn = DBHandler.getDatabaseConnection();
	}

	public Osoba getShortPersonData(int id) throws SQLException {
		Osoba person = null;

		PreparedStatement st;
		String queryString;
		String fname = "", lname = "", PESEL = "";

		// try {
		queryString = "SELECT imiê, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, kodPocztowy, miejscowoœæ, telefon FROM Osoby WHERE idOsoby=?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			fname = rs.getString("imiê");
			lname = rs.getString("nazwisko");
			PESEL = rs.getString("PESEL");

			break;
		}

		rs.close();
		st.close();
		person = new Osoba(id, fname, lname, PESEL);

		/*
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */
		return person;
	}

	public Osoba getPersonData(int id) throws SQLException {

		Osoba person = null;

		PreparedStatement st;
		String queryString;
		String fname = "", lname = "", PESEL = "", homeNo = "", flatNo = "", code = "", street = "", city = "", phone = "";

		// try {
		queryString = "SELECT imiê, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, kodPocztowy, miejscowoœæ, telefon FROM Osoby WHERE idOsoby=?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			fname = rs.getString("imiê");
			lname = rs.getString("nazwisko");
			PESEL = rs.getString("PESEL");
			street = rs.getString("ulica");
			homeNo = rs.getString("nrDomu");
			flatNo = rs.getString("nrMieszkania");
			code = rs.getString("kodPocztowy");
			city = rs.getString("miejscowoœæ");
			phone = rs.getString("telefon");

			break;
		}
		rs.close();
		st.close();

		person = new Osoba(id, fname, lname, PESEL, street, homeNo, flatNo,
				city, code, phone);

		/*
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */
		return person;
	}

}
