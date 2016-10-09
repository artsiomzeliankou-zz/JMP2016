package by.gsu.epamlab.ifaces;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.ValidationException;

/**
 * Implementation of {@link HttpServlet]
 */
public abstract class AbstractBaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		String path = context.getInitParameter(Constants.PATH_NAME);
		context.setAttribute(Constants.PATH_NAME, path);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	abstract protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected void jumpPage(String url, String message,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(Messages.KEY_ERROR_MESSAGE, message);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
		rd.forward(request, response);
	}

	protected void checkData(String fstField, String sndField)
			throws ValidationException {
		if (fstField == null || sndField == null) {
			throw new ValidationException(Messages.ERROR_NULL);
		}
		if (Constants.KEY_EMPTY.equals(fstField.trim())) {
			throw new ValidationException(Messages.ERROR_EMPTY);
		}
	}

	protected void checkData(String login, String[] passwords)
			throws ValidationException {
		final int FST_PASS_IND = 0;
		final int SND_PASSD_IND = 1;

		if (login == null || passwords == null) {
			throw new ValidationException(Messages.ERROR_NULL);
		}
		if (Constants.KEY_EMPTY.equals(login)) {
			throw new ValidationException(Messages.ERROR_EMPTY_LOGIN);
		}
		String firstPassword = passwords[FST_PASS_IND].trim();
		String secondPassword = passwords[SND_PASSD_IND].trim();
		if (Constants.KEY_EMPTY.equals(firstPassword)) {
			throw new ValidationException(Messages.ERROR_EMPTY_PASSWORD);
		}
		if (Constants.KEY_EMPTY.equals(secondPassword)) {
			throw new ValidationException(Messages.ERROR_EMPTY_PASSWORD_CONF);
		}
		if (!firstPassword.equals(secondPassword)) {
			throw new ValidationException(Messages.ERROR_PASSWORD_CONF);
		}
	}

	protected void setSessionAttributes(HttpServletRequest request,
			HttpSession session) {
		session.setAttribute(Constants.SERVLET_URI, request.getRequestURI());
		session.setAttribute(Constants.SERVLET_PATH, request.getContextPath());
	}

	protected String getMenuId(HttpServletRequest request, HttpSession session) {
		if (request.getParameter(Constants.MENU_ID) != null) {
			return request.getParameter(Constants.MENU_ID);
		} else {
			return (String) session.getAttribute(Constants.MENU_ID);
		}
	}
}