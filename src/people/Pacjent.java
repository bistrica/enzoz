package people;

import items.Choroba;

import java.util.ArrayList;

public class Pacjent extends Osoba {

	Lekarz lekarzProwadzacy;
	ArrayList<Choroba> chorobyPrzewlek쿮;
	
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
		chorobyPrzewlek쿮=illnesses;
	}

	public Pacjent(Osoba person) {
		this(person, null);
	}

	public ArrayList<Choroba> getChorobyPrzewlek쿮() {
		return chorobyPrzewlek쿮;
	}

	public void setChorobyPrzewlek쿮(ArrayList<Choroba> chorobyPrzewlek쿮) {
		this.chorobyPrzewlek쿮 = chorobyPrzewlek쿮;
	}

	@Override
	public String toString() {
		return "Pacjent [lekarzProwadzacy=" + lekarzProwadzacy
				+ ", chorobyPrzewlek쿮=" + chorobyPrzewlek쿮 + "]" + super.toString();
	}

	public String getAddressInfo() {
		String lokal= nrMieszkania==null ? "" : "m. "+nrMieszkania;
		return ulica+" "+nrDomu+" "+lokal+", "+kod+" "+miejscowosc;
	}

	public String getMainInfo() {
		return imie+" "+nazwisko+" "+PESEL;
	}

	

}
