package in.fssa.homebakery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.fssa.homebakery.dto.OrderDetailDTO;
import in.fssa.homebakery.dto.OrderDetailDTO.OrderStatus;
import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.model.Product;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.util.ConnectionUtil;

public class OrderDAO {

	public void create(OrderDetailDTO order) throws PersistanceException {
		Connection conn = null;
		PreparedStatement stmt = null;
		Timestamp time = new Timestamp(System.currentTimeMillis());


		try {
	        String query = "INSERT INTO orders (user_id, product_id, price_id, quantity, address, delivery_date, delivery_time, status, ordered_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        conn = ConnectionUtil.getConnection();
	        stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

	        stmt.setInt(1, order.getUser().getId()); // Assuming user.getId() returns the user's ID
	        stmt.setInt(2, order.getProduct().getId()); // Assuming product.getId() returns the product's ID
	        stmt.setInt(3, order.getProductPrice().getId()); // Assuming productPrice.getId() returns the price's ID
	        stmt.setInt(4, order.getQuantity());
	        stmt.setString(5, order.getAddress());
	        Timestamp deliveryDate = new Timestamp(order.getDeliveryDate().getTime());
	        stmt.setTimestamp(6, deliveryDate);
	        Time deliveryTime = new Time(order.getDeliveryTime().getTime());
	        stmt.setTime(7, deliveryTime);
	        stmt.setString(8, order.getStatus().toString().toUpperCase()); 
	        stmt.setTimestamp(9, time);

	        stmt.executeUpdate();

	        ResultSet generatedKeys = stmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int orderId = generatedKeys.getInt(1);
	            order.setId(orderId); // Assuming setId exists in OrderDetailDTO to set the generated order ID
	        }

	        System.out.println("Order has been successfully created");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        throw new PersistanceException(e.getMessage());
	    } finally {
	        ConnectionUtil.close(conn, stmt);
	    }
	}
	
	

	public void update(int orderId, OrderDetailDTO order) throws PersistanceException {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    
	    try {
	        String query = "UPDATE orders SET user_id = ?, product_id = ?, price_id = ?, quantity = ?, address = ?, delivery_date = ?, delivery_time = ?, status = ?, ordered_time = ? WHERE id = ?";
	        conn = ConnectionUtil.getConnection();
	        stmt = conn.prepareStatement(query);

	        stmt.setInt(1, order.getUser().getId());
	        stmt.setInt(2, order.getProduct().getId());
	        stmt.setInt(3, order.getProductPrice().getId());
	        stmt.setInt(4, order.getQuantity());
	        stmt.setString(5, order.getAddress());
	        Timestamp deliveryDate = new Timestamp(order.getDeliveryDate().getTime());
	        stmt.setTimestamp(6, deliveryDate);
	        Time deliveryTime = new Time(order.getDeliveryTime().getTime());
	        stmt.setTime(7, deliveryTime);

	        stmt.setString(8, order.getStatus().toString());
	        stmt.setTimestamp(9, time);
	        stmt.setInt(10, orderId);

	        int rowsUpdated = stmt.executeUpdate();

	        if (rowsUpdated > 0) {
	            System.out.println("Order with ID " + orderId + " has been successfully updated");
	        } else {
	            System.out.println("No order with ID " + orderId + " found for update");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new PersistanceException(e.getMessage());
	    } finally {
	        ConnectionUtil.close(conn, stmt);
	    }
	}

	public void changeStatus(int orderId, OrderStatus newStatus) throws PersistanceException {
		 Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        String query = "UPDATE orders SET status = ? WHERE id = ?";
		        conn = ConnectionUtil.getConnection();
		        stmt = conn.prepareStatement(query);

		        stmt.setString(1, newStatus.toString()); // Convert the enum to a string
		        stmt.setInt(2, orderId);

		        int rowsUpdated = stmt.executeUpdate();

		        if (rowsUpdated > 0) {
		            System.out.println("Order with ID " + orderId + " has been successfully updated to status: " + newStatus);
		        } else {
		            System.out.println("No order with ID " + orderId + " found for status update");
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println(e.getMessage());
		        throw new PersistanceException(e.getMessage());
		    } finally {
		        ConnectionUtil.close(conn, stmt);
		    }
	}

	public List<OrderDetailDTO> findOrdersByUserId(int userId) throws PersistanceException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<OrderDetailDTO> orders = new ArrayList<>();

	    try {
	        String query = "SELECT id, address, status, quantity, product_id, price_id, delivery_date, delivery_time, ordered_time  FROM orders WHERE user_id = ?";
	        conn = ConnectionUtil.getConnection();
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, userId);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            OrderDetailDTO order = new OrderDetailDTO();
	            ProductDetailDTO product = new ProductDetailDTO();
	            ProductPrice productPrice = new ProductPrice();
	            order.setId(rs.getInt("id"));
	            order.setAddress(rs.getString("address"));
	            product.setId(rs.getInt("product_id"));
	            productPrice.setId(rs.getInt("price_id"));
	            order.setStatus(OrderDetailDTO.OrderStatus.valueOf(rs.getString("status")));
	            order.setQuantity(rs.getInt("quantity"));
	            order.setDeliveryDate(rs.getTimestamp("delivery_date"));
	            order.setDeliveryTime(rs.getTime("delivery_time"));
	            order.setOrderedTime(rs.getTimestamp("ordered_time"));
	            order.setProduct(product);
	            order.setProductPrice(productPrice);
	            orders.add(order);
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        throw new PersistanceException(e.getMessage());
	    } finally {
	        ConnectionUtil.close(conn, ps, rs);
	    }
	    
	    return orders;
	}
	
	public OrderDetailDTO findOrdersByOrderId(int orderId) throws PersistanceException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    OrderDetailDTO order = new OrderDetailDTO();

	    try {
	        String query = "SELECT id, address, status, quantity, product_id, price_id, delivery_date, delivery_time, ordered_time  FROM orders WHERE id = ?";
	        conn = ConnectionUtil.getConnection();
	        ps = conn.prepareStatement(query);
	        ps.setInt(1, orderId);

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            ProductDetailDTO product = new ProductDetailDTO();
	            ProductPrice productPrice = new ProductPrice();
	            order.setId(rs.getInt("id"));
	            order.setAddress(rs.getString("address"));
	            product.setId(rs.getInt("product_id"));
	            productPrice.setId(rs.getInt("price_id"));
	            order.setStatus(OrderDetailDTO.OrderStatus.valueOf(rs.getString("status")));
	            order.setQuantity(rs.getInt("quantity"));
	            order.setDeliveryDate(rs.getTimestamp("delivery_date"));
	            order.setDeliveryTime(rs.getTime("delivery_time"));
	            order.setOrderedTime(rs.getTimestamp("ordered_time"));
	            order.setProduct(product);
	            order.setProductPrice(productPrice);
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        throw new PersistanceException(e.getMessage());
	    } finally {
	        ConnectionUtil.close(conn, ps, rs);
	    }
	    
	    return order;
	}

	public List<OrderDetailDTO> findAllOrders() throws PersistanceException {
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<OrderDetailDTO> orders = new ArrayList<>();

	    try {
	        String query = "SELECT id, address, status, quantity, product_id, price_id, delivery_date, delivery_time, ordered_time FROM orders";
	        conn = ConnectionUtil.getConnection();
	        ps = conn.prepareStatement(query);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            OrderDetailDTO order = new OrderDetailDTO();
	            Product product = new Product();
	            ProductPrice productPrice = new ProductPrice();
	            order.setId(rs.getInt("id"));
	            order.setAddress(rs.getString("address"));
	            product.setId(rs.getInt("product_id"));
	            productPrice.setId(rs.getInt("price_id"));
	            order.setStatus(OrderDetailDTO.OrderStatus.valueOf(rs.getString("status")));
	            order.setQuantity(rs.getInt("quantity"));
	            order.setDeliveryDate(rs.getTimestamp("delivery_date"));
	            order.setDeliveryTime(rs.getTime("delivery_time"));
	            order.setOrderedTime(rs.getTimestamp("ordered_time"));

	            orders.add(order);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        throw new PersistanceException(e.getMessage());
	    } finally {
	        ConnectionUtil.close(conn, ps, rs);
	    }
	    
	    return orders;
	}
	
	public static boolean orderExists(int orderId) throws PersistanceException {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT 1 FROM orders WHERE id = ?";
	        conn = ConnectionUtil.getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, orderId);

	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }

	        return false; // No rows returned, order does not exist
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        throw new PersistanceException(e.getMessage());
	    } finally {
	        ConnectionUtil.close(conn, stmt, rs);
	    }
	}

}
