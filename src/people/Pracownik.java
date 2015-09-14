package people;

public class Pracownik extends Osoba {

	public Pracownik(int id, String imie, String nazwisko, String pESEL,
			String ulica, String nrDomu, String nrMieszkania,
			String miejscowosc, String kod, String telefon) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania,
				miejscowosc, kod, telefon);
	}

	public Pracownik(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);
	}

	@Override
	public String toString() {
		return "Pracownik: " + super.toString();
	}

	@Override
	public boolean equals(Object other) {
		System.out.println(other + ", " + this + " "
				+ (other instanceof Pracownik && ((Pracownik) other).id == id));
		return (other instanceof Pracownik && ((Pracownik) other).id == id);
	}
	// GeorgianCalendar dataZatrudnienia;
}
