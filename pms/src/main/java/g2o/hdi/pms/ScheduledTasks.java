package g2o.hdi.pms;

import java.time.LocalDate;

import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;


@Component
public class ScheduledTasks {
	LocalDate ld=LocalDate.now();
	
	@Scheduled(fixedRate = 5000)
	public void createPatient() {
		// Create a patient
				Patient newPatient = buildPatient();

				// Create a client
				FhirContext ctx = FhirContext.forR4();
				IGenericClient client = ctx.newRestfulGenericClient("http://localhost:8080");

				// Create the resource on the server
				MethodOutcome outcome = client
					.create()
					.resource(newPatient)
					.execute();

				// Log the ID that the server assigned
				IIdType id = outcome.getId();
				
				System.out.println("created is: "+id.getValue());
	
	}

	private Patient buildPatient() {
		Patient newPatient = new Patient();
		ld=ld.plusDays(1);
		// Populate the patient with fake information
		newPatient
			.addName()
				.setFamily("DevDays"+ld.getDayOfMonth())
				.addGiven("John")
				.addGiven("Q"+ld.getDayOfWeek());
		newPatient
			.addIdentifier()
				.setSystem("http://acme.org/mrn")
				.setValue("1234567");
		newPatient.setGender(ld.getDayOfMonth()%2==0?Enumerations.AdministrativeGender.MALE:Enumerations.AdministrativeGender.FEMALE);
		
		newPatient.setBirthDateElement(new DateType(ld.toString()));
		return newPatient;
	}

}
