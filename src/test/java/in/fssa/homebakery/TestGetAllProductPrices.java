package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.service.ProductPriceService;

public class TestGetAllProductPrices {

	@Test
	public void testGetAllProductPrices() {
		ProductPriceService productPriceService = new ProductPriceService(); // Replace with your actual service class

		assertDoesNotThrow(() -> {
			Set<ProductPrice> products = productPriceService.findAll(); // Replace with the actual method name

			for (ProductPrice prod : products) {
				System.out.println(prod.toString());
			}
		});
	}

	@Test
	public void testGetProductsPricesById() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = 3;

		assertDoesNotThrow(() -> {
			ProductPrice products = productPriceService.findById(id);
		});
	}

	@Test
	public void testGetProductsPricesByInvalidId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = -3;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productPriceService.findById(id);
		});
		
		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testGetProductsPricesByProductId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = 3;

		assertDoesNotThrow(() -> {
			List<ProductPrice> products = productPriceService.findByProductId(id);
			
			for (ProductPrice prod : products) {
				System.out.println(prod.toString());
			}
		});
	}

	@Test
	public void testGetProductsPricesByInvalidProductId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = -3;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productPriceService.findByProductId(id);
		});
		
		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testGetProductsPricesByNonExistingProductId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = 35;
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			productPriceService.findByProductId(id);
		});
		
		String expectedMessage = "Product does not exist";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testGetCurrentPriceByProductId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = 3;

		assertDoesNotThrow(() -> {
			List<ProductPrice> products = productPriceService.findCurrentPrice(id);
			
			for (ProductPrice prod : products) {
				System.out.println(prod.toString());
			}
		});
	}
	
	@Test
	public void testGetCurrentPriceByInvalidProductId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = -3;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productPriceService.findCurrentPrice(id);
		});
		
		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testGetCurrentPriceByNonExistingProductId() {
		ProductPriceService productPriceService = new ProductPriceService();

		int id = 99;
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			productPriceService.findCurrentPrice(id);
		});
		
		String expectedMessage = "Product does not exist";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
}
