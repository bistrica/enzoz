package tests;

import static org.junit.Assert.assertEquals;
import items.Appointment;
import items.Clinic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import people.Doctor;
import GUI_items.SearchHelper;
import appointment.AppointmentDBH;
import daos.ClinicDAO;
import database.DBHandler;
import exceptions.ArchiveException;
import exceptions.TodayException;

public class GetAllClinics {

	static ArrayList<Clinic> clinics;
	static ClinicDAO cli;
	static AppointmentDBH appDBH;

	static {

		String[] names = {

		"laboratorium", "poradnia alergologiczna", "poradnia chirurgii",
				"poradnia chirurgii onkologicznej",
				"poradnia chirurgii ogólnej",
				"poradnia chirurgii urazowo-ortopedycznej",
				"poradnia chorób p³uc i gruŸlicy", "poradnia dermatologiczna",
				"poradnia diabetologiczna", "poradnia endokrynologiczna",
				"poradnia gastroenterologiczna", "poradnia geriatryczna",
				"poradnia ginekologiczno-po³o¿nicza",
				"poradnia hematologiczna", "poradnia kardiochirurgiczna",
				"poradnia kardiologiczna", "poradnia neurochirurgiczna",
				"poradnia neurologiczna", "poradnia okulistyczna",
				"poradnia onkologiczna", "poradnia otolaryngologiczna",
				"poradnia rehabilitacyjna", "poradnia reumatologiczna",
				"szpital" };

		clinics = new ArrayList<Clinic>();
		int id = 87;
		for (String name : names) {
			Clinic clinic = new Clinic(id++, name);
			clinics.add(clinic);
		}

		cli = new ClinicDAO();
		appDBH = new AppointmentDBH();

		try {
			DBHandler.createAndGetDatabaseConnection("adolega", "adolega");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetAllClinics() throws SQLException {
		assertEquals(clinics, cli.getAllClinics());
	}

	@Test
	public void testGetClinic() throws SQLException {

		assertEquals(clinics.get(0), cli.getClinic(87));
		assertEquals(clinics.get(1), cli.getClinic(88));
		assertEquals(clinics.get(clinics.size() - 1), cli.getClinic(110));
		assertEquals(null, cli.getClinic(-1));
		assertEquals(null, cli.getClinic(115));

	}

	@Test
	public void testGetTodayAppointments() throws TodayException {

		Doctor doc = new Doctor(301, "Aleksandra", "Do³êga", "12345678912");
		ArrayList<Appointment> apps = appDBH.getTodayAppointments(doc);
		assertEquals(new ArrayList<Appointment>(), apps);

		doc = new Doctor(302, "Aleksandra", "Miœ", "12345678912");
		apps = appDBH.getTodayAppointments(doc);
		assertEquals(3, apps.size());

		HashSet<Integer> indexes = new HashSet<Integer>(), appIndexes = new HashSet<Integer>();
		indexes.add(82369);
		indexes.add(82370);
		indexes.add(82371);

		for (Appointment a : apps)
			appIndexes.add(a.getId());

		assertEquals(indexes, appIndexes);
	}

	@Test
	public void testSearchData() throws ArchiveException {
		SearchHelper sh = new SearchHelper();
		sh.setYear(2013);
		sh.setDay(-1);
		sh.setMonth(-1);
		sh.setPESEL(-1);
		sh.setSurname(null);

		ArrayList<Appointment> apps2013 = appDBH.searchData(sh);
		System.out.println(apps2013.size());

		assertEquals(768, apps2013.size());

		sh.setSurname(new Doctor(301, "Aleksandra", "Do³êga", "12345678912"));
		apps2013 = appDBH.searchData(sh);
		assertEquals(256, apps2013.size());

		long pesel = Long.parseLong("44021712811");
		sh.setPESEL(pesel);
		apps2013 = appDBH.searchData(sh);
		assertEquals(3, apps2013.size());

	}

}
