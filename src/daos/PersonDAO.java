package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import people.Person;
import database.DBHandler;

public class PersonDAO {

	public PersonDAO() {

	}

	public Person getShortPersonData(int id) throws SQLException {
		Connection conn = DBHandler.getDatabaseConnection();

		Person person = null;

		PreparedStatement st;
		String queryString;
		String fname = "", lname = "", PESEL = "";

		queryString = "SELECT Imie, Nazwisko, PESEL, Ulica, NrDomu, NrMieszkania, KodPocztowy, Miejscowosc, Telefon FROM osoby WHERE IdOsoby=?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			fname = rs.getString("Imie");
			lname = rs.getString("Nazwisko");
			PESEL = rs.getString("PESEL");

			break;
		}

		rs.close();
		st.close();
		person = new Person(id, fname, lname, PESEL);

		return person;
	}

	public Person getPersonData(int id) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		Person person = null;

		PreparedStatement st;
		String queryString;
		String fname = "", lname = "", PESEL = "", homeNo = "", flatNo = "", code = "", street = "", city = "", phone = "";

		queryString = "SELECT Imie, Nazwisko, PESEL, Ulica, NrDomu, NrMieszkania, KodPocztowy, Miejscowosc, Telefon FROM osoby WHERE IdOsoby=?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			fname = rs.getString("Imie");
			lname = rs.getString("Nazwisko");
			PESEL = rs.getString("PESEL");
			street = rs.getString("Ulica");
			homeNo = rs.getString("NrDomu");
			flatNo = rs.getString("NrMieszkania");
			code = rs.getString("KodPocztowy");
			city = rs.getString("Miejscowosc");
			phone = rs.getString("Telefon");

			break;
		}
		rs.close();
		st.close();

		person = new Person(id, fname, lname, PESEL, street, homeNo, flatNo,
				city, code, phone);

		return person;
	}

	public int getPersonId(long pESEL) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		String queryString = "SELECT IdOsoby FROM osoby WHERE PESEL=?";
		PreparedStatement st = conn.prepareStatement(queryString);
		st.setLong(1, pESEL);
		ResultSet rs = st.executeQuery();
		int id = -1;
		while (rs.next()) {
			id = rs.getInt("IdOsoby");
		}
		return id;

	}

}
