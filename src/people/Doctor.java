package people;

public class Doctor extends Employee {

	private int PWZ;

	public Doctor(int id, String name, String surname, String pESEL,
			String street, String houseNo, String flatNo, String city,
			String code, String phone, int PWZ) {
		super(id, name, surname, pESEL, street, houseNo, flatNo, city, code,
				phone);
		this.PWZ = PWZ;

	}

	public Doctor(int id, String name, String surname, String pESEL) {
		super(id, name, surname, pESEL);
	}

	public Doctor(int id, String name, String surname, String pESEL, int PWZ) {
		super(id, name, surname, pESEL);
		this.PWZ = PWZ;
	}

	public Doctor(Person person, int PWZ) {
		// person.street,person.houseNo,person.flatNo,person.city,person.code,person.phone
		// zbêdne
		this(person.id, person.name, person.surname, person.PESEL,
				person.street, person.houseNo, person.flatNo, person.city,
				person.code, person.phone, PWZ);
	}

	public Doctor(Person person) {
		this(person, -1); // TODO: zaznaczyæ,¿e dla testu
	}

	@Override
	public String toString() {
		return super.getName();
	}

	/*
	 * public void setPWZ(int PWZ) { this.PWZ=PWZ; }
	 */

}
