package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.service.UserService;

public class TestCreateUser {

	@Test
	public void testCreateUserWithValidData() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail("formido123@gmail.com");
		newUser.setPassword("Aspirin2004");
		newUser.setPhoneNo(9345209293l);

		assertDoesNotThrow(() -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithInvalidData() {

		UserService userService = new UserService();
		User newUser = null;

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});

		String expectedMessage = "Invalid user input";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	public void testCreateUserWithInvalidFirstName() {
		UserService userService = new UserService();
		User newUser = new User();
		
		newUser.setFirstName(null);
		newUser.setLastName("Boi");
		newUser.setEmail("formido123@gmail.com");
		newUser.setPassword("Aspirin2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});

		String expectedMessage = "First Name cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testCreateUserWithInvalidEmailPattern() {
		UserService userService = new UserService();
		User newUser = new User();
		
		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail("formgmail.com");
		newUser.setPassword("Aspirin2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});

		String expectedMessage = "Email does not match the pattern";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testCreateUserWithInvalidEmail() {
		UserService userService = new UserService();
		User newUser = new User();
		
		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(null);
		newUser.setPassword("Aspirin2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});

		String expectedMessage = "Email cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testCreateUserWithInvalidPhoneNo() {
		UserService userService = new UserService();
		User newUser = new User();
		
		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail("formidoboi123@gmail.com");
		newUser.setPassword("Aspirin2004");
		newUser.setPhoneNo(123456789l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});

		String expectedMessage = "Phone number is invalid";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	public void testCreateUserWithInvalidPassword() {
		UserService userService = new UserService();
		User newUser = new User();
		
		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail("formidoboi123@gmail.com");
		newUser.setPassword(null);
		newUser.setPhoneNo(6374111637l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});

		String expectedMessage = "Password cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
}
