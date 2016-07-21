package com.epam.jmp.patterns.creational.factories;

import com.epam.jmp.patterns.creational.dao.IPersonDAO;
import com.epam.jmp.patterns.creational.dao.PersonImpDB;

public class DB_PersonDaoFactory implements AbstractPesonDaoFactory{

	public IPersonDAO createPersonDao(){
		return new PersonImpDB();
	}
}
