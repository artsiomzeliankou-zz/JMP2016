package by.gsu.epamlab.controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.dao.factories.TaskFactory;
import by.gsu.epamlab.dao.ifaces.ITaskDAO;
import by.gsu.epamlab.ifaces.AbstractBaseController;
import by.gsu.epamlab.model.beans.User;

public class CreateTaskController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreateTaskController.class);

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter(Constants.KEY_TASK_TITLE);

		String stringDate = request.getParameter(Constants.KEY_TASK_DATE);

		HttpSession session = request.getSession(true);

		User user = (User) session.getAttribute(Constants.KEY_USER);

		ITaskDAO todoDAO = TaskFactory.getClassFromFactory();

		try {

			if (request.getParameter(Constants.CHECK) != null) {
				jumpPage(Constants.JUMP_CREATE_TASK, Messages.ERROR_LOGIN,
						request, response);
				return;
			}

			checkData(title, stringDate);
			Date dueDate = getcorrectDate(stringDate);
			todoDAO.createNewTask(user, title, dueDate);

			session.setAttribute(Constants.KEY_USER, user);
			logger.debug(Messages.SUCCESSFULL_CREATE);
			jumpPage(Constants.JUMP_ACTIVE_TASK, Constants.KEY_EMPTY, request,
					response);

		} catch (ValidationException | ParseException e) {
			logger.warn(Messages.ERROR_CREATE, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);

		} catch (DaoSystemException e) {
			logger.warn(Messages.ERROR_SOURCE, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);
		}

	}

	private Date getcorrectDate(String stringDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.INPUT_DATE_FORMAT);
		java.util.Date utilDate = sdf.parse(stringDate);
		return new Date(utilDate.getTime());

	}
}
