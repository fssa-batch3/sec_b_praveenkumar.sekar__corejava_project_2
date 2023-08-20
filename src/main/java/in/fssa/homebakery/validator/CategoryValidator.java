package in.fssa.homebakery.validator;

import in.fssa.homebakery.exception.ValidationException;

public class CategoryValidator {

	/**
	 * Validates a category ID to ensure it is within a valid range.
	 *
	 * This method checks whether the provided category ID is within a valid range.
	 * If the category ID is not within the valid range, a ValidationException is
	 * thrown with an error message.
	 *
	 * @param categoryId The category ID to be validated.
	 * @throws ValidationException If the category ID is outside the valid range.
	 */
	public static void validateId(int categoryId) throws ValidationException {
		if (categoryId <= 0 || categoryId > 3) {
			throw new ValidationException("Invalid input id");
		}
	}
}
