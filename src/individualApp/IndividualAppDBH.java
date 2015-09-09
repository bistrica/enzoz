package individualApp;

import items.Choroba;
import items.Lek;
import items.Poradnia;

import java.sql.SQLException;
import java.util.ArrayList;

import daos.ChorobaDAO;
import daos.LekDAO;
import daos.PoradniaDAO;

public class IndividualAppDBH {

	ChorobaDAO illnessDAO;
	LekDAO drugDAO;
	PoradniaDAO clinicDAO;
	
	public IndividualAppDBH() {
		illnessDAO=new ChorobaDAO();
		drugDAO=new LekDAO();
		clinicDAO=new PoradniaDAO();
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

	public ArrayList<Lek> getAllMedicines() {
		ArrayList<Lek> med=new ArrayList<Lek>();
		try {
			med=drugDAO.getAllMedicines();
		}
		catch (SQLException e){
			//TODO: ogarn¹æ te wyj¹tki i ich g³êbokoœæ
			e.printStackTrace();
		}
		return med;
	}

	public ArrayList<Poradnia> getAllClinics() {
		ArrayList<Poradnia> cli=new ArrayList<Poradnia>();
		try {
			cli=clinicDAO.getAllClinics();
		}
		catch (SQLException e){
			//TODO: ogarn¹æ te wyj¹tki i ich g³êbokoœæ
			e.printStackTrace();
		}
		return cli;
	}

}
