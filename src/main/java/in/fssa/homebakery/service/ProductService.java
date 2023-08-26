package in.fssa.homebakery.service;

import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.CategoryDAO;
import in.fssa.homebakery.dao.ProductDAO;
import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.exception.ServiceException;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.model.ProductPrice;
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
	public void createProduct(ProductDetailDTO newProduct) throws ValidationException , ServiceException{


		try {
			ProductDAO productDAO = new ProductDAO();
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			ProductValidator.validate(newProduct);
			ProductValidator.validatePriceList(newProduct.getPrices());
			int id = productDAO.create(newProduct);
			
			for (ProductPrice newPrice : newProduct.getPrices()) {
				productPriceDAO.create(newPrice, id);
			}
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
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
	public void deleteProduct(int id) throws ServiceException, ValidationException {
		ProductDAO productDAO = new ProductDAO();
		
		try {
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			IntUtil.rejectIfInvalidInt(id);
			boolean test = ProductDAO.productExists(id);
			
			if (!test) {
				throw new RuntimeException("Product does not exist");
			}
			
			productDAO.delete(id);
			productPriceDAO.delete(id);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

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
	public Set<ProductDetailDTO> getAllProducts()throws ValidationException, ServiceException {

		Set<ProductDetailDTO> productList;
		try {
			ProductDAO productDAO = new ProductDAO();
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			productList = productDAO.findAll();
			for (ProductDetailDTO product : productList) {
				List<ProductPrice> prices = productPriceDAO.findByProductId(product.getId());
				product.setPrices(prices);
				System.out.println(product);
			}
			return productList;
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
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
	public ProductDetailDTO getByProductId(int id) throws ValidationException, ServiceException {
	    
	    try {
	    	ProductDAO productDAO = new ProductDAO();
	    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			IntUtil.rejectIfInvalidInt(id);
			boolean test = ProductDAO.productExists(id);
			
			if (!test) {
				throw new RuntimeException("Product does not exist");
			}
			
			ProductDetailDTO product = productDAO.findById(id);
			if (product != null) {
				List<ProductPrice> prices = productPriceDAO.findByProductId(product.getId());
				product.setPrices(prices);
			}
			return product;
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	    
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
	public List<ProductDetailDTO> getByCategoryId(int categoryId) throws ValidationException, ServiceException {
	    
	    try {
	    	ProductDAO productDAO = new ProductDAO();
	    	ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			CategoryValidator.validateId(categoryId);
			boolean test = CategoryDAO.categoryExists(categoryId);
			
			if (!test) {
				throw new RuntimeException("Category does not exist");
			}
			
			List<ProductDetailDTO> productList = productDAO.findByCategoryId(categoryId);
			for (ProductDetailDTO product : productList) {
				List<ProductPrice> prices = productPriceDAO.findByProductId(product.getId());
				product.setPrices(prices);
			}
			return productList;
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	    
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
	 * @throws ValidationException, ServiceException 
	 * @throws RuntimeException If the specified product does not exist or if an
	 *                          error occurs during the update process.
	 * @throws Exception        If there's an issue with the provided 'id' or
	 *                          'newProduct'.
	 */
	public void updateProduct(int id, Product newProduct) throws ValidationException, ServiceException {
		try {
			IntUtil.rejectIfInvalidInt(id);
			boolean test = ProductDAO.productExists(id);
			
			if (!test) {
				throw new RuntimeException("Product does not exist");
			}
			
			ProductValidator.validate(newProduct);
			
			ProductDAO productDAO = new ProductDAO();
			productDAO.update(id, newProduct);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

}
