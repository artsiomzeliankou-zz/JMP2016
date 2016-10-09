package by.gsu.epamlab.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.ifaces.AbstractBaseController;

public class DownloadFileController extends AbstractBaseController {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DownloadFileController.class);

	protected void performTask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String filename = (String) request.getParameter(Constants.KEY_TASK_FILENAME);
		String path = (String) request.getSession().getServletContext()
				.getAttribute(Constants.PATH_NAME);

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);

		File file = new File(path + filename);
		FileInputStream fileIn = null;
		ServletOutputStream out = null;

		try {
			fileIn = new FileInputStream(file);
			out = response.getOutputStream();

			byte[] outputByte = new byte[(int) file.length()];
			while (fileIn.read(outputByte, 0, (int) file.length()) != -1) {
				out.write(outputByte, 0, (int) file.length());
			}
			logger.debug(Messages.SUCCESSFULL_DOWNLOAD);
		} catch (Exception e) {
			logger.warn(Messages.ERROR_DOWNLOAD_FILE + e);
		 } finally {
	            if (fileIn != null) {
	            	fileIn.close();
	            }
	            if (out != null) {
	            	out.close();
	            }

	        }

	}
}
