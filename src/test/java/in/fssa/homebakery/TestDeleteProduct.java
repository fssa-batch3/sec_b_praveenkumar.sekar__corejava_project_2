package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.service.ProductService;

public class TestDeleteProduct {

	@Test
	@Order(1)
	public void testDeleteProductWithValidId() {
		ProductService prodService = new ProductService();

		int id = 4;

		assertDoesNotThrow(() -> {
			prodService.deleteProduct(id);
		});
	}

	@Test
	@Order(2)
	public void testDeleteProductWithInvalidId() {
		ProductService prodService = new ProductService();

		int id = -2;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			prodService.deleteProduct(id);
		});

		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();
	}
	
	@Test
	@Order(3)
	public void testDeleteProductWithNonExistingId() {
		ProductService prodService = new ProductService();

		int id = 44;
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			prodService.deleteProduct(id);
		});

		String expectedMessage = "Product does not exist";
		String actualMessage = exception.getMessage();
	}
	
}
