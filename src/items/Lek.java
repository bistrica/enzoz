package items;

public class Lek {

	String nazwa, postac, dawka, opakowanie;
	int id;
	
	public Lek(int id, String nazwa, String postac, String dawka, String opakowanie) {
		this.id=id;
		this.nazwa=nazwa;
		this.postac=postac;
		this.dawka=dawka;
		this.opakowanie=opakowanie;

		
	}

	@Override
	public boolean equals(Object obj) {
		//return toString().equals(obj.toString());
		return (obj!=null && obj instanceof Lek && ((Lek)obj).id==id);
	}

	
	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getPostac() {
		return postac;
	}

	public void setPostac(String postac) {
		this.postac = postac;
	}

	public String getDawka() {
		return dawka;
	}

	public void setDawka(String dawka) {
		this.dawka = dawka;
	}

	public String getOpakowanie() {
		return opakowanie;
	}

	public void setOpakowanie(String opakowanie) {
		this.opakowanie = opakowanie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return nazwa + ", " + postac + ", "
				+ dawka + ", " + opakowanie;
	}
}
