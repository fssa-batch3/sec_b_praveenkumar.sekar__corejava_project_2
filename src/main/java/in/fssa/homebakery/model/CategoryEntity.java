package in.fssa.homebakery.model;

import javax.validation.constraints.NotNull;

public class CategoryEntity {
	private int id;
	
	@NotNull(message = "category name cannot be null")
	private String categoryName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public String toString() {
		return "CategoryEntity [id=" + id + ", categoryName=" + categoryName + "]";
	}
	
}
