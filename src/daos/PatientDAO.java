package daos;

import items.Illness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Person;
import people.Patient;
import database.DBHandler;

public class PatientDAO {

	// Connection conn;

	PersonDAO personDAO;
	IllnessDAO illnessDAO;

	public PatientDAO() {
		// conn = DBHandler.getDatabaseConnection();
		personDAO = new PersonDAO();
		illnessDAO = new IllnessDAO();
	}

	public Patient getPatientData(int id) throws SQLException {

		// Connection conn = DBHandler.getDatabaseConnection();

		Person person = personDAO.getPersonData(id);
		ArrayList<Illness> illnesses = getPatientConstantIllnesses(id);
		// System.out.println("ill: " + illnesses);
		return new Patient(person, illnesses);

	}

	private ArrayList<Illness> getPatientConstantIllnesses(int id)
			throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		// System.out.println("GPCI");
		ArrayList<Illness> illnesses = new ArrayList<Illness>();
		PreparedStatement st;
		String queryString = "SELECT idChoroby FROM chorobyPrzewlekle WHERE idPacjenta = ?";// "SELECT idTypu FROM pracownicy WHERE login = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();// Update();
		int illId;
		while (rs.next()) {
			illId = rs.getInt("idChoroby");
			Illness illness = null;
			illness = illnessDAO.getIllnessData(illId);
			System.out.println("CH " + illness);
			illnesses.add(illness);
			// System.out.println("const: "+illness.getId());
		}
		rs.close();
		st.close();
		return illnesses;

	}

	public void writePatientConstantIllnesses(Patient patient)
			throws SQLException {
		if (patient == null)
			return;
		illnessDAO.writeConstantIllnesses(patient.getId(),
				patient.getConstantIllnesses());

	}

}
