package daos;

import items.Lek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class LekDAO {

	// Connection conn;

	public LekDAO() {
		// conn = DBHandler.getDatabaseConnection();
	}

	public ArrayList<Lek> getAllMedicines() throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT idLeku, nazwa, postac, dawka, opakowanie FROM leki";
		ArrayList<Lek> medicines = new ArrayList<Lek>();
		st = conn.prepareStatement(queryString);

		ResultSet rs = st.executeQuery();
		String type = "", name = "", dose = "", pckg = "";
		int id = -1;
		Lek drug = null;

		while (rs.next()) {
			id = rs.getInt("idLeku");
			name = rs.getString("nazwa");
			type = rs.getString("postac");
			dose = rs.getString("dawka");
			pckg = rs.getString("opakowanie");

			drug = new Lek(id, name, type, dose, pckg);
			medicines.add(drug);
		}
		rs.close();
		st.close();

		return medicines;

	}

	public Lek getDrug(int drugId) throws SQLException {

		Connection conn = DBHandler.getDatabaseConnection();

		PreparedStatement st;
		String queryString = "SELECT nazwa, postac, dawka, opakowanie FROM leki WHERE idLeku = ?";

		st = conn.prepareStatement(queryString);
		st.setInt(1, drugId);

		ResultSet rs = st.executeQuery();
		String type = "", name = "", dose = "", pckg = "";

		Lek drug = null;

		while (rs.next()) {
			name = rs.getString("nazwa");
			type = rs.getString("postac");
			dose = rs.getString("dawka");
			pckg = rs.getString("opakowanie");
			break;
		}
		rs.close();
		st.close();
		drug = new Lek(drugId, name, type, dose, pckg);
		return drug;

	}

}
