package in.fssa.homebakery.util;

import in.fssa.homebakery.exception.ValidationException;

public class IntUtil {

	/**
	 * Validates an integer value and throws a ValidationException if it's invalid.
	 *
	 * This method checks whether the provided integer value is less than or equal
	 * to zero. If the value is invalid (less than or equal to zero), a
	 * ValidationException is thrown with an appropriate error message.
	 *
	 * @param num The integer value to be validated.
	 * @throws ValidationException If the integer value is less than or equal to
	 *                             zero.
	 */
	public static void rejectIfInvalidInt(int num) throws ValidationException {
		if (num <= 0) {
			throw new ValidationException("Invalid Id");
		}
	}
}
