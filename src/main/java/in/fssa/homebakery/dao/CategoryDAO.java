package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.interface_files.CategoryInterface;
import in.fssa.homebakery.model.Category;
import in.fssa.homebakery.util.ConnectionUtil;

public class CategoryDAO implements CategoryInterface{

	@Override
	public void create(Category newT) {
		System.out.println("Cannot create new category");
		
	}

	@Override
	public void update(int id, Category updatedCategory) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			String query = "UPDATE categories SET category_name = ? WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setString(1, updatedCategory.getCategoryName());
			ps.setInt(2, id);
			ps.executeUpdate();

			System.out.println("Category has been successfully updated");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps);
		}
	}

	@Override
	public void delete(int id) {
		System.out.println("Cannot delete categories");
	}

	@Override
	public Set<Category> findAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		Set<Category> setOfCategory = new HashSet<>();
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM categories";
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
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return setOfCategory;
	}

	@Override
	public Category findById(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		Category category = null;
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM categories WHERE id = ?";
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
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return category;
	}

}
