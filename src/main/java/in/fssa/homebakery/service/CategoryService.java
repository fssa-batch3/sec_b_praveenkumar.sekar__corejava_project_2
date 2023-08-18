package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import in.fssa.homebakery.dao.CategoryDAO;
import in.fssa.homebakery.model.Category;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.validator.CategoryValidator;

public class CategoryService {
	
	/**
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public Category findById(int categoryId) throws Exception {
		CategoryValidator.validateId(categoryId);
		boolean test = categoryExists(categoryId);

		if (!test) {
			throw new RuntimeException("Category does not exist");
		}

		CategoryDAO categoryDao = new CategoryDAO();
		return categoryDao.findById(categoryId);
	}
	
	/**
	 * 
	 * @param id
	 * @param updatedCategory
	 * @throws Exception
	 */
	public void update(int id, Category updatedCategory) throws Exception {
		CategoryValidator.validateId(id);

		boolean test = categoryExists(id);

		if (!test) {
			throw new RuntimeException("Category does not exist");
		}

		CategoryDAO categoryDao = new CategoryDAO();
		categoryDao.update(2, updatedCategory);

	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Category> getAll() {
		CategoryDAO categoryDAO = new CategoryDAO();

		Set<Category> categoryList = categoryDAO.findAll();

		return categoryList;
	}
	
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	public boolean categoryExists(int categoryId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT COUNT(*) FROM categories WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, categoryId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0; // If count > 0, category exists
			}

			return false; // No rows returned, category does not exist
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}
}
