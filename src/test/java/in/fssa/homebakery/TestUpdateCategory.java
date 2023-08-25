package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.Category;
import in.fssa.homebakery.service.CategoryService;

public class TestUpdateCategory {

	@Test
	public void testUpdateCategoryWithValidData() {
		CategoryService categoryService = new CategoryService();
		Category updatedCategory = new Category();

		updatedCategory.setCategoryName("Cookies");

		assertDoesNotThrow(() -> {
			categoryService.updateCategory(1, updatedCategory);
		});
	}

	@Test
	public void testUpdateCategoryWithInvalidData() {
		CategoryService categoryService = new CategoryService();
		Category updatedCategory = new Category();

		updatedCategory.setCategoryName("Cookies");

		Exception exception = assertThrows(ValidationException.class, () -> {
			categoryService.updateCategory(4, updatedCategory);
		});

		String expectedMessage = "Invalid input id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

}
