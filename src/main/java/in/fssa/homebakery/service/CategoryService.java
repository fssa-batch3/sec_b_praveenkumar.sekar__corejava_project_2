package in.fssa.homebakery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import in.fssa.homebakery.dao.CategoryDAO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.model.Category;
import in.fssa.homebakery.util.ConnectionUtil;
import in.fssa.homebakery.validator.CategoryValidator;

public class CategoryService {

	/**
	 * Retrieves a category by its ID.
	 *
	 * This method validates the provided 'categoryId' using the
	 * 'CategoryValidator.validateId' method, ensuring that it is a valid category
	 * ID. It then checks if the category exists using the 'categoryExists' method.
	 * If the category does not exist, a RuntimeException is thrown with the message
	 * "Category does not exist". If the category exists, the method uses a
	 * 'CategoryDAO' instance to retrieve and return the category details
	 * corresponding to the provided 'categoryId'.
	 *
	 * @param categoryId The ID of the category to be retrieved.
	 * @return A 'Category' object representing the details of the retrieved
	 *         category.
	 * @throws Exception If the provided 'categoryId' is not valid, or if an error
	 *                   occurs during the database retrieval process. The specific
	 *                   exception type and message depend on the underlying
	 *                   validation and retrieval logic.
	 */
	public Category findById(int categoryId) throws Exception {
		CategoryValidator.validateId(categoryId);
		CategoryDAO categoryDao = new CategoryDAO();
		boolean test = CategoryDAO.categoryExists(categoryId);

		if (!test) {
			throw new RuntimeException("Category does not exist");
		}

		return categoryDao.findById(categoryId);
	}

	/**
	 * Updates an existing category entry in the database.
	 *
	 * This method validates the provided 'id' using the
	 * 'CategoryValidator.validateId' method, ensuring that it is a valid category
	 * ID. It then checks if the category exists using the 'categoryExists' method.
	 * If the category does not exist, a RuntimeException is thrown with the message
	 * "Category does not exist". If the category exists, the method uses a
	 * 'CategoryDAO' instance to update the category details based on the provided
	 * 'updatedCategory'. The category update is performed using the
	 * 'categoryDao.update' method.
	 *
	 * @param id              The ID of the category to be updated.
	 * @param updatedCategory An instance of 'Category' containing the updated
	 *                        category information.
	 * @throws Exception If the provided 'id' is not valid, or if an error occurs
	 *                   during the database update process. The specific exception
	 *                   type and message depend on the underlying validation and
	 *                   update logic.
	 */
	public void update(int id, Category updatedCategory) throws Exception {
		CategoryValidator.validateId(id);
		CategoryDAO categoryDao = new CategoryDAO();

		boolean test = CategoryDAO.categoryExists(id);

		if (!test) {
			throw new RuntimeException("Category does not exist");
		}

		categoryDao.update(2, updatedCategory);

	}

	/**
	 * Retrieves all categories from the database.
	 *
	 * This method uses a 'CategoryDAO' instance to retrieve all category entries
	 * stored in the database. The retrieved categories are returned as a 'Set' of
	 * 'Category' objects.
	 *
	 * @return A 'Set' containing 'Category' objects representing the details of all
	 *         categories.
	 */
	public Set<Category> getAll() {
		CategoryDAO categoryDAO = new CategoryDAO();

		Set<Category> categoryList = new HashSet<>();
		try {
			categoryList = categoryDAO.findAll();
		} catch (PersistanceException e) {
			e.printStackTrace();
		}

		return categoryList;
	}
}
