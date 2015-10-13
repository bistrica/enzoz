package people;

public class Person {

	int id;
	String name, surname, PESEL, street, houseNo, flatNo, city, code, phone;

	public Person(int id, String name, String surname, String pESEL) {

		this.id = id;
		this.name = name;
		this.surname = surname;
		PESEL = pESEL;
	}

	public Person(int id, String name, String surname, String pESEL,
			String street, String houseNo, String flatNo, String city,
			String code, String phone) {

		this.id = id;
		this.name = name;
		this.surname = surname;
		this.PESEL = pESEL;
		this.street = street;
		this.houseNo = houseNo;
		this.flatNo = flatNo;
		this.city = city;
		this.code = code;
		this.phone = phone;
	}

	public String getFirstName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getPESEL() {
		return PESEL;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public String getPhone() {
		return phone;
	}

	public String nameToString() {
		return name + " " + surname;
	}

	public int getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return "Osoba [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", PESEL=" + PESEL + ", street=" + street + ", houseNo="
				+ houseNo + ", flatNo=" + flatNo + ", city=" + city + ", code="
				+ code + ", phone=" + phone + "]";
	}

	public String getName() {
		return name + " " + surname;
	}

}
