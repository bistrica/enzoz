package items;

public class Examination {

	Clinic clinic;
	String description;

	public Examination(Clinic clinic, String description) {
		this.clinic = clinic;
		this.description = description;

	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
