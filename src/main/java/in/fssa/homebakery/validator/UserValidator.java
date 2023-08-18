package in.fssa.homebakery.validator;

import java.util.regex.Pattern;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.StringUtil;

public class UserValidator {
	
	/**
	 * 
	 * @param user
	 * @throws Exception
	 */
	public static void validate(User user) throws Exception {
		
		Pattern email_pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		
		Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		
		if (user == null) {
			throw new ValidationException("Invalid user input");
		}
		
		StringUtil.rejectIfInvalidString(user.getEmail(), "Email");
		StringUtil.rejectIfInvalidString(user.getPassword(), "Password");
		StringUtil.rejectIfInvalidString(user.getFirstName(), "First Name");
		
		if(!email_pattern.matcher(user.getEmail()).matches()) {
			throw new ValidationException("Email does not match the pattern");
		}
		
		if(user.getPhoneNo() < 6000000000l || user.getPhoneNo() > 9999999999l) {
			throw new ValidationException("Phone number is invalid");
		}
		
	}
}
