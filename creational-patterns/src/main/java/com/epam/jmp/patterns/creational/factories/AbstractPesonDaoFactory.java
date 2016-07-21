package com.epam.jmp.patterns.creational.factories;

import com.epam.jmp.patterns.creational.dao.IPersonDAO;

public interface AbstractPesonDaoFactory {

	public IPersonDAO createPersonDao();
}
