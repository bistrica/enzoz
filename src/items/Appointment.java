package items;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import people.Doctor;
import people.Patient;

public class Appointment {

	private GregorianCalendar dateTime;
	private Patient patient;
	private Doctor doctor;
	private ArrayList<Illness> recognizedIllnesses;
	private Prescription prescription;
	private ArrayList<Examination> examinations;
	private Interview interview;

	private String status;

	private int id;
	private boolean isArchive = false;

	public Appointment(int id, GregorianCalendar data) {
		this.id = id;
		this.dateTime = data;
	}

	public Appointment(GregorianCalendar data, Patient patient) {
		this.dateTime = data;
		this.patient = patient;
	}

	public Appointment(GregorianCalendar data, Patient patient, Doctor doctor) {
		this.dateTime = data;
		this.patient = patient;
		this.doctor = doctor;
	}

	public Appointment(int id, GregorianCalendar data, Patient patient,
			Doctor doctor) {
		this.id = id;
		this.dateTime = data;
		this.patient = patient;
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public GregorianCalendar getDate() {
		return dateTime;
	}

	public String getHourToString() {

		String zero = dateTime.get(Calendar.MINUTE) >= 10 ? "" : "0";
		return dateTime.get(Calendar.HOUR_OF_DAY) + ":" + zero
				+ dateTime.get(Calendar.MINUTE);
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public ArrayList<Illness> getRecognizedIllnesses() {
		return recognizedIllnesses;
	}

	public int getId() {
		return this.id;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Wizyta [data=" + dateTime + ", patient=" + patient
				+ ", doctor=" + doctor + ", rozpoznaneChoroby="
				+ recognizedIllnesses + ", id=" + id + "]";
	}

	public String getDataToString() {
		return dateTime.get(Calendar.YEAR) + "-"
				+ (dateTime.get(Calendar.MONTH) + 1) + "-"
				+ dateTime.get(Calendar.DATE) + ", " + getHourToString();
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public ArrayList<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(ArrayList<Examination> exams) {
		this.examinations = exams;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

	public void setRecognizedIllnesses(ArrayList<Illness> recognizedIllneses) {
		this.recognizedIllnesses = recognizedIllneses;
	}

	public boolean isArchiveAppointment() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
