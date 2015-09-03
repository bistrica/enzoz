package people;

public class Lekarz extends Pracownik {

	private int PWZ;
	
	public Lekarz(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String miejscowosc, String kod, String telefon, int PWZ) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania, miejscowosc, kod, telefon);
		this.PWZ=PWZ;
		
	}

	public Lekarz(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);	
	}

	public Lekarz(int id, String imie, String nazwisko, String pESEL, int PWZ) {
		super(id, imie, nazwisko, pESEL);
		this.PWZ=PWZ;
	}

	public Lekarz(Osoba person, int PWZ) {
		//person.ulica,person.nrDomu,person.nrMieszkania,person.miejscowosc,person.kod,person.telefon zbêdne
		this(person.id,person.imie,person.nazwisko,person.PESEL, person.ulica,person.nrDomu,person.nrMieszkania,person.miejscowosc,person.kod,person.telefon, PWZ);
	}
	
	@Override
	public String toString() {
		return "Lekarz: " + super.toString();
	}
	
	/*public void setPWZ(int PWZ) {
		this.PWZ=PWZ;
	}*/
	
	
}
