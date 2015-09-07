package individualApp;

import items.Choroba;

import java.sql.SQLException;
import java.util.ArrayList;

import daos.ChorobaDAO;

public class IndividualAppDBH {

	ChorobaDAO illnessDAO;
	
	
	public IndividualAppDBH() {
		illnessDAO=new ChorobaDAO();
	}
	
	public ArrayList<Choroba> getAllIllnesses() {
		ArrayList<Choroba> ill=new ArrayList<Choroba>();
		try {
			ill=illnessDAO.getAllIllnesses();
		}
		catch (SQLException e){
			//TODO: ogarn¹æ te wyj¹tki i ich g³êbokoœæ
			e.printStackTrace();
		}
		return ill;
		
	}

}
