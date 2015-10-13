package items;

public class Illness {

	private String code, name;
	private int id;

	public Illness(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Illness(int id, String code, String name) {
		this.code = code;
		this.name = name.trim();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return code + "  " + name;
	}

	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}
}
