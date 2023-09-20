package in.fssa.homebakery.model;

import in.fssa.homebakery.dto.ProductDetailDTO;

public class Cart extends ProductDetailDTO{
	private int quantity;

	public Cart() {
		
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
