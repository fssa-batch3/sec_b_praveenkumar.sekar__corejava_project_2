package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.PriceValidator;

public class ProductPriceService {

    public void update(int id, ProductPrice productPrice, double quantity) throws Exception {
    	ProductService productService = new ProductService();
    	
    	IntUtil.rejectIfInvalidInt(id);
    	PriceValidator.validate(productPrice);
    	PriceValidator.validateQuantity(quantity);
    	
    	boolean test = productService.productExists(id);
    	
    	if(!test) {
    		throw new RuntimeException("Product does not exist");
    	}
    	
    	boolean check = quantityExistsForProduct(id, quantity);
    	
    	if(!check) {
    		throw new RuntimeException("Quantity does not exist");
    	}
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        productPriceDAO.update(id, productPrice, quantity);
    }

    public Set<ProductPrice> findAll() {
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
    	Set<ProductPrice> priceList = productPriceDAO.findAll();
		for (ProductPrice price : priceList) {
			System.out.println(price);
		}
		return priceList;
    }

    public List<ProductPrice> findByProductId(int id) throws Exception {
    	IntUtil.rejectIfInvalidInt(id);
    	ProductService productService = new ProductService();
    	boolean test = productService.productExists(id);
    	
    	if(!test) {
    		throw new RuntimeException("Product does not exist");
    	}
    	
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        
    	List<ProductPrice> priceList = productPriceDAO.findByProductId(id);
		for (ProductPrice price : priceList) {
			System.out.println(price);
		}
		return priceList;
    }

    public ProductPrice findById(int id) throws Exception {
    	IntUtil.rejectIfInvalidInt(id);
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        return productPriceDAO.findById(id);
    }

    public List<ProductPrice> findCurrentPrice(int productId) throws Exception {
    	IntUtil.rejectIfInvalidInt(productId);
    	ProductService productService = new ProductService();
    	boolean test = productService.productExists(productId);
    	
    	if(!test) {
    		throw new RuntimeException("Product does not exist");
    	}
    	
    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
        
    	List<ProductPrice> priceList = productPriceDAO.findCurrentPrice(productId);
		for (ProductPrice price : priceList) {
			System.out.println(price);
		}
		return priceList;
    }
	
	public boolean quantityExistsForProduct(int productId, double quantity) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT * FROM product_prices WHERE id = ? AND quantity = ?";
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
