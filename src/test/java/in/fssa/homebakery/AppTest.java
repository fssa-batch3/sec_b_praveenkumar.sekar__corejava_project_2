package in.fssa.homebakery;

import in.fssa.homebakery.service.UserService;

public class AppTest {

	public static void main(String[] args) {
		UserService service = new UserService();
		service.getAll(); 
	}

}
