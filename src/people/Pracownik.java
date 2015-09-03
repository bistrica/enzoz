package people;

public class Pracownik extends Osoba {

	public Pracownik(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String miejscowosc, String kod, String telefon) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania, miejscowosc, kod, telefon);
	}

	public Pracownik(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);
	}
	
	@Override
	public String toString() {
		return "Pracownik: " + super.toString();
	}

	//GeorgianCalendar dataZatrudnienia;
}
