package by.gsu.epamlab.controllers;

import java.io.IOException;
import java.util.List;

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
import by.gsu.epamlab.model.beans.Status;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.utils.CoreUtils;

public class ShowUnactiveTaskController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(ShowUnactiveTaskController.class);

	@Override
	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		User user = (User) session.getAttribute(Constants.KEY_USER);

		String menuId = getMenuId(request, session);

		ITaskDAO taskDAO = TaskFactory.getClassFromFactory();

		try {
			List<Task> listTodo = taskDAO.getTaskListByUserAndStatus(user,
					menuId);
			if (listTodo.size() != 0) {
				request.setAttribute(Constants.FLAG_SHOW_TASKS, 1);
			}

			String tableHeader = CoreUtils
					.getCapitalizedStringFromString(menuId);
			request.setAttribute(Constants.TABLE_HEADER, tableHeader);

			setShowLinks(menuId, request);

			request.setAttribute(Constants.KEY_TASKS, listTodo);

			session.setAttribute(Constants.MENU_ID, menuId);

			setSessionAttributes(request, session);

			logger.debug(Messages.SUCCESS_SHOW_TASKS);

			jumpPage(Constants.JUMP_SHOW_UNACTIVE_TASK, Constants.KEY_EMPTY,
					request, response);
		} catch (ValidationException e) {
			logger.warn(Messages.ERROR_SHOW_TASKS, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);

		} catch (DaoSystemException e) {
			logger.warn(Messages.ERROR_SOURCE, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);
		}
	}

	private static void setShowLinks(String showTaskId,
			HttpServletRequest request) {
		Status status = Status.fromString(showTaskId);
		if (status.equals(Status.REMOVED)) {
			request.setAttribute(Constants.FLAG_SHOW_LINKS, 1);
		}

	}
}