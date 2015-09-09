package items;

public class Poradnia {

	String nazwa;
	int id;
	
	public Poradnia(int id, String nazwa) {
		this.id=id;
		this.nazwa=nazwa;
	}
	
	@Override
	public String toString(){
		return nazwa;
	}
}
