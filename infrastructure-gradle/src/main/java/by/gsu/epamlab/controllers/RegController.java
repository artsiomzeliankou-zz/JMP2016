package by.gsu.epamlab.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.dao.factories.UserFactory;
import by.gsu.epamlab.dao.ifaces.IUserDAO;
import by.gsu.epamlab.ifaces.AbstractBaseController;
import by.gsu.epamlab.model.beans.User;

public class RegController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RegController.class);

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(Constants.KEY_LOGIN);

		String[] passwords = request.getParameterValues(Constants.KEY_PASSWORD);

		IUserDAO userDAO = UserFactory.getClassFromFactory();

		try {

			checkData(login, passwords);

			String password = request.getParameter(Constants.KEY_PASSWORD);

			User user = userDAO.registrateUser(login, password);

			HttpSession session = request.getSession(true);

			session.setAttribute(Constants.KEY_USER, user);

			session.setAttribute(Constants.MENU_ID, Constants.START_DAY);

			logger.debug(Messages.SUCCESS_REGISTRATE);

			jumpPage(Constants.JUMP_ACTIVE_TASK, Constants.KEY_EMPTY, request,
					response);

		} catch (ValidationException e) {

			logger.warn(Messages.ERROR_REGISTRATE, e);

			jumpPage(Constants.JUMP_REGISTRATE, e.getMessage(), request,
					response);
		
		} catch (DaoSystemException e) {

			logger.warn(Messages.ERROR_SOURCE, e);

			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);
		}
	}

}