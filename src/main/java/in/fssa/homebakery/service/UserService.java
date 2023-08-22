package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.dao.UserDAO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.exception.ServiceException;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.UserValidator;

public class UserService {

	/**
	 * Retrieves a set of all active users from the database.
	 *
	 * This method queries the database to retrieve a set of all users who are
	 * marked as active. The retrieved user information is encapsulated in User
	 * objects and added to the returned set.
	 * 
	 * @return A set containing all active users retrieved from the database.
	 * @throws RuntimeException If an error occurs while querying the database.
	 */
	public Set<User> getAll() {
		UserDAO userDao = new UserDAO();
		Set<User> userList = new HashSet<>();
		try {
			userList = userDao.findAll();
			for (User user : userList) {
				System.out.println(user);
			}
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * Creates a new user in the database.
	 *
	 * This method is responsible for creating a new user in the database based on
	 * the provided User object. The provided user information is validated before
	 * attempting to create the user in the database.
	 * 
	 * @param newUser The User object containing the details of the new user to be
	 *                created.
	 * @throws Exception If the provided user information is invalid or an error
	 *                   occurs during database interaction.
	 */
	public void create(User newUser) throws ValidationException {
		UserDAO userDao = new UserDAO();
		try {
			UserValidator.validate(newUser);
			userDao.create(newUser);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Updates an existing user's information in the database.
	 *
	 * This method is responsible for updating the information of an existing user
	 * in the database based on the provided user ID and updated User object. The
	 * provided user ID and updated information are validated before attempting to
	 * update the user's information in the database.
	 * 
	 * @param id          The ID of the user whose information needs to be updated.
	 * @param updatedUser The User object containing the updated details of the
	 *                    user.
	 * @throws Exception If the provided user ID is invalid, the user does not
	 *                   exist, the updated user information is invalid, or an error
	 *                   occurs during database interaction.
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
	 * Deletes a user from the database.
	 *
	 * This method is responsible for deleting a user from the database based on the
	 * provided user ID. The provided user ID is validated before attempting to
	 * delete the user.
	 * 
	 * @param userId The ID of the user to be deleted.
	 * @throws ValidationException If the provided user ID is invalid.
	 * @throws RuntimeException    If the user does not exist or an error occurs
	 *                             during database interaction.
	 */
	public void delete(int userId) throws ValidationException {

		IntUtil.rejectIfInvalidInt(userId);

		boolean check = isUserPresent(userId);

		if (!check) {
			throw new RuntimeException("User does not exist");
		}
		UserDAO userDao = new UserDAO();
		try {
			userDao.delete(userId);
		} catch (PersistanceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves a user from the database based on the provided user ID.
	 *
	 * This method is responsible for retrieving a user from the database using the
	 * provided user ID. The provided user ID is validated before attempting to
	 * retrieve the user.
	 * 
	 * @param userId The ID of the user to be retrieved.
	 * @return The retrieved user.
	 * @throws ValidationException If the provided user ID is invalid.
	 * @throws RuntimeException    If the user does not exist or an error occurs
	 *                             during database interaction.
	 */
	public User findById(int userId) throws ValidationException {
		IntUtil.rejectIfInvalidInt(userId);

		boolean check = isUserPresent(userId);

		if (!check) {
			throw new RuntimeException("User does not exist");
		}

		UserDAO userDao = new UserDAO();
		User user = null;
		try {
			user =  userDao.findById(userId);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	/**
	 * Checks if a user with the provided user ID exists in the database.
	 *
	 * This method determines whether a user with the given user ID exists in the
	 * database. It queries the database and returns a boolean value indicating the
	 * presence of the user.
	 * 
	 * @param id The ID of the user to be checked.
	 * @return True if the user with the provided ID exists, otherwise false.
	 * @throws RuntimeException If an error occurs during database interaction.
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
