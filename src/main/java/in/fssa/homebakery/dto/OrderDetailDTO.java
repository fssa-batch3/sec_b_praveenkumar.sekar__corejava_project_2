package in.fssa.homebakery.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.model.User;

public class OrderDetailDTO {

	public enum OrderStatus {
		DELIVERED, NOT_DELIVERED, CANCELLED
	}

	private int id;
	private String address;
	private OrderStatus status;
	private int quantity;
	private User user;
	private ProductDetailDTO product;
	private ProductPrice productPrice;
	private Date deliveryDate;
	private Time deliveryTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProductDetailDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDetailDTO product) {
		this.product = product;
	}

	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date currentDate) {
		this.deliveryDate = currentDate;
	}

	public Time getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Time deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}
