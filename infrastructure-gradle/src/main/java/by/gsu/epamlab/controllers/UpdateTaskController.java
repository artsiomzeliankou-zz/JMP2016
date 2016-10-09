package by.gsu.epamlab.controllers;

import java.io.File;
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
import by.gsu.epamlab.dao.factories.TaskFactory;
import by.gsu.epamlab.dao.ifaces.ITaskDAO;
import by.gsu.epamlab.ifaces.AbstractBaseController;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.utils.UpdateTaskDecoder;

public class UpdateTaskController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateTaskController.class);

	@Override
	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String path = (String) request.getSession().getServletContext()
				.getAttribute(Constants.PATH_NAME);
		try {
			User user = (User) session.getAttribute(Constants.KEY_USER);
			String[] arrTaskId = request
					.getParameterValues(Constants.KEY_BOXES);

			String updateType = request.getParameter(Constants.KEY_SUBMIT_TYPE);

			ITaskDAO taskDao = TaskFactory.getClassFromFactory();

			checkDeleteFiles(user, arrTaskId, updateType, taskDao, path);

			taskDao.updateTask(user, arrTaskId, updateType);

			logger.debug(Messages.SUCCESS_UPDATE_TASKS);

			String servletURI = (String) session
					.getAttribute(Constants.SERVLET_URI);
			String servletPath = (String) session
					.getAttribute(Constants.SERVLET_PATH);
			String url = servletURI.substring(servletPath.length());

			jumpPage(url, Constants.KEY_EMPTY, request, response);

		} catch (ValidationException e) {
			logger.warn(Messages.ERROR_UPDATE_TASKS, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);

		} catch (DaoSystemException e) {
			logger.warn(Messages.ERROR_SOURCE, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);
		}

	}

	private void checkDeleteFiles(User user, String[] arrTaskId,
			String updateTypeString, ITaskDAO taskDao, String path)
			throws ValidationException, DaoSystemException {

		UpdateTaskDecoder updateType = UpdateTaskDecoder
				.fromString(updateTypeString);
		if (updateType == null) {
			throw new ValidationException(Messages.ERROR_VALIDATION);
		} else if (updateType.equals(UpdateTaskDecoder.DELETE)) {
			String[] fileNames = taskDao.getfileNames(user, arrTaskId);
			deleteFiles(fileNames, path);
		}
	}

	private void deleteFiles(String[] fileNames, String path) {
		for (int i = 0; i < fileNames.length; i++) {

			File deleteFile = new File((path).concat(fileNames[i]));
			if (deleteFile.exists())
				deleteFile.delete();

		}
	}

}
