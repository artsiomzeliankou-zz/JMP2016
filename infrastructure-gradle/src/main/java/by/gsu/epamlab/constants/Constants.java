package by.gsu.epamlab.constants;

import by.gsu.epamlab.model.beans.User;

public final class Constants {

	public static final String DATE_FORMAT = "dd.MM";
	public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
	
//	public static final String DRIVER_NAME = "org.gjt.mm.mysql.Driver";
//	public static final String DB_URL = "jdbc:mysql://localhost/todo";
//	public static final String DB_LOGIN = "epamlab";
//	public static final String DB_PASSWORD = "111";
	
	public static final String DRIVER_NAME = "org.postgresql.Driver";
	public static final String DB_URL = "jdbc:postgresql://localhost:5432/todo";
	public static final String DB_LOGIN = "postgres";
	public static final String DB_PASSWORD = "test1234";
	
	
	public static final String DELIMITER = ";";
	public static final User EMPTY_USER = null;
	public static final String JUMP_LOGIN = "/WEB-INF/views/login.jsp";
	public static final String JUMP_REGISTRATE = "/WEB-INF/views/registrate.jsp";
	public static final String JUMP_START = "/WEB-INF/views/start.jsp";
	public static final String JUMP_ERROR_PAGE = "/WEB-INF/views/errorPage.jsp";
	public static final String JUMP_CREATE_TASK = "/WEB-INF/views/createTask.jsp";
	public static final String JUMP_SHOW_ACTIVE_TASK = "/WEB-INF/views/showActiveTask.jsp";
	public static final String JUMP_SHOW_UNACTIVE_TASK = "/WEB-INF/views/showUnactiveTask.jsp";
	public static final String JUMP_UPLOAD_FILE = "/WEB-INF/views/uploadFile.jsp";
	public static final String JUMP_ACTIVE_TASK = "/showActiveTask";
	public static final String JUMP_UNACTIVE_TASK = "/showUnactiveTask";
	public static final String KEY_EMPTY = "";
	public static final String KEY_GUEST = "guest";
	public static final String KEY_LOGIN = "login";
	public static final String KEY_ID_LOGIN = "idLogin";
//	public static final String KEY_USER_ID = "userId";
	public static final String KEY_TASK_TITLE = "title";
	public static final String KEY_TASK_STATUS = "status";
	public static final String KEY_TASK_DATE = "date";
	public static final String KEY_ID_TASK = "idTask";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_USER = "user";
	public static final String KEY_TASKS = "tasks";
	public static final String PATH_NAME = "path";
	public static final String KEY_TASK_FILENAME = "fileName";
	public static final String NAME_CLASSES_ROOT = "WEB-INF\\classes\\";
	public static final String NAME_PROJECT_ROOT = "/";
	public static final String USER_STATUS = "status";
	public static final String TABLE_HEADER = "tableHeader";		
	public static final String MENU_ID ="menuId";	
	public static final String SPACE = " ";
	public static final String START_DAY = "TODAY";
	public static final String UPDATE_ID = "updateId";
	public static final String KEY_BOXES = "checkBoxes";
	public static final String KEY_SUBMIT_TYPE = "submitType";
	public static final String FLAG_SHOW_DATE = "showDate";
	public static final String FLAG_SHOW_LINKS = "showLinks";
	public static final String KEY_DATA = "data";
	public static final String FLAG_SHOW_TASKS = "showTasks";
	public static final String LOG4J_LOCATION = "log4j-properties-location";
	public static final String KEY_SLASH = "/";
	public static final String KEY_ACTION = "action";
	public static final String JSP_PREFIX = "/WEB-INF/views/";
	public static final String JSP_START = "start";
	public static final String JSP_EXT = ".jsp";
	public static final String MAIN_PAGE = "main";
	public static final String SERVLET_URI = "servletURI";
	public static final String SERVLET_PATH = "servletPath";
	public static final String CHECK = "checkData";
	
}
