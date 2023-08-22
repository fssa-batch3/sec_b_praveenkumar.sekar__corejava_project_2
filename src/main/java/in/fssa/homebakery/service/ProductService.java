package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.ProductDAO;
import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.CategoryValidator;
import in.fssa.homebakery.validator.ProductValidator;

public class ProductService {

	/**
	 * Creates a new product along with its associated product prices.
	 *
	 * This method first validates the provided 'newProduct' using the
	 * 'ProductValidator.validate' method and ensures that it meets the required
	 * criteria. It also validates the list of product prices within the
	 * 'newProduct' using 'ProductValidator.validatePriceList'.
	 * 
	 * The method then uses a 'ProductDAO' instance to create the main product entry
	 * in the database by invoking the 'create' method, which returns the ID of the
	 * newly created product.
	 * 
	 * After creating the main product entry, the method iterates through the list
	 * of product prices within 'newProduct'. For each 'ProductPrice' in the list,
	 * it uses a 'ProductPriceDAO' instance to create the associated product price
	 * entry in the database using the 'create' method, passing the newly created
	 * product's ID.
	 * 
	 * @param newProduct A 'ProductDetailDTO' object containing the details of the
	 *                   new product, including product information and a list of
	 *                   product prices.
	 * @throws Exception If the provided 'newProduct' is not valid or if an error
	 *                   occurs during the database insertion process. The specific
	 *                   exception type and message depend on the underlying
	 *                   validation and insertion logic.
	 */
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

	/**
	 * Deletes a product by its ID.
	 *
	 * This method first checks if a product with the specified 'id' exists using
	 * the 'productExists' method. If the product does not exist, a
	 * 'RuntimeException' is thrown with an appropriate error message.
	 * 
	 * If the product exists, a 'ProductDAO' instance is created, and the 'delete'
	 * method is invoked on it to delete the product entry.
	 * 
	 * @param id The ID of the product to be deleted.
	 * @throws ValidationException 
	 * @throws PersistanceException 
	 * @throws RuntimeException If the specified product does not exist or if an
	 *                          error occurs during the deletion process.
	 */
	public void delete(int id) throws ValidationException, PersistanceException {
		ProductDAO productDao = new ProductDAO();
		ProductPriceDAO productPriceDao = new ProductPriceDAO();
		
		IntUtil.rejectIfInvalidInt(id);

		boolean test = productExists(id);

		if (!test) {
			throw new RuntimeException("Product does not exist");
		}

		productDao.delete(id);
		productPriceDao.delete(id);
	}

	/**
	 * Retrieves a set of all products along with their associated product prices.
	 *
	 * This method uses a 'ProductDAO' instance to retrieve a set of all products
	 * from the database by invoking the 'findAll' method. For each retrieved
	 * 'ProductDetailDTO' in the set, the method uses a 'ProductPriceDAO' instance
	 * to retrieve the associated product prices using the 'findByProductId' method,
	 * and then sets the retrieved prices using 'setPrices' on the product.
	 * 
	 * The method prints each product along with its associated prices to the
	 * console and returns the set of retrieved products.
	 *
	 * @return A set containing 'ProductDetailDTO' objects, each representing a
	 *         product along with its associated product prices.
	 * @throws PersistanceException 
	 */
	public Set<ProductDetailDTO> getAll() throws PersistanceException {
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
	
	/**
	 * Retrieves a product by its ID along with its associated product prices.
	 *
	 * This method uses a 'ProductDAO' instance to retrieve a product from the database by invoking the 'findById' method.
	 * It then uses a 'ProductPriceDAO' instance to retrieve the associated product prices using the 'findByProductId'
	 * method, and sets the retrieved prices using 'setPrices' on the product.
	 *
	 * If a matching product is found for the provided ID, the method prints the product along with its associated prices to
	 * the console and returns the retrieved product. If no matching product is found, the method returns null.
	 *
	 * @param id The ID of the product to be retrieved.
	 * @return A 'ProductDetailDTO' object representing the retrieved product along with its associated product prices.
	 *         Returns null if no matching product is found for the provided ID.
	 * @throws ValidationException 
	 * @throws PersistanceException 
	 */
	public ProductDetailDTO getById(int id) throws ValidationException, PersistanceException {
	    ProductDAO productDao = new ProductDAO();
	    ProductPriceDAO priceDao = new ProductPriceDAO();
	    
	    IntUtil.rejectIfInvalidInt(id);
	    
	    boolean test = productExists(id);

		if (!test) {
			throw new RuntimeException("Product does not exist");
		}

	    ProductDetailDTO product = productDao.findById(id);
	    if (product != null) {
	        List<ProductPrice> prices = priceDao.findByProductId(product.getId());
	        product.setPrices(prices);
	    }
	    return product;
	}
	
	/**
	 * Retrieves a set of products by their category ID along with their associated product prices.
	 *
	 * This method uses a 'ProductDAO' instance to retrieve a set of products from the database by invoking the 'findByCategoryId' method.
	 * For each retrieved 'ProductDetailDTO' in the set, the method uses a 'ProductPriceDAO' instance to retrieve the associated product
	 * prices using the 'findByProductId' method, and then sets the retrieved prices using 'setPrices' on each product.
	 *
	 * The method prints each product along with its associated prices to the console and returns the set of retrieved products.
	 *
	 * @param categoryId The category ID for which products are to be retrieved.
	 * @return A set containing 'ProductDetailDTO' objects, each representing a product of the specified category along with its
	 *         associated product prices.
	 * @throws ValidationException 
	 * @throws PersistanceException 
	 */
	public List<ProductDetailDTO> getByCategoryId(int categoryId) throws ValidationException, PersistanceException {
	    ProductDAO productDao = new ProductDAO();
	    ProductPriceDAO priceDao = new ProductPriceDAO();
	    
	    CategoryValidator.validateId(categoryId);
	    
	    CategoryService catService = new CategoryService();
	    boolean test = catService.categoryExists(categoryId);
	    
	    if (!test) {
			throw new RuntimeException("Category does not exist");
		}
	    
	    List<ProductDetailDTO> productList = productDao.findByCategoryId(categoryId);
	    for (ProductDetailDTO product : productList) {
	        List<ProductPrice> prices = priceDao.findByProductId(product.getId());
	        product.setPrices(prices);
	    }
	    return productList;
	}
	

	/**
	 * Updates the information of a product identified by its ID.
	 *
	 * This method first checks if a product with the specified 'id' exists using
	 * the 'productExists' method. If the product does not exist, a
	 * 'RuntimeException' is thrown with an appropriate error message.
	 * 
	 * If the product exists, the 'IntUtil.rejectIfInvalidInt' method is used to
	 * validate the 'id'.
	 * 
	 * The 'ProductValidator.validate' method is invoked to validate the attributes
	 * of the 'newProduct' parameter.
	 * 
	 * After successful validation, a 'ProductDAO' instance is created, and the
	 * 'update' method is called to update the product information.
	 * 
	 * @param id         The ID of the product to be updated.
	 * @param newProduct The updated information for the product.
	 * @throws RuntimeException If the specified product does not exist or if an
	 *                          error occurs during the update process.
	 * @throws Exception        If there's an issue with the provided 'id' or
	 *                          'newProduct'.
	 */
	public void update(int id, Product newProduct) throws Exception {
		IntUtil.rejectIfInvalidInt(id);

		boolean test = productExists(id);

		if (!test) {
			throw new RuntimeException("Product does not exist");
		}

		ProductValidator.validate(newProduct);

		ProductDAO productDAO = new ProductDAO();
		productDAO.update(id, newProduct);
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
	 * @throws RuntimeException If an error occurs while querying the database.
	 */
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
				return count > 0;
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
