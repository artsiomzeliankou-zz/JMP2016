package by.gsu.epamlab.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.NoSuchEntityException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.dao.ifaces.IUserDAO;
import by.gsu.epamlab.dao.ifaces.UserDaoAbstract;
import by.gsu.epamlab.model.beans.User;

public class UserImplMemory extends UserDaoAbstract implements IUserDAO {

	private static List<User> users = new ArrayList<User>();

	public UserImplMemory() {
		users.add(new User("john", "john"));
		users.add(new User("mike", "mike"));
		users.add(new User("bill", "bill"));
		users.add(new User("mary", "mary"));
		users.add(new User("user", "user"));
	}

	@Override
	public User retrieveUser(String login, String password)
			throws DaoSystemException, NoSuchEntityException {
		for (User user : users) {
			if (user.getLogin().equals(login)
					&& user.getPassword().equals(password)) {
				return user;
			}
		}
		throw new NoSuchEntityException(Messages.ERROR_NO_SUCH_USER);
	}

	protected void insertUser(User user) throws DaoSystemException,
			ValidationException {
		users.add(user);
	}

	protected void checkLogin(String login) throws DaoSystemException,
			ValidationException {
		boolean isLoginExist = false;
		for (User user : users) {
			if (user.getLogin().equals(login)) {
				isLoginExist = true;
			}
		}
		if (isLoginExist) {
			throw new ValidationException(Messages.ERROR_LOGIN);
		}

	}
}