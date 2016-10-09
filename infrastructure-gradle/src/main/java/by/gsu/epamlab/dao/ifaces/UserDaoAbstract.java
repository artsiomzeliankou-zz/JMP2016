package by.gsu.epamlab.dao.ifaces;

import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.NoSuchEntityException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.User;

/**
 * Implementation of {@link IUserDAO]
 */
public abstract class UserDaoAbstract implements IUserDAO {

	@Override
	public User registrateUser(String login, String password)
			throws DaoSystemException, ValidationException {
		User user = new User(login, password);
		try {
			checkLogin(login);
		} catch (ValidationException e) {
			throw new ValidationException(Messages.ERROR_REGISTRATION);
		}
		insertUser(user);
		return user;

	}

	@Override
	public abstract User retrieveUser(String login, String password)
			throws DaoSystemException, NoSuchEntityException;

	/**
	 * Add new user to repository
	 * 
	 * @param user
	 *            the new user or throws ValidationException if such user exists
	 */
	protected abstract void insertUser(User user) throws DaoSystemException,
			ValidationException;

	/**
	 * Check user's login
	 * 
	 * @param login
	 *            the user login throws ValidationException if no such user
	 *            login in repository
	 */
	protected abstract void checkLogin(String login) throws DaoSystemException,
			ValidationException;

}
