package in.fssa.homebakery.model;

import java.util.List;
import java.util.Set;

public class ProductEntity {
	
	private int id;
	private String name;
	private String description;
	private int categoryId;
//	private List<ProductPrice> price;   //  {  id, name, desc, category_id, [ { 500g, 10},{1kg, 20}, {2kg, 30} ]  }
	private boolean isVeg;
	private boolean isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return name;
	}
	public void setProductName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public boolean isVeg() {
		return isVeg;
	}
	public void setVeg(boolean isVeg) {
		this.isVeg = isVeg;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
