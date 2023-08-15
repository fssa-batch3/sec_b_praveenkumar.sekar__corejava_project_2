package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.interface_files.ProductPricesInterface;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;

public class ProductPriceDAO implements ProductPricesInterface {
	
	@Override
	public void create(ProductPrice newPrice, int productId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			String query = "INSERT INTO product_prices (product_id, price, quantity, type, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, productId);
				stmt.setDouble(2, newPrice.getPrice());
				stmt.setInt(3, newPrice.getQuantity());
				stmt.setString(4, newPrice.getType().toString());
				stmt.setDate(5, java.sql.Date.valueOf(newPrice.getStartDate()));
				stmt.setNull(6, Types.DATE);
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

	@Override
	public void update(int id, ProductPrice newT) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<ProductPrice> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductPrice findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ProductPrice newT) {
		// TODO Auto-generated method stub
	}
}
