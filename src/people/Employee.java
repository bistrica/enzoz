package people;

public class Employee extends Person {

	public Employee(int id, String name, String surname, String pESEL,
			String street, String houseNo, String flatNo, String city,
			String code, String phone) {
		super(id, name, surname, pESEL, street, houseNo, flatNo, city, code,
				phone);
	}

	public Employee(int id, String name, String surname, String pESEL) {
		super(id, name, surname, pESEL);
	}

	@Override
	public String toString() {
		return "Pracownik: " + super.toString();
	}

	@Override
	public boolean equals(Object other) {
		System.out.println(other + ", " + this + " "
				+ (other instanceof Employee && ((Employee) other).id == id));
		return (other instanceof Employee && ((Employee) other).id == id);
	}
}
