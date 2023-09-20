package in.fssa.homebakery.validator;

import in.fssa.homebakery.dto.OrderDetailDTO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.util.StringUtil;

public class OrderValidator {
	public static void validate(OrderDetailDTO orderDetailDTO) throws ValidationException {
		if (orderDetailDTO == null) {
			throw new ValidationException("Invalid order input");
		}

		if (orderDetailDTO.getQuantity() <= 0){
			throw new ValidationException("Invalid amount of quantity");
		}

		StringUtil.rejectIfInvalidString(orderDetailDTO.getAddress(), "Address");
		
		ProductValidator.validate(orderDetailDTO.getProduct());
		PriceValidator.validate(orderDetailDTO.getProductPrice());
		UserValidator.validate(orderDetailDTO.getUser());
	}
}
