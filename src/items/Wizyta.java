package items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import people.Lekarz;
import people.Pacjent;

public class Wizyta {

	private GregorianCalendar data;
	private Pacjent pacjent;
	private Lekarz lekarz;
	private ArrayList<Choroba> rozpoznaneChoroby;
	private Recepta recepta;
	private ArrayList<Skierowanie> skierowania;
	private Konsultacja konsultacja;

	private String status;

	private int id;
	private boolean isArchive = false;

	// int id;

	/*
	 * public String danePacjenta() { return
	 * pacjent.getImie()+" "+pacjent.getNazwisko(); }
	 */
	public Wizyta(int id, GregorianCalendar data) {
		this.id = id;
		this.data = data;
	}

	public Wizyta(GregorianCalendar data, Pacjent pacjent) {
		this.data = data;
		this.pacjent = pacjent;
	}

	public Wizyta(GregorianCalendar data, Pacjent pacjent, Lekarz lekarz) {
		this.data = data;
		this.pacjent = pacjent;
		this.lekarz = lekarz;
	}

	public Wizyta(int id, GregorianCalendar data, Pacjent pacjent, Lekarz lekarz) {
		this.id = id;
		this.data = data;
		this.pacjent = pacjent;
		this.lekarz = lekarz;
	}

	public Pacjent getPacjent() {
		return pacjent;
	}

	public GregorianCalendar getData() {
		return data;
	}

	public String getHourToString() {
		// SDF przyjmuje tylko Date!
		/*
		 * DateFormat outputFormat = new SimpleDateFormat("MM/yyyy"); DateFormat
		 * inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		 * 
		 * String inputText = "2012-11-17T00:00:00.000-05:00"; Date date =
		 * inputFormat.parse(inputText); String outputText =
		 * outputFormat.format(date);
		 */
		// SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		// return format.format(data);
		String zero = data.get(Calendar.MINUTE) >= 10 ? "" : "0";
		return data.get(Calendar.HOUR_OF_DAY) + ":" + zero
				+ data.get(Calendar.MINUTE);
	}

	public Lekarz getLekarz() {
		return lekarz;
	}

	public void setLekarz(Lekarz lekarz) {
		this.lekarz = lekarz;
	}

	public ArrayList<Choroba> getRozpoznaneChoroby() {
		return rozpoznaneChoroby;
	}

	public int getId() {
		return this.id;
	}

	public void setPacjent(Pacjent pacjent) {
		this.pacjent = pacjent;
	}

	@Override
	public String toString() {
		return "Wizyta [data=" + data + ", pacjent=" + pacjent + ", lekarz="
				+ lekarz + ", rozpoznaneChoroby=" + rozpoznaneChoroby + ", id="
				+ id + "]";
	}

	public String getDataToString() {
		return data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1)
				+ "-" + data.get(Calendar.DATE) + ", " + getHourToString();
	}

	public Recepta getRecepta() {
		return recepta;
	}

	public void setRecepta(Recepta recepta) {
		this.recepta = recepta;
	}

	public ArrayList<Skierowanie> getSkierowania() {
		return skierowania;
	}

	public void setSkierowania(ArrayList<Skierowanie> skierowania) {
		this.skierowania = skierowania;
	}

	public Konsultacja getKonsultacja() {
		return konsultacja;
	}

	public void setKonsultacja(Konsultacja konsultacja) {
		this.konsultacja = konsultacja;
	}

	public void setRozpoznaneChoroby(ArrayList<Choroba> rozpoznaneChoroby) {
		this.rozpoznaneChoroby = rozpoznaneChoroby;
	}

	public boolean isArchiveAppointment() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wizyta other = (Wizyta) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
