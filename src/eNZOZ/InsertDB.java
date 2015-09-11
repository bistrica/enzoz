package eNZOZ;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

//import com.mysql.jdbc.PreparedStatement;

public class InsertDB {

	Connection conn = null;
	
	
	public InsertDB(){
		try {
			
			 //Class.forName("com.mysql.jdbc.Driver").newInstance();
	         String login="adolega";
	         String haslo="adolega";
			 
	        conn=DriverManager.getConnection(
	            "jdbc:mysql://localhost/enzoz?useUnicode=true&characterEncoding=UTF-8", login, haslo);//login, haslo);
	        
		     /*conn =
		       DriverManager.getConnection("jdbc:mysql://localhost/enzoz?" +
		                                   "user=ola&password=ola");
*/
		   
		     System.out.println("Uda³o siê po³¹czyæ z baz¹ danych..."); 
		     //insertDoctors();
		     //insertData();
		     //insertCodes();
		     //insertPharmindex();
		     //insertPlaces();
		     //insertTypes();
		    // insertEmployees();
		     //insertPatients();
		     /*deleteApps();
		     insertApps();
		     System.out.println("Done apps.");
		     insertInterviews();
		     System.out.println("Done int.");
		     insertPrescriptions();
		     System.out.println("Done pre.");
		     insertRequests();*/
//		     insertDuplicateConstantIllness();
		     System.out.println("Done all.");
		     
		   
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	private void deleteApps() {
		String[] queries={
				"DELETE FROM konsultacje",
				"DELETE FROM pozycjeNaReceptach",
				"DELETE FROM recepty",
				"DELETE FROM pozycjeNaSkierowaniach",
				"DELETE FROM skierowania",
				"DELETE FROM rozpoznaneChoroby",
				"DELETE FROM wizyty"
				};
        try {
        	conn.setAutoCommit(false);
            Statement st=conn.createStatement();
            for (String q: queries)
            	st.addBatch(q);
            st.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        }
        catch(SQLException e) {System.out.println("whats up?"+e.getErrorCode()); e.printStackTrace(); return; }
        
	}
	
	private void insertDuplicateConstantIllness() {
		// TODO Auto-generated method stubINSERT INTO `chorobyprzewlek³e`(`IdPacjenta`, `IdChoroby`) VALUES (6,15897), (3,15897)
		//1062 DUPLICATE KEY
		String query="INSERT INTO `chorobyprzewlek³e`(`IdPacjenta`, `IdChoroby`) VALUES (6,15897), (3,15897)";//"INSERT INTO osoby (imiê,nazwisko,pesel,telefon,ulica,nrdomu,nrmieszkania,kodpocztowy,miejscowoœæ) VALUES ('"+s[0]+"', '"+s[1]+"', '"+s[2]+"', '"+s[3]+"', '"+s[4]+"', '"+s[5]+"', '"+s[6]+"', '"+s[7]+"', '"+s[8]+"')";
        try {
            Statement st=conn.createStatement();
            st.executeUpdate(query);
        }
        catch(SQLException e) {System.out.println("whats up?"+e.getErrorCode()); e.printStackTrace(); return; }
        
	}

	private void insertRequests() {

		String query="SELECT idWizyty, data FROM wizytyArchiwum";
		
		ArrayList<Integer> ids=new ArrayList<>();
		ArrayList<String> daty=new ArrayList<>();
		ArrayList<Integer> poradnie=new ArrayList<>();
		
		
		ResultSet rs;
		
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	ids.add(rs.getInt("idWizyty"));
            	daty.add(rs.getString("data"));
 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 3 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
		 
		
		 
		 query="SELECT idPoradni FROM poradnie";
		 
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	poradnie.add(rs.getInt("idPoradni"));
            	
 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 2 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }

		
		 Random rand=new Random();
		 int idSkier=1;
		 
		 query="SELECT MAX(idSkierowania) idSkier FROM skierowania";
		 
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	idSkier=rs.getInt("idSkier");

 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 5 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
		 
		 for (int i=0;i<ids.size();i++) {
			 
			 int ilePozycjiSkierowan=rand.nextInt(3);
			 if (ilePozycjiSkierowan==0) continue;
			 
			 int id=ids.get(i);
			 String data=daty.get(i);
			 
			 
			 
			 idSkier++;
			
			 
			 query="INSERT INTO skierowania (idSkierowania, idWizyty, data) VALUES (?,?,?)";
			 try{      
				 	PreparedStatement st=conn.prepareStatement(query);
	    			 st.setInt(1,idSkier);
				 	 st.setInt(2, id);
	    			 st.setString(3, data);
	    			 st.executeUpdate();
		 	 }
	         catch(SQLException e) {System.out.println("whats up?");  e.printStackTrace(); return; }
	             
			
			
			 int[] idPoradni=getRandomIds(ilePozycjiSkierowan, poradnie);
			 
			 String komentarz=getRandomDesc();
			 
			 for (int poradnia: idPoradni){//int j=0;j<iloscPozycjiNaRecepcie;j++){
				 query="INSERT INTO pozycjeNaSkierowaniach (idSkierowania, idPoradni, Komentarz) VALUES (?,?,?)";
				 try{      
					 	PreparedStatement st=conn.prepareStatement(query);
		    			st.setInt(1, idSkier);
		    			st.setInt(2,poradnia);
		    			st.setString(3, komentarz);
		    			st.executeUpdate();
			 	 }
		         catch(SQLException e) {System.out.println("whats up 4?");  e.printStackTrace(); return; }
			 }
		 }
			 
			 
		 
		 
		
		
	}

	private String getRandomDesc() {
		Random r=new Random();
		return desc[r.nextInt(desc.length)];
	}

	String[] desc={"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis nisi nulla. Morbi convallis maximus efficitur. ",
		"Nulla nec mi non ipsum placerat semper. Sed felis urna, posuere in nisl at, fermentum porttitor metus. Morbi efficitur faucibus justo, a vulputate elit faucibus et. Sed pulvinar et nisi eu facilisis. Quisque id bibendum sem. ",
		"Sed malesuada nibh ac cursus bibendum. Donec quis maximus justo. Mauris lacinia dui sed magna aliquet, id condimentum massa euismod. Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
	
	};
	
	private void insertPrescriptions() {

		String query="SELECT idWizyty, data FROM wizytyArchiwum";
		
		ArrayList<Integer> ids=new ArrayList<>();
		ArrayList<String> daty=new ArrayList<>();
		ArrayList<Integer> leki=new ArrayList<>();
		
		
		ResultSet rs;
		
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	ids.add(rs.getInt("idWizyty"));
            	daty.add(rs.getString("data"));
 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 3 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
		 
		 System.out.println(ids);
		 System.out.println(daty);
		 //if (true) return;
		 
		 query="SELECT idLeku FROM leki";
		 
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	leki.add(rs.getInt("idLeku"));
            	
 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 2 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
		 
		 Random rand=new Random(); 
		 
		 
		 //okreœl kolejne id recepty
		 int idRec=1;
		 
		 query="SELECT MAX(idRecepty) idRec FROM recepty";
		 
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	idRec=rs.getInt("idRec");

 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 5 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
		 
		 
		 
		 
		 for (int i=0;i<ids.size();i++) {
			 int id=ids.get(i);
			 String data=daty.get(i);
			 
			 idRec++;
			 query="INSERT INTO recepty (idRecepty, idWizyty, data) VALUES (?,?,?)";
			 try{      
				 	PreparedStatement st=conn.prepareStatement(query);
	    			 st.setInt(1,idRec);
				 	 st.setInt(2, id);
	    			 st.setString(3, data);
	    			 st.executeUpdate();
		 	 }
	         catch(SQLException e) {System.out.println("whats up?");  e.printStackTrace(); return; }
	             
			 int iloscPozycjiNaRecepcie=rand.nextInt(4)+1;
			 int[] idLekow=getRandomIds(iloscPozycjiNaRecepcie, leki);
			 
			 
			 for (int lek: idLekow){//int j=0;j<iloscPozycjiNaRecepcie;j++){
				 query="INSERT INTO pozycjeNaReceptach (idRecepty, idLeku, iloœæOpakowañ, iloœæDawek, przyjêciaNaDzieñ, procentRefundacji) VALUES (?,?,?,?,?,?)";
				 try{      
					 	PreparedStatement st=conn.prepareStatement(query);
		    			st.setInt(1, idRec);
		    			st.setInt(2,lek);
		    			st.setInt(3, rand.nextInt(2)+1);
		    			st.setInt(4, rand.nextInt(2)+1);
		    			st.setInt(5, rand.nextInt(3)+1);
		    			st.setInt(6, rand.nextInt(1));
		    			st.executeUpdate();
			 	 }
		         catch(SQLException e) {System.out.println("whats up 4?");  e.printStackTrace(); return; }
			 }
		 }
		
		
	}

	private int[] getRandomIds(int iloscPozycjiNaRecepcie,
			ArrayList<Integer> leki) {
		
		int[] drugs=new int[iloscPozycjiNaRecepcie];
		Collections.shuffle(leki);
		for (int i=0;i<iloscPozycjiNaRecepcie;i++)
			drugs[i]=leki.get(i);
		return drugs;
	}

	private void insertInterviews() {
		String query="SELECT idWizyty, data FROM wizytyArchiwum";
		
		ArrayList<Integer> ids=new ArrayList<>();
		
		ArrayList<String> daty=new ArrayList<>();
		
		
		ResultSet rs;
		
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
            
             while (rs.next()){
            	ids.add(rs.getInt("idWizyty"));
            	daty.add(rs.getString("data"));
 			}
         }
         catch(SQLException e) {
        	 System.out.println("whats up 3 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
		
		 for (int i=0;i<ids.size();i++) {
			 int id=ids.get(i);
			 String data=daty.get(i);
			 String konsultacja=getRandomInterview(id);
			
			 
			
			 query="INSERT INTO konsultacje (idWizyty, treœæ, data) VALUES (?,?,?)";
			 try{      
				 	PreparedStatement st=conn.prepareStatement(query);
	    			 st.setInt(1, id);
	    			 st.setString(2, konsultacja);
	    			 st.setString(3, data);
	    			 st.executeUpdate();
		 	 }
	         catch(SQLException e) {System.out.println("whats up?");  e.printStackTrace(); return; }
	             
			 
		 }
		
		
	}

	private String getRandomInterview(int id) {
		String iv="Konsultacja "+id+"/x: ";
		String[] random={"Bez zmian. Stan w normie.","Skargi na ból brzucha","Zapalenie oskrzeli","P³uca czyste, gard³o w normie.","Zatrucie pokarmowe."}; 
		//String lorem=" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vestibulum in lectus a egestas. Mauris volutpat dolor nec ornare fermentum. Vivamus hendrerit feugiat sollicitudin. Pellentesque sit amet ipsum aliquet ligula tristique consequat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur ex augue, congue mattis arcu at, blandit porttitor mauris. Donec iaculis quam metus, sit amet placerat purus gravida vitae. Vestibulum at pellentesque magna.";
		return iv+" "+random[id%random.length];//+lorem;
	}

	private void insertEmployees() {
		 ArrayList<String[]> lista=dajPracownikow();
		 System.out.println("dano");
		 
		 //todo: columny!
         for (String[] s : lista) {
             String query="INSERT INTO osoby (imiê,nazwisko,pesel,telefon,ulica,nrdomu,nrmieszkania,kodpocztowy,miejscowoœæ) VALUES ('"+s[0]+"', '"+s[1]+"', '"+s[2]+"', '"+s[3]+"', '"+s[4]+"', '"+s[5]+"', '"+s[6]+"', '"+s[7]+"', '"+s[8]+"')";
             try {
                 Statement st=conn.createStatement();
                 st.executeUpdate(query);
             }
             catch(SQLException e) {System.out.println("whats up?");  e.printStackTrace(); return; }
             
             //pobierz id œwie¿o dodanej osoby
             query="SELECT idOsoby FROM osoby ORDER BY idOsoby DESC LIMIT 1;";
             int idOsoby=1;
             
             try {
                 Statement st=conn.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 rs.next();
                 idOsoby = rs.getInt("idOsoby");
                 System.out.println(idOsoby);
             }
             catch(SQLException e) {System.out.println("whats up 3 ?");  e.printStackTrace(); return; }
             
             
             
             
             query="INSERT INTO pracownicy VALUES ("+idOsoby+", "+s[9]+", '"+s[10]+"', null)";//'"+s[12]+"', '"+s[4]+"', '"+s[5]+"', '"+s[6]+"', '"+s[7]+"', '"+s[8]+"', '"+s[9]+"')";
             try {
                 Statement st=conn.createStatement();
                 st.executeUpdate(query);
             }
             catch(SQLException e) {System.out.println("whats up 2 ?");  e.printStackTrace(); return; }
         }
         
         System.out.println("Zakoñczono.");

	}

	private ArrayList<String[]> dajPracownikow() {
		 ArrayList<String[]> lista=new ArrayList<>();
	     
		 //lekarze
		 //TODO: id = select id from typyPracownikow
		 String idLekarza="1";
		 //imiê, nazwisko, pesel, telefon, ulica, nr domu, nr mieszkania, kod, miejscowosc 
		 String[] adolega={"Aleksandra","Do³êga","90020509589","800199111", "Mariana Smoluchowskiego", "34", "6", "50-372", "Wroc³aw", idLekarza, "adolega"};
	     String[] amis={"Aleksandra","Miœ","80021509589","770189122", "Kazimierza Pu³askiego", "4A", "16", "50-300", "Wroc³aw", idLekarza, "amis"};
	     String[] jkowalski={"Jan","Kowalski","76090509580","823199123", "Prosta", "13", "2", "52-172", "Wroc³aw", idLekarza, "jkowalski"};
		 lista.add(adolega);
		 lista.add(amis);
		 lista.add(jkowalski);
	     
	     //rejestratorzy
	     String idRejestratora="2";
	     String[] jnowak={"Janina","Nowak","50030509589","823199333", "Prosta", "12", "2", "52-172", "Wroc³aw", idRejestratora, "jnowak"};
	     String[] snowacka={"Sylwia","Nowacka","86022066587","665199123", "Weso³a", "131", "2a", "52-992", "Wroc³aw",idRejestratora, "snowacka"};
	     String[] mmarciniec={"Marzena","Marciniec","77121809519","888199456", "Józefa Pi³sudskiego", "13", "2", "52-172", "Wroc³aw", idRejestratora, "mmarciniec"};
	     String[] dpiotrowska={"Dorota","Piotrowska","92111709589","821234523", "Józefa Pi³sudskiego", "1", "27", "52-172", "Wroc³aw", idRejestratora, "dpiotrowska"};
	     lista.add(jnowak);
	     lista.add(snowacka);
	     lista.add(mmarciniec);
	     lista.add(dpiotrowska);
	     
	     //admini
	     String idAdmina="3";
	     String[] abula={"Aleksandra", "Bu³a", "90020509587","663144343", "Prosta", "12", "29", "52-172", "Wroc³aw", idAdmina, "abula"};
	     lista.add(abula);
		 
		 return lista;

		
		
	}

	private void insertPharmindex() {
		ArrayList<String[]> lista=dajLeki();
		 System.out.println("dano");
		 String query="INSERT INTO leki (nazwa, postaæ, dawka, opakowanie) VALUES (?,?,?,?)";
		 try {
		 int ind=0;
			 for (String[] s : lista) {
				 PreparedStatement st=conn.prepareStatement(query);
				 st.setString(1, s[0]);
				 st.setString(2, s[1]);
				 st.setString(3, s[2]);
				 st.setString(4, s[3]);
				 st.executeUpdate();
				 ind++;
				 System.out.println(ind + " "+s[0]);
	             }
        
            
        }
		 catch(SQLException e) {
       	 System.out.println("whats up?");  
       	 e.printStackTrace();
        }
        
        System.out.println("Zakoñczono.");

		
	}

	private void insertCodes() {
		 ArrayList<String[]> lista=dajChoroby();
		 System.out.println("dano");
		 
		 try {
		 int ind=0;
			 for (String[] s : lista) {
				// StringBuilder s=new Str
				 //s[0]=s[0].;
	             //String query="INSERT INTO choroby (kod, nazwa) VALUES ('"+s[0]+"', '"+s[1]+"')";//, '"+s[2]+"', '"+s[3]+"', '"+s[4]+"', '"+s[5]+"', '"+s[6]+"', '"+s[7]+"', '"+s[8]+"', '"+s[9]+"')";
				 PreparedStatement st=conn.prepareStatement("INSERT INTO choroby (kod, nazwa) VALUES (?,?)");
				 st.setString(1, s[0]);
				 st.setString(2, s[1]);
				 st.executeUpdate();
				 ind++;
				 System.out.println(ind + " "+s[0]);
	             
	             	
	               
	                // st.addBatch(query);
	                 //st.executeUpdate(query);
	             }
         // st.executeBatch();   
             
         }
		 catch(SQLException e) {
        	 System.out.println("whats up?");  
        	 e.printStackTrace();
        	 //break;
         }
         
         System.out.println("Zakoñczono.");

		
	}

	private ArrayList<String[]> dajChoroby() {
		 ArrayList<String[]> lista=new ArrayList<>();
	      
	     String path="C:/Users/Olusiak/Downloads/TODO/kody.txt";
		 FileInputStream input;
		
		 try {
				input = new FileInputStream (path);
				InputStreamReader isr = new InputStreamReader(input, "UTF-8");

				BufferedReader br=new BufferedReader(isr);//(Reader)isr);
				String linia=null; 


				while ((linia=br.readLine()) != null) {
		           if (!linia.contains("<label>")) {
		              String[] haslo=linia.trim().split("_");
		              
		              lista.add(haslo);
		           
		             
		             // System.out.println(haslo[0] + " -> " + haslo[1]);
		           
		           }
		       }
		     
		       input.close();
		 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return lista;
		
		
	}

	
	private ArrayList<String[]> dajLeki() {
		 ArrayList<String[]> lista=new ArrayList<>();
	      
	     String path="C:/Users/Olusiak/Downloads/IN¯/leki.txt";
		 FileInputStream input;
		
		 try {
				input = new FileInputStream (path);
				InputStreamReader isr = new InputStreamReader(input, "UTF-8");

				BufferedReader br=new BufferedReader(isr);//(Reader)isr);
				String linia=null; 


				while ((linia=br.readLine()) != null) {
		          
		              String[] haslo=linia.trim().split(":");
		              
		              lista.add(haslo);
		           
		             
		             // System.out.println(haslo[0] + " -> " + haslo[1]);
		           
		           
		       }
		     
		       input.close();
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
		 return lista;
		
		
	}
	
	
	private ArrayList<String> dajPoradnie() {
		 ArrayList<String> lista=new ArrayList<>();
	      
	     String path="C:/Users/Olusiak/Desktop/poradnie.txt";
		 FileInputStream input;
		
		 try {
				input = new FileInputStream (path);
				InputStreamReader isr = new InputStreamReader(input, "UTF-8");

				BufferedReader br=new BufferedReader(isr);//(Reader)isr);
				String linia=null; 


				while ((linia=br.readLine()) != null) {


		              lista.add(linia);
		              //System.out.println(">"+linia);
		       }
		     
		       input.close();
		 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return lista;
		
		
	}

	
	
	public ArrayList<String[]> dajOsobyPacjentow() {
		
	     ArrayList<String[]> lista=new ArrayList<>();
	       
	            Random ran=new Random();
	            
	            String[] nazwiska= { "Adamiec", "£oœ", "Kochaniec", "Witkiewicz", "Anulewicz", "Ba³towicz", "Piotrowicz", "£apuszka", "Odrzanik", "Ziêba", "Dziêcio³", "Kotas", "Druzga³a", "Szuka³a", "Lipowicz", "Œwierk", "Skoczylas", "Polak", "Pa³ys", "Jakubiec", "Tomczak",  "Rzepa", "Brzêczyszczykiewicz", "Marchewka", "Nowak", "Szmidt", "Weso³y", "Wala", "Kowalczyk", "Kmicic", "Jêdrysik", "Mañkowicz", "Adamek", "Pacynka", "Toma", "Ratajec", "Kosmala", "Kowaniec", "Koper", "£opatka", "Chmurka" };
	            String[] imiona= { "Beata", "B³a¿ej", "Zbigniew", "Zenon", "Eustachy", "Ksawery", "Aleksandra", "Paulina", "Dominika", "Helena", "Krystyna", "¯aneta", "Katarzyna", "Arkadiusz", "Witold", "Antoni","Karol","Anna", "Ernest", "Piotr", "Adam", "Marek", "Irena", "Monika", "Eleonora", "Anna", "Pawe³", "Micha³", "Marcin" };
	            String[] ulice = { "Leœna", "Polna", "Wincentego Pola", "Œwidnicka", "Czekoladowa", "Zakopiañska", "Basztowa", "Kucharska", "Bananowa", "Gruszkowa", "Zajêcza", "Niebieska", "Krzywa", "Krótka" };
	            int x=12811;
	            int pkt=96;
	            
	           
	            int ilosc=300;
	            int imionaLength=imiona.length, nazwiskaLength=nazwiska.length;
	            
	            //for (int i=0; i<nazwiska.length; i++)
	            //    for (int j=0; j<imiona.length; j++) 
	            
	            for (int i=1;i<=ilosc;i++)
	            {
	            	
	            	int ranN=ran.nextInt(nazwiskaLength), ranI=ran.nextInt(imionaLength);
	            	
	                    String[] pacjent=new String[10];    
	                    
	                     int r=pkt-ran.nextInt(60);
	                     int m=ran.nextInt(12)+1;
	                     int d=ran.nextInt(30)+1;
	                     if (m==2 && d==29) d=28;
	                   
	                    pacjent[0]=""+i;
	                    pacjent[1]=imiona[ranI];
	                    pacjent[2]=nazwiska[ranN];
	                   
	                    pacjent[3]=r+""+((m<10) ? "0" : "")+m+""+((d<10) ? "0" : "")+d+""+x;
		                   
	                    pacjent[4]="713"+r+"21";
	                    pacjent[5]=ulice[ran.nextInt(8)];
	                    pacjent[6]=""+(ran.nextInt(100)+1);
	                    pacjent[7]=""+(ran.nextInt(20)+1);
	                    pacjent[8]="50-372";
	                    pacjent[9]="Wroc³aw";
	                    //
	                    //pacjent[8]= Integer.parseInt(pacjent[7]) % 9 == 0 ? "0" : "1";
	                    lista.add(pacjent);
	                    System.out.println(">>"+pacjent[0]+" "+pacjent[1]+" "+pacjent[2]+" "+pacjent[3]+" "+pacjent[4]+" "+pacjent[5]+" "+pacjent[6]+" "+pacjent[7]+" "+pacjent[8]);                
	            }
	
	            return lista;
	}
	
	
	private void insertData() {
		 ArrayList<String[]> lista=dajOsobyPacjentow();
		 System.out.println("dano");
         for (String[] s : lista) {
             String query="INSERT INTO osoby VALUES ("+s[0]+", '"+s[1]+"', '"+s[2]+"', '"+s[3]+"', '"+s[4]+"', '"+s[5]+"', '"+s[6]+"', '"+s[7]+"', '"+s[8]+"', '"+s[9]+"')";
             try {
                 Statement st=conn.createStatement();
                 st.executeUpdate(query);
             }
             catch(SQLException e) {System.out.println("whats up?");  e.printStackTrace();}
             
         }
         
         System.out.println("Zakoñczono.");
	}
	
	private void insertPlaces() {
		 ArrayList<String> lista=dajPoradnie();
		 System.out.println("dano");
        for (String s : lista) {
          
        	PreparedStatement st;
			try {
				st = conn.prepareStatement("INSERT INTO poradnie (nazwa) VALUES (?)");
				st.setString(1, s);
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
        	
        }
        
        System.out.println("Zakoñczono.");
	}
	

	private void insertTypes() {
		 ArrayList<String> lista=dajTypy();
		 System.out.println("dano");
        for (String s : lista) {
          
        	PreparedStatement st;
			try {
				st = conn.prepareStatement("INSERT INTO typyPracownikow (nazwa) VALUES (?)");
				st.setString(1, s);
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
        	
        }
        
        System.out.println("Zakoñczono.");
	}
	
	
	private void insertDoctors() {
		String specjalnosc="internista";
		ArrayList<Integer> lekarze=new ArrayList<Integer>();
		
		//pobierz id typu, jakim jest lekarz
		String query="SELECT idTypu FROM typyPracownikow WHERE nazwa='lekarz'";
		int idTypu=1;
		ResultSet rs;
		
		 try {
             Statement st=conn.createStatement();
             rs = st.executeQuery(query);
             rs.next();
             idTypu = rs.getInt("idTypu");
             System.out.println(idTypu);
         }
         catch(SQLException e) {
        	 System.out.println("whats up 3 ?");  
        	 e.printStackTrace(); 
        	 return; 
         }
     
		 //wybierz lekarzy spoœród pracowników
		query="SELECT idOsoby FROM pracownicy WHERE idTypu = ?";    
		PreparedStatement st;
		try {
			st = conn.prepareStatement(query);
			st.setInt(1, idTypu);
			rs = st.executeQuery();
			while (rs.next()){
				lekarze.add(rs.getInt("idOsoby"));
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
        
		//dodaj internistów
		int i=1;
		for (int idLekarza: lekarze){
			query="INSERT INTO lekarze (idOsoby, PWZ, specjalnoœæ) VALUES (?,?,?)";    
			//PreparedStatement st;
			try {
				st = conn.prepareStatement(query);
				st.setInt(1, idLekarza);
				st.setInt(2,i*1111111);
				i++;
				st.setString(3, specjalnosc);
				st.executeUpdate();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
	}
	
	private ArrayList<Integer> dajLekarzy() {
		ArrayList<Integer> lekarze=new ArrayList<Integer>();
		
		String query="SELECT idOsoby FROM lekarze";    
		
		try {
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
            	lekarze.add(rs.getInt("idOsoby"));
            }
        }
        catch(SQLException e) {System.out.println("whats up 3 ?");  e.printStackTrace(); }
		return lekarze;
        
	}
	
	private ArrayList<Integer> dajOsobyNaPacjentow(ArrayList<Integer> lekarze){

		String query="SELECT idOsoby FROM osoby";   
		ArrayList<Integer> pacjenci=new ArrayList<Integer>();
		
		try {
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
            	int pacjent=rs.getInt("idOsoby");
            	if (!lekarze.contains(pacjent)){
            		pacjenci.add(pacjent);
            	}
            }
        }
        catch(SQLException e) {System.out.println("whats up 2 ?");  e.printStackTrace(); }
		
		return pacjenci;
	}
	
	private void insertPatients() {
		//wybierz lekarzy
		ArrayList<Integer> lekarze=dajLekarzy();
		
		//wybierz pacjentów i przypisz im lekarzy
		
		//wybierz pacjentów [którzy nie s¹ lekarzami - za³o¿enie]
		ArrayList<Integer> pacjenci=dajOsobyNaPacjentow(lekarze);
		
		
		//przypisz lekarzy
		int lekarzeSize=lekarze.size();
		PreparedStatement st;
		String query;
		for (int pacjent: pacjenci) {
			query="INSERT INTO pacjenci (idPacjenta, idLekarza) VALUES (?,?)";    
			
			try {
				st = conn.prepareStatement(query);
				st.setInt(1, pacjent);
				st.setInt(2,lekarze.get(pacjent%lekarzeSize));
				st.executeUpdate();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
        
	}
	
	private void insertApps() {
		ArrayList<Integer> lekarze=dajLekarzy();
		ArrayList<Integer> pacjenci=dajPacjentow();
		
		int lekarzeSize=lekarze.size(), pacjenciSize=pacjenci.size();
		int idPacjenta, idLekarza;
		String termin, query="INSERT INTO wizyty (idPacjenta, idLekarza, data, status) VALUES (?,?,?,?)";
		Random random = new Random();
		
		ArrayList<TempWizyta> dane=new ArrayList<TempWizyta>();
		HashSet<String> hasze1=new HashSet<String>();
		HashSet<String> hasze2=new HashSet<String>();
		String hasz1, hasz2;
		for (int i=0;i<3000; i++){
			idPacjenta=pacjenci.get(random.nextInt(pacjenciSize));
			idLekarza=lekarze.get(i%lekarzeSize);
			termin=dajRandomowyTimestamp(idLekarza%2==0);
			hasz1=idPacjenta+""+termin;
			hasz2=idLekarza+""+termin;
			if (hasze1.contains(hasz1) || hasze2.contains(hasz2)) {
				System.out.println("takie same hasze: "+hasz1+" ; "+hasz2);
				continue;
			}
			hasze1.add(hasz1);
			hasze2.add(hasz2);
			//Object[] obj={idPacjenta, idLekarza, termin};
			TempWizyta obj=new TempWizyta(idPacjenta,idLekarza,termin);
			dane.add(obj);
		}
		
		Collections.sort(dane);
		for (TempWizyta e: dane)
			System.out.println(e.getTermin());
		//if (true) return;
		
		for (TempWizyta o: dane){
			PreparedStatement st;
			try {
				st = conn.prepareStatement(query);
				st.setInt(1, o.getIdPacjenta());//(Integer)o[0]);
				st.setInt(2, o.getIdLekarza());//(Integer)o[1]);
				st.setString(3, o.getTermin());//(String)o[2]);
				st.setString(4, "oczekuj¹ca");
				st.executeUpdate();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private ArrayList<Integer> dajPacjentow() {
		ArrayList<Integer> pacjenci=new ArrayList<Integer>();
		
		String query="SELECT idPacjenta FROM pacjenci";    
		
		try {
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
            	pacjenci.add(rs.getInt("idPacjenta"));
            }
        }
        catch(SQLException e) {System.out.println("whats up 3 ?");  e.printStackTrace(); }
		return pacjenci;
	}

	private String dajRandomowyTimestamp(boolean drugaZmiana) {
		//2013-2016
		int rokStart=2013, rokEnd=2016;
		int rok= rokStart + (int)Math.round(Math.random() * (rokEnd - rokStart));
		
		//dla 2016 tylko do marca
		Random random = new Random();
		int miesiac= (rok==2016) ? 1+random.nextInt(3)  : 1+random.nextInt(12);
		
		int dzien;
		switch (miesiac) {
			case 2:
				int dni= (rok==2016) ? 29 : 28; //bo 2013-2015
				dzien= 1+random.nextInt(dni); 
				break;
			case 4: case 6: case 9: case 11:
				dzien= 1+random.nextInt(30); 
				break;
			default:
				dzien= 1+random.nextInt(31); 
				break;
				
		}
		
		//od 7 do 20 - 2 zmiany: 7-14, 13-20
		int godzina=7+random.nextInt(7); 
		if (drugaZmiana) godzina+=6;
		
		int[] minuty={0,15,30,45};
		int minuta=minuty[random.nextInt(minuty.length)];
		
		String zeroG =  (godzina<10) ? "0" : "";
		String zeroM =  (minuta<10) ? "0" : "";
		String zeroD =  (dzien<10) ? "0" : "";
		String zeroMc =  (miesiac<10) ? "0" : "";
		
		return rok+"-"+zeroMc+miesiac+"-"+zeroD+dzien+" "+zeroG+godzina+":"+zeroM+minuta+":00";
	}
	
	private ArrayList<String> dajTypy() {

		ArrayList<String> typy=new ArrayList<String>();
		typy.add("lekarz");
		typy.add("rejestratorka");
		typy.add("administrator");
		return typy;
	}

	
	
	public static void main(String[] args) {
		new InsertDB();
	}
}
