package in.fssa.homebakery.validator;

import java.util.List;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.StringUtil;

public class ProductValidator {

	/**
	 * Validates a ProductDetailDTO object to ensure its properties are valid.
	 *
	 * This method checks whether the provided ProductDetailDTO object is valid for
	 * processing. It performs various checks on the object's properties, such as
	 * ensuring the category ID is valid, the price list is not null or empty, and
	 * the product name and description are not null or empty. If any validation
	 * check fails, a ValidationException is thrown with an appropriate error
	 * message.
	 *
	 * @param product The ProductDetailDTO object to be validated.
	 * @throws ValidationException If the provided ProductDetailDTO object or its
	 *                             properties are invalid.
	 */
	public static void validate(ProductDetailDTO product) throws ValidationException {
		if (product == null) {
			throw new ValidationException("Invalid product input");
		}

		if (product.getCategoryId() <= 0 || product.getCategoryId() > 3) {
			throw new ValidationException("Invalid category Id");
		}

		if (product.getPrices() == null) {
			throw new ValidationException("Price List cannot be null or empty");
		}

		StringUtil.rejectIfInvalidString(product.getName(), "Product Name");
		StringUtil.rejectIfInvalidString(product.getDescription(), "Description");

	}

	/**
	 * Validates a Product object to ensure its properties are valid.
	 *
	 * This method checks whether the provided Product object is valid for
	 * processing. It performs various checks on the object's properties, such as
	 * ensuring the category ID is valid, the product name and description are not
	 * null or empty. If any validation check fails, a ValidationException is thrown
	 * with an appropriate error message.
	 *
	 * @param product The Product object to be validated.
	 * @throws ValidationException If the provided Product object or its properties
	 *                             are invalid.
	 */
	public static void validate(Product product) throws ValidationException {
		if (product == null) {
			throw new ValidationException("Invalid product input");
		}

		if (product.getCategoryId() <= 0 || product.getCategoryId() > 3) {
			throw new ValidationException("Invalid category Id");
		}

		StringUtil.rejectIfInvalidString(product.getProductName(), "Product Name");
		StringUtil.rejectIfInvalidString(product.getDescription(), "Description");

	}

	/**
	 * Validates a list of ProductPrice objects to ensure their properties are
	 * valid.
	 *
	 * This method checks whether the provided list of ProductPrice objects is valid
	 * for processing. It ensures that the list is not empty and that each
	 * individual ProductPrice object within the list is also valid by invoking the
	 * PriceValidator.validate method for each object. If any validation check
	 * fails, a ValidationException is thrown with an appropriate error message.
	 *
	 * @param priceList The list of ProductPrice objects to be validated.
	 * @throws ValidationException If the provided list of ProductPrice objects or
	 *                             their properties are invalid.
	 */
	public static void validatePriceList(List<ProductPrice> priceList) throws ValidationException {
		if (priceList.isEmpty()) {
			throw new ValidationException("No price found");
		}
		for (ProductPrice price : priceList) {
			PriceValidator.validate(price);
		}

	}

}
