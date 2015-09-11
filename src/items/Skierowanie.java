package items;

public class Skierowanie {

	Poradnia poradnia;
	String opisBadan;
	
	public Skierowanie(Poradnia poradnia, String opisBadan) {
		this.poradnia=poradnia;
		this.opisBadan=opisBadan;
		
	}

	public Poradnia getPoradnia() {
		return poradnia;
	}

	public void setPoradnia(Poradnia poradnia) {
		this.poradnia = poradnia;
	}

	public String getOpisBadan() {
		return opisBadan;
	}

	public void setOpisBadan(String opisBadan) {
		this.opisBadan = opisBadan;
	}
	
	
}
