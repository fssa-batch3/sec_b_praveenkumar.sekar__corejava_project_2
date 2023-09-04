package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.service.ProductService;
import in.fssa.homebakery.service.UserService;

public class TestGetAllUsers {
	
	@Test
	@Order(1)
	public void getAllUsers() {
		UserService userService = new UserService(); // Replace with your actual service class

		assertDoesNotThrow(() -> {
			Set<User> users = userService.getAllUsers(); // Replace with the actual method name

			for(User user : users) {
				System.out.println(user.toString());
			}
		});
	}
	
}
