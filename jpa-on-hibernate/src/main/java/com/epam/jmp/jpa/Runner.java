package com.epam.jmp.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.epam.jmp.jpa.entities.Employee;
import com.epam.jmp.jpa.entities.EmployeeStatus;

public class Runner {

	public static void main(String[] args) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JMP");
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction userTransaction = em.getTransaction();
	    
	    userTransaction.begin();
	    Employee employee = new Employee();
	    employee.setEmployeeStatus(EmployeeStatus.FULL_TIME_REGULAR);
	    
	    em.persist(employee);
	    userTransaction.commit();
	    em.close();
	    entityManagerFactory.close();
		
		
	}

}
