package by.gsu.epamlab.constants;

public class SqlRequests {

	public static final String INSERT_USER = "INSERT INTO users (login, password) VALUES(?,?)";
	public static final String SELECT_LOGIN = "SELECT login FROM users WHERE login = (?)";
	public static final String SELECT_USER = "SELECT login , password FROM users WHERE login=(?) AND password=(?)";
	public static final String SELECT_USER_ID = "SELECT idLogin FROM users WHERE login=(?)";
	public static final String INSERT_NEW_TODO = "INSERT INTO tasks (userId, title, status, date, fileName) VALUES(?,?,?,?,?)";
	public static final String SELECT_TASKS_BY_STATUS = "SELECT idTask, title, status, date, fileName FROM tasks WHERE userId=(?) AND status=(?) ORDER BY date";
	
//	public static final String SELECT_TODAY_TASKS = "SELECT idTask, title, date, fileName FROM tasks WHERE userId=(?) AND status='todo' AND date <= CURDATE() ORDER BY date";
//	public static final String SELECT_TOMORROW_TASKS = "SELECT idTask, title, date, fileName FROM tasks WHERE userId=(?) AND status='todo' AND date = CURDATE() + INTERVAL 1 DAY ORDER BY date";
//	public static final String SELECT_SOMEDAY_TASKS = "SELECT idTask, title, date, fileName FROM tasks WHERE userId=(?) AND status='todo' AND date > CURDATE() + INTERVAL 1 DAY ORDER BY date";
	public static final String SELECT_ANYDAY_TASKS = "SELECT idTask, title, date, fileName FROM tasks WHERE userId=(?) AND status='todo' ORDER BY date";
	public static final String SELECT_TODAY_TASKS = SELECT_ANYDAY_TASKS;
	public static final String SELECT_TOMORROW_TASKS = SELECT_ANYDAY_TASKS;
	public static final String SELECT_SOMEDAY_TASKS = SELECT_ANYDAY_TASKS;
	
	public static final String FIX_TASK = "UPDATE tasks SET status='fixed' WHERE idTask=(?) AND userId=(?)";
	public static final String REMOVE_TASK = "UPDATE tasks SET status='removed' WHERE idTask=(?) AND userId=(?)";
//	public static final String READD_TASK = "UPDATE tasks SET status='todo', date=CURDATE() WHERE idTask=(?) AND userId=(?)";
	public static final String READD_TASK = "UPDATE tasks SET status='todo' WHERE idTask=(?) AND userId=(?)";
	
	public static final String ADD_FILENAME = "UPDATE tasks SET fileName=(?) WHERE idTask=(?) AND userId=(?)";
	public static final String DELETE_TASK = "DELETE FROM  tasks WHERE idTask=(?) AND userId=(?)";
	public static final String SELECT_FILENAMES = "SELECT fileName FROM tasks WHERE idTask=(?) AND userId=(?)";


}
