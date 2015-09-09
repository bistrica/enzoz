package rendering;

import items.Lek;
import items.PozycjaNaRecepcie;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class DrugTableModel extends DefaultTableModel { //AbstractTableModel {

	//ArrayList<PozycjaNaRecepcie> positions;
	
	public DrugTableModel() {
		//positions=new ArrayList<PozycjaNaRecepcie>();
	}
	
	/*@Override
	public int getRowCount() {
		
		return positions.size();
	}*/

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	

	/*@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return PozycjaNaRecepcie.class;
	}*/

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return true;
	}

	/*@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return positions.get(rowIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		positions.add(rowIndex,(PozycjaNaRecepcie)aValue);

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	public boolean contains(Lek newDrug) {
		for (PozycjaNaRecepcie pos: positions)
			if (pos.getLek().equals(newDrug))
				return true;
		return false;
	}

	public void add(PozycjaNaRecepcie pos) {
		positions.add(pos);
	}
*/
}
