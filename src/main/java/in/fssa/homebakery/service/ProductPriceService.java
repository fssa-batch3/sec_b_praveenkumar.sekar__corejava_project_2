package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;

public class ProductPriceService {

    public void update(int id, ProductPrice productPrice, double quantity, Timestamp time) {
    	ProductService productService = new ProductService();
    	boolean test = productService.productExists(id);
    	
    	if(!test) {
    		throw new RuntimeException("Product does not exist");
    	}
    	
    	boolean check = quantityExistsForProduct(id, quantity);
    	
    	if(!check) {
    		throw new RuntimeException("Quantity does not exist");
    	}
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        productPriceDAO.update(id, productPrice, quantity, time);
    }

    public Set<ProductPrice> findAll() {
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        return productPriceDAO.findAll();
    }

    public List<ProductPrice> findByProductId(int id) {
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        return productPriceDAO.findByProductId(id);
    }

    public ProductPrice findById(int id) {
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        return productPriceDAO.findById(id);
    }

    public List<ProductPrice> findCurrentPrice(int productId) {
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        return productPriceDAO.findCurrentPrice(productId);
    }
	
	public boolean quantityExistsForProduct(int productId, double quantity) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT COUNT(*) FROM products WHERE id = ? AND quantity = ?";
	        conn = ConnectionUtil.getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, productId);
	        stmt.setDouble(2, quantity);

	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0; // If count > 0, product with quantity for the specified product ID exists
	        }

	        return false; // No rows returned, product with quantity for the specified product ID does not exist
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally {
	        ConnectionUtil.close(conn, stmt, rs);
	    }
	}
	
}
