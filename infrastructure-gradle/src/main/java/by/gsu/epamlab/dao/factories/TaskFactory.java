package by.gsu.epamlab.dao.factories;

import by.gsu.epamlab.dao.ifaces.ITaskDAO;
import by.gsu.epamlab.dao.impl.TaskImplDB;

public class TaskFactory {

	public static ITaskDAO getClassFromFactory() {
		return new TaskImplDB();
		// return new TaskImplMemory();
	}
}
