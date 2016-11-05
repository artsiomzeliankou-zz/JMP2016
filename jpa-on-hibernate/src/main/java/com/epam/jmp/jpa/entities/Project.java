package com.epam.jmp.jpa.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "PROJECT_CODE")
    private String projectCode;
	
	@Column(name = "EFFECTIVE_DATE")
    private String effectiveDate;
	
	@Column(name = "MEMBERS_NUMBER")
	private int membersNumber;
	
	@ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();

	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(""); 
		for(Employee employee : employees){
			sb.append(employee.getPersonalInfo().getFirstName())
				.append(" ").append(employee.getPersonalInfo().getLastName())
				.append(";");
		}
		return "Project: [code: " + projectCode
					+ "], [effectiveDate: " + effectiveDate
					+ "], [membersNumber: " + membersNumber
					+ "], [members: " + sb.toString()
					+ "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public int getMembersNumber() {
		return membersNumber;
	}

	public void setMembersNumber(int membersNumber) {
		this.membersNumber = membersNumber;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}
