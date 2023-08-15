package in.fssa.homebakery.interface_files;

import java.util.List;
import java.util.Set;

import in.fssa.homebakery.model.ProductPrice;

public interface ProductPricesInterface extends Base<ProductPrice>{
	
	public abstract	Set<ProductPrice> findAll();
	public abstract ProductPrice findById(int id);
	public void create(ProductPrice newT, int id);
}
