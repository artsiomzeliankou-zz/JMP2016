package by.gsu.epamlab.dao.factories;

import by.gsu.epamlab.dao.ifaces.IUserDAO;
import by.gsu.epamlab.dao.impl.UserImplDB;

public class UserFactory {
	public static IUserDAO getClassFromFactory() {
		return new UserImplDB();
//		return new UserImplMemory();
	}

}
