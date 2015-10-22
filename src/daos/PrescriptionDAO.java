package daos;

import items.PrescriptedItem;
import items.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class PrescriptionDAO {

	private PrescriptedItemDAO prescriptionPositionDAO;

	public PrescriptionDAO() {

		prescriptionPositionDAO = new PrescriptedItemDAO();
	}

	public Prescription getPrescription(int appId) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idRecepty FROM recepty WHERE IdWizyty = ? ORDER BY Data DESC LIMIT 1";
		int idPresc = -1;
		ArrayList<PrescriptedItem> positions = new ArrayList<PrescriptedItem>();

		// try {

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			idPresc = rs.getInt(1);
			break;
		}
		rs.close();
		st.close();
		positions = prescriptionPositionDAO.getPrescriptedPositions(idPresc);

		return new Prescription(positions);
	}

	public void writeToDatabase(int appId, Prescription prescription)
			throws SQLException {

		if (prescription == null)
			return;

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "INSERT INTO recepty (IdWizyty) VALUES (?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		st.executeUpdate();

		queryString = "SELECT idRecepty FROM recepty WHERE IdWizyty = ? ORDER BY Data DESC LIMIT 1 ";

		st = conn.prepareStatement(queryString);
		st.setInt(1, appId);
		ResultSet rs = st.executeQuery();

		int prescId = -1;
		while (rs.next()) {
			prescId = rs.getInt(1);
			break;
		}

		rs.close();
		st.close();

		ArrayList<PrescriptedItem> prescribedPositions = prescription
				.getPozycje();
		prescriptionPositionDAO.writePositions(prescId, prescribedPositions);

	}

}
