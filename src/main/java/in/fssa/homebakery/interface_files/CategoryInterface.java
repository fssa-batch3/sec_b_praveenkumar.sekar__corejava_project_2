package in.fssa.homebakery.interface_files;

import java.util.Set;

import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.model.Category;

public interface CategoryInterface extends Base<Category>{
	public abstract	Set<Category> findAll() throws PersistanceException;
	public abstract Category findById(int id) throws PersistanceException;
}
