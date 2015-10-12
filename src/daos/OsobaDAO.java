package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import people.Osoba;
import database.DBHandler;

public class OsobaDAO {

	// private Connection conn;

	public OsobaDAO() {
		// conn = DBHandler.getDatabaseConnection();
	}

	public Osoba getShortPersonData(int id) throws SQLException {
		Connection conn = DBHandler.getDatabaseConnection();

		Osoba person = null;

		PreparedStatement st;
		String queryString;
		String fname = "", lname = "", PESEL = "";

		// try {
		queryString = "SELECT imie, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, kodPocztowy, miejscowosc, telefon FROM Osoby WHERE idOsoby=?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			fname = rs.getString("imie");
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

		Connection conn = DBHandler.getDatabaseConnection();
		Osoba person = null;

		PreparedStatement st;
		String queryString;
		String fname = "", lname = "", PESEL = "", homeNo = "", flatNo = "", code = "", street = "", city = "", phone = "";

		// try {
		queryString = "SELECT imie, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, kodPocztowy, miejscowosc, telefon FROM Osoby WHERE idOsoby=?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			fname = rs.getString("imie");
			lname = rs.getString("nazwisko");
			PESEL = rs.getString("PESEL");
			street = rs.getString("ulica");
			homeNo = rs.getString("nrDomu");
			flatNo = rs.getString("nrMieszkania");
			code = rs.getString("kodPocztowy");
			city = rs.getString("miejscowosc");
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

	public int getPersonId(long pESEL) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		String queryString = "SELECT idOsoby FROM Osoby WHERE PESEL=?";
		PreparedStatement st = conn.prepareStatement(queryString);
		st.setLong(1, pESEL);
		ResultSet rs = st.executeQuery();
		int id = -1;
		while (rs.next()) {
			id = rs.getInt("idOsoby");
		}
		return id;

	}

}
