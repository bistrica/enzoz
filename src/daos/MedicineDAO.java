package daos;

import items.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class MedicineDAO {

	// Connection conn;

	public MedicineDAO() {
		// conn = DBHandler.getDatabaseConnection();
	}

	public ArrayList<Medicine> getAllMedicines() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idLeku, nazwa, postac, dawka, opakowanie FROM leki";
		ArrayList<Medicine> medicines = new ArrayList<Medicine>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String type = "", name = "", dose = "", pckg = "";
		int id = -1;
		Medicine drug = null;

		while (rs.next()) {
			id = rs.getInt("idLeku");
			name = rs.getString("nazwa");
			type = rs.getString("postac");
			dose = rs.getString("dawka");
			pckg = rs.getString("opakowanie");

			drug = new Medicine(id, name, type, dose, pckg);
			medicines.add(drug);
		}
		rs.close();
		st.close();

		return medicines;

	}

	public Medicine getDrug(int drugId) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT nazwa, postac, dawka, opakowanie FROM leki WHERE idLeku = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, drugId);

		ResultSet rs = st.executeQuery();
		String type = "", name = "", dose = "", pckg = "";

		Medicine drug = null;

		while (rs.next()) {
			name = rs.getString("nazwa");
			type = rs.getString("postac");
			dose = rs.getString("dawka");
			pckg = rs.getString("opakowanie");
			break;
		}
		rs.close();
		st.close();
		drug = new Medicine(drugId, name, type, dose, pckg);
		return drug;

	}

}
