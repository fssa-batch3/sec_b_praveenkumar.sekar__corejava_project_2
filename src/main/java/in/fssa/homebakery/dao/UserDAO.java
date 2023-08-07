package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.interface_files.UserInterface;
import in.fssa.homebakery.model.User;

public class UserDAO implements UserInterface {

	@Override
	public void create(User newT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, User newT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
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
//			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
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
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
