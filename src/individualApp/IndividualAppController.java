package individualApp;

import appointment.AppointmentDBH;
import appointment.AppointmentView;
import items.Wizyta;

public class IndividualAppController {

	Wizyta appointment;
	
	IndividualAppDBH iam;
	IndividualAppView iav;
	
	public IndividualAppController(Wizyta app, boolean isEditable) {
		this.appointment=app;
		
		iam=new IndividualAppDBH();
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	iav=new IndividualAppView(isEditable);
				
				iav.setIllnessesList(iam.getAllIllnesses());
				iav.setAllMedicinesList(iam.getAllMedicines());
				iav.setClinicsList(iam.getAllClinics());
				iav.setInfo(appointment.getPacjent().getMainInfo(), appointment.getPacjent().getAddressInfo());
				
				iav.setVisible(true);
		    }
		} );
		
	}
	
}
