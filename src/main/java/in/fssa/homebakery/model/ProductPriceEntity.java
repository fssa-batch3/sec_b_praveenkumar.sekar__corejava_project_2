package in.fssa.homebakery.model;

import java.sql.Timestamp;
import java.time.LocalDate;



public class ProductPriceEntity {
	
	public enum QuantityType {
		KG, NOS
	}
	
	private int id;
	private double quantity;
	private int productId;
	private QuantityType type;
	private double price;
	private Timestamp startDate;
	private Timestamp endDate;
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
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
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public QuantityType getType() {
		return type;
	}
	
	public void setType(QuantityType type) {
		this.type = type;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	
	public Timestamp getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	    
}
