package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.interface_files.UserInterface;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.ConnectionUtil;

public class UserDAO implements UserInterface {

	@Override
	public void create(User newUser) throws RuntimeException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			String query = "INSERT INTO users (first_name, last_name, email, password, phone_no) VALUES (?,?,?,?,?)";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setString(1, newUser.getFirstName());
			ps.setString(2, newUser.getLastName());
			ps.setString(3, newUser.getEmail());
			ps.setString(4, newUser.getPassword());
			ps.setLong(4, newUser.getPhoneNo());
			ps.executeUpdate();

			System.out.println("User has been successfully created");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps);
		}
	}

	@Override
	public void update(int id,User updatedUser) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			String query = "UPDATE users SET first_name = ? , last_name = ? , phone_no = ? WHERE is_active = 1 AND id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setString(1, updatedUser.getFirstName());
			ps.setString(2, updatedUser.getLastName());
			ps.setLong(3, updatedUser.getPhoneNo());
			ps.setInt(4, id);
			ps.executeUpdate();

			System.out.println("User has been successfully updated");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps);
		}
	}

	@Override
	public void delete(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			String query = "UPDATE users SET is_active = ? WHERE is_active = 1 AND id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, 0);
			ps.setInt(2, userId);
			ps.executeUpdate();

			System.out.println("User has been successfully deactivated");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps);
		}
	}
	
	@Override
	public Set<User> findAll() throws RuntimeException {
//		Set<User> userList = UserList.listOfUsers;
//		return userList;
		Connection conn = null;
		PreparedStatement ps = null;
		Set<User> setOfUser = new HashSet<>();
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM users WHERE is_active = 1";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNo(rs.getLong("phone_no"));
				user.setActive(rs.getBoolean("is_active"));
				user.setPassword(rs.getString("password"));
				setOfUser.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
//			ConnectionUtil.close(conn, ps, rs);
		}
		return setOfUser;
	}

	@Override
	public User findById(int userId) throws RuntimeException {

		Connection conn = null;
		PreparedStatement ps = null;
		User user = null;
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM users WHERE is_active = 1 AND id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setPhoneNo(rs.getLong("phone_no"));
				user.setActive(rs.getBoolean("is_active")); 
				user.setPassword(rs.getString("password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return user;
	}
	
}
