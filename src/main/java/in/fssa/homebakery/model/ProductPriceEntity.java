package in.fssa.homebakery.model;

import java.time.LocalDate;

public class ProductPriceEntity {
	
	public enum QuantityType {
		KG, NOS
	}
	
	private int id;
	private int quantity;
	private QuantityType type;
	private double price;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public QuantityType getType() {
		return type;
	}
	
	public void setType(QuantityType type) {
		this.type = type;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	    
}
