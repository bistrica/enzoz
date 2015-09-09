package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBHandler;
import people.Lekarz;
import people.Osoba;

public class OsobaDAO {

	public Osoba getPersonData(int idOsoby) throws SQLException {

			Connection conn=DBHandler.getDatabaseConnection();
			Osoba person=null;
		
			PreparedStatement st;
			String queryString;
			String imie="",nazwisko="",PESEL="",nrDomu="",nrMieszkania="",kod="",ulica="",miejscowosc="",telefon="";
				
		//	try {	
				queryString="SELECT imiê, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, kodPocztowy, miejscowoœæ, telefon FROM Osoby WHERE idOsoby=?";
				st = conn.prepareStatement(queryString);
				st.setInt(1, idOsoby);
				ResultSet rs = st.executeQuery();
				while (rs.next()){
					imie=rs.getString("imiê");
					nazwisko=rs.getString("nazwisko");
					PESEL=rs.getString("PESEL");
					ulica=rs.getString("ulica");
					nrDomu=rs.getString("nrDomu");
					nrMieszkania=rs.getString("nrMieszkania");
					kod=rs.getString("kodPocztowy");
					miejscowosc=rs.getString("miejscowoœæ");
					telefon=rs.getString("telefon");
					
					break;
				}
				
				person=new Osoba(idOsoby, imie,nazwisko,PESEL, ulica, nrDomu, nrMieszkania, miejscowosc, kod, telefon);
				
			/*} catch (SQLException e) {
				e.printStackTrace();
			}*/	
			return person;
	}
	
	
}
