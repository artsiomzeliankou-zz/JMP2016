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

/**
 * Application starting servlet
 */
public class MainController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(ShowActiveTaskController.class);

	@Override
	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug(Messages.START);

		HttpSession session = request.getSession(true);

		String viewName = (String) request.getParameter(Constants.KEY_ACTION);

		String link;
		
		if (viewName == null) {
			link = Constants.JSP_PREFIX.concat(Constants.JSP_START).concat(Constants.JSP_EXT);
			session.invalidate();
		} else {
			link = Constants.JSP_PREFIX.concat(viewName).concat(Constants.JSP_EXT);
		}

		jumpPage(link, Constants.KEY_EMPTY, request, response);

	}

}
