package com.epam.jmp.patterns.creational.factories;

import com.epam.jmp.patterns.creational.dao.IPersonDAO;
import com.epam.jmp.patterns.creational.dao.PersonImplFS;

public class FS_PersonDaoFactory implements AbstractPesonDaoFactory{

	public IPersonDAO createPersonDao(){
		return new PersonImplFS();
	}
}
