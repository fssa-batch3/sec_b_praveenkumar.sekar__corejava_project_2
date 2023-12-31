package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.service.UserService;

public class TestCreateUser {
	
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10;

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new SecureRandom();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
    
    String randomString = generateRandomString();

	@Test
	@Order(1)
	public void testCreateUserWithValidData() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		assertDoesNotThrow(() -> {
			userService.createUser(newUser);
		});
	}

	@Test
	@Order(2)
	public void testCreateUserWithInvalidData() {

		UserService userService = new UserService();
		User newUser = null;

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Invalid user input";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(3)
	public void testCreateUserWithInvalidFirstName() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName(null);
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "First Name cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(4)
	public void testCreateUserWithEmptyFirstName() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "First Name cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(5)
	public void testCreateUserWithInvalidFirstNamePattern() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Prave  1243");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Name should only contain alphabets and be seprated by only one space";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(6)
	public void testCreateUserWithInvalidEmailPattern() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail("formgmail.com");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});


		String expectedMessage = "Invalid email format. Please provide an email address with the following conditions:\n"
				+ "- Should follow the standard email format (e.g., user@example.com)\n"
				+ "- Should contain only letters, numbers, and special characters (@, .)\n"
				+ "- Should have a valid domain name and top-level domain (e.g., .com, .org)";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(7)
	public void testCreateUserWithInvalidEmail() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(null);
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Email cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	@Order(8)
	public void testCreateUserWithEmptyEmail() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail("");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(9345209293l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Email cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(9)
	public void testCreateUserWithInvalidPhoneNo() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("Aspirin@2004");
		newUser.setPhoneNo(123456789l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Phone number is invalid";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(10)
	public void testCreateUserWithInvalidPassword() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword(null);
		newUser.setPhoneNo(6374111637l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Password cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	@Order(11)
	public void testCreateUserWithEmptyPassword() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("");
		newUser.setPhoneNo(6374111637l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Password cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
	@Test
	@Order(12)
	public void testCreateUserWithInvalidPasswordPattern() {
		UserService userService = new UserService();
		User newUser = new User();

		newUser.setFirstName("Formido");
		newUser.setLastName("Boi");
		newUser.setEmail(randomString + "@gmail.com");
		newUser.setPassword("Praveen123");
		newUser.setPhoneNo(6374111637l);

		Exception exception = assertThrows(ValidationException.class, () -> {
			userService.createUser(newUser);
		});

		String expectedMessage = "Invalid password format. Please provide a password with the following conditions:\n"
				+ "- Should be at least 8 characters long\n"
				+ "- Should contain at least one lowercase letter\n"
				+ "- Should contain at least one uppercase letter\n"
				+ "- Should contain at least one digit\n"
				+ "- Should contain at least one special character (@, $, !, %, *, ?, &)";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
}
