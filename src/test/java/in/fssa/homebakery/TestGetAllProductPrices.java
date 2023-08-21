package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Set;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.service.ProductPriceService;

public class TestGetAllProductPrices {
	
	@Test
	public void testGetAllProductPrices () {
		ProductPriceService productPriceService = new ProductPriceService(); // Replace with your actual service class

		assertDoesNotThrow(() -> {
			Set<ProductPrice> products = productPriceService.findAll(); // Replace with the actual method name

			for(ProductPrice prod : products) {
				System.out.println(prod.toString());
			}
		});
	}
}
