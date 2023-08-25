package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.service.UserService;

public class TestDeleteUser {

	@Test
	public void deleteUserByValidId() {
		UserService userService = new UserService();

		int id = 3;

		assertDoesNotThrow(() -> {
			userService.deleteUser(id);
		});
	}

	@Test
	public void deleteUserByInvalidId() {
		UserService userService = new UserService();

		int id = -3;
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.deleteUser(id);
		});

		String expectedMessage = "Invalid Id";
		String actualMessage = exception.getMessage();
	}
	
	@Test
	public void deleteUserByNonExistingId() {
		UserService userService = new UserService();

		int id = 29;
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.deleteUser(id);
		});

		String expectedMessage = "User does not exist";
		String actualMessage = exception.getMessage();
	}
	
}
