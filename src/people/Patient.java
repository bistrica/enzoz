package people;

import items.Illness;

import java.util.ArrayList;

public class Patient extends Person {

	Doctor mainDoctor;
	ArrayList<Illness> constantIllnesses;

	public Patient(int id, String name, String surname, String pESEL,
			String street, String houseNo, String flatNo, String city,
			String code, String phone) {
		super(id, name, surname, pESEL, street, houseNo, flatNo, city, code,
				phone);
		// TODO Auto-generated constructor stub
	}

	public Patient(int id, String name, String surname, String pESEL) {
		super(id, name, surname, pESEL);
		// TODO Auto-generated constructor stub
	}

	public Patient(Person person, ArrayList<Illness> illnesses) {
		this(person.id, person.name, person.surname, person.PESEL,
				person.street, person.houseNo, person.flatNo, person.city,
				person.code, person.phone);
		constantIllnesses = illnesses;
	}

	public Patient(Person person) {
		this(person, null);
	}

	public ArrayList<Illness> getChorobyPrzewlek�e() {
		return constantIllnesses;
	}

	public void setChorobyPrzewlek�e(ArrayList<Illness> constIllnesses) {
		this.constantIllnesses = constIllnesses;
	}

	@Override
	public String toString() {
		return "Pacjent [lekarzProwadzacy=" + mainDoctor
				+ ", chorobyPrzewlek�e=" + constantIllnesses + "]"
				+ super.toString();
	}

	public String getAddressInfo() {
		String flat = flatNo == null ? "" : "m. " + flatNo;
		return street + " " + houseNo + " " + flat + ", " + code + " " + city;
	}

	public String getMainInfo() {
		return name + " " + surname + " " + PESEL;
	}

}
