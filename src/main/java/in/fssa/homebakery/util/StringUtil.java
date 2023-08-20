package in.fssa.homebakery.util;

import in.fssa.homebakery.exception.ValidationException;

public class StringUtil {

	/**
	 * Validates a string input and throws a ValidationException if it's invalid.
	 *
	 * This method checks whether the provided string input is null or empty after
	 * trimming. If the input is invalid (null or empty), a ValidationException is
	 * thrown with an appropriate error message that includes the input name.
	 *
	 * @param input     The string input to be validated.
	 * @param inputName The name of the input, used in the error message.
	 * @throws ValidationException If the string input is null or empty after
	 *                             trimming.
	 */
	public static void rejectIfInvalidString(String input, String inputName) throws ValidationException {
		if (input == null || "".equals(input.trim())) {
			throw new ValidationException(inputName.concat(" cannot be null or empty"));
		}
	}

	/**
	 * Checks if a string input is valid.
	 *
	 * This method checks whether the provided string input is null or empty after
	 * trimming. If the input is valid (not null and not empty), it returns true.
	 * Otherwise, it returns false.
	 *
	 * @param input The string input to be checked for validity.
	 * @return True if the string input is not null and not empty after trimming,
	 *         false otherwise.
	 */
	public static boolean isValidString(String input) {
		if (input == null || "".equals(input.trim())) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if a string input is invalid.
	 *
	 * This method checks whether the provided string input is null or empty after
	 * trimming. If the input is invalid (null or empty), it returns true.
	 * Otherwise, it returns false.
	 *
	 * @param input The string input to be checked for validity.
	 * @return True if the string input is null or empty after trimming, false if
	 *         the input is valid.
	 */
	public static boolean isInvalidString(String input) {
		if (input == null || "".equals(input.trim())) {
			return true;
		}

		return false;
	}

}