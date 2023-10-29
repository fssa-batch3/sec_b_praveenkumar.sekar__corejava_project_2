package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.interfaces.CategoryInterface;
import in.fssa.homebakery.model.Category;
import in.fssa.homebakery.util.ConnectionUtil;

public class CategoryDAO implements CategoryInterface {

	@Override
	public void create(Category newT) {
	}

	/**
	 * Updates the category information with the specified ID in the database.
	 *
	 * This method updates the category name for the category with the provided ID.
	 * The updated category information is obtained from the 'updatedCategory'
	 * parameter. If the update is successful, the database record is modified, and
	 * a success message is printed. If any exception occurs during the process, it
	 * is caught, and a RuntimeException is thrown.
	 *
	 * @param id              The ID of the category to be updated.
	 * @param updatedCategory An instance of the 'Category' class containing the
	 *                        updated category information. It should have the
	 *                        updated category name.
	 * @throws PersistanceException
	 */
	@Override
	public void update(int id, Category updatedCategory) throws PersistanceException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			String query = "UPDATE categories SET category_name = ? WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setString(1, updatedCategory.getCategoryName());
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps);
		}
	}

	/**
	 * Placeholder method for deleting a category by ID.
	 *
	 * This method is intended as a placeholder and does not perform any actual
	 * deletion of categories. Instead, it prints a message indicating that
	 * categories cannot be deleted.
	 *
	 * @param id The ID of the category to be deleted.
	 */
	@Override
	public void delete(int id) {
	}

	/**
	 * Retrieves all categories from the database.
	 *
	 * This method queries the database to retrieve all categories stored in the
	 * 'categories' table. It creates 'Category' objects for each retrieved record
	 * and adds them to a 'Set'. The set is then returned, containing all the
	 * retrieved categories. If any database-related exception occurs during the
	 * process, it is caught, and a RuntimeException is thrown.
	 *
	 * @return A 'Set' containing all the retrieved 'Category' objects from the
	 *         database.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	@Override
	public Set<Category> findAll() throws PersistanceException {
		Connection conn = null;
		PreparedStatement ps = null;
		Set<Category> setOfCategory = new HashSet<>();
		ResultSet rs = null;

		try {
			String query = "SELECT id, category_name FROM categories";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setCategoryName(rs.getString("category_name"));
				setOfCategory.add(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return setOfCategory;
	}

	/**
	 * Retrieves a category from the database by its ID.
	 *
	 * This method queries the database to retrieve a category stored in the
	 * 'categories' table that matches the provided ID. If a matching record is
	 * found, a 'Category' object is created, populated with the retrieved data, and
	 * returned. If no matching record is found, the method returns null. If any
	 * database-related exception occurs during the process, it is caught, and a
	 * RuntimeException is thrown.
	 *
	 * @param id The ID of the category to be retrieved.
	 * @return A 'Category' object containing the data of the retrieved category.
	 *         Returns null if no matching category is found.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	@Override
	public Category findById(int id) throws PersistanceException {
		Connection conn = null;
		PreparedStatement ps = null;
		Category category = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, category_name FROM categories WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				category = new Category();
				category.setId(rs.getInt("id"));
				category.setCategoryName(rs.getString("category_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return category;
	}
	
	/**
	 * Checks if a category with the given ID exists in the database.
	 *
	 * This method queries the database to check if a category with the provided
	 * 'categoryId' exists in the 'categories' table. If a category with the given
	 * ID is found, the method returns 'true'. Otherwise, it returns 'false'.
	 *
	 * @param categoryId The ID of the category to check for existence.
	 * @return 'true' if a category with the provided ID exists in the database,
	 *         'false' otherwise.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database query
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public static boolean categoryExists(int categoryId) throws PersistanceException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT 1 FROM categories WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, categoryId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0; // If count > 0, category exists
			}

			return false; // No rows returned, category does not exist
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

}
