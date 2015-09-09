package GUI_items;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import items.Poradnia;
import items.PozycjaNaRecepcie;
import items.Skierowanie;

public class ExamPanel extends JPanel {

	private Skierowanie position;
	
	private JTextArea description;
	private JComboBox<Poradnia> clinics;
	private String removeString="Usuñ skierowanie";

	private String descLabelString;

	private String clinicLabelString;
	
	public ExamPanel(Poradnia[] clinics) {
		//this.position=pos;
	
		
		this.clinics=new JComboBox<Poradnia>(clinics);
		
		
		 JButton removeButton=new JButton(removeString);
		   JPanel thisPanel=this;
		   removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Container cont=thisPanel.getParent();
				cont.remove(thisPanel);
				cont.revalidate();
			}
		});
		  
		   description=new JTextArea(5, 50);
		   description.setLineWrap(true);
		   JScrollPane examination=new JScrollPane(description);
		   
		   JPanel buttonPanel=new JPanel();
		   buttonPanel.add(removeButton);
		
		   JLabel descLabel=new JLabel(descLabelString);
		   JLabel clinicLabel=new JLabel(clinicLabelString);
		  
		   JPanel examPane=new JPanel();
		   examPane.add(descLabel);
		   examPane.add(examination);
		  
		   JPanel clinicPane=new JPanel();
		   clinicPane.add(clinicLabel);
		   clinicPane.add(this.clinics);
		  
		   setBorder(new EtchedBorder());
		   setLayout(new BorderLayout());
		   add(clinicPane, BorderLayout.NORTH);
		   add(examPane, BorderLayout.CENTER);
		   add(buttonPanel, BorderLayout.SOUTH);
		   setMinimumSize(getPreferredSize());
		   setMaximumSize(new Dimension(getPreferredSize().width, 200));
	}
	
	
	
	
	public Skierowanie getPosition() {
		return position;
	}

	public void setPosition(Skierowanie position) {
		this.position = position;
	}
	
}
