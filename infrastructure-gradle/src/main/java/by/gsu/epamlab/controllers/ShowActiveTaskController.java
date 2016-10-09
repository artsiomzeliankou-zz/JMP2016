package by.gsu.epamlab.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.utils.ActiveTaskDecoder;
import by.gsu.epamlab.utils.CoreUtils;

public class ShowActiveTaskController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(ShowActiveTaskController.class);

	@Override
	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		User user = (User) session.getAttribute(Constants.KEY_USER);

		String menuId = getMenuId(request, session);

		ActiveTaskDecoder activeTaskName = ActiveTaskDecoder.fromString(menuId);

		try {
			String tableName = getTableName(activeTaskName, request);
			request.setAttribute(Constants.TABLE_HEADER, tableName);

			ITaskDAO taskDAO = TaskFactory.getClassFromFactory();

			List<Task> listTodo;

			listTodo = taskDAO.getActiveTaskListByUserAndDate(user, menuId);

			if (listTodo.size() != 0) {
				request.setAttribute(Constants.FLAG_SHOW_TASKS, 1);
			}
			listTodo = setStringDate(listTodo);

			request.setAttribute(Constants.KEY_TASKS, listTodo);

			session.setAttribute(Constants.MENU_ID, menuId);

			setSessionAttributes(request, session);

			logger.debug(Messages.SUCCESS_SHOW_TASKS);

			jumpPage(Constants.JUMP_SHOW_ACTIVE_TASK, Constants.KEY_EMPTY,
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

	private String getTableName(ActiveTaskDecoder activeTaskName,
			HttpServletRequest request) throws ValidationException {

		if (activeTaskName != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(CoreUtils.getCapitalizedStringFromEnum(activeTaskName))
					.append(Constants.SPACE);
			switch (activeTaskName) {
			case TODAY:
				sb.append(getTableDate(activeTaskName));
				break;
			case TOMORROW:
				sb.append(getTableDate(activeTaskName));
				break;
			case SOMEDAY:
				sb.append(Constants.SPACE);
				request.setAttribute(Constants.FLAG_SHOW_DATE, 1);
				break;
			default:
				throw new ValidationException(Messages.ERROR_DATE_TO_SHOW);
			}
			return sb.toString();
		}

		throw new ValidationException(Messages.ERROR_VALIDATION);

	}

	private List<Task> setStringDate(List<Task> listTodo) {
		if (listTodo.size() != 0) {
			for (Task todo : listTodo) {
				todo.setDate();
			}
		}
		return listTodo;

	}

	private String getTableDate(ActiveTaskDecoder runDate) {
		Calendar calendar = Calendar.getInstance();

		if (runDate == ActiveTaskDecoder.TOMORROW) {
			calendar.add(Calendar.DATE, 1);
		}
		return (new SimpleDateFormat(Constants.DATE_FORMAT).format(calendar
				.getTime()));
	}

}
