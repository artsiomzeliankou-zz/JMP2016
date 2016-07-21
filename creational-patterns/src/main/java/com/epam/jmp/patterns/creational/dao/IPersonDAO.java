package com.epam.jmp.patterns.creational.dao;

import com.epam.jmp.patterns.creational.Person;

public interface IPersonDAO {

	public void writePerson(Person person);
    public Person readPerson();
    public Person readPerson(String name);

}
