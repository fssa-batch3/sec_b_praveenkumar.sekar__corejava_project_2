package in.fssa.homebakery.interface_files;

import java.util.Set;

import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.model.User;

public interface UserInterface extends Base<User>{
	public abstract	Set<User> findAll() throws PersistanceException;
	public abstract User findById(int id) throws PersistanceException;
}

