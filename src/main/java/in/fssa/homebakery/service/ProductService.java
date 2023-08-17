package in.fssa.homebakery.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.ProductDAO;
import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.validator.ProductValidator;

public class ProductService {

	public void create(ProductDetailDTO newProduct) throws Exception {

		ProductDAO productDao = new ProductDAO();
		ProductPriceDAO productPriceDao = new ProductPriceDAO();

		ProductValidator.validate(newProduct);
		ProductValidator.validatePriceList(newProduct.getPrices());

		int id = productDao.create(newProduct);

		for (ProductPrice newPrice : newProduct.getPrices()) {
			productPriceDao.create(newPrice, id);
		}

	}
	
	public void delete(int id) {
		ProductDAO productDao = new ProductDAO();
		
		boolean test = productExists(id);
		
		if(!test) {
			throw new RuntimeException("Product does not exist");
		}
		
		productDao.delete(id);
	}
	
	public Set<ProductDetailDTO> getAll() {
		ProductDAO productDao = new ProductDAO();
		ProductPriceDAO priceDao = new ProductPriceDAO();
		
		Set<ProductDetailDTO> productList = productDao.findAll();
		for (ProductDetailDTO product : productList) {
			List<ProductPrice> prices = priceDao.findByProductId(product.getId());
			product.setPrices(prices);
			System.out.println(product);
		}
		return productList;
	}
	
	
	
	public boolean productExists(int productId) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT COUNT(*) FROM products WHERE id = ? AND is_active = ?";
	        conn = ConnectionUtil.getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, productId);
	        stmt.setInt(2, 1);

	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0; // If count > 0, product exists
	        }

	        return false; // No rows returned, product does not exist
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally {
	        ConnectionUtil.close(conn, stmt, rs);
	    }
	}



}
