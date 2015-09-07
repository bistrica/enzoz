package items;

public class Choroba {

	private String kod, nazwa;
	private int id;
	
	public Choroba(String kod, String nazwa) {
		this.kod = kod;
		this.nazwa = nazwa;
	}

	public Choroba(int id, String kod, String nazwa) {
		this.kod = kod;
		this.nazwa = nazwa.trim();
		this.id=id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return kod+"  "+nazwa;
	}
}
