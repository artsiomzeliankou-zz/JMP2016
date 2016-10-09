package by.gsu.epamlab.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoBusinessException;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.factories.UserFactory;
import by.gsu.epamlab.dao.ifaces.IUserDAO;
import by.gsu.epamlab.ifaces.AbstractBaseController;

public class LoginController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginController.class);

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(Constants.KEY_LOGIN);

		String password = request.getParameter(Constants.KEY_PASSWORD);

		IUserDAO userDAO = UserFactory.getClassFromFactory();

		try {

			checkData(login, password);

			User user = userDAO.retrieveUser(login, password);

			HttpSession session = request.getSession(true);

			session.setAttribute(Constants.KEY_USER, user);

			session.setAttribute(Constants.MENU_ID, Constants.START_DAY);

			logger.debug(Messages.SUCCESSFULL_LOGIN);

			jumpPage(Constants.JUMP_ACTIVE_TASK, Constants.KEY_EMPTY, request,
					response);

		} catch (DaoBusinessException e) {

			logger.warn(Messages.ERROR_LOGIN, e);

			jumpPage(Constants.JUMP_LOGIN, Messages.ERROR_LOGIN, request,
					response);

		} catch (DaoSystemException e) {

			logger.warn(Messages.ERROR_SOURCE, e);

			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);
		}
	}

}
