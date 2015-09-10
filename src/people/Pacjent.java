package people;

import items.Choroba;

import java.util.ArrayList;

public class Pacjent extends Osoba {

	Lekarz lekarzProwadzacy;
	ArrayList<Choroba> chorobyPrzewlek�e;
	
	public Pacjent(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania,String miejscowosc, String kod, String telefon) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania, miejscowosc, kod, telefon);
		// TODO Auto-generated constructor stub
	}

	public Pacjent(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);
		// TODO Auto-generated constructor stub
	}

	public Pacjent(Osoba person, ArrayList<Choroba> illnesses) {
		this(person.id, person.imie, person.nazwisko,person.PESEL, person.ulica,person.nrDomu,person.nrMieszkania,person.miejscowosc,person.kod,person.telefon);
		chorobyPrzewlek�e=illnesses;
	}

	public Pacjent(Osoba person) {
		this(person, null);
	}

	public ArrayList<Choroba> getChorobyPrzewlek�e() {
		return chorobyPrzewlek�e;
	}

	public void setChorobyPrzewlek�e(ArrayList<Choroba> chorobyPrzewlek�e) {
		this.chorobyPrzewlek�e = chorobyPrzewlek�e;
	}

	@Override
	public String toString() {
		return "Pacjent [lekarzProwadzacy=" + lekarzProwadzacy
				+ ", chorobyPrzewlek�e=" + chorobyPrzewlek�e + "]" + super.toString();
	}

	public String getAddressInfo() {
		String lokal= nrMieszkania==null ? "" : "m. "+nrMieszkania;
		return ulica+" "+nrDomu+" "+lokal+", "+kod+" "+miejscowosc;
	}

	public String getMainInfo() {
		return imie+" "+nazwisko+" "+PESEL;
	}

	

}
