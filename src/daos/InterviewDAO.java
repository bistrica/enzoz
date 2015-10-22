package daos;

import items.Interview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBHandler;

public class InterviewDAO {

	public InterviewDAO() {

	}

	public Interview getInterview(int appId) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT Tresc FROM konsultacje WHERE IdWizyty = ? ORDER BY data DESC LIMIT 1";
		String interview = "";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			interview = rs.getString("Tresc");
			break;
		}
		rs.close();
		st.close();

		return new Interview(interview);
	}

	public void writeToDatabase(int appId, Interview interview)
			throws SQLException {

		if (interview == null)
			return;

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "INSERT INTO konsultacje (IdWizyty, Tresc) VALUES (?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		st.setString(2, interview.getContent());
		st.executeUpdate();
		st.close();

	}

}
