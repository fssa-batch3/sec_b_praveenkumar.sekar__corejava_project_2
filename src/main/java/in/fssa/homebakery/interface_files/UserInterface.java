package in.fssa.homebakery.interface_files;

import java.util.Set;

import in.fssa.homebakery.model.User;

public interface UserInterface extends Base<User>{
	public abstract	Set<User> findAll();
	public abstract User findById(int id);
}

