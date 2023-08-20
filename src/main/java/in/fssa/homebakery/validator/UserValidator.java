package in.fssa.homebakery.validator;

import java.util.regex.Pattern;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.StringUtil;

public class UserValidator {

	/**
	 * Validates a User object to ensure its properties are valid.
	 *
	 * This method checks whether the provided User object is valid for processing.
	 * It ensures that the User object and its properties meet the required
	 * validation criteria. Specifically, it validates the user's email, password,
	 * first name, and phone number. If any validation check fails, a
	 * ValidationException is thrown with an appropriate error message.
	 *
	 * @param user The User object to be validated.
	 * @throws ValidationException If the provided User object or its properties are
	 *                             invalid.
	 */
	public static void validate(User user) throws Exception {

		Pattern email_pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

		Pattern passwordPattern = Pattern
				.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

		if (user == null) {
			throw new ValidationException("Invalid user input");
		}

		StringUtil.rejectIfInvalidString(user.getEmail(), "Email");
		StringUtil.rejectIfInvalidString(user.getPassword(), "Password");
		StringUtil.rejectIfInvalidString(user.getFirstName(), "First Name");

		if (!email_pattern.matcher(user.getEmail()).matches()) {
			throw new ValidationException("Email does not match the pattern");
		}

		if (user.getPhoneNo() < 6000000000l || user.getPhoneNo() > 9999999999l) {
			throw new ValidationException("Phone number is invalid");
		}

	}
}
