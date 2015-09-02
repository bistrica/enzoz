package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import people.Lekarz;
import people.Pracownik;

public class DBHandler {

	Connection conn;
	String host="localhost", login;
	private static DBHandler dbh=null;
	
	/*public DBHandler(Connection conn) {
		this.conn=conn;
	}*/

	public static DBHandler createAndGetDatabaseHandler(String login, String pass)  throws SQLException {
		if (dbh==null) {
			dbh=new DBHandler(login,pass);
		}
		return dbh;	
	}
	
	public static DBHandler getDatabaseHandler() {
		return dbh;
	}
	
	private DBHandler(String login, String pass) throws SQLException {

		  this.conn=DriverManager.getConnection(
		            "jdbc:mysql://"+host+"/enzoz?useUnicode=true&characterEncoding=UTF-8", login, pass);
		  this.login=login;
	}

	public Pracownik getCurrentUser() {
		
		Pracownik user=null;
		PreparedStatement st;
		String queryString="SELECT idOsoby, nazwa FROM pracownicy p JOIN typyPracownikow t ON t.idTypu=p.idTypu WHERE p.login = ?";//"SELECT idTypu FROM pracownicy WHERE login = ?";
		String typ="", lekarz="lekarz", rej="rejestratorka", admin="administrator";
		int idOsoby=-1;
		
		try {
			st = conn.prepareStatement(queryString);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();//Update();
			while (rs.next()){
				typ=rs.getString("nazwa");
				idOsoby=rs.getInt("idOsoby");
				break;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (typ.equals(lekarz))
			user=getDoctorData(idOsoby);
		else {
			//TODO or not todo		
		}
		
		
		return user;
		
	}

	private Lekarz getDoctorData(int idOsoby) {

		Lekarz lekarz=null;
		
		PreparedStatement st;
		String queryString;
		String imie="",nazwisko="",PESEL="";
		int PWZ=-1;
		
		
		try {
			
			queryString="SELECT imiê, nazwisko, PESEL FROM Osoby WHERE idOsoby=?";
			st = conn.prepareStatement(queryString);
			st.setInt(1, idOsoby);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				imie=rs.getString("imiê");
				nazwisko=rs.getString("nazwisko");
				PESEL=rs.getString("PESEL");
				break;
			}
			
			queryString="SELECT PWZ FROM Lekarze WHERE idOsoby=?";
			st = conn.prepareStatement(queryString);
			st.setInt(1, idOsoby);
			rs = st.executeQuery();
			while (rs.next()){
				PWZ=rs.getInt("PWZ");
				break;
			}
			
			lekarz=new Lekarz(idOsoby, imie, nazwisko, PESEL, PWZ);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		System.out.println(lekarz.getImie()+" "+lekarz.getNazwisko());
		return lekarz;
	}
	
	public Wizyta[] getAppointments(int doctorId) {
		PreparedStatement st;
		String queryString="SELECT idWizyty, idPacjenta FROM wizytyDzis WHERE Lekarz=?";	
		
		try {
			st = conn.prepareStatement(queryString);
			st.setInt(1, doctorId);
			ResultSet rs = st.executeQuery();//Update();
			while (rs.next()){
				int appId=rs.getInt("idWizyty");
				int patientId=rs.getInt("idPacjenta");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
