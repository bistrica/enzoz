package people;

public class Lekarz extends Pracownik {

	int PWZ;
	
	public Lekarz(int id, String imie, String nazwisko, String pESEL, String ulica,
			String nrDomu, String nrMieszkania, String telefon, int PWZ) {
		super(id, imie, nazwisko, pESEL, ulica, nrDomu, nrMieszkania, telefon);
		this.PWZ=PWZ;
		
	}

	public Lekarz(int id, String imie, String nazwisko, String pESEL) {
		super(id, imie, nazwisko, pESEL);	
	}

	public Lekarz(int id, String imie, String nazwisko, String pESEL, int PWZ) {
		super(id, imie, nazwisko, pESEL);
		this.PWZ=PWZ;
	}
	
	
	
	
}
