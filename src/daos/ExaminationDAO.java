package daos;

import items.Clinic;
import items.Examination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class ExaminationDAO {

	// Connection conn;
	ClinicDAO clinicsDAO;

	public ExaminationDAO() {
		// conn = DBHandler.getDatabaseConnection();
		clinicsDAO = new ClinicDAO();
	}

	public ArrayList<Examination> getExaminations(int appId)
			throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		ArrayList<Examination> examinations = new ArrayList<Examination>();

		// if (true) throw new SQLException();

		PreparedStatement st;
		String queryString = "SELECT idSkierowania FROM skierowania WHERE idWizyty = ? ORDER BY data DESC LIMIT 1";
		int idExam = -1;

		// try {
		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			idExam = rs.getInt("idSkierowania");
			break;
		}

		queryString = "SELECT idPoradni, komentarz FROM pozycjeNaSkierowaniach WHERE idSkierowania = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, idExam);

		rs = st.executeQuery();

		while (rs.next()) {
			Clinic clinic = clinicsDAO.getClinic(rs.getInt("idPoradni"));
			Examination examination = new Examination(clinic,
					rs.getString("komentarz"));
			System.out.println("::>" + examination.getDescription());
			examinations.add(examination);
		}

		rs.close();
		st.close();

		return examinations;
	}

	public void writeToDatabase(int appId, ArrayList<Examination> exams)
			throws SQLException {
		if (exams == null)
			return;

		// boolean autoCommit = conn.getAutoCommit();
		// conn.setAutoCommit(false);

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "INSERT INTO skierowania (idWizyty) VALUES (?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		st.executeUpdate();

		queryString = "SELECT idSkierowania FROM skierowania WHERE idWizyty = ? ORDER BY data DESC LIMIT 1 ";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();

		int examId = -1;
		while (rs.next()) {
			examId = rs.getInt(1);
			break;
		}
		rs.close();

		queryString = "INSERT INTO pozycjeNaSkierowaniach (idSkierowania, idPoradni, komentarz) VALUES (?,?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, examId); // to check

		for (Examination pos : exams) {
			st.setInt(2, pos.getClinic().getId());
			st.setString(3, pos.getDescription());
			st.addBatch();
		}

		st.executeBatch();
		// conn.commit();
		st.close();
		// conn.setAutoCommit(autoCommit);

	}

}
