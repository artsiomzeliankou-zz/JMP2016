package by.gsu.epamlab.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.ifaces.AbstractBaseController;

public class LogoutController extends AbstractBaseController {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LogoutController.class);

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		logger.debug(Messages.SUCCESSFULL_LOGOUT);

		session.invalidate();

		response.sendRedirect(Constants.MAIN_PAGE);
	}

}
