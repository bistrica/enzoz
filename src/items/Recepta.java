package items;

import java.util.ArrayList;

public class Recepta {

	ArrayList<PozycjaNaRecepcie> pozycje;
	int id; //przy zapisie
	
	public Recepta(ArrayList<PozycjaNaRecepcie> pozycje) {
		this.pozycje=pozycje;
	}

	public ArrayList<PozycjaNaRecepcie> getPozycje() {
		return pozycje;
	}

	public void setPozycje(ArrayList<PozycjaNaRecepcie> pozycje) {
		this.pozycje = pozycje;
	}
}
