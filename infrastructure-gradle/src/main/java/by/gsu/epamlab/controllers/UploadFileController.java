package by.gsu.epamlab.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.io.*;
import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.dao.factories.TaskFactory;
import by.gsu.epamlab.dao.ifaces.ITaskDAO;
import by.gsu.epamlab.ifaces.AbstractBaseController;
import by.gsu.epamlab.model.beans.UploadBean;
import by.gsu.epamlab.model.beans.User;

public class UploadFileController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UploadFileController.class);

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		String idTaskPar = request.getParameter(Constants.KEY_ID_TASK);
		
		if (idTaskPar != null) {
			session.setAttribute(Constants.KEY_ID_TASK, idTaskPar);
			jumpPage(Constants.JUMP_UPLOAD_FILE, Constants.KEY_EMPTY, request,	response);
			return;
		}
		
		String idTask = (String) session.getAttribute(Constants.KEY_ID_TASK);
		
		String path = (String) request.getSession().getServletContext()
				.getAttribute(Constants.PATH_NAME);

		final int BUFFER_SIZE = 65536;
		response.setContentType("text/plain");
		ServletInputStream in = request.getInputStream();
		BufferedInputStream bf = new BufferedInputStream((InputStream) in,
				BUFFER_SIZE);

		byte[] buffer = new byte[BUFFER_SIZE];
		int j = 0;
		int c;
		while ((c = bf.read()) != -1) {
			buffer[j] = (byte) c;
			j++;
		}

		byte[] inputData = new byte[j];
		for (int i = 0; i < j; i++) {
			inputData[i] = buffer[i];
		}
		buffer = null;

		String boundary = extractBoundary(request.getHeader("Content-Type"));

		UploadBean uploadBean = extractFile(inputData, boundary);

		try {
			uploadBean.saveFile(inputData, path);
			User user = (User) session.getAttribute(Constants.KEY_USER);
			ITaskDAO taskDao = TaskFactory.getClassFromFactory();
			taskDao.addFileName(user, idTask, uploadBean.getFileName());
			logger.debug(Messages.SUCCESS_FILE_UPLOAD);
			jumpPage(Constants.JUMP_ACTIVE_TASK, Constants.KEY_EMPTY, request,
					response);

		} catch (ValidationException e) {
			logger.warn(Messages.ERROR_FILE_UPLOAD, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);

		} catch (DaoSystemException e) {
			logger.warn(Messages.ERROR_SOURCE, e);
			jumpPage(Constants.JUMP_ERROR_PAGE, e.getMessage(), request,
					response);
		}

	}

	private UploadBean extractFile(byte[] inputData, String boundary) {

		String dataString = new String(inputData);

		int startInd = dataString.indexOf(boundary);
		int endInd = dataString.lastIndexOf(boundary);

		UploadBean uploadBean = new UploadBean(Messages.ERROR_NO_FILE,
				startInd, endInd - 2);

		extractData(uploadBean, dataString.substring(startInd, endInd));

		return uploadBean;

	}

	private void extractData(UploadBean uploadBean, String data) {

		String delimiter = new String(new char[] { '\r', '\n', '\r', '\n' });
		String header;
		int index = data.indexOf(delimiter, 2);
		if (index != -1) {
			header = data.substring(0, index);
			uploadBean.setFileName(getFilename(header));
			int startInd = 0;
			startInd += (index + 4);
			uploadBean.setStartInd(startInd);
		}

	}

	private String getFilename(String header) {
		String fileName = Constants.KEY_EMPTY;
		int index;
		if ((index = header.toLowerCase().indexOf("filename=")) != -1) {
			int upIndex = header.indexOf((int) '"', index + 1 + 9);
			fileName = header.substring(index + 9 + 1, upIndex);
			index = fileName.lastIndexOf((int) '/');
			upIndex = fileName.lastIndexOf((int) '\\');
			fileName = fileName.substring(Math.max(index, upIndex) + 1);
		} else
			fileName = Messages.ERROR_NO_FILE;
		return fileName;

	}

	private String extractBoundary(String header) {
		if (header != null) {
			int index = header.lastIndexOf("boundary=");
			return ("--").concat(header.substring(index + 9));
		}
		return Constants.KEY_EMPTY;
	}
}