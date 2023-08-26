package in.fssa.homebakery.validator;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;

public class PriceValidator {

	/**
	 * Validates a ProductPrice object to ensure its properties are valid.
	 *
	 * This method checks whether the provided ProductPrice object is not null and
	 * whether its price and quantity properties are valid values. If any of the
	 * validations fail, a ValidationException is thrown with an appropriate error
	 * message.
	 *
	 * @param price The ProductPrice object to be validated.
	 * @throws ValidationException If the ProductPrice object or its properties are
	 *                             invalid.
	 */
	public static void validate(ProductPrice price) throws ValidationException {
		if (price == null) {
			throw new ValidationException("Price cannot be null");
		}

		if (price.getPrice() <= 0) {
			throw new ValidationException("Invalid price");
		}

		if (price.getQuantity() <= 0.0) {
			throw new ValidationException("Invalid Quantity");
		}
	}

	/**
	 * Validates a quantity value to ensure it is a valid positive number.
	 *
	 * This method checks whether the provided quantity value is greater than zero.
	 * If the validation fails, a ValidationException is thrown with an appropriate
	 * error message.
	 *
	 * @param quantity The quantity value to be validated.
	 * @throws ValidationException If the quantity value is invalid (not greater
	 *                             than zero).
	 */
	public static void validateQuantity(double quantity) throws ValidationException {
		if (quantity <= 0.0) {
			throw new ValidationException("Invalid Quantity");
		}
	}
}
