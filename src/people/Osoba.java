package people;

public class Osoba {
	
	int id;
	String imie, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, telefon;

	
	public Osoba(int id, String imie, String nazwisko, String pESEL) {
		
		this.id=id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		PESEL = pESEL;
	}

	public Osoba(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String telefon) {
		
		this.id=id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		PESEL = pESEL;
		this.ulica = ulica;
		this.nrDomu = nrDomu;
		this.nrMieszkania = nrMieszkania;
		this.telefon = telefon;
	}

	public String getImie() {
		return imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public String getPESEL() {
		return PESEL;
	}

	public String getUlica() {
		return ulica;
	}

	public String getNrDomu() {
		return nrDomu;
	}

	public String getNrMieszkania() {
		return nrMieszkania;
	}

	public String getTelefon() {
		return telefon;
	}
	
	public String nameToString() {
		return imie+" "+nazwisko;
	}

	public int getId() {
		return id;
	}

}
