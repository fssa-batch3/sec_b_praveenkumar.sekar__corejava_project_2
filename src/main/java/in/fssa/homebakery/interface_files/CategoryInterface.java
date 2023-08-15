package in.fssa.homebakery.interface_files;

import java.util.Set;

import in.fssa.homebakery.model.Category;

public interface CategoryInterface extends Base<Category>{
	public abstract	Set<Category> findAll();
	public abstract Category findById(int id);
}
