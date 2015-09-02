package items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import people.Lekarz;
import people.Pacjent;

public class Wizyta {
	
	GregorianCalendar data;
	Pacjent pacjent;
	Lekarz lekarz;
	ArrayList<Choroba> rozpoznaneChoroby;
	//int id;
	
	/*public String danePacjenta() {
		return pacjent.getImie()+" "+pacjent.getNazwisko();
	}*/
	
	public Wizyta(GregorianCalendar data, Pacjent pacjent) {
		this.data=data;
		this.pacjent=pacjent;
	}
	
	public Wizyta(GregorianCalendar data, Pacjent pacjent, Lekarz lekarz) {
		this.data=data;
		this.pacjent=pacjent;
		this.lekarz=lekarz;
	}
	
	public Pacjent getPacjent() {
		return pacjent;
	}
	
	public GregorianCalendar getData() {
		return data;
	}

	public String getHourToString() {
		return data.get(Calendar.HOUR)+":"+data.get(Calendar.MINUTE);
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
		// TODO Auto-generated method stub
		return 0;
	}
	

	
	
	
}
