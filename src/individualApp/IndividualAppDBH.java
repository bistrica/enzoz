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
			//TODO: ogarn�� te wyj�tki i ich g��boko��
			e.printStackTrace();
		}
		return ill;
		
	}

}
