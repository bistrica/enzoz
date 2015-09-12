package individualApp;

import items.Choroba;
import items.Lek;
import items.Poradnia;

import java.sql.SQLException;
import java.util.ArrayList;

import daos.ChorobaDAO;
import daos.LekDAO;
import daos.PoradniaDAO;
import database.DBHandler;
import exceptions.LoadDataException;
import exceptions.PreviewCannotBeCreatedException;

public class IndividualAppDBH {

	ChorobaDAO illnessDAO;
	LekDAO drugDAO;
	PoradniaDAO clinicDAO;
	private String illnessesString="dane chorób";
	private String medicinesString="dane leków";
	private String clinicsString="dane poradni";
	
	public IndividualAppDBH() {
		illnessDAO=new ChorobaDAO();
		drugDAO=new LekDAO();
		clinicDAO=new PoradniaDAO();
	}
	
	public ArrayList<Choroba> getAllIllnesses() throws LoadDataException {
		ArrayList<Choroba> ill=new ArrayList<Choroba>();
		try {
			ill=illnessDAO.getAllIllnesses();
		}
		catch (SQLException e){
			
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()){
				DBHandler.resetTrialsNo();
				throw new LoadDataException(illnessesString);
			
			}
			else{
				System.out.println("ONCE MORE IND");
				DBHandler.incrementTrialsNo();
				ill=getAllIllnesses();
			}
		}
		
		DBHandler.resetTrialsNo();			
		return ill;
		
	}

	public ArrayList<Lek> getAllMedicines() throws LoadDataException {
		ArrayList<Lek> med=new ArrayList<Lek>();
		try {
			med=drugDAO.getAllMedicines();
		}
		catch (SQLException e){
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()){
				DBHandler.resetTrialsNo();
				throw new LoadDataException(medicinesString);
			
			}
			else{
				System.out.println("ONCE MORE IND");
				DBHandler.incrementTrialsNo();
				med=getAllMedicines();
			}
		}
		
		DBHandler.resetTrialsNo();		
		return med;
	}

	public ArrayList<Poradnia> getAllClinics() throws LoadDataException {
		ArrayList<Poradnia> cli=new ArrayList<Poradnia>();
		try {
			cli=clinicDAO.getAllClinics();
		}
		catch (SQLException e){
			e.printStackTrace();
			
			if (!DBHandler.reconnect() || DBHandler.isCriticalNoExceeded()){
				DBHandler.resetTrialsNo();
				throw new LoadDataException(clinicsString);
			
			}
			else{
				System.out.println("ONCE MORE IND");
				DBHandler.incrementTrialsNo();
				cli=getAllClinics();
			}
		}
		
		DBHandler.resetTrialsNo();		
		return cli;
	}

}
