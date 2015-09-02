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
		this.nazwa = nazwa;
		this.id=id;
	}

	public int getId() {
		return id;
	}

}
