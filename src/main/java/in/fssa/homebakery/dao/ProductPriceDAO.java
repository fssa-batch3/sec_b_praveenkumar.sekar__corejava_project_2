package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.model.ProductPriceEntity.QuantityType;
import in.fssa.homebakery.util.ConnectionUtil;

public class ProductPriceDAO {

	/**
	 * Creates a new product price entry in the database.
	 *
	 * This method inserts a new product price entry into the 'product_prices' table
	 * in the database. The product price information is obtained from the
	 * 'newPrice' parameter, and the associated product is identified by
	 * 'productId'. If the insertion is successful, a new product price record is
	 * created. If any exception occurs during the process, it is caught, and a
	 * RuntimeException is thrown.
	 *
	 * @param newPrice  An instance of 'ProductPrice' containing the new price
	 *                  details to be created. It should include the price,
	 *                  quantity, type, and start date.
	 * @param productId The ID of the product for which the new price entry is being
	 *                  created.
	 * @throws RuntimeException If an error occurs during the database insertion
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public void create(ProductPrice newPrice, int productId) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String query = "INSERT INTO product_prices (product_id, price, quantity, type, start_date) VALUES (?, ?, ?, ?, ?)";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, productId);
			stmt.setDouble(2, newPrice.getPrice());
			stmt.setInt(3, newPrice.getQuantity());
			stmt.setString(4, newPrice.getType().toString());
			stmt.setTimestamp(5, newPrice.getStartDate());
			stmt.executeUpdate();

			System.out.println("Product prices have been successfully created");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt);
		}
	}

	/**
	 * Creates a new product price entry with updated quantity in the database.
	 *
	 * This method inserts a new product price entry into the 'product_prices' table
	 * in the database. The product price information is obtained from the
	 * 'productPrice' parameter, and the associated product is identified by 'id'.
	 * The updated quantity is provided as 'quantity'. The current timestamp is used
	 * as the start date for the new price entry. If the insertion is successful, a
	 * new product price record is created with the updated quantity. If any
	 * exception occurs during the process, it is caught, and a RuntimeException is
	 * thrown.
	 *
	 * @param id           The ID of the product for which the new price entry is
	 *                     being created.
	 * @param productPrice An instance of 'ProductPrice' containing the updated
	 *                     price details to be created. It should include the price,
	 *                     type, and start date.
	 * @param quantity     The updated quantity for the product price entry.
	 * @throws RuntimeException If an error occurs during the database insertion
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public void update(int id, ProductPrice productPrice, double quantity) {
		Connection conn = null;
		PreparedStatement stmt = null;
		Timestamp time = new Timestamp(System.currentTimeMillis());

		try {
			String query = "INSERT INTO product_prices (product_id, price, quantity, type, start_date) VALUES (?, ?, ?, ?, ?)";

			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setDouble(2, productPrice.getPrice());
			stmt.setDouble(3, quantity);
			stmt.setString(4, productPrice.getType().toString());
			stmt.setTimestamp(5, time);
			stmt.executeUpdate();

			System.out.println("A new row has been inserted into product_prices");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, stmt);
		}
	}

	/**
	 * Sets the end date for a specific product price entry in the database.
	 *
	 * This method updates the 'end_date' field of a specific product price entry in
	 * the 'product_prices' table to the current timestamp, indicating the end of
	 * the pricing period for a particular product and quantity. The product price
	 * entry is identified by the provided 'productId' and 'quantity'. If the update
	 * is successful, the 'end_date' field is set, and a success message is printed.
	 * If no matching rows are found for the given product and quantity, or if the
	 * 'end_date' is already set, an appropriate message is printed. If any
	 * exception occurs during the process, it is caught, and a RuntimeException is
	 * thrown.
	 *
	 * @param productId The ID of the product for which the end date of the price
	 *                  entry is being set.
	 * @param quantity  The quantity associated with the product price entry.
	 * @throws RuntimeException If an error occurs during the database update
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public void setEndDate(int productId, double quantity) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			String query = "UPDATE product_prices SET end_date = ? WHERE product_id = ? AND quantity = ? AND end_date IS NULL";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);

			// Set the parameters for the prepared statement
			stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setInt(2, productId);
			stmt.setDouble(3, quantity);

			// Execute the update
			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("End date has been set for productId: " + productId + ", quantity: " + quantity);
			} else {
				System.out.println("No matching rows found with productId: " + productId + ", quantity: " + quantity
						+ " or end_date is already set.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt);
		}
	}

	/**
	 * Retrieves all product price entries from the database.
	 *
	 * This method queries the database to retrieve all product price entries stored
	 * in the 'product_prices' table. For each product price entry, a 'ProductPrice'
	 * object is created, populated with the retrieved data, and added to a 'Set'.
	 * The set is then returned, containing details of all product price entries. If
	 * any exception occurs during the process, it is caught, and a RuntimeException
	 * is thrown.
	 *
	 * @return A 'Set' containing 'ProductPrice' objects representing details of all
	 *         product price entries.
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public Set<ProductPrice> findAll() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Set<ProductPrice> productPrices = new HashSet<>();

		try {
			String query = "SELECT * FROM product_prices";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductPrice productPrice = new ProductPrice();
				productPrice.setId(rs.getInt("id"));
				productPrice.setProductId(rs.getInt("product_id"));
				productPrice.setPrice(rs.getInt("price"));
				productPrice.setQuantity(rs.getInt("quantity"));
				productPrice.setType(QuantityType.valueOf(rs.getString("type")));
				productPrice.setStartDate(rs.getTimestamp("start_date"));
				productPrice.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date") : null);

				productPrices.add(productPrice);
			}

			return productPrices;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * Retrieves all product price entries associated with a specific product ID
	 * from the database.
	 *
	 * This method queries the database to retrieve all product price entries stored
	 * in the 'product_prices' table that are associated with the provided product
	 * ID. The retrieved entries are ordered by start date in descending order. For
	 * each matching product price entry, a 'ProductPrice' object is created,
	 * populated with the retrieved data, and added to a 'List'. The list is then
	 * returned, containing details of product price entries for the given product
	 * ID. If any exception occurs during the process, it is caught, and a
	 * RuntimeException is thrown.
	 *
	 * @param id The ID of the product for which the associated product price
	 *           entries are being retrieved.
	 * @return A 'List' containing 'ProductPrice' objects representing details of
	 *         product price entries associated with the given product ID.
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public List<ProductPrice> findByProductId(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM product_prices WHERE product_id = ? AND end_date IS null";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			List<ProductPrice> productPrices = new ArrayList<>();
			while (rs.next()) {
				ProductPrice productPrice = new ProductPrice();
				productPrice.setId(rs.getInt("id"));
				productPrice.setProductId(rs.getInt("product_id"));
				productPrice.setPrice(rs.getInt("price"));
				productPrice.setQuantity(rs.getInt("quantity"));
				productPrice.setType(QuantityType.valueOf(rs.getString("type").toUpperCase()));
				productPrice.setStartDate(rs.getTimestamp("start_date"));
				productPrice.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date") : null);

				productPrices.add(productPrice);
			}

			return productPrices;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * Retrieves a specific product price entry from the database by its ID.
	 *
	 * This method queries the database to retrieve a specific product price entry
	 * stored in the 'product_prices' table that matches the provided ID. If a
	 * matching product price entry is found, a 'ProductPrice' object is created,
	 * populated with the retrieved data, and returned. If no matching product price
	 * entry is found, the method returns null. If any exception occurs during the
	 * process, it is caught, and a RuntimeException is thrown.
	 *
	 * @param id The ID of the product price entry to be retrieved.
	 * @return A 'ProductPrice' object containing the data of the retrieved product
	 *         price entry. Returns null if no matching product price entry is
	 *         found.
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public ProductPrice findById(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductPrice productPrice = null;

		try {
			String query = "SELECT * FROM product_prices WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if (rs.next()) {
				productPrice = new ProductPrice();
				productPrice.setId(rs.getInt("id"));
				productPrice.setProductId(rs.getInt("product_id"));
				productPrice.setPrice(rs.getInt("price"));
				productPrice.setQuantity(rs.getInt("quantity"));
				productPrice.setType(QuantityType.valueOf(rs.getString("type").toUpperCase()));
				productPrice.setStartDate(rs.getTimestamp("start_date"));
				productPrice.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date") : null);
			}

			return productPrice;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

	/**
	 * Retrieves the current product price entries associated with a specific
	 * product ID from the database.
	 *
	 * This method queries the database to retrieve the current product price
	 * entries stored in the 'product_prices' table that are associated with the
	 * provided product ID and have no end date set (i.e., the pricing period is
	 * ongoing). For each matching current product price entry, a 'ProductPrice'
	 * object is created, populated with the retrieved data, and added to a 'List'.
	 * The list is then returned, containing details of current product price
	 * entries for the given product ID. If any exception occurs during the process,
	 * it is caught, and a RuntimeException is thrown.
	 *
	 * @param productId The ID of the product for which the associated current
	 *                  product price entries are being retrieved.
	 * @return A 'List' containing 'ProductPrice' objects representing details of
	 *         current product price entries associated with the given product ID.
	 * @throws RuntimeException If an error occurs during the database retrieval
	 *                          process. The original exception is printed, and a
	 *                          RuntimeException is thrown.
	 */
	public List<ProductPrice> findCurrentPrice(int productId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT * FROM product_prices WHERE product_id = ? AND end_date IS NULL";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, productId);

			rs = stmt.executeQuery();

			List<ProductPrice> currentPrices = new ArrayList<>();

			while (rs.next()) {
				ProductPrice productPrice = new ProductPrice();
				productPrice.setId(rs.getInt("id"));
				productPrice.setProductId(rs.getInt("product_id"));
				productPrice.setPrice(rs.getInt("price"));
				productPrice.setQuantity(rs.getInt("quantity"));
				productPrice.setType(QuantityType.valueOf(rs.getString("type").toUpperCase()));
				productPrice.setStartDate(rs.getTimestamp("start_date"));
				productPrice.setEndDate(rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date") : null);

				currentPrices.add(productPrice);
			}

			return currentPrices;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}
}
