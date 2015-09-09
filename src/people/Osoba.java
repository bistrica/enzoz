package people;

public class Osoba {
	
	int id;
	String imie, nazwisko, PESEL, ulica, nrDomu, nrMieszkania, miejscowosc, kod, telefon;

	
	public Osoba(int id, String imie, String nazwisko, String pESEL) {
		
		this.id=id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		PESEL = pESEL;
	}

	public Osoba(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String miejscowosc, String kod, String telefon) {
		
		this.id=id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.PESEL = pESEL;
		this.ulica = ulica;
		this.nrDomu = nrDomu;
		this.nrMieszkania = nrMieszkania;
		this.miejscowosc=miejscowosc;
		this.kod=kod;
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

	public String getMiejscowosc() {
		return miejscowosc;
	}

	@Override
	public String toString() {
		return "Osoba [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko
				+ ", PESEL=" + PESEL + ", ulica=" + ulica + ", nrDomu="
				+ nrDomu + ", nrMieszkania=" + nrMieszkania + ", miejscowosc="
				+ miejscowosc + ", kod=" + kod + ", telefon=" + telefon + "]";
	}
	
	public String getName() {
		return imie+" "+nazwisko;
	}

	

}
