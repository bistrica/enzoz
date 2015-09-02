package items;

import java.util.Calendar;
import java.util.GregorianCalendar;

import people.Lekarz;
import people.Pacjent;

public class Wizyta {
	
	GregorianCalendar data;
	Pacjent pacjent;
	Lekarz lekarz;
	
	/*public String danePacjenta() {
		return pacjent.getImie()+" "+pacjent.getNazwisko();
	}*/
	
	public Pacjent getPacjent() {
		return pacjent;
	}
	
	public GregorianCalendar getData() {
		return data;
	}

	public String getHourToString() {
		return data.get(Calendar.HOUR)+":"+data.get(Calendar.MINUTE);
	}
	
	
	
	
	
}
