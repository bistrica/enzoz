package people;

public class Pacjent extends Osoba {

	Lekarz lekarzProwadzacy;
	
	public Pacjent(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String telefon) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania, telefon);
		// TODO Auto-generated constructor stub
	}

	public Pacjent(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);
		// TODO Auto-generated constructor stub
	}

	

}
