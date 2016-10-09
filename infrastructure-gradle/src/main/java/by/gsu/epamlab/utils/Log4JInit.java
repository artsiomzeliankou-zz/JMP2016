package by.gsu.epamlab.utils;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

import by.gsu.epamlab.constants.Constants;

public class Log4JInit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() {

		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter(Constants.LOG4J_LOCATION);
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}
	}

}