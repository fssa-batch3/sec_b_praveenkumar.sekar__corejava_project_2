package in.fssa.homebakery.validator;

import in.fssa.homebakery.exception.ValidationException;


public class CategoryValidator {
	
	public static void validateId(int categoryId ) throws ValidationException { 
		if(categoryId <= 0 || categoryId > 3) {
			throw new ValidationException("Invalid input id");
		}
	}
}
