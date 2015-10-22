package daos;

import items.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class MedicineDAO {

	

	public MedicineDAO() {
		
	}

	public ArrayList<Medicine> getAllMedicines() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT IdLeku, Nazwa, Postac, Dawka, Opakowanie FROM leki";
		ArrayList<Medicine> medicines = new ArrayList<Medicine>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String type = "", name = "", dose = "", pckg = "";
		int id = -1;
		Medicine drug = null;

		while (rs.next()) {
			id = rs.getInt("IdLeku");
			name = rs.getString("Nazwa");
			type = rs.getString("Postac");
			dose = rs.getString("Dawka");
			pckg = rs.getString("Opakowanie");

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
		String queryString = "SELECT Nazwa, Postac, Dawka, Opakowanie FROM leki WHERE IdLeku = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, drugId);

		ResultSet rs = st.executeQuery();
		String type = "", name = "", dose = "", pckg = "";

		Medicine drug = null;

		while (rs.next()) {
			name = rs.getString("Nazwa");
			type = rs.getString("Postac");
			dose = rs.getString("Dawka");
			pckg = rs.getString("Opakowanie");
			break;
		}
		rs.close();
		st.close();
		drug = new Medicine(drugId, name, type, dose, pckg);
		return drug;

	}

}
