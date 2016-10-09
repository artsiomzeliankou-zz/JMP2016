package by.gsu.epamlab.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.Messages;
import by.gsu.epamlab.constants.SqlRequests;
import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.NoSuchEntityException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.dao.ifaces.IUserDAO;
import by.gsu.epamlab.dao.ifaces.UserDaoAbstract;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.utils.JdbcUtils;

/**
 * Implementation of {@link IUserDAO]
 */
public class UserImplDB extends UserDaoAbstract implements IUserDAO {

	@Override
	public User retrieveUser(String login, String password)
			throws DaoSystemException, NoSuchEntityException {
		try {
			Connection cn = null;
			PreparedStatement psSelectUser = null;
			ResultSet rs = null;
			try {
				cn = JdbcUtils.getConnection();
				psSelectUser = cn.prepareStatement(SqlRequests.SELECT_USER);
				final int LOGIN_NUM = 1;
				final int PASS_NUM = 2;
				psSelectUser.setString(LOGIN_NUM, login);
				psSelectUser.setString(PASS_NUM, password);
				rs = psSelectUser.executeQuery();
				if (rs.next()) {
					return new User(rs.getString(Constants.KEY_LOGIN),
							rs.getString(Constants.KEY_PASSWORD));
				}
			} finally {
				JdbcUtils.closeQuietly(rs);
				JdbcUtils.closeQuietly(psSelectUser);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}
		throw new NoSuchEntityException(Messages.ERROR_NO_SUCH_USER);
	}

	public void checkLogin(String login) throws DaoSystemException,
			ValidationException {

		boolean isLoginExist = false;
		try {
			Connection cn = null;
			PreparedStatement psSelectUser = null;
			ResultSet rs = null;
			try {
				cn = JdbcUtils.getConnection();
				psSelectUser = cn.prepareStatement(SqlRequests.SELECT_LOGIN);
				final int LOGIN_NUM = 1;
				psSelectUser.setString(LOGIN_NUM, login);
				synchronized (UserImplDB.class) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					rs = psSelectUser.executeQuery();
					if (rs.next()) {
						isLoginExist = true;
					}
				}
			} finally {
				JdbcUtils.closeQuietly(rs);
				JdbcUtils.closeQuietly(psSelectUser);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}
		if (isLoginExist) {
			throw new ValidationException(Messages.ERROR_LOGIN);
		}
	}

	protected void insertUser(User user) throws DaoSystemException,
			ValidationException {
		try {
			Connection cn = null;
			PreparedStatement psInsertUser = null;
			try {
				cn = JdbcUtils.getConnection();
				psInsertUser = cn.prepareStatement(SqlRequests.INSERT_USER);
				final int LOGIN_NUM = 1;
				final int PASS_NUM = 2;
				psInsertUser.setString(LOGIN_NUM, user.getLogin());
				psInsertUser.setString(PASS_NUM, user.getPassword());
				psInsertUser.executeUpdate();
			} finally {
				JdbcUtils.closeQuietly(psInsertUser);
				JdbcUtils.closeQuietly(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoSystemException(Messages.ERROR_SOURCE, e);
		}
	}

}
