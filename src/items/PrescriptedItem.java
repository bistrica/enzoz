package items;

public class PrescriptedItem {

	Medicine drug;
	int packageCount;
	double dosesCountPerIngestion;
	int ingestionCount;
	double discountPercent;

	public PrescriptedItem(Medicine drug) {
		this(drug, 1, 1.0, 1, 1.0);
	}

	public PrescriptedItem(Medicine drug, int pckgNo, double dosesNo,
			int ingestionNo, double discountPrcnt) {

		this.drug = drug;
		this.packageCount = pckgNo;
		this.dosesCountPerIngestion = dosesNo;
		this.ingestionCount = ingestionNo;
		this.discountPercent = discountPrcnt;
	}

	public Medicine getMedicine() {
		return drug;
	}

	public void setMedicine(Medicine drug) {
		this.drug = drug;
	}

	public int getPackageCount() {
		return packageCount;
	}

	public void setPackageCount(int pckgNo) {
		this.packageCount = pckgNo;
	}

	public double getDosesCountPerIngestion() {
		return dosesCountPerIngestion;
	}

	public void setDosesCountPerIngestion(double dosesNo) {
		this.dosesCountPerIngestion = dosesNo;
	}

	public int getIngestionCount() {
		return ingestionCount;
	}

	public void setIngestionCount(int ingestionNo) {
		this.ingestionCount = ingestionNo;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPrcnt) {
		this.discountPercent = discountPrcnt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrescriptedItem other = (PrescriptedItem) obj;
		if (Double.doubleToLongBits(dosesCountPerIngestion) != Double
				.doubleToLongBits(other.dosesCountPerIngestion))
			return false;
		if (packageCount != other.packageCount)
			return false;
		if (ingestionCount != other.ingestionCount)
			return false;
		if (drug == null) {
			if (other.drug != null)
				return false;
		} else if (!drug.equals(other.drug))
			return false;
		if (Double.doubleToLongBits(discountPercent) != Double
				.doubleToLongBits(other.discountPercent))
			return false;
		return true;
	}

	/*
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (obj == null) return false; if (getClass() != obj.getClass())
	 * return false; PozycjaNaRecepcie other = (PozycjaNaRecepcie) obj; if (drug
	 * == null) { if (other.drug != null) return false; } else if
	 * (!drug.equals(other.drug)) return false; return true; }
	 */

}
