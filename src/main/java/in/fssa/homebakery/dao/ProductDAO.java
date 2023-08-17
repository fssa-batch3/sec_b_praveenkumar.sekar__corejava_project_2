package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.interface_files.ProductInterface;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.util.ConnectionUtil;

public class ProductDAO {

	 
	public void create(Product newProduct) {
		System.out.println("cannot create product");
	}

	 
	public void update(int id, Product newProduct) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String query = "UPDATE products SET product_name = ?, description = ?, category_id = ?, is_veg = ?, is_active = ? WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setString(1, newProduct.getProductName());
			stmt.setString(2, newProduct.getDescription());
			stmt.setInt(3, newProduct.getCategoryId());
			stmt.setBoolean(4, newProduct.isVeg());
			stmt.setBoolean(5, newProduct.isActive());
			stmt.setInt(6, id);

			stmt.executeUpdate();

			System.out.println("Product with ID " + id + " has been successfully updated");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt);
		}

	}

	 
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			String query = "UPDATE products SET is_active = ? WHERE is_active = 1 AND id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, 0);
			ps.setInt(2, id);
			ps.executeUpdate();

			System.out.println("Product has been successfully deactivated");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps);
		}

	}

	public Set<ProductDetailDTO> findAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		Set<ProductDetailDTO> setOfUser = new HashSet<>();
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM products WHERE is_active = 1";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				ProductDetailDTO product = new ProductDetailDTO();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setCategoryId(rs.getInt("categoru_id"));
				product.setVeg(rs.getBoolean("is_veg"));
				product.setActive(rs.getBoolean("is_active"));
				setOfUser.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return setOfUser;
	}

	 
	public Product findById(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		Product product = null;
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM products WHERE is_active = 1 AND id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setProductName(rs.getString("produt_name"));
				product.setDescription(rs.getString("description"));
				product.setCategoryId(rs.getInt("categoru_id"));
				product.setVeg(rs.getBoolean("is_veg"));
				product.setActive(rs.getBoolean("is_active"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return product;
	}

	 
	public int create(ProductDetailDTO productDetailDto) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int productId = -1;
		
		try {
			String query = "INSERT INTO products (product_name, description, category_id, is_veg, is_active) VALUES (?, ?, ?, ?, ?)";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.setString(1, productDetailDto.getName());
			stmt.setString(2, productDetailDto.getDescription());
			stmt.setInt(3, productDetailDto.getCategoryId());
			stmt.setBoolean(4, productDetailDto.isVeg());
			stmt.setBoolean(5, productDetailDto.isActive());
			stmt.executeUpdate();

			System.out.println("Product has been successfully created");

			 ResultSet generatedKeys = stmt.getGeneratedKeys();
		        if (generatedKeys.next()) {
		            productId = generatedKeys.getInt(1);
		        }
		        
		        
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt);
		}
		return productId;
	}

}
