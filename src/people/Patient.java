package people;

import items.Illness;

import java.util.ArrayList;

public class Patient extends Person {

	private Doctor mainDoctor;
	private ArrayList<Illness> constantIllnesses;

	public Patient(int id, String name, String surname, String pESEL,
			String street, String houseNo, String flatNo, String city,
			String code, String phone) {
		super(id, name, surname, pESEL, street, houseNo, flatNo, city, code,
				phone);
	}

	public Patient(int id, String name, String surname, String pESEL) {
		super(id, name, surname, pESEL);
	}

	public Patient(Person person, ArrayList<Illness> illnesses) {
		super(person);
		constantIllnesses = illnesses;
	}

	public Patient(Person person) {
		this(person, null);
	}

	public ArrayList<Illness> getConstantIllnesses() {
		return constantIllnesses;
	}

	public void setConstantIllnesses(ArrayList<Illness> constIllnesses) {
		this.constantIllnesses = constIllnesses;
	}

	@Override
	public String toString() {
		return "Pacjent [lekarzProwadzacy=" + mainDoctor
				+ ", chorobyPrzewlekłe=" + constantIllnesses + "]"
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
