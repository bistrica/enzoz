package eNZOZ;

public class TempWizyta implements Comparable<TempWizyta> {

	private int IdPacjenta;
	private int IdLekarza;
	private String termin;

	public TempWizyta(int IdPacjenta, int IdLekarza, String termin) {
		this.setIdPacjenta(IdPacjenta);
		this.setIdLekarza(IdLekarza);
		this.setTermin(termin);
		
	}

	public int getIdPacjenta() {
		return IdPacjenta;
	}

	public void setIdPacjenta(int IdPacjenta) {
		this.IdPacjenta = IdPacjenta;
	}

	public int getIdLekarza() {
		return IdLekarza;
	}

	public void setIdLekarza(int IdLekarza) {
		this.IdLekarza = IdLekarza;
	}

	public String getTermin() {
		return termin;
	}

	public void setTermin(String termin) {
		this.termin = termin;
	}

	
	@Override
	public int compareTo(TempWizyta o) {
		return termin.compareTo(((TempWizyta)o).termin);
	}

}
