package in.fssa.homebakery.validator;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.StringUtil;

public class UserValidator {

	public static void validate(User user) throws Exception {
		if (user == null) {
			throw new ValidationException("Invalid user input");
		}
		StringUtil.rejectIfInvalidString(user.getEmail(), "Email");
		StringUtil.rejectIfInvalidString(user.getPassword(), "Password");
		StringUtil.rejectIfInvalidString(user.getFirstName(), "First Name");
	}
}
