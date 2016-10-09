package by.gsu.epamlab.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.constants.SqlRequests;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.dao.ifaces.ITaskDAO;
import by.gsu.epamlab.model.beans.Status;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.utils.ActiveTaskDecoder;
import by.gsu.epamlab.utils.JdbcUtils;
import by.gsu.epamlab.utils.UpdateTaskDecoder;

/**
 * Implementation of {@link ITaskDAO]
 */
public class TaskImplDB implements ITaskDAO {

	@Override
	public List<Task> getTaskListByUserAndStatus(User user, String menuId)
			throws DaoSystemException, ValidationException {
		checkUser(user);
		List<Task> listTodobByStatus = new ArrayList<Task>();
		int userId = getUserId(user);
		String taskStatus = menuId.trim().toLowerCase();
		try {
			Connection cn = null;
			PreparedStatement psSelectTask = null;
			ResultSet rs = null;
			try {
				cn = JdbcUtils.getConnection();
				psSelectTask = cn
						.prepareStatement(SqlRequests.SELECT_TASKS_BY_STATUS);
				final int ID_NUM = 1;
				final int STATUS_NUM = 2;
				psSelectTask.setInt(ID_NUM, userId);
				psSelectTask.setString(STATUS_NUM, taskStatus);
				rs = psSelectTask.executeQuery();
				while (rs.next()) {
					int idTask = rs.getInt(Constants.KEY_ID_TASK);
					String title = rs.getString(Constants.KEY_TASK_TITLE);
					String statusString = rs
							.getString(Constants.KEY_TASK_STATUS);
					Date date = rs.getDate(Constants.KEY_TASK_DATE);
					String fileName = rs.getString(Constants.KEY_TASK_FILENAME);
					Status status = Status.fromString(statusString);
					listTodobByStatus.add(new Task(idTask, userId, title,
							status, date, fileName));
				}
				return listTodobByStatus;
			} finally {
				JdbcUtils.closeQuietly(rs);
				JdbcUtils.closeQuietly(psSelectTask);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}
	}

	@Override
	public List<Task> getActiveTaskListByUserAndDate(User user,
			String showTaskId) throws DaoSystemException, ValidationException {
		checkUser(user);
		List<Task> listTodoByDate = new ArrayList<Task>();
		int userId = getUserId(user);
		try {
			Connection cn = null;
			PreparedStatement psSelectTask = null;
			ResultSet rs = null;
			try {
				cn = JdbcUtils.getConnection();
				String request = getShowRequest(showTaskId);
				psSelectTask = cn.prepareStatement(request);
				final int ID_NUM = 1;
				psSelectTask.setInt(ID_NUM, userId);
				rs = psSelectTask.executeQuery();
				while (rs.next()) {
					int idTask = rs.getInt(Constants.KEY_ID_TASK);
					String title = rs.getString(Constants.KEY_TASK_TITLE);
					Date date = rs.getDate(Constants.KEY_TASK_DATE);
					String fileName = rs.getString(Constants.KEY_TASK_FILENAME);
					listTodoByDate.add(new Task(idTask, userId, title,
							Status.TODO, date, fileName));
				}
				return listTodoByDate;
			} finally {
				JdbcUtils.closeQuietly(rs);
				JdbcUtils.closeQuietly(psSelectTask);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}
	}

	@Override
	public void createNewTask(User user, String title, Date dueDate)
			throws DaoSystemException, ValidationException {
		checkUser(user);
		int userId = getUserId(user);
		String statusTodo = Status.TODO.toString().toLowerCase();
		try {
			Connection cn = null;
			PreparedStatement psInsertTodo = null;
			try {
				cn = JdbcUtils.getConnection();
				psInsertTodo = cn.prepareStatement(SqlRequests.INSERT_NEW_TODO);
				final int USER_ID_NUM = 1;
				final int TITLE_NUM = 2;
				final int STATUS_NUM = 3;
				final int DATE_NUM = 4;
				final int FILENAME_NUM = 5;
				psInsertTodo.setInt(USER_ID_NUM, userId);
				psInsertTodo.setString(TITLE_NUM, title);
				psInsertTodo.setString(STATUS_NUM, statusTodo);
				psInsertTodo.setDate(DATE_NUM, dueDate);
				psInsertTodo.setString(FILENAME_NUM, Constants.KEY_EMPTY);
				psInsertTodo.executeUpdate();
			} finally {
				JdbcUtils.closeQuietly(psInsertTodo);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}

	}

	@Override
	public void updateTask(User user, String[] arrTaskIdString,
			String updateType) throws DaoSystemException, ValidationException {
		checkUser(user);
		int userId = getUserId(user);
		int[] taskId = getArrTaskId(arrTaskIdString);
		String request = getUpdateRequest(updateType);
		try {
			Connection cn = null;
			PreparedStatement psUpdateTask = null;

			try {
				cn = JdbcUtils.getConnection();
				psUpdateTask = cn.prepareStatement(request);

				final int TASK_ID_NUM = 1;
				final int USER_ID_NUM = 2;
				for (int i = 0; i < taskId.length; i++) {
					psUpdateTask.setInt(TASK_ID_NUM, taskId[i]);
					psUpdateTask.setInt(USER_ID_NUM, userId);
					psUpdateTask.executeUpdate();
				}
			} finally {
				JdbcUtils.closeQuietly(psUpdateTask);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}

	}

	@Override
	public void addFileName(User user, String idTask, String fileName)
			throws DaoSystemException, ValidationException {
		checkUser(user);
		int userId = getUserId(user);
		int taskId = 0;
		try {
			taskId = Integer.parseInt(idTask);
		} catch (NumberFormatException e) {
			throw new ValidationException(Messages.ERROR_TO_UPDATE);
		}

		try {
			Connection cn = null;
			PreparedStatement psAddFile = null;
			try {
				cn = JdbcUtils.getConnection();
				psAddFile = cn.prepareStatement(SqlRequests.ADD_FILENAME);
				final int FILENAME_NUM = 1;
				final int TASK_ID_NUM = 2;
				final int USER_ID_NUM = 3;
				psAddFile.setString(FILENAME_NUM, fileName);
				psAddFile.setInt(TASK_ID_NUM, taskId);
				psAddFile.setInt(USER_ID_NUM, userId);
				psAddFile.executeUpdate();
			} finally {
				JdbcUtils.closeQuietly(psAddFile);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}
	}

	@Override
	public String[] getfileNames(User user, String[] arrTaskIdString)
			throws DaoSystemException, ValidationException {
		checkUser(user);
		int userId = getUserId(user);
		int[] taskId = getArrTaskId(arrTaskIdString);
		String[] fileNames = new String[arrTaskIdString.length];
		try {
			Connection cn = null;
			PreparedStatement psFileSearch = null;
			ResultSet rs = null;
			try {
				cn = JdbcUtils.getConnection();
				psFileSearch = cn
						.prepareStatement(SqlRequests.SELECT_FILENAMES);
				final int TASK_ID_NUM = 1;
				final int USER_ID_NUM = 2;
				psFileSearch.setInt(USER_ID_NUM, userId);
				for (int i = 0; i < taskId.length; i++) {
					psFileSearch.setInt(TASK_ID_NUM, taskId[i]);
					rs = psFileSearch.executeQuery();
					while (rs.next()) {
						String fileName = rs
								.getString(Constants.KEY_TASK_FILENAME);
						fileNames[i] = fileName;
					}
				}
				return fileNames;
			} finally {
				JdbcUtils.closeQuietly(rs);
				JdbcUtils.closeQuietly(psFileSearch);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}

	}

	private int getUserId(User user) throws DaoSystemException,
			ValidationException {
		if (user != null) {
			String login = user.getLogin();
			try {
				Connection cn = null;
				PreparedStatement psSelectUserId = null;
				ResultSet rs = null;
				try {
					cn = JdbcUtils.getConnection();
					psSelectUserId = cn
							.prepareStatement(SqlRequests.SELECT_USER_ID);
					final int LOGIN_NUM = 1;
					psSelectUserId.setString(LOGIN_NUM, login);
					rs = psSelectUserId.executeQuery();
					if (rs.next()) {
						return rs.getInt(Constants.KEY_ID_LOGIN);
					}
				} finally {
					JdbcUtils.closeQuietly(rs);
					JdbcUtils.closeQuietly(psSelectUserId);
					JdbcUtils.closeQuietly(cn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DaoSystemException(Messages.ERROR_SOURCE, e);
			}
		}
		throw new ValidationException(Messages.ERROR_VALIDATION);
	}

	private String getShowRequest(String menuId) throws ValidationException {
		String dateString;
		if (menuId == null) {
			dateString = ActiveTaskDecoder.TODAY.toString();
		} else {
			dateString = menuId;
		}
		ActiveTaskDecoder date = ActiveTaskDecoder.fromString(dateString);
		switch (date) {
		case TODAY:
			return ActiveTaskDecoder.TODAY.getSqlStrinq();
		case TOMORROW:
			return ActiveTaskDecoder.TOMORROW.getSqlStrinq();
		case SOMEDAY:
			return ActiveTaskDecoder.SOMEDAY.getSqlStrinq();
		default:
			throw new ValidationException(Messages.ERROR_DATE_TO_SHOW);
		}

	}

	private String getUpdateRequest(String updateType)
			throws ValidationException {

		UpdateTaskDecoder updType = UpdateTaskDecoder.fromString(updateType);
		switch (updType) {
		case FIX:
			return UpdateTaskDecoder.FIX.getSqlStrinq();
		case REMOVE:
			return UpdateTaskDecoder.REMOVE.getSqlStrinq();
		case READD:
			return UpdateTaskDecoder.READD.getSqlStrinq();
		case DELETE:
			return UpdateTaskDecoder.DELETE.getSqlStrinq();
		default:
			throw new ValidationException(Messages.ERROR_TO_UPDATE);
		}

	}

	private int[] getArrTaskId(String[] arrTaskIdString)
			throws ValidationException {

		if (arrTaskIdString != null) {
			int[] arrTaskIdInt = new int[arrTaskIdString.length];
			for (int i = 0; i < arrTaskIdString.length; i++) {
				try {
					arrTaskIdInt[i] = Integer.parseInt(arrTaskIdString[i]);
				} catch (NumberFormatException e) {
					throw new ValidationException(Messages.ERROR_TO_UPDATE, e);
				}
			}
			return arrTaskIdInt;
		}
		throw new ValidationException(Messages.ERROR_NO_CHOSEN_TASKS_TO_UPDATE);

	}

	private void checkUser(User user) throws ValidationException {
		if (user == Constants.EMPTY_USER) {
			throw new ValidationException(Messages.ERROR_VALIDATION);
		}
	}

}
