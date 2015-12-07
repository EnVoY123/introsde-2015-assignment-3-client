package introsde.assignment.client;

import introsde.assignment.soap.Measure;
import introsde.assignment.soap.MeasureType;
import introsde.assignment.soap.Person;
import introsde.assignment.soap.People;
import introsde.assignment.soap.PeopleService;
import introsde.assignment.soap.CurrentHealth;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.util.logging.*;
import java.io.PrintWriter;



public class PeopleClient {
	static PrintWriter logger = null;
	public static void main(String[] args) throws Exception {
		
		logger = new PrintWriter("logfile.log");

		// for person
		JAXBContext jaxbContextPerson = JAXBContext.newInstance(Person.class);
		Marshaller jaxbMarshallerPerson = jaxbContextPerson.createMarshaller();
		jaxbMarshallerPerson.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		// For measure
		JAXBContext jaxbContextMeasure = JAXBContext.newInstance(Measure.class);
		Marshaller jaxbMarshallerMeasure = jaxbContextMeasure
				.createMarshaller();
		jaxbMarshallerMeasure.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);

		PeopleService service = new PeopleService();
        People people = service.getPeopleImplPort();


		System.out.println("================Request 1===================");
		logger.println("================Request 1===================");
		List<Person> pList = people.readPersonList();
		System.out.println("Result ==> " + pList);
		logger.println("Result ==> " + pList);
		personToXML(pList);

		System.out.println("================Request 2===================");
		logger.println("================Request 2===================");
		Person p = people.readPerson((long) 2);
		printInfo(p);
		//jaxbMarshallerPerson.marshal(p, System.out);
		//jaxbMarshallerPerson.marshal(p, logger);

		System.out.println("================Request 3===================");
		logger.println("================Request 3===================");
		Person p1 = people.readPerson((long) 1);
		p1.setLastname("Mister");
		p1.setFirstname("Johnson");
		p1.setBirthdate("1941-08-06");

		Person updatePerson = people.updatePerson(p1);
		printInfo(updatePerson);
		//jaxbMarshallerPerson.marshal(updatePerson, System.out);
		//jaxbMarshallerPerson.marshal(updatePerson, logger);

		System.out.println("================Request 4===================");
		logger.println("================Request 4===================");
		Person p2 = new Person();
		p2.setBirthdate("1941-08-06");
		p2.setFirstname("Don");
		p2.setLastname("Carleone");

		Person pNew = people.createPerson(p2);
		printInfo(pNew);
		//jaxbMarshallerPerson.marshal(pNew, System.out);
		//jaxbMarshallerPerson.marshal(pNew, logger);

		System.out.println("================Request 5===================");
		logger.println("================Request 5===================");
		int del = people.deletePerson(pNew.getPersonId());
		System.out.println("Result ==> Successful deleted person with ID " + del);
		logger.println("Result ==> Successful deleted person with ID " + del);

		System.out.println("================Request 6===================");
		logger.println("================Request 6===================");
		List<Measure> mList = people.readPersonHistory(1, "weight");
		System.out.println("Result ==> " + mList.toString());
		logger.println("Result ==> " + mList.toString());
		measureToXML(mList);

		System.out.println("================Request 7===================");
		logger.println("================Request 7===================");
		List<MeasureType> ms = people.readMeasureTypes();
			for (int i=0; i<ms.size(); i++) {
				System.out.println("Measures:");
				logger.println("Measures:");
				System.out.println(ms.get(i).getMeasureName());
				logger.println(ms.get(i).getMeasureName());
			}

		logger.println("Result ==> " + ms);
		//jaxbMarshallerMeasure.marshal(measure, System.out);
		//jaxbMarshallerMeasure.marshal(measure, logger);

		System.out.println("================Request 8===================");
		logger.println("================Request 8===================");
		Measure bla = people.readPersonMeasurement(2, "height", 454);
		printMes(bla);


		System.out.println("================Request 9===================");
		logger.println("================Request 9===================");

		Measure measureToAdd = new Measure();
		measureToAdd.setMeasureType("weight");
		measureToAdd.setMeasureValue("78");
		measureToAdd.setDateRegistered("2011-08-06");
		Measure m = people.savePersonMeasurement(1, measureToAdd);
		printMes(m);

		//jaxbMarshaller.marshal(list, System.out);
		//jaxbMarshaller.marshal(list, logger);

		System.out.println("===============Request 10===================");
		logger.println("================Request 10===================");
		m.setMeasureType("weight");
		m.setMeasureValue("128");
		m.setDateRegistered("2015-08-06");
		Measure mUpdated = people.updatePersonMeasure(1, m);
		printMes(mUpdated);
		//jaxbMarshallerMeasure.marshal(mUpdated, System.out);
		//jaxbMarshallerMeasure.marshal(mUpdated, logger);

		logger.close();

	}

	public static void personToXML(List<Person> people) {
		try {

			PersonList list = new PersonList();
			list.set(people);

			JAXBContext jaxbContext = JAXBContext.newInstance(PersonList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

			jaxbMarshaller.marshal(list, System.out);
			jaxbMarshaller.marshal(list, logger);

		} catch (JAXBException e) {
			// some exception occured
			System.out.println(e);
		}
	}

	public static void printInfo(Person pu){

		System.out.println("Result ==> " + pu.getFirstname());
		System.out.println("Result ==> " + pu.getLastname());
		System.out.println("Result ==> " + pu.getBirthdate());
		logger.println("Result ==> " + pu.getFirstname());
		logger.println("Result ==> " + pu.getLastname());
		logger.println("Result ==> " + pu.getBirthdate());

		if (pu.getCurrentHealth() != null) {
			CurrentHealth hp = pu.getCurrentHealth();
			List<Measure> measures = hp.getMeasure();
			for (int i=0; i<measures.size(); i++) {
				System.out.println("Measure:");
				System.out.println(measures.get(i).getMeasureType());
				System.out.println(measures.get(i).getMeasureValue());
				logger.println("Measure:");
				logger.println(measures.get(i).getMeasureType());
				logger.println(measures.get(i).getMeasureValue());

			}
		}


	}

		public static void printMes(Measure mu){

		System.out.println("Result ==> " + mu.getMeasureValue());
		System.out.println("Result ==> " + mu.getMeasureType());
		System.out.println("Result ==> " + mu.getMid());
		System.out.println("Result ==> " + mu.getDateRegistered());

		logger.println("Result ==> " + mu.getMeasureValue());
		logger.println("Result ==> " + mu.getMeasureType());
		logger.println("Result ==> " + mu.getMid());
		logger.println("Result ==> " + mu.getDateRegistered());
	


	}

	public static void measureToXML(List<Measure> people) {
		try {

			MeasureList list = new MeasureList();
			list.set(people);

			JAXBContext jaxbContext = JAXBContext
					.newInstance(MeasureList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

			jaxbMarshaller.marshal(list, System.out);
			jaxbMarshaller.marshal(list, logger);


		} catch (JAXBException e) {
			// some exception occured
			System.out.println(e);
		}
	}

	@XmlRootElement(name = "people")
	static class PersonList {

		@XmlElement(name = "person")
		private List<Person> list;

		public PersonList() {
			list = new ArrayList<Person>();
		}

		public void add(Person p) {
			list.add(p);
		}

		public void set(List<Person> list) {
			this.list = list;
		}
	}

	@XmlRootElement(name = "healthProfile-history")
	static class MeasureList {

		@XmlElement(name = "measure")
		private List<Measure> list;

		public MeasureList() {
			list = new ArrayList<Measure>();
		}

		public void add(Measure p) {
			list.add(p);
		}

		public void set(List<Measure> list) {
			this.list = list;
		}
	}
	
	@XmlRootElement(name="measureTypes")
	static class MeasureTypeXML implements Serializable {
		private static final long serialVersionUID = 1L;

		@XmlElement(name = "measureType")
		List<String> measureType;
		
		public void add(String x){
			this.measureType.add(x);
		}
		
		public MeasureTypeXML(){
			measureType = new ArrayList<String>();
		}
		
		public List<String> get(){
			return measureType;
		}
		
		public void set(List<String> measureTypes){
			this.measureType = measureTypes;
		}
	}

}