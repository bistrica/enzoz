package daos;

import items.Illness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Patient;
import database.DBHandler;

public class IllnessDAO {

	public IllnessDAO() {
	}

	public Illness getIllnessData(int id) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT Kod, Nazwa FROM choroby WHERE IdChoroby = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		String code = "", name = "";

		Illness illness = null;
		while (rs.next()) {
			code = rs.getString("Kod");
			name = rs.getString("Nazwa");
			illness = new Illness(id, code, name);
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

	public ArrayList<Illness> getIllnessData(ArrayList<Integer> ids)
			throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT Kod, Nazwa FROM choroby WHERE IdChoroby = ?";// "SELECT idTypu FROM pracownicy WHERE login = ?";
		ArrayList<Illness> illnesses = new ArrayList<Illness>();

		st = conn.prepareStatement(queryString);
		String idList = convertIds(ids);
		st.setString(1, idList);
		ResultSet rs = st.executeQuery();
		String code = "", name = "";

		Illness illness = null;
		int i = 0;
		while (rs.next()) {
			code = rs.getString("Kod");
			name = rs.getString("Nazwa");
			illness = new Illness(ids.get(i++), code, name);
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

	public static boolean writeConstantIllnessData(Patient patient) {
		int patientId = patient.getId();

		Connection conn = DBHandler.getDatabaseConnection();
		ArrayList<Illness> illnesses = patient.getConstantIllnesses();

		try {
			conn.setAutoCommit(false);
			// TODO: BATCH!!!!!
			for (Illness ill : illnesses) {
				String query = "INSERT INTO chorobyprzewlekle (IdPacjenta, IdChoroby) VALUES (?,?)";
				try {
					PreparedStatement st = conn.prepareStatement(query);
					st.setInt(1, patientId);
					st.setInt(2, ill.getId());
					st.executeUpdate();

					st.close();
				} catch (SQLException e) {
					if (e.getErrorCode() == 1062) // DUPLICATE PRIMARY KEY
						continue;
					else {
						conn.rollback();
						break;
					}
				}
			}
			System.out.println("tttt");
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public ArrayList<Illness> getAllIllnesses() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT IdChoroby, Kod, Nazwa FROM choroby";
		ArrayList<Illness> illnesses = new ArrayList<Illness>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String code = "", name = "";
		int id = -1;
		Illness illness = null;

		while (rs.next()) {
			id = rs.getInt("IdChoroby");
			code = rs.getString("Kod");
			name = rs.getString("Nazwa");
			illness = new Illness(id, code, name);
			illnesses.add(illness);
		}

		rs.close();
		st.close();
		// if (true) throw new SQLException();

		return illnesses;
	}

	//
	public ArrayList<Illness> getTemporaryIllnesses(int appId,
			ArrayList<Illness> constIllnesses) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idRozpoznania FROM rozpoznaniachorob WHERE IdWizyty = ? ORDER BY data DESC LIMIT 1";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		int diagnosisId = -1;
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			diagnosisId = rs.getInt("idRozpoznania");
			break;
		}

		queryString = "SELECT IdChoroby FROM pozycjerozpoznan WHERE idRozpoznania = ?";

		ArrayList<Illness> illnesses = new ArrayList<Illness>();

		int id = -1;
		Illness illness = null;

		st = conn.prepareStatement(queryString);
		st.setInt(1, diagnosisId);
		rs = st.executeQuery();

		while (rs.next()) {
			id = rs.getInt("IdChoroby");

			queryString = "SELECT Kod, Nazwa FROM choroby WHERE IdChoroby = ?";

			st = conn.prepareStatement(queryString);
			st.setInt(1, id);
			ResultSet rs2 = st.executeQuery();
			String code = "", name = "";
			while (rs2.next()) {
				code = rs2.getString("Kod");
				name = rs2.getString("Nazwa");
				break;
			}
			rs2.close();
			illness = new Illness(id, code, name);
			illnesses.add(illness);
		}

		rs.close();
		st.close();

		return illnesses;

	}

	private String illnessesIDs(ArrayList<Illness> constIllnesses) {
		String ids = "(";

		int lastIndex = constIllnesses.size() - 1;
		for (int i = 0; i <= lastIndex; i++) {// Choroba ill: constIllnesses){
			ids += constIllnesses.get(i).getId();
			if (i != lastIndex)
				ids += ",";
		}
		ids += ")";
		System.out.println(ids);
		return ids;

	}

	public void writeConstantIllnesses(int patientId,
			ArrayList<Illness> constIllnesses) throws SQLException {

		if (constIllnesses == null)
			return;

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;

		String queryString = "DELETE FROM chorobyprzewlekle WHERE IdPacjenta = ?";
		st = conn.prepareStatement(queryString);
		st.setInt(1, patientId);
		st.executeUpdate();

		queryString = "INSERT INTO chorobyprzewlekle (IdPacjenta, IdChoroby) VALUES (?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, patientId);

		for (Illness ill : constIllnesses) {
			st.setInt(2, ill.getId());
			st.addBatch();
		}

		st.executeBatch();

		st.close();

	}

	public void writeCurrentIllnesses(int appId,
			ArrayList<Illness> tempIllnesses) throws SQLException {

		if (tempIllnesses == null)
			return;

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "INSERT INTO rozpoznaniachorob (IdWizyty) VALUES (?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		st.executeUpdate();

		if (tempIllnesses.isEmpty()) {
			st.close();
			return;
		}

		queryString = "SELECT idRozpoznania FROM rozpoznaniachorob WHERE IdWizyty = ? ORDER BY data DESC LIMIT 1 ";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();

		int diagnosisId = -1;
		while (rs.next()) {
			diagnosisId = rs.getInt(1);
			break;
		}
		rs.close();

		queryString = "INSERT INTO pozycjerozpoznan (idRozpoznania, IdChoroby) VALUES (?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, diagnosisId); // to check

		for (Illness pos : tempIllnesses) {
			st.setInt(2, pos.getId());
			st.addBatch();
		}

		st.executeBatch();
		st.close();

	}

}
