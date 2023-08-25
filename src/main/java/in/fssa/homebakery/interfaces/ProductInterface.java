package in.fssa.homebakery.interfaces;

import java.util.Set;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.model.Product;

public interface ProductInterface extends Base<Product>{
	public abstract	Set<ProductDetailDTO> findAll();
	public abstract Product findById(int id);
	void create(ProductDetailDTO productDetailDto);
	
}
