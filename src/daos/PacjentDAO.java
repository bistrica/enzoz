package daos;

import items.Choroba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import people.Lekarz;
import people.Osoba;
import people.Pacjent;
import people.Pracownik;
import database.DBHandler;

public class PacjentDAO {

	Connection conn;
	
	//?
	OsobaDAO personDAO;
	ChorobaDAO illnessDAO;
	//
	public PacjentDAO() {
		conn=DBHandler.getDatabaseConnection();
		personDAO=new OsobaDAO();
		illnessDAO=new ChorobaDAO();
	}
	
	public Pacjent getPatientData(int id) throws SQLException {

		Osoba person=personDAO.getPersonData(id);
		ArrayList<Choroba> illnesses=getPatientConstantIllnesses(id); 
				
		return new Pacjent(person,illnesses);
	
	}

	private ArrayList<Choroba> getPatientConstantIllnesses(int id) throws SQLException {

			ArrayList<Choroba> illnesses=new ArrayList<Choroba>();
			PreparedStatement st;
			String queryString="SELECT idChoroby FROM chorobyPrzewlek³e WHERE idPacjenta = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
			
			st = conn.prepareStatement(queryString);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();//Update();
			int illId;
			while (rs.next()){
				illId=rs.getInt("idChoroby");
				Choroba illness=illnessDAO.getIllnessData(illId);
				illnesses.add(illness);
			}
			return illnesses;

	}
}
