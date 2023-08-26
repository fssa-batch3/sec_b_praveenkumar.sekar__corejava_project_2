package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.interfaces.ProductInterface;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.util.ConnectionUtil;

public class ProductDAO {

	/**
	 * Updates product information with the specified ID in the database.
	 *
	 * This method updates the product information for the product with the provided
	 * ID. The updated product information is obtained from the 'newProduct'
	 * parameter. If the update is successful, the database record is modified, and
	 * a success message is printed. If any exception occurs during the process, it
	 * is caught, and a RuntimeException is thrown.
	 *
	 * @param id         The ID of the product to be updated.
	 * @param newProduct An instance of the 'Product' class containing the updated
	 *                   product information. It should have the updated product
	 *                   name, description, category ID, and boolean values
	 *                   indicating whether the product is vegetarian and active.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database update
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public void update(int id, Product newProduct) throws PersistanceException {
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

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, stmt);
		}

	}

	/**
	 * Deactivates a product by setting its 'is_active' status to false in the
	 * database.
	 *
	 * This method updates the 'is_active' status of the product with the provided
	 * ID to false, effectively deactivating the product in the database. If the
	 * deactivation is successful, the database record is modified, and a success
	 * message is printed. If any exception occurs during the process, it is caught,
	 * and a RuntimeException is thrown.
	 *
	 * @param id The ID of the product to be deactivated.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database update
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public void delete(int id) throws PersistanceException {
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps);
		}

	}

	/**
	 * Retrieves all active products from the database along with their details.
	 *
	 * This method queries the database to retrieve all products that are currently
	 * active, based on the 'is_active' status in the 'products' table. For each
	 * active product, a 'ProductDetailDTO' object is created, populated with the
	 * retrieved data, and added to a 'Set'. The set is then returned, containing
	 * all active products' details. If any database-related exception occurs during
	 * the process, it is caught, and a RuntimeException is thrown.
	 *
	 * @return A 'Set' containing 'ProductDetailDTO' objects representing details of
	 *         all active products.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public Set<ProductDetailDTO> findAll() throws PersistanceException {
		Connection conn = null;
		PreparedStatement ps = null;
		Set<ProductDetailDTO> setOfUser = new HashSet<>();
		ResultSet rs = null;

		try {
			String query = "SELECT id, product_name, description, category_id, is_veg FROM products WHERE is_active = 1";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				ProductDetailDTO product = new ProductDetailDTO();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setCategoryId(rs.getInt("category_id"));
				product.setVeg(rs.getBoolean("is_veg"));
				setOfUser.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return setOfUser;
	}

	/**
	 * Retrieves an active product from the database by its ID along with its
	 * details.
	 *
	 * This method queries the database to retrieve an active product stored in the
	 * 'products' table that matches the provided ID and is marked as active (based
	 * on 'is_active' status). If a matching active product is found, a 'Product'
	 * object is created, populated with the retrieved data, and returned. If no
	 * matching active product is found, the method returns null. If any
	 * database-related exception occurs during the process, it is caught, and a
	 * RuntimeException is thrown.
	 *
	 * @param id The ID of the active product to be retrieved.
	 * @return A 'Product' object containing the data of the retrieved active
	 *         product. Returns null if no matching active product is found.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public ProductDetailDTO findById(int id) throws PersistanceException {
		Connection conn = null;
		PreparedStatement ps = null;
		ProductDetailDTO product = null;
		ResultSet rs = null;

		try {
			String query = "SELECT id, product_name, description, category_id, is_veg FROM products WHERE is_active = 1 AND id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				product = new ProductDetailDTO();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setCategoryId(rs.getInt("category_id"));
				product.setVeg(rs.getBoolean("is_veg"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return product;
	}

	/**
	 * Retrieves a list of active products from the database based on a specific
	 * category ID along with their details.
	 *
	 * This method queries the database to retrieve all active products stored in
	 * the 'products' table that belong to the specified category ID and are marked
	 * as active (based on 'is_active' status). The retrieved product data is used
	 * to create a list of 'Product' objects, which is then returned. If no matching
	 * active products are found for the given category ID, an empty list is
	 * returned. If any database-related exception occurs during the process, it is
	 * caught, and a RuntimeException is thrown.
	 *
	 * @param categoryId The ID of the category for which to retrieve active
	 *                   products.
	 * @return A list of 'Product' objects containing the data of the retrieved
	 *         active products for the specified category. Returns an empty list if
	 *         no matching active products are found.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public List<ProductDetailDTO> findByCategoryId(int categoryId) throws PersistanceException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<ProductDetailDTO> productList = new ArrayList<>();
		ResultSet rs = null;

		try {
			String query = "SELECT id, product_name, description, category_id, is_veg FROM products WHERE is_active = 1 AND category_id = ?";
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, categoryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProductDetailDTO product = new ProductDetailDTO();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setCategoryId(rs.getInt("category_id"));
				product.setVeg(rs.getBoolean("is_veg"));
				product.setActive(rs.getBoolean("is_active"));
				productList.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, ps, rs);
		}
		return productList;
	}

	/**
	 * Creates a new product in the database with the provided details.
	 *
	 * This method inserts a new product into the 'products' table in the database
	 * using the information provided in the 'productDetailDto' parameter. If the
	 * insertion is successful, a new product record is created, and the generated
	 * product ID is returned. If any exception occurs during the process, it is
	 * caught, and a RuntimeException is thrown.
	 *
	 * @param productDetailDto An instance of 'ProductDetailDTO' containing the
	 *                         details of the new product to be created. It should
	 *                         include the product name, description, category ID,
	 *                         and boolean values indicating whether the product is
	 *                         vegetarian and active.
	 * @return The ID of the newly created product. Returns -1 if an error occurs
	 *         during the database insertion.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs during the database insertion
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public int create(ProductDetailDTO productDetailDto) throws PersistanceException {
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

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, stmt);
		}
		return productId;
	}
	
	/**
	 * Checks if a product with the specified product ID exists and is active.
	 *
	 * This method queries the database to check if a product with the given
	 * 'productId' exists and is marked as active.
	 * 
	 * If a product matching the ID and active status is found, the method returns
	 * 'true', indicating that the product exists. If no such product is found, the
	 * method returns 'false'.
	 * 
	 * @param productId The ID of the product to check.
	 * @return 'true' if a product with the specified ID exists and is active,
	 *         'false' otherwise.
	 * @throws PersistanceException 
	 * @throws RuntimeException If an error occurs while querying the database.
	 */
	public static boolean productExists(int productId) throws PersistanceException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT 1 FROM products WHERE id = ? AND is_active = ?";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, productId);
			stmt.setInt(2, 1);

			rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}

			return false; // No rows returned, product does not exist
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new PersistanceException(e.getMessage());
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

}
