package in.fssa.homebakery.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionUtil {

	/**
	 * Establishes a database connection using the provided configuration.
	 *
	 * This method creates a database connection to the configured database server.
	 * It uses the database hostname, username, and password obtained from the
	 * environment variables.
	 * 
	 * @return A database connection instance.
	 * @throws SQLException 
	 * @throws RuntimeException If an error occurs while establishing the
	 *                          connection.
	 */
	public static Connection getConnection() throws SQLException {



		Connection connection = null;
	    String url;
        String userName;
        String passWord;

        if (System.getenv("CI") != null) {
            url = System.getenv("DATABASE_HOSTNAME");
            userName = System.getenv("DATABASE_USERNAME");
            passWord = System.getenv("DATABASE_PASSWORD");
        } else {
            Dotenv env = Dotenv.load();
            url = env.get("DATABASE_HOSTNAME");
            userName = env.get("DATABASE_USERNAME");
            passWord = env.get("DATABASE_PASSWORD");
        }

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, userName, passWord);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

		return connection;
	}

	/**
	 * Closes the database connection and prepared statement.
	 *
	 * This method is used to close both the database connection and the prepared
	 * statement after they have been used for database operations. It ensures that
	 * the resources are properly released and the connection is closed.
	 *
	 * @param connection The database connection to be closed.
	 * @param ps         The prepared statement to be closed.
	 */
	public static void close(Connection connection, PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Closes the database connection, prepared statement, and result set.
	 *
	 * This method is used to close the database connection, prepared statement, and
	 * result set after they have been used for database operations. It ensures that
	 * the resources are properly released and the connection is closed.
	 *
	 * @param connection The database connection to be closed.
	 * @param ps         The prepared statement to be closed.
	 * @param rs         The result set to be closed.
	 */
	public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
