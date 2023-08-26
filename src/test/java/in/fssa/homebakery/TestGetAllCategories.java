package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Category;
import in.fssa.homebakery.service.CategoryService;

public class TestGetAllCategories {

	@Test
	@Order(1)
	public void getAllCategories() {
		CategoryService categoryService = new CategoryService();

		assertDoesNotThrow(() -> {
			Set<Category> category = categoryService.getAllCategories();

			System.out.println(category);
		});
	}

	@Test
	@Order(2)
	public void getCategoryById() {
		CategoryService categoryService = new CategoryService();
		
		assertDoesNotThrow(() -> {
			Category category = categoryService.findByCategoryId(2);

			System.out.println(category);
		});
	}

	@Test
	@Order(3)
	public void getCategoryByInvalidId() {
		CategoryService categoryService = new CategoryService();

		Exception exception = assertThrows(ValidationException.class, () -> {
			categoryService.findByCategoryId(4);
		});

		String expectedMessage = "Invalid input id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

}
