package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.PriceValidator;

public class ProductPriceService {

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
	public void update(int id, ProductPrice productPrice, double quantity) throws Exception {
		ProductService productService = new ProductService();

		IntUtil.rejectIfInvalidInt(id);
		PriceValidator.validate(productPrice);
		PriceValidator.validateQuantity(quantity);

		boolean test = productService.productExists(id);

		if (!test) {
			throw new RuntimeException("Product does not exist");
		}

		boolean check = quantityExistsForProduct(id, quantity);

		if (!check) {
			throw new RuntimeException("Quantity does not exist");
		}
		ProductPriceDAO productPriceDAO = new ProductPriceDAO();

		productPriceDAO.setEndDate(id, quantity);
		productPriceDAO.update(id, productPrice, quantity);
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
	public Set<ProductPrice> findAll() throws PersistanceException {
		ProductPriceDAO productPriceDAO = new ProductPriceDAO();
		Set<ProductPrice> priceList = productPriceDAO.findAll();
		for (ProductPrice price : priceList) {
			System.out.println(price);
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
	 * @throws Exception If the provided 'id' is not valid, if the product does not
	 *                   exist, or if an error occurs during the database retrieval
	 *                   process. The specific exception type and message depend on
	 *                   the underlying validation and retrieval logic.
	 */
	public List<ProductPrice> findByProductId(int id) throws Exception {
		IntUtil.rejectIfInvalidInt(id);
		ProductService productService = new ProductService();
		boolean test = productService.productExists(id);

		if (!test) {
			throw new RuntimeException("Product does not exist");
		}

		ProductPriceDAO productPriceDAO = new ProductPriceDAO();

		List<ProductPrice> priceList = productPriceDAO.findByProductId(id);
		for (ProductPrice price : priceList) {
			System.out.println(price);
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
	 * @throws Exception If the provided 'id' is not valid or if an error occurs
	 *                   during the database retrieval process. The specific
	 *                   exception type and message depend on the underlying
	 *                   validation and retrieval logic.
	 */
	public ProductPrice findById(int id) throws Exception {
		IntUtil.rejectIfInvalidInt(id);
		ProductPriceDAO productPriceDAO = new ProductPriceDAO();
		return productPriceDAO.findById(id);
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
	 * @throws Exception If the provided 'productId' is not valid, if the product
	 *                   does not exist, or if an error occurs during the database
	 *                   retrieval process. The specific exception type and message
	 *                   depend on the underlying validation and retrieval logic.
	 */
	public List<ProductPrice> findCurrentPrice(int productId) throws Exception {
		IntUtil.rejectIfInvalidInt(productId);
		ProductService productService = new ProductService();
		boolean test = productService.productExists(productId);

		if (!test) {
			throw new RuntimeException("Product does not exist");
		}

		ProductPriceDAO productPriceDAO = new ProductPriceDAO();

		List<ProductPrice> priceList = productPriceDAO.findCurrentPrice(productId);
		for (ProductPrice price : priceList) {
			System.out.println(price);
		}
		return priceList;
	}

	/**
	 * Checks if a specific quantity exists for a given product ID.
	 *
	 * This method checks whether a product price entry with the provided
	 * 'productId' and 'quantity' exists in the database. It uses the 'productId'
	 * and 'quantity' parameters to query the database and determine if such an
	 * entry exists.
	 *
	 * If an entry with the provided 'productId' and 'quantity' is found, the method
	 * returns 'true', indicating that the quantity exists. If no matching entry is
	 * found, the method returns 'false', indicating that the quantity does not
	 * exist.
	 *
	 * @param productId The ID of the product to check for.
	 * @param quantity  The quantity to check for.
	 * @return 'true' if a product price entry with the specified 'productId' and
	 *         'quantity' exists, 'false' otherwise.
	 */
	public boolean quantityExistsForProduct(int productId, double quantity) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT COUNT(*) FROM product_prices WHERE product_id = ? AND quantity = ?";
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, productId);
			stmt.setDouble(2, quantity);

			rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0; // If count > 0, product with quantity for the specified product ID exists
			}

			return false; // No rows returned, product with quantity for the specified product ID does not
							// exist
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, stmt, rs);
		}
	}

}
