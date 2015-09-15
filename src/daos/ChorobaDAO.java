package daos;

import items.Choroba;
import items.Wizyta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Pacjent;
import database.DBHandler;

public class ChorobaDAO {

	private static Connection conn;

	public ChorobaDAO() {
		conn = DBHandler.getDatabaseConnection();
	}

	public Choroba getIllnessData(int id) throws SQLException {

		PreparedStatement st;
		String queryString = "SELECT kod, nazwa FROM choroby WHERE idChoroby = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		String code = "", name = "";

		Choroba illness = null;
		while (rs.next()) {
			code = rs.getString("kod");
			name = rs.getString("nazwa");
			illness = new Choroba(id, code, name);
			break;
		}

		rs.close();
		st.close();

		// if (true) throw new SQLException();
		/*
		 * if (DBHandler.counter++%4!=0) throw new SQLException();
		 * System.out.println("Classic return");
		 */

		return illness;
	}

	public ArrayList<Choroba> getIllnessData(ArrayList<Integer> ids)
			throws SQLException {

		PreparedStatement st;
		String queryString = "SELECT kod, nazwa FROM choroby WHERE idChoroby = ?";// "SELECT idTypu FROM pracownicy WHERE login = ?";
		ArrayList<Choroba> illnesses = new ArrayList<Choroba>();

		st = conn.prepareStatement(queryString);
		String idList = convertIds(ids);
		st.setString(1, idList);
		ResultSet rs = st.executeQuery();
		String code = "", name = "";

		Choroba illness = null;
		int i = 0;
		while (rs.next()) {
			code = rs.getString("kod");
			name = rs.getString("nazwa");
			illness = new Choroba(ids.get(i++), code, name);
			illnesses.add(illness);
		}

		rs.close();
		st.close();
		// if (true) throw new SQLException();

		return illnesses;
	}

	private static String convertIds(ArrayList<Integer> ids) {

		return ids.toString().replace('[', '(').replace(']', ')');
	}

	// lub void i throws SQLException (?)
	public static boolean writeConstantIllnessData(Pacjent patient) {
		int patientId = patient.getId();
		ArrayList<Choroba> illnesses = patient.getChorobyPrzewlek³e();

		try {
			conn.setAutoCommit(false);
			// TODO: BATCH!!!!!
			for (Choroba ill : illnesses) {
				String query = "INSERT INTO chorobyPrzewlek³e (idPacjenta, idChoroby) VALUES (?,?)";
				try {
					PreparedStatement st = conn.prepareStatement(query);
					st.setInt(1, patientId);
					st.setInt(2, ill.getId());
					st.executeUpdate();

					st.close();
				}
				// TODO:
				catch (SQLException e) {
					if (e.getErrorCode() == 1062) // DUPLICATE PRIMARY KEY
						continue;
					// return false;
					else {
						conn.rollback();
						break;
					}
				}
			}
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public static boolean writeCurrentIllnessData(Wizyta app) {
		int appId = WizytaDAO.getId(app);
		if (appId == -1)
			return false;
		ArrayList<Choroba> illnesses = app.getRozpoznaneChoroby();
		// TODO: zapewniæ, by w tymczasowych nie by³o sta³ych chorób

		try {
			conn.setAutoCommit(false);

			for (Choroba ill : illnesses) {
				String query = "INSERT INTO chorobyTymczasowe (idWizyty, idChoroby) VALUES (?,?)";
				try {
					PreparedStatement st = conn.prepareStatement(query);
					st.setInt(1, appId);
					st.setInt(2, ill.getId());
					st.executeUpdate();
					st.close();
				}
				// TODO:
				catch (SQLException e) {
					if (e.getErrorCode() == 1062) // DUPLICATE PRIMARY KEY
						continue;
					// return false;
					else {
						conn.rollback();
						break;
					}
				}
			}
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public ArrayList<Choroba> getAllIllnesses() throws SQLException {

		PreparedStatement st;
		String queryString = "SELECT idChoroby, kod, nazwa FROM choroby";
		ArrayList<Choroba> illnesses = new ArrayList<Choroba>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String code = "", name = "";
		int id = -1;
		Choroba illness = null;

		while (rs.next()) {
			id = rs.getInt("idChoroby");
			code = rs.getString("kod");
			name = rs.getString("nazwa");
			illness = new Choroba(id, code, name);
			illnesses.add(illness);
		}

		rs.close();
		st.close();
		// if (true) throw new SQLException();

		return illnesses;
	}

	// TODO :: !!!
	public ArrayList<Choroba> getTemporaryIllnesses(int appId,
			ArrayList<Choroba> constIllnesses) throws SQLException {

		PreparedStatement st;
		String queryString = "SELECT idRozpoznania FROM rozpoznaneChoroby WHERE idWizyty = ? ORDER BY data DESC LIMIT 1";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		int diagnosisId = -1;
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			diagnosisId = rs.getInt("idRozpoznania");
			break;
		}

		queryString = "SELECT idChoroby FROM pozycjeRozpoznan WHERE idRozpoznania = ?";
		if (constIllnesses != null && !constIllnesses.isEmpty())
			queryString += " AND idChoroby NOT IN "
					+ illnessesIDs(constIllnesses);

		ArrayList<Choroba> illnesses = new ArrayList<Choroba>();

		int id = -1;
		Choroba illness = null;

		// try {
		st = conn.prepareStatement(queryString);
		st.setInt(1, diagnosisId);
		rs = st.executeQuery();

		while (rs.next()) {
			id = rs.getInt("idChoroby");

			queryString = "SELECT kod, nazwa FROM choroby WHERE idChoroby = ?";

			st = conn.prepareStatement(queryString);
			st.setInt(1, id);
			ResultSet rs2 = st.executeQuery();
			String code = "", name = "";
			while (rs2.next()) {
				code = rs2.getString("kod");
				name = rs2.getString("nazwa");
				break;
			}
			rs2.close();
			System.out.println("temp: " + id + "," + code + "," + name);
			illness = new Choroba(id, code, name);
			illnesses.add(illness);
		}
		/*
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */

		rs.close();
		st.close();

		return illnesses;

	}

	private String illnessesIDs(ArrayList<Choroba> constIllnesses) {
		String ids = "(";

		int lastIndex = constIllnesses.size() - 1;
		for (int i = 0; i <= lastIndex; i++) {// Choroba ill: constIllnesses){
			ids += constIllnesses.get(i).getId();
			if (i != lastIndex)
				ids += ",";
			// ill.getId()
		}
		ids += ")";
		System.out.println(ids);
		return ids;

	}

	public void writeConstantIllnesses(int patientId,
			ArrayList<Choroba> constIllnesses) throws SQLException {

		System.out.println("CONST: " + constIllnesses);

		if (constIllnesses == null)
			return;

		System.out.println(">const size " + constIllnesses.size());
		PreparedStatement st;

		// if (constIllnesses.isEmpty()) { // DELETE, brak rozpoznañ
		String queryString = "DELETE FROM chorobyPrzewlek³e WHERE idPacjenta = ?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, patientId);
		st.executeUpdate();
		// st.close();
		// return;
		// }

		// String
		queryString = "INSERT INTO chorobyPrzewlek³e (idPacjenta, idChoroby) VALUES (?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, patientId); // to check

		for (Choroba ill : constIllnesses) {
			st.setInt(2, ill.getId());
			st.addBatch();
		}

		st.executeBatch();

		// conn.commit();
		st.close();

	}

	public void writeCurrentIllnesses(int appId,
			ArrayList<Choroba> tempIllnesses) throws SQLException {

		if (tempIllnesses == null)
			return;

		PreparedStatement st;
		String queryString = "INSERT INTO rozpoznaneChoroby (idWizyty) VALUES (?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		st.executeUpdate();

		queryString = "SELECT idRozpoznania FROM rozpoznaneChoroby WHERE idWizyty = ? ORDER BY data DESC LIMIT 1 ";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();

		int diagnosisId = -1;
		while (rs.next()) {
			diagnosisId = rs.getInt(1);
			break;
		}
		rs.close();

		queryString = "INSERT INTO pozycjeRozpoznan (idRozpoznania, idChoroby) VALUES (?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, diagnosisId); // to check

		for (Choroba pos : tempIllnesses) {
			st.setInt(2, pos.getId());
			st.addBatch();
		}

		st.executeBatch();
		// conn.commit();
		st.close();

	}

}
