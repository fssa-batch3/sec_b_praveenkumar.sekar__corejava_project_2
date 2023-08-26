package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.service.ProductService;

public class TestGetAllProducts {

	@Test
	@Order(1)
	public void getAllProducts() {
		ProductService productService = new ProductService(); // Replace with your actual service class

		assertDoesNotThrow(() -> {
			Set<ProductDetailDTO> products = productService.getAllProducts(); // Replace with the actual method name

			for(ProductDetailDTO prod : products) {
				System.out.println(prod.toString());
			}
		});
	}
	
	@Test
	@Order(2)
	public void getAllProductByValidId() {
		ProductService productService = new ProductService();// Replace with your actual service class
		
		int id = 1;
		
		assertDoesNotThrow(() -> {
			ProductDetailDTO products = productService.getByProductId(id); // Replace with the actual method name

				System.out.println(products.toString());
		});
	}
	
	@Test
	@Order(3)
	public void getProductByInvalidId() {
		ProductService productService = new ProductService();// Replace with your actual service class
		
		int id = -2;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.getByProductId(id);
		});
		
		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
		
	}
	
	@Test
	@Order(4)
	public void getProductByIdOfNonExistingProduct() {
		ProductService productService = new ProductService();// Replace with your actual service class
		
		int id = 10;
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			productService.getByProductId(id);
		});
		
		String expectedMessage = "Product does not exist";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
		
	}
	
	@Test
	@Order(5)
	public void getProductsByValidCategoryId() {
		ProductService productService = new ProductService(); // Replace with your actual service class
		
		int categoryId = 2;
		
		assertDoesNotThrow(() -> {
			List<ProductDetailDTO> products = productService.getByCategoryId(categoryId); // Replace with the actual method name

			for(ProductDetailDTO prod : products) {
				System.out.println(prod.toString());
			}
		});
	}
	
	@Test
	@Order(6)
	public void getProductByInvalidCategoryId() {
		ProductService productService = new ProductService();// Replace with your actual service class
		
		int id = -2;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.getByCategoryId(id);
		});
		
		String expectedMessage = "Invalid input id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
		
	}
	
}
