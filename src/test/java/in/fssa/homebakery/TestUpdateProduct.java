package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.service.ProductService;

public class TestUpdateProduct {

	@Test
	public void testUpdateProductWithValidInput() {
		ProductService productService = new ProductService();

		Product product = new Product();
		int id = 2;
		product.setProductName("Banana cake");
		product.setDescription("You can sleep very good");
		product.setCategoryId(3);
		product.setVeg(false);
		product.setActive(true);

		assertDoesNotThrow(() -> {
			productService.update(id, product);
		});

	}

	@Test
	public void testUpdateProductWithInvalidId() {
		ProductService productService = new ProductService();

		Product product = new Product();
		int id = -1;
		product.setProductName("Banana cake");
		product.setDescription("You can sleep very good");
		product.setCategoryId(3);
		product.setVeg(false);
		product.setActive(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.update(id, product);
		});

		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	public void testUpdateProductWithProductIdNotExists() {
		ProductService productService = new ProductService();

		Product product = new Product();
		int id = 99;
		product.setProductName("Banana cake");
		product.setDescription("You can sleep very good");
		product.setCategoryId(3);
		product.setVeg(false);
		product.setActive(true);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			productService.update(id, product);
		});

		String expectedMessage = "Product does not exist";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testUpdateProductWithInvalidCategoryId() {
		ProductService productService = new ProductService();

		Product product = new Product();
		int id = 2;
		product.setProductName("Banana cake");
		product.setDescription("You can sleep very good");
		product.setCategoryId(-4);
		product.setVeg(false);
		product.setActive(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.update(id, product);
		});

		String expectedMessage = "Invalid category Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test 
	public void testUpdateWithInvalidProductname() {
		ProductService productService = new ProductService();

		Product product = new Product();
		int id = 2;
		product.setProductName(null);
		product.setDescription("You can sleep very good");
		product.setCategoryId(3);
		product.setVeg(false);
		product.setActive(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.update(id, product);
		});

		String expectedMessage = "Product Name cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test 
	public void testUpdateWithInvalidDescription() {
		ProductService productService = new ProductService();

		Product product = new Product();
		int id = 2;
		product.setProductName("Banana cake");
		product.setDescription(null);
		product.setCategoryId(3);
		product.setVeg(false);
		product.setActive(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.update(id, product);
		});

		String expectedMessage = "Description cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

}
