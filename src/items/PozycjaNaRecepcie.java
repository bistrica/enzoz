package items;

public class PozycjaNaRecepcie {

	Lek lek;
	int iloscOpakowan;
	double iloscDawekNaPrzyjecie;
	int iloscPrzyjec;
	double procentRefundacji;

	public PozycjaNaRecepcie(Lek lek) {
		this(lek, 1, 1.0, 1, 1.0);
	}

	public PozycjaNaRecepcie(Lek lek, int iloscOpakowan,
			double iloscDawekNaPrzyjecie, int iloscPrzyjec,
			double procentRefundacji) {

		this.lek = lek;
		this.iloscOpakowan = iloscOpakowan;
		this.iloscDawekNaPrzyjecie = iloscDawekNaPrzyjecie;
		this.iloscPrzyjec = iloscPrzyjec;
		this.procentRefundacji = procentRefundacji;
	}

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public int getIloscOpakowan() {
		return iloscOpakowan;
	}

	public void setIloscOpakowan(int iloscOpakowan) {
		this.iloscOpakowan = iloscOpakowan;
	}

	public double getIloscDawekNaPrzyjecie() {
		return iloscDawekNaPrzyjecie;
	}

	public void setIloscDawekNaPrzyjecie(double iloscDawekNaPrzyjecie) {
		this.iloscDawekNaPrzyjecie = iloscDawekNaPrzyjecie;
	}

	public int getIloscPrzyjec() {
		return iloscPrzyjec;
	}

	public void setIloscPrzyjec(int iloscPrzyjec) {
		this.iloscPrzyjec = iloscPrzyjec;
	}

	public double getProcentRefundacji() {
		return procentRefundacji;
	}

	public void setProcentRefundacji(double procentRefundacji) {
		this.procentRefundacji = procentRefundacji;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PozycjaNaRecepcie other = (PozycjaNaRecepcie) obj;
		if (Double.doubleToLongBits(iloscDawekNaPrzyjecie) != Double
				.doubleToLongBits(other.iloscDawekNaPrzyjecie))
			return false;
		if (iloscOpakowan != other.iloscOpakowan)
			return false;
		if (iloscPrzyjec != other.iloscPrzyjec)
			return false;
		if (lek == null) {
			if (other.lek != null)
				return false;
		} else if (!lek.equals(other.lek))
			return false;
		if (Double.doubleToLongBits(procentRefundacji) != Double
				.doubleToLongBits(other.procentRefundacji))
			return false;
		return true;
	}

	/*
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (obj == null) return false; if (getClass() != obj.getClass())
	 * return false; PozycjaNaRecepcie other = (PozycjaNaRecepcie) obj; if (lek
	 * == null) { if (other.lek != null) return false; } else if
	 * (!lek.equals(other.lek)) return false; return true; }
	 */

}
