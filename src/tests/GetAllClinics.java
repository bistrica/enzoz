package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import daos.ClinicDAO;
import items.Clinic;

import java.sql.SQLException;
import java.util.*;

public class GetAllClinics {

	ArrayList<Clinic> clinics;

	public void init() {

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

	}

	@Test
	public void test() throws SQLException {
		
		ClinicDAO cli=new ClinicDAO();
		assertEquals(cli.getAllClinics(), clinics));
		
		
		
	}
}
