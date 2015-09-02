package eNZOZ;

public class TempWizyta implements Comparable {

	private int idPacjenta;
	private int idLekarza;
	private String termin;

	public TempWizyta(int idPacjenta, int idLekarza, String termin) {
		this.setIdPacjenta(idPacjenta);
		this.setIdLekarza(idLekarza);
		this.setTermin(termin);
		
	}

	public int getIdPacjenta() {
		return idPacjenta;
	}

	public void setIdPacjenta(int idPacjenta) {
		this.idPacjenta = idPacjenta;
	}

	public int getIdLekarza() {
		return idLekarza;
	}

	public void setIdLekarza(int idLekarza) {
		this.idLekarza = idLekarza;
	}

	public String getTermin() {
		return termin;
	}

	public void setTermin(String termin) {
		this.termin = termin;
	}

	@Override
	public int compareTo(Object o) {
		return termin.compareTo(((TempWizyta)o).termin);
	}

}
