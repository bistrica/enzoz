package people;

public class Pracownik extends Osoba {

	public Pracownik(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String telefon) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania, telefon);
	}

	public Pracownik(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);
	}

	//GeorgianCalendar dataZatrudnienia;
}
