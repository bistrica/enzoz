package tests;

import static org.junit.Assert.assertEquals;
import login.LoginDBH;

import org.junit.Test;

import people.Doctor;
import people.Employee;
import database.DBHandler;
import exceptions.BadDataException;
import exceptions.ConnectionException;

public class TryToLog {

	static LoginDBH logDBH = new LoginDBH();;

	@Test(expected = ConnectionException.class)
	public void testTryToLog() throws ConnectionException, BadDataException {

		DBHandler.terminate();

		Employee e = logDBH.tryToLog("adolega", "badPassword");

		System.out.println("E " + e);
	}

	@Test(expected = BadDataException.class)
	public void testTryToLogUserWithoutAppAccount() throws ConnectionException,
			BadDataException {

		DBHandler.terminate();

		logDBH.tryToLog("admin", "admin");

	}

	@Test
	public void testTryToLogProperData() throws ConnectionException,
			BadDataException {

		DBHandler.terminate();

		Doctor doc = new Doctor(301, "Aleksandra", "Do³êga", "12345678912");

		Employee user = logDBH.tryToLog("adolega", "adolega");
		assertEquals(doc, user);

	}

}
