package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import people.Lekarz;
import people.Osoba;
import people.Pracownik;
import database.DBHandler;

public class PracownikDAO {

	OsobaDAO personDAO;
	Connection conn;
	
	public PracownikDAO() {
		personDAO=new OsobaDAO();
		conn=DBHandler.getDatabaseConnection();
	}
	
	public Pracownik getEmployeeData(String login) throws SQLException {
		
		
		
		PreparedStatement st;
		String queryString="SELECT idOsoby, nazwa FROM pracownicy p JOIN typyPracownikow t ON t.idTypu=p.idTypu WHERE p.login = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
		String typ="", lekarz="lekarz", rej="rejestratorka", admin="administrator";
		int idOsoby=-1;
		
		Pracownik employee=null;
		Osoba person=null;
		
		//try {
			st = conn.prepareStatement(queryString);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();//Update();
			while (rs.next()){
				typ=rs.getString("nazwa");
				idOsoby=rs.getInt("idOsoby");
				break;
			}
			
			person=personDAO.getPersonData(idOsoby);
			
			//sprawdü, czy to lekarz
			if (typ.equals(lekarz)) {
				
				int PWZ=-1;
				queryString="SELECT PWZ FROM Lekarze WHERE idOsoby=?";
				st = conn.prepareStatement(queryString);
				st.setInt(1, idOsoby);
				rs = st.executeQuery();
				while (rs.next()){
					PWZ=rs.getInt("PWZ");
					break;
				}
				
				employee=new Lekarz(person, PWZ);
			}
			else {
				//TODO or not todo		
			}
			
			
			
		/*} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		
		return employee;
	}
}
