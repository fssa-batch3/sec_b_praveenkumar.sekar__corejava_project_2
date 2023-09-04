package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.service.ProductPriceService;

public class TestCreateProductPrice {
	
	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis()); // Current timestamp
	
	@Test
	public void testCreateProductPriceWithValidInput() {
		
		ProductPriceService priceService = new ProductPriceService();
		
		int productId = 1;
		ProductPrice price = new ProductPrice();
		price.setQuantity(1);
		price.setType(ProductPrice.QuantityType.KG);
		price.setPrice(10);
		price.setStartDate(currentTimestamp);
		
		assertDoesNotThrow(() -> {
			priceService.createProductPrice(productId, price);
		});
		
	}
	
	
	
}
