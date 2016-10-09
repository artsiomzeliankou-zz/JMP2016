package by.gsu.epamlab.dao.ifaces;

import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.NoSuchEntityException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.User;

public interface IUserDAO {

	/**
     * Register new user with login and password
     * @param login the user login
     * @param password user password
     * @return the user with login and password or throws ValidationException if user with such login exists
     */
	public User registrateUser(String login, String password) throws DaoSystemException, ValidationException; 
	
	/**
     * Get from repository user by login and password
     * @param login the user login
     * @param password user password
     * @return the user with login and password or throws NoSuchEntityException if no such user
     */
	public User retrieveUser(String login, String password) throws DaoSystemException, NoSuchEntityException;
	

}
