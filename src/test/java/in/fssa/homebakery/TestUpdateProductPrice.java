package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.service.ProductPriceService;
import in.fssa.homebakery.service.ProductService;

public class TestUpdateProductPrice {

	@Test
	@Order(1)
	public void testUpdateValidProduct() throws Exception {

		ProductPriceService productService = new ProductPriceService();
		// Arrange
		int productId = 1;
		ProductPrice productPrice = new ProductPrice();
		productPrice.setQuantity(1);
		productPrice.setType(ProductPrice.QuantityType.KG);
		productPrice.setPrice(1000);
		double quantity = 2.0;

		// Act and Assert
		assertDoesNotThrow(() -> {
			productService.updateProductPrice(productId, productPrice, quantity);
		});
	}

	@Test
	@Order(2)
	public void testUpdateInvalidProduct() {

		ProductPriceService productService = new ProductPriceService();
		// Arrange
		int productId = 1;
		ProductPrice productPrice = null;// Initialize with valid values
		double quantity = 2.0;

		// Act and Assert
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.updateProductPrice(productId, productPrice, quantity);
		});

		String expectedMessage = "Price cannot be null";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(3)
	public void testUpdateInvalidQuantity() {

		ProductPriceService productService = new ProductPriceService();

		// Arrange
		int productId = 1;
		ProductPrice productPrice = new ProductPrice();
		productPrice.setQuantity(1);
		productPrice.setType(ProductPrice.QuantityType.KG);
		productPrice.setPrice(10);
		// Set the start date// Initialize with valid values
		double quantity = 0.0;

		// Act and Assert
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.updateProductPrice(productId, productPrice, quantity);
		});

		String expectedMessage = "Invalid Quantity";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(4)
	public void testUpdateInvalidProductId() {
		ProductPriceService productService = new ProductPriceService();

		// Arrange
		int productId = -2;
		ProductPrice productPrice = new ProductPrice();
		productPrice.setQuantity(1);
		productPrice.setType(ProductPrice.QuantityType.KG);
		productPrice.setPrice(10);
		// Set the start date// Initialize with valid values
		double quantity = 2.0;

		// Act and Assert
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.updateProductPrice(productId, productPrice, quantity);
		});

		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(5)
	public void testUpdateProductDoesNotExist() {
		ProductPriceService productService = new ProductPriceService();

		// Arrange
		int productId = 33;
		ProductPrice productPrice = new ProductPrice();
		productPrice.setQuantity(1);
		productPrice.setType(ProductPrice.QuantityType.KG);
		productPrice.setPrice(10);
		double quantity = 2.0;

		// Act and Assert
		Exception exception = assertThrows(RuntimeException.class, () -> {
			productService.updateProductPrice(productId, productPrice, quantity);
		});

		String expectedMessage = "Product does not exist";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	@Order(6)
	public void testUpdateInvalidPrice() {
		ProductPriceService productService = new ProductPriceService();

		// Arrange
		int productId = 3;
		ProductPrice productPrice = new ProductPrice();
		productPrice.setQuantity(1);
		productPrice.setType(ProductPrice.QuantityType.KG);
		productPrice.setPrice(0);
		double quantity = 2.0;

		// Act and Assert
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.updateProductPrice(productId, productPrice, quantity);
		});

		String expectedMessage = "Invalid price";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
}
