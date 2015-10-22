package daos;

import items.Illness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Patient;
import people.Person;
import database.DBHandler;

public class PatientDAO {

	private PersonDAO personDAO;
	private IllnessDAO illnessDAO;

	public PatientDAO() {

		personDAO = new PersonDAO();
		illnessDAO = new IllnessDAO();
	}

	public Patient getPatientData(int id) throws SQLException {

		Person person = personDAO.getPersonData(id);
		ArrayList<Illness> illnesses = getPatientConstantIllnesses(id);
		return new Patient(person, illnesses);

	}

	public ArrayList<Illness> getPatientConstantIllnesses(int id)
			throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		ArrayList<Illness> illnesses = new ArrayList<Illness>();
		PreparedStatement st;
		String queryString = "SELECT IdChoroby FROM chorobyprzewlekle WHERE IdPacjenta = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		int illId;
		while (rs.next()) {
			illId = rs.getInt("IdChoroby");
			Illness illness = null;
			illness = illnessDAO.getIllnessData(illId);
			illnesses.add(illness);
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
