package in.fssa.homebakery.validator;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;

public class PriceValidator {
	
	public static void validate(ProductPrice price) throws Exception {
		if(price == null) {
			throw new ValidationException("Price cannot be null");
		}
		
		if(price.getPrice() <= 0) {
			throw new ValidationException("Invalid price");
		}
		
		if(price.getQuantity() <= 0.0) {
			throw new ValidationException("Invalid Quantity");
		}
	}
	
	public static void validateQuantity(double quantity) throws Exception{
		if(quantity <= 0.0) {
			throw new ValidationException("Invalid Quantity");
		}
	}
}
