package daos;

import items.Medicine;
import items.PrescriptedItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class PrescriptedItemDAO {

	
	MedicineDAO drugDAO;

	public PrescriptedItemDAO() {
		
		drugDAO = new MedicineDAO();
	}

	public ArrayList<PrescriptedItem> getPrescriptedPositions(int idPresc)
			throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();
		// IdLeku iloscOpakowan iloscDawek przyjeciaNaDzien ProcentRefundacji

		PreparedStatement st;
		String queryString = "SELECT IdLeku, iloscOpakowan, iloscDawek, przyjeciaNaDzien, procentRefundacji FROM pozycjenareceptach WHERE idRecepty = ?";
		ArrayList<PrescriptedItem> positions = new ArrayList<PrescriptedItem>();

		// try {
		st = conn.prepareStatement(queryString);
		st.setInt(1, idPresc);
		ResultSet rs = st.executeQuery();
		int drugId = -1, pckgNo = -1, ingestionNo = -1;
		double dosesNo = -1.0, discPrcnt = -1.0;
		Medicine drug = null;

		while (rs.next()) {
			drugId = rs.getInt("IdLeku");
			pckgNo = rs.getInt("iloscOpakowan");
			dosesNo = rs.getDouble("iloscDawek");
			ingestionNo = rs.getInt("przyjeciaNaDzien");
			discPrcnt = rs.getDouble("procentRefundacji");

			drug = drugDAO.getDrug(drugId);// new Lek(id,name,type,dose,pckg);
			PrescriptedItem position = new PrescriptedItem(drug, pckgNo,
					dosesNo, ingestionNo, discPrcnt);
			positions.add(position);
		}
		rs.close();
		st.close();
		/*
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */

		return positions;

	}

	public void writePositions(int prescriptionId,
			ArrayList<PrescriptedItem> prescribedPositions) throws SQLException {

		// boolean autoCommit = conn.getAutoCommit();
		// conn.setAutoCommit(false);

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "INSERT INTO pozycjenareceptach (idRecepty, IdLeku, iloscOpakowan, iloscDawek, przyjeciaNaDzien, procentRefundacji) VALUES (?,?,?,?,?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, prescriptionId); // to check

		for (PrescriptedItem pos : prescribedPositions) {
			st.setInt(2, pos.getMedicine().getId());
			st.setInt(3, pos.getPackageCount());
			st.setDouble(4, pos.getDosesCountPerIngestion());
			st.setInt(5, pos.getIngestionCount());
			st.setDouble(6, pos.getDiscountPercent());
			st.addBatch();
		}

		st.executeBatch();
		
		st.close();
		

	}
}
