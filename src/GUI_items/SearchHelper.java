package GUI_items;

import people.Lekarz;

public class SearchHelper {

	int day, month, year;
	long PESEL;
	Lekarz surname;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getPESEL() {
		return PESEL;
	}

	public void setPESEL(long pESEL) {
		PESEL = pESEL;
	}

	public Lekarz getSurname() {
		return surname;
	}

	public void setSurname(Lekarz surname) {
		this.surname = surname;
	}
}
