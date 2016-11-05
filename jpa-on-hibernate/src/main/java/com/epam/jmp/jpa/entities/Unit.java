package com.epam.jmp.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "UNIT")
public class Unit {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column(name = "NAME")
    private String name;
    
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "unit", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(""); 
		for(Employee employee : employees){
			sb.append(employee.getPersonalInfo().getFirstName())
				.append(" ").append(employee.getPersonalInfo().getLastName())
				.append(";");
		}
		return "Unit: [name: " + name
					+ "], [employees: " + sb.toString()
					+ "]";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
