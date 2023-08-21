package in.fssa.homebakery.dto;

import java.util.List;

import in.fssa.homebakery.model.ProductPrice;

public class ProductDetailDTO {

	private int id;
	private String name;
	private String description;
	private int categoryId;
	private boolean isVeg;
	private boolean isActive;

	private List<ProductPrice> priceList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public List<ProductPrice> getPrices() {
		return priceList;
	}

	public void setPrices(List<ProductPrice> prices) {
		this.priceList = prices;
	}

	@Override
	public String toString() {
		return "ProductDetailDTO [id=" + id + ", name=" + name + ", description=" + description + ", categoryId="
				+ categoryId + ", isVeg=" + isVeg + ", isActive=" + isActive + ", priceList=" + priceList + "]";
	}
}
