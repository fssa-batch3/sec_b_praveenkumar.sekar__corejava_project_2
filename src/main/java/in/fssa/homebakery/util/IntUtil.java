package in.fssa.homebakery.util;

import in.fssa.homebakery.exception.ValidationException;

public class IntUtil {
	
	/**
	 * 
	 * @param num
	 * @throws ValidationException
	 */
	public static void rejectIfInvalidInt(int num) throws ValidationException{
		if(num <=0) {
			throw new ValidationException("Invalid Id");
		}
	}
}
