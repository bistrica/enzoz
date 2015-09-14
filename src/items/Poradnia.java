package items;

public class Poradnia {

	String nazwa;
	int id;

	public Poradnia(int id, String nazwa) {
		this.id = id;
		this.nazwa = nazwa;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return nazwa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poradnia other = (Poradnia) obj;
		if (id != other.id)
			return false;
		if (nazwa == null) {
			if (other.nazwa != null)
				return false;
		} else if (!nazwa.equals(other.nazwa))
			return false;
		return true;
	}
}
