package daos;

import items.Lek;
import items.PozycjaNaRecepcie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBHandler;

public class PozycjaNaRecepcieDAO {

	Connection conn;
	LekDAO drugDAO;

	public PozycjaNaRecepcieDAO() {
		conn = DBHandler.getDatabaseConnection();
		drugDAO = new LekDAO();
	}

	public ArrayList<PozycjaNaRecepcie> getPrescriptedPositions(int idPresc)
			throws SQLException {

		// IdLeku iloœæOpakowañ iloœæDawek PrzyjêciaNaDzieñ ProcentRefundacji

		PreparedStatement st;
		String queryString = "SELECT idLeku, iloœæOpakowañ, iloœæDawek, przyjêciaNaDzieñ, procentRefundacji FROM pozycjeNaReceptach WHERE idRecepty = ?";
		ArrayList<PozycjaNaRecepcie> positions = new ArrayList<PozycjaNaRecepcie>();

		// try {
		st = conn.prepareStatement(queryString);
		st.setInt(1, idPresc);
		ResultSet rs = st.executeQuery();
		int drugId = -1, pckgNo = -1, ingestionNo = -1;
		double dosesNo = -1.0, discPrcnt = -1.0;
		Lek drug = null;

		while (rs.next()) {
			drugId = rs.getInt("idLeku");
			pckgNo = rs.getInt("iloœæOpakowañ");
			dosesNo = rs.getDouble("iloœæDawek");
			ingestionNo = rs.getInt("przyjêciaNaDzieñ");
			discPrcnt = rs.getDouble("procentRefundacji");

			drug = drugDAO.getDrug(drugId);// new Lek(id,name,type,dose,pckg);
			PozycjaNaRecepcie position = new PozycjaNaRecepcie(drug, pckgNo,
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
			ArrayList<PozycjaNaRecepcie> prescribedPositions)
			throws SQLException {

		// boolean autoCommit = conn.getAutoCommit();
		// conn.setAutoCommit(false);

		PreparedStatement st;
		String queryString = "INSERT INTO pozycjeNaReceptach (idRecepty, idLeku, iloœæOpakowañ, iloœæDawek, przyjêciaNaDzieñ, procentRefundacji) VALUES (?,?,?,?,?,?)";

		st = conn.prepareStatement(queryString);
		st.setInt(1, prescriptionId); // to check

		for (PozycjaNaRecepcie pos : prescribedPositions) {
			st.setInt(2, pos.getLek().getId());
			st.setInt(3, pos.getIloscOpakowan());
			st.setDouble(4, pos.getIloscDawekNaPrzyjecie());
			st.setInt(5, pos.getIloscPrzyjec());
			st.setDouble(6, pos.getProcentRefundacji());
			st.addBatch();
		}

		st.executeBatch();
		// conn.commit();
		st.close();
		// conn.setAutoCommit(autoCommit);

	}
}
