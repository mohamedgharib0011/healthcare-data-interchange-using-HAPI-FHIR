package g2o.hdi.hub.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

public class PatientProvider implements IResourceProvider {

   private Map<String, Patient> myPatients = new HashMap<String, Patient>();
   private long myNextId = 1;

   /**
    * Constructor
    */
   public PatientProvider() {
   }

   @Override
   public Class<? extends IBaseResource> getResourceType() {
      return Patient.class;
   }

   /**
    * Simple implementation of the "read" method
    */
   @Read()
   public Patient read(@IdParam IdType theId) {
      Patient retVal = myPatients.get(theId.getIdPart());
      if (retVal == null) {
         throw new ResourceNotFoundException(theId);
      }
      return retVal;
   }

   /**
    * The "@Create" annotation indicates that this method implements "create=type", which adds a
    * new instance of a resource to the server.
    */
   @Create()
   public MethodOutcome createPatient(@ResourceParam Patient thePatient) {

      // Here we are just generating IDs sequentially
      long id = myNextId++;
      IdType newId = new IdType("Patient", Long.toString(id));
      thePatient.setId(newId);
      
      myPatients.put(id+"", thePatient);

      return new MethodOutcome(new IdType(id));
   }
   
   @Search
   public List<Patient> findPatientsUsingArbitraryCtriteria() {
      return myPatients.values().stream().collect(Collectors.toList());
   }

   

}
