package items;

public class Medicine {

	String name, formulation, dose, packageDescription;
	int id;

	public Medicine(int id, String name, String formulation, String dose,
			String packageDesc) {
		this.id = id;
		this.name = name;
		this.formulation = formulation;
		this.dose = dose;
		this.packageDescription = packageDesc;

	}

	@Override
	public boolean equals(Object obj) {
		// return toString().equals(obj.toString());
		return (obj != null && obj instanceof Medicine && ((Medicine) obj).id == id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormulation() {
		return formulation;
	}

	public void setFormulation(String formulation) {
		this.formulation = formulation;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getPackageDescription() {
		return packageDescription;
	}

	public void setPackageDescription(String packageDesc) {
		this.packageDescription = packageDesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String desc = name + ", " + formulation + ", ";
		if (!dose.isEmpty())
			desc += dose + ", ";
		desc += packageDescription;
		return desc;
	}
}
