package in.fssa.homebakery.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.ProductDAO;
import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.exception.ServiceException;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.PriceValidator;

public class ProductPriceService {
	
	
	/**
	 * Creates a new product price for a specific product.
	 *
	 * This method creates a new product price entry in the database for a given product. The product is identified by its
	 * unique identifier, and the new price details are provided as a `ProductPrice` object. The method performs validation
	 * on the input parameters and ensures that the provided product exists before creating the price entry.
	 *
	 * @param productId The unique identifier of the product for which the price is being created.
	 * @param productPrice The `ProductPrice` object containing the details of the new price.
	 * @throws ValidationException If the input parameters are not valid or the price details fail validation checks.
	 * @throws ServiceException If there is an error while creating the product price entry.
	 * @throws RuntimeException If the specified product does not exist.
	 */
	public void createProductPrice(int productId, ProductPrice productPrice)
			throws ValidationException, ServiceException {

		try {
			IntUtil.rejectIfInvalidInt(productId);
			PriceValidator.validate(productPrice);

			boolean test = ProductDAO.productExists(productId);
			if (!test) {
				throw new RuntimeException("Product does not exist");
			}
			
			boolean check = ProductPriceDAO.quantityExistsForProduct(productId, productPrice.getQuantity());

			if (check) {
				throw new RuntimeException("Quantity already exist for product");
			}
			
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			
			productPriceDAO.create(productPrice,productId);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Updates a product's price and sets an end date for the previous price entry.
	 *
	 * This method validates the provided 'id', 'productPrice', and 'quantity' using
	 * appropriate validation methods. It ensures that 'id' is a valid integer,
	 * 'productPrice' is valid using 'PriceValidator.validate', and 'quantity' is
	 * valid using 'PriceValidator.validateQuantity'. It then checks if the product
	 * with the provided 'id' exists using the 'productExists' method from the
	 * 'ProductService'.
	 *
	 * If the product does not exist, a RuntimeException is thrown with the message
	 * "Product does not exist". If the product exists, the method checks if the
	 * provided 'quantity' exists for the product using 'quantityExistsForProduct'.
	 * If the quantity does not exist, a RuntimeException is thrown with the message
	 * "Quantity does not exist".
	 *
	 * The method then proceeds to set an end date for the previous price entry
	 * using 'setEndDate' in 'ProductPriceDAO', and subsequently updates the
	 * product's price and quantity using 'update' in 'ProductPriceDAO'.
	 *
	 * @param id           The ID of the product for which the price and quantity
	 *                     are being updated.
	 * @param productPrice An instance of 'ProductPrice' containing the updated
	 *                     price information.
	 * @param quantity     The updated quantity for the product.
	 * @throws Exception If any of the provided parameters are not valid, if the
	 *                   product does not exist, or if an error occurs during the
	 *                   database update process. The specific exception type and
	 *                   message depend on the underlying validation and update
	 *                   logic.
	 */
	public void updateProductPrice(int productId, ProductPrice productPrice, double quantity)
			throws ValidationException, ServiceException {

		try {
			IntUtil.rejectIfInvalidInt(productId);
			PriceValidator.validate(productPrice);
			PriceValidator.validateQuantity(quantity);

			boolean test = ProductDAO.productExists(productId);
			if (!test) {
				throw new RuntimeException("Product does not exist");
			}

			boolean check = ProductPriceDAO.quantityExistsForProduct(productId, quantity);

			if (!check) {
				throw new RuntimeException("Quantity does not exist");
			}
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();

			productPriceDAO.setEndDate(productId, quantity);
			productPriceDAO.update(productId, productPrice, quantity);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void deleteProductPrice(int productPriceId) throws ValidationException, ServiceException {
		
		try {
			IntUtil.rejectIfInvalidInt(productPriceId);
			
			boolean check = ProductPriceDAO.priceExists(productPriceId);
			
			if(!check) {
				throw new RuntimeException("Price does not exist");
			}
			
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			
			productPriceDAO.delete(productPriceId);
			
			
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	/**
	 * Retrieves all product prices from the database.
	 *
	 * This method uses a 'ProductPriceDAO' instance to retrieve all product price
	 * entries stored in the database. The retrieved product prices are returned as
	 * a 'Set' of 'ProductPrice' objects. Additionally, for each retrieved
	 * 'ProductPrice', the method prints its details using 'System.out.println' for
	 * debugging purposes.
	 *
	 * @return A 'Set' containing 'ProductPrice' objects representing the details of
	 *         all product prices.
	 * @throws PersistanceException
	 */
	public Set<ProductPrice> findAllProductPrices() throws ServiceException {
		ProductPriceDAO productPriceDAO = new ProductPriceDAO();
		Set<ProductPrice> priceList = new HashSet<>();
		try {
			priceList = productPriceDAO.findAll();
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return priceList;
	}

	/**
	 * Retrieves a list of product prices associated with a specific product ID.
	 *
	 * This method validates the provided 'id' using 'IntUtil.rejectIfInvalidInt'.
	 * It ensures that 'id' is a valid integer. It then checks if the product with
	 * the provided 'id' exists using the 'productExists' method from the
	 * 'ProductService'.
	 *
	 * If the product does not exist, a RuntimeException is thrown with the message
	 * "Product does not exist". If the product exists, the method uses a
	 * 'ProductPriceDAO' instance to retrieve a list of product prices associated
	 * with the provided 'id'. The retrieved product prices are returned as a 'List'
	 * of 'ProductPrice' objects. Additionally, for each retrieved 'ProductPrice',
	 * the method prints its details using 'System.out.println' for debugging
	 * purposes.
	 *
	 * @param id The ID of the product for which to retrieve associated product
	 *           prices.
	 * @return A 'List' containing 'ProductPrice' objects representing the details
	 *         of product prices associated with the specified product.
	 * @throws ValidationException , ServiceException
	 * @throws Exception           If the provided 'id' is not valid, if the product
	 *                             does not exist, or if an error occurs during the
	 *                             database retrieval process. The specific
	 *                             exception type and message depend on the
	 *                             underlying validation and retrieval logic.
	 */
	public List<ProductPrice> findByProductId(int id) throws ValidationException, ServiceException {
		List<ProductPrice> priceList = new ArrayList<>();
		try {
			IntUtil.rejectIfInvalidInt(id);
			boolean test = ProductDAO.productExists(id);

			if (!test) {
				throw new RuntimeException("Product does not exist");
			}

			ProductPriceDAO productPriceDAO = new ProductPriceDAO();

			priceList = productPriceDAO.findByProductId(id);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return priceList;
	}

	/**
	 * Retrieves a specific product price entry by its ID.
	 *
	 * This method validates the provided 'id' using 'IntUtil.rejectIfInvalidInt'.
	 * It ensures that 'id' is a valid integer. It then uses a 'ProductPriceDAO'
	 * instance to retrieve a product price entry associated with the provided 'id'.
	 * The retrieved product price entry is returned as a 'ProductPrice' object.
	 *
	 * @param id The ID of the product price entry to retrieve.
	 * @return A 'ProductPrice' object representing the details of the retrieved
	 *         product price entry.
	 * @throws ValidationException
	 * @throws Exception           If the provided 'id' is not valid or if an error
	 *                             occurs during the database retrieval process. The
	 *                             specific exception type and message depend on the
	 *                             underlying validation and retrieval logic.
	 */
	public ProductPrice findByPriceId(int id) throws ValidationException, ServiceException {
		try {
			IntUtil.rejectIfInvalidInt(id);
			ProductPriceDAO productPriceDAO = new ProductPriceDAO();
			return productPriceDAO.findById(id);
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Retrieves the current product prices for a specific product.
	 *
	 * This method validates the provided 'productId' using
	 * 'IntUtil.rejectIfInvalidInt'. It ensures that 'productId' is a valid integer.
	 * It then checks if the product with the provided 'productId' exists using the
	 * 'productExists' method from the 'ProductService'.
	 *
	 * If the product does not exist, a RuntimeException is thrown with the message
	 * "Product does not exist". If the product exists, the method uses a
	 * 'ProductPriceDAO' instance to retrieve the current product prices associated
	 * with the provided 'productId'. The retrieved current product prices are
	 * returned as a 'List' of 'ProductPrice' objects. Additionally, for each
	 * retrieved 'ProductPrice', the method prints its details using
	 * 'System.out.println' for debugging purposes.
	 *
	 * @param productId The ID of the product for which to retrieve the current
	 *                  product prices.
	 * @return A 'List' containing 'ProductPrice' objects representing the current
	 *         product prices for the specified product.
	 * @throws ValidationException
	 * @throws Exception           If the provided 'productId' is not valid, if the
	 *                             product does not exist, or if an error occurs
	 *                             during the database retrieval process. The
	 *                             specific exception type and message depend on the
	 *                             underlying validation and retrieval logic.
	 */
	public List<ProductPrice> findCurrentPrice(int productId) throws ServiceException, ValidationException {
		try {
			IntUtil.rejectIfInvalidInt(productId);
			boolean test = ProductDAO.productExists(productId);

			if (!test) {
				throw new RuntimeException("Product does not exist");
			}

			ProductPriceDAO productPriceDAO = new ProductPriceDAO();

			List<ProductPrice> priceList = productPriceDAO.findCurrentPrice(productId);
			return priceList;
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Retrieves the current product prices for a specific product.
	 *
	 * This method validates the provided 'productId' using
	 * 'IntUtil.rejectIfInvalidInt'. It ensures that 'productId' is a valid integer.
	 * It then checks if the product with the provided 'productId' exists using the
	 * 'productExists' method from the 'ProductService'.
	 *
	 * If the product does not exist, a RuntimeException is thrown with the message
	 * "Product does not exist". If the product exists, the method uses a
	 * 'ProductPriceDAO' instance to retrieve the current product prices associated
	 * with the provided 'productId'. The retrieved current product prices are
	 * returned as a 'List' of 'ProductPrice' objects. Additionally, for each
	 * retrieved 'ProductPrice', the method prints its details using
	 * 'System.out.println' for debugging purposes.
	 *
	 * @param productId The ID of the product for which to retrieve the current
	 *                  product prices.
	 * @return A 'List' containing 'ProductPrice' objects representing the current
	 *         product prices for the specified product.
	 * @throws ValidationException
	 * @throws Exception           If the provided 'productId' is not valid, if the
	 *                             product does not exist, or if an error occurs
	 *                             during the database retrieval process. The
	 *                             specific exception type and message depend on the
	 *                             underlying validation and retrieval logic.
	 */
	public List<ProductPrice> findPriceByQuantity(int quantity) throws ServiceException, ValidationException {
		try {
			IntUtil.rejectIfInvalidInt(quantity);

			ProductPriceDAO productPriceDAO = new ProductPriceDAO();

			List<ProductPrice> priceList = productPriceDAO.findPricesByQuantity(quantity);
			return priceList;
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	
	/**
	 * Finds the product price based on the product ID and quantity.
	 *
	 * This method retrieves the product price details from the database for a given product identified by its unique
	 * identifier, and a specified quantity. The method performs validation on the input parameters and ensures that the
	 * provided product exists before attempting to fetch the price details.
	 *
	 * @param productId The unique identifier of the product for which the price is being fetched.
	 * @param d The quantity for which the price is being fetched.
	 * @return The `ProductPrice` object containing the price details for the specified product and quantity.
	 * @throws ServiceException If there is an error while retrieving the product price details.
	 * @throws ValidationException If the input parameters are not valid.
	 * @throws RuntimeException If the specified product does not exist.
	 */
	public ProductPrice findPriceByIdAndQuantity(int productId, double d)
			throws ServiceException, ValidationException {
		try {
			IntUtil.rejectIfInvalidDouble(d);

			boolean test = ProductDAO.productExists(productId);

			if (!test) {
				throw new RuntimeException("Product does not exist");
			}

			ProductPriceDAO productPriceDAO = new ProductPriceDAO();

			ProductPrice price =  productPriceDAO.findPriceByIdAndQuantity(productId, d);
			
			return price;
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

}
