package in.fssa.homebakery.interface_files;

import java.util.Set;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.model.Product;

public interface ProductInterface extends Base<Product>{
	public abstract	Set<Product> findAll();
	public abstract Product findById(int id);
	void create(ProductDetailDTO productDetailDto);
	
}
