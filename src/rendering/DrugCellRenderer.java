package rendering;

import items.Medicine;
import items.PrescriptedItem;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class DrugCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	
	DrugCellPanel rowPanel;
	DrugCellPanel rowPanel2;
	//private DrugCellPanel edit;
	
	
	
	
	
	
	public DrugCellRenderer() {
		rowPanel=new DrugCellPanel();
		rowPanel2=new DrugCellPanel();
	}
	
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

				PrescriptedItem pos=null;
				/*if (value instanceof PozycjaNaRecepcie)
					 pos=(PozycjaNaRecepcie) value;
				
				else if (value instanceof DrugCellPanel)
					 pos=((DrugCellPanel) value).getValue();
				*/
				
				pos=(PrescriptedItem)table.getModel().getValueAt(row, column);
					rowPanel.update(pos);
				   //edit=rowPanel2;
				   return rowPanel;
			}

			@Override
			public Object getCellEditorValue() {
				return rowPanel2;//edit.getValue();
			}

			@Override
			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row, int column) {

				System.out.println("Val : "+value);
				//if (!(value instanceof PozycjaNaRecepcie) || value==null) return rowPanel;
				
				PrescriptedItem pos=null;
				/*if (value instanceof PozycjaNaRecepcie)
					 pos=(PozycjaNaRecepcie) value;
				
				else if (value instanceof DrugCellPanel)
					 pos=((DrugCellPanel) value).getValue();
				*/
				pos=(PrescriptedItem)table.getModel().getValueAt(row, column);
				System.out.println("posval "+pos);
				//PozycjaNaRecepcie pos=(PozycjaNaRecepcie) value;
				rowPanel2.update(pos);
//			   edit=rowPanel;
			   return rowPanel2;
			}
		
	}

	

