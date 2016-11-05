package com.epam.jmp.jpa.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "EMPLOYEE_STATUS")
	private EmployeeStatus employeeStatus;
	
	@Embedded
	private Address address;
	
	@OneToOne
	@JoinColumn(name = "PERSONAL_INFO")
	private Personal personalInfo;
	
	@ManyToMany
	@JoinTable(
			name = "EMPLOYEE_TO_PROJECTS", 
			joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"), 
			inverseJoinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"))
	private Set<Project> projects = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "UNIT")
	private Unit unit;

	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder(""); 
		for(Project project : projects){
			sb.append(project.getProjectCode()).append(";");
		}
		
		return "\nEmlpoyee: [name: " + personalInfo.getFirstName() + " " + personalInfo.getLastName()
				+ "], [email: " + personalInfo.getEmail()
				+ "], [address: " + address.getStreet() + " "  + address.getHouse() + " "  + address.getFlat()
				+ "], [status: " + employeeStatus
				+ "], [projects: " + sb.toString()
				+ "]";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public EmployeeStatus getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(EmployeeStatus employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public Personal getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(Personal personalInfo) {
		this.personalInfo = personalInfo;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
