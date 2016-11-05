package com.epam.jmp.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.epam.jmp.jpa.entities.Address;
import com.epam.jmp.jpa.entities.Employee;
import com.epam.jmp.jpa.entities.EmployeeStatus;
import com.epam.jmp.jpa.entities.Personal;
import com.epam.jmp.jpa.entities.Project;
import com.epam.jmp.jpa.entities.Unit;

public class Runner {

	private static long test_employeeId;
	private static long test_personalInfoId;
	private static long test_project1Id;
	private static long test_project2Id;
	private static long test_project3Id;
	private static long test_unit1Id;
	private static long test_unit2Id;
	
	public static void main(String[] args) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JMP");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		createEntities(em);
		findEntities(em);
		updateEntities(em);
		addEmployeeToUnit(em);
		assignEmployeeToProject(em);
		deleteEntities(em);

		
	    em.close();
	    entityManagerFactory.close();

	}

	// Create Employee / Unit / Project
	private static void createEntities(EntityManager em) {
		//create Address POJO
		Address address = new Address();
		address.setStreet("Lenina");
		address.setHouse(10);
		address.setFlat(206);
		
		//create Personal POJO
		Personal personalInfo = new  Personal();
		personalInfo.setFirstName("Ivan");
		personalInfo.setLastName("Petrov");
		personalInfo.setEmail("ivan@epam.com");
		
		//create several Project POJO
		Project project1 = new Project();
		project1.setProjectCode("CLRA-NOC");
		project1.setMembersNumber(30);
		project1.setEffectiveDate("01.01.2012");
		
		Project project2 = new Project();
		project2.setProjectCode("SAP-SDNS");
		project2.setMembersNumber(40);
		project2.setEffectiveDate("10.11.2010");
		
		Project project3 = new Project();
		project3.setProjectCode("KRON-APD");
		project3.setMembersNumber(25);
		project3.setEffectiveDate("02.02.2016");
		
		//create several Unit POJO
		Unit unit1 = new Unit();
		unit1.setName("java");
		
		Unit unit2 = new Unit();
		unit2.setName("javascript");
		
		//create Employee POJO
		Employee employee = new Employee();
		employee.setEmployeeStatus(EmployeeStatus.FULL_TIME_REGULAR);
		employee.setAddress(address);
		employee.setPersonalInfo(personalInfo);

		Set<Project> employeeProjects = new HashSet<>();
		employeeProjects.add(project2);
		employeeProjects.add(project3);
		employee.setProjects(employeeProjects);
		employee.setUnit(unit1);
		
		//do act with DB
		em.getTransaction().begin();
		
		em.persist(personalInfo);
		em.persist(project1);
		em.persist(project2);
		em.persist(project3);
		em.persist(unit1);
		em.persist(unit2);
		em.persist(employee);
		
		em.getTransaction().commit();
		
		test_employeeId = employee.getId();
		test_personalInfoId = personalInfo.getId();
		test_project1Id = project1.getId();
		test_project2Id = project2.getId();
		test_project3Id = project3.getId();
		test_unit1Id = unit1.getId();
		test_unit2Id = unit2.getId();
		
	}
	
	// Find employee / Unit / Project by id
	private static void findEntities(EntityManager em) {
		
		Employee employee = em.find(Employee.class, test_employeeId);
		if(employee != null){
			System.out.println(employee);
		}else{
			System.out.println("\nEmlpoyee with ID: " + test_employeeId + " not found");
		}
		
		Project project = em.find(Project.class, test_project2Id);
		if(project != null){
			System.out.println(project);
		}else{
			System.out.println("Project with ID: " + test_project2Id + " not found");
		}
		
		Unit unit = em.find(Unit.class, test_unit1Id);
		if(unit != null){
			System.out.println("Unit name: " + unit.getName());
		}else{
			System.out.println("Unit with ID: " + test_unit2Id + " not found");
		}
		
	}
	
	// Update Employee / Unit / Project by id
	private static void updateEntities(EntityManager em) {
		
		Employee employee = em.find(Employee.class, test_employeeId);
		if(employee != null){
			employee.setEmployeeStatus(EmployeeStatus.PART_TIME_REGULAR);
			em.getTransaction().begin();
			em.merge(employee);
			em.getTransaction().commit();
		}
		
		Project project = em.find(Project.class, test_project2Id);
		if(project != null){
			project.setEffectiveDate("12.12.12");
			em.getTransaction().begin();
			em.merge(project);
			em.getTransaction().commit();
		}
		
		Unit unit = em.find(Unit.class, test_unit1Id);
		if(unit != null){
			unit.setName("js_unit");
			em.getTransaction().begin();
			em.merge(unit);
			em.getTransaction().commit();
		}
		
		//check for updates
		findEntities(em);
	}
	
	// Assign Employee for Project by id's
	private static void assignEmployeeToProject(EntityManager em) {
		
		Employee employee = em.find(Employee.class, test_employeeId);
		if(employee != null){
			Project project = em.find(Project.class, test_project3Id);
			if(project != null){
				System.out.println("\nProject Before assignment: " + project);
				project.getEmployees().add(employee);
				em.getTransaction().begin();
				em.merge(project);
				em.getTransaction().commit();

				//check for updates
				Project updatedProject = em.find(Project.class, test_project3Id);
				System.out.println("Project After assignment: " + updatedProject);
			}
		}
	}
	
		// Add Employee to Unit by id's
		private static void addEmployeeToUnit(EntityManager em) {

		Employee employee = em.find(Employee.class, test_employeeId);
		if(employee != null){
			Unit unit = em.find(Unit.class, test_unit2Id);
			if(unit != null){
				System.out.println("\nUnit Before Employee added: " + unit);
				unit.getEmployees().add(employee);
				em.getTransaction().begin();
				em.merge(unit);
				em.getTransaction().commit();

				//check for updates
				Unit updatedUnit = em.find(Unit.class, test_unit2Id);
				System.out.println("Unit After Employee added: " + updatedUnit);
			}
		}
	}

	// Delete employee / Unit / Project by id
	private static void deleteEntities(EntityManager em) {

		Employee employee = em.find(Employee.class, test_employeeId);
		if(employee != null){
			em.getTransaction().begin();
			em.remove(employee);
			em.getTransaction().commit();
		}
		
		Project project = em.find(Project.class, test_project1Id);
		if(project != null){
			em.getTransaction().begin();
			em.remove(project);
			em.getTransaction().commit();
		}
		
		Unit unit = em.find(Unit.class, test_unit2Id);
		if(unit != null){
			em.getTransaction().begin();
			em.remove(unit);
			em.getTransaction().commit();
		}

	}

}
