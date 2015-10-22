package items;

import java.util.ArrayList;

public class Prescription {

	private ArrayList<PrescriptedItem> pozycje;
	private int id; // przy zapisie

	public Prescription(ArrayList<PrescriptedItem> pozycje) {
		this.pozycje = pozycje;
	}

	public ArrayList<PrescriptedItem> getPozycje() {
		return pozycje;
	}

	public void setPozycje(ArrayList<PrescriptedItem> pozycje) {
		this.pozycje = pozycje;
	}
}
