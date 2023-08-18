package in.fssa.homebakery.validator;

import java.util.List;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.StringUtil;

public class ProductValidator {
	
	/**
	 * 
	 * @param product
	 * @throws Exception
	 */
	public static void validate(ProductDetailDTO product) throws Exception {
		if (product == null) {
			throw new ValidationException("Invalid product input");
		}
		
		if(product.getCategoryId() <= 0 || product.getCategoryId() > 3) {
			throw new ValidationException("Invalid category Id");
		}
		
		if(product.getPrices() == null) {
			throw new ValidationException("Price List cannot be null or empty");
		}
		
		StringUtil.rejectIfInvalidString(product.getName(), "Product Name");
		StringUtil.rejectIfInvalidString(product.getDescription(), "Description");
		
	}
	
	/**
	 * 
	 * @param product
	 * @throws Exception
	 */
	public static void validate(Product product) throws Exception {
		if (product == null) {
			throw new ValidationException("Invalid product input");
		}
		
		if(product.getCategoryId() <= 0 || product.getCategoryId() > 3) {
			throw new ValidationException("Invalid category Id");
		}
		
		StringUtil.rejectIfInvalidString(product.getProductName(), "Product Name");
		StringUtil.rejectIfInvalidString(product.getDescription(), "Description");
		
	}
	
	/**
	 * 
	 * @param priceList
	 * @throws Exception
	 */
	public static void validatePriceList(List<ProductPrice> priceList) throws Exception{
		if(priceList.isEmpty()) {
			throw new ValidationException("No price found");
		}
		for(ProductPrice price : priceList) {
			PriceValidator.validate(price);
		}
		
	}
	
	
}
