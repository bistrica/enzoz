package items;

import java.util.ArrayList;

public class Recepta {

	ArrayList<PozycjaNaRecepcie> pozycje;
	int id; //przy zapisie
	
	public Recepta(ArrayList<PozycjaNaRecepcie> pozycje) {
		this.pozycje=pozycje;
	}
}
