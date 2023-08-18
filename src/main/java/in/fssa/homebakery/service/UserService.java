package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import in.fssa.homebakery.dao.UserDAO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.UserValidator;

public class UserService {
	
	/**
	 * 
	 * @return
	 */
	public Set<User> getAll() {
		UserDAO userDao = new UserDAO();
		Set<User> userList = userDao.findAll();
		for (User user : userList) {
			System.out.println(user);
		}
		return userList;
	}
	
	/**
	 * 
	 * @param newUser
	 * @throws Exception
	 */
	public void create(User newUser) throws Exception {
		UserDAO userDao = new UserDAO();
		UserValidator.validate(newUser);
		userDao.create(newUser);
	}
	
	/**
	 * 
	 * @param id
	 * @param updatedUser
	 * @throws Exception
	 */
	public void update(int id, User updatedUser) throws Exception {
		UserDAO userDao = new UserDAO();

		IntUtil.rejectIfInvalidInt(id);

		boolean check = isUserPresent(id);

		if (!check) {
			throw new RuntimeException("User does not exist");
		}
		UserValidator.validate(updatedUser);
		userDao.update(id, updatedUser);

	}
	
	/**
	 * 
	 * @param userId
	 * @throws ValidationException
	 */
	public void delete(int userId) throws ValidationException {

		IntUtil.rejectIfInvalidInt(userId);

		boolean check = isUserPresent(userId);

		if (!check) {
			throw new RuntimeException("User does not exist");
		}
		UserDAO userDao = new UserDAO();
		userDao.delete(userId);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws ValidationException
	 */
	public User findById(int userId) throws ValidationException {
		IntUtil.rejectIfInvalidInt(userId);

		boolean check = isUserPresent(userId);

		if (!check) {
			throw new RuntimeException("User does not exist");
		}
		
		UserDAO userDao = new UserDAO();
		return userDao.findById(userId);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean isUserPresent(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionUtil.getConnection();
			String query = "SELECT COUNT(*) FROM users WHERE id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

}
