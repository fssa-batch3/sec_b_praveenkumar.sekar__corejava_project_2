package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.model.ProductPriceEntity.QuantityType;
import in.fssa.homebakery.util.ConnectionUtil;

public class ProductPriceDAO {

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

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

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

	public List<ProductPrice> findByProductId(int id) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT * FROM product_prices WHERE product_id = ? ORDER BY start_date DESC";
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
