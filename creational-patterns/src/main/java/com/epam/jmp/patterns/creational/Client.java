package com.epam.jmp.patterns.creational;

import com.epam.jmp.patterns.creational.dao.IPersonDAO;
import com.epam.jmp.patterns.creational.factories.AbstractPesonDaoFactory;
import com.epam.jmp.patterns.creational.factories.DB_PersonDaoFactory;
import com.epam.jmp.patterns.creational.factories.FS_PersonDaoFactory;

public class Client {
	
	private IPersonDAO personDao = null;
	
	public Client(String sourceType) {
		SourceType source = getEnumFromString(sourceType);
		if(source != null){
			AbstractPesonDaoFactory pesonDaoFactory = null;
			switch (source){
				case FS: pesonDaoFactory = new FS_PersonDaoFactory();
				case DB: pesonDaoFactory = new DB_PersonDaoFactory();
			}
			personDao = buildPersonDao(pesonDaoFactory);
		}
	}
	
	private  SourceType getEnumFromString(String sourceType) {
		if (sourceType != null) {
			try {
				return SourceType.valueOf(sourceType.trim().toUpperCase());
			} catch (IllegalArgumentException ex) {
			}
		}
		return null;
	}

	
	private IPersonDAO buildPersonDao(AbstractPesonDaoFactory pesonDaoFactory){
		if(pesonDaoFactory == null){
			throw new IllegalArgumentException("Source type is not correct!");
		}
		IPersonDAO personDao = pesonDaoFactory.createPersonDao();
		return personDao;
	}
	
	public void writePerson (Person person){
		if (personDao != null){
			personDao.writePerson(person);
		}
	} 
	
	public Person readPerson() {
		Person person = null;
		if (personDao != null){
			person = personDao.readPerson();
		}
		return person;
	}

	public Person readPerson (String name){
		Person person = null;
		if (personDao != null){
			person = personDao.readPerson(name);
		}
		return person;
	}
}
