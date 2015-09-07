package individualApp;

import appointment.AppointmentDBH;
import appointment.AppointmentView;
import items.Wizyta;

public class IndividualAppController {

	Wizyta appointment;
	
	IndividualAppDBH iam;
	IndividualAppView iav;
	
	public IndividualAppController(Wizyta app) {
		this.appointment=app;
		iav=new IndividualAppView();
		iam=new IndividualAppDBH();
		iav.setIllnessesList(iam.getAllIllnesses());
		iav.setVisible(true);
	}
	
}
