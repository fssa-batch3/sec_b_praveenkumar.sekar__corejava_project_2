package in.fssa.homebakery.service;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ServiceException;

import in.fssa.homebakery.dao.OrderDAO;
import in.fssa.homebakery.dao.ProductDAO;
import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.dao.UserDAO;
import in.fssa.homebakery.dto.OrderDetailDTO;
import in.fssa.homebakery.dto.OrderDetailDTO.OrderStatus;
import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.PersistanceException;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.model.User;
import in.fssa.homebakery.util.IntUtil;
import in.fssa.homebakery.validator.OrderValidator;

public class OrderService {
	
	
	public void createOrder(OrderDetailDTO newOrder, int productId, int priceId, String email) throws ValidationException, ServiceException {
	    try {
	        ProductService productService = new ProductService();
	        ProductDetailDTO product = productService.getByProductId(productId);
	        
	        ProductPriceService priceService = new ProductPriceService();
	        ProductPrice price = priceService.findByPriceId(priceId);
	        
	        UserService userService = new UserService();
	        User user = userService.findByEmail(email);
	        
	        newOrder.setProduct(product);
	        newOrder.setProductPrice(price);
	        newOrder.setUser(user);
	        
	        OrderValidator.validate(newOrder);
	        

	        OrderDAO orderDAO = new OrderDAO();   
	        orderDAO.create(newOrder); 

	    } catch (PersistanceException e) {
	        e.printStackTrace();
	        throw new ServiceException(e.getMessage());
	    }
	}
	
	
	public void updateOrder(int orderId, OrderDetailDTO newOrder) throws ValidationException, ServiceException {
	    try {
	        IntUtil.rejectIfInvalidInt(orderId);
	        boolean orderExists = OrderDAO.orderExists(orderId);

	        if (!orderExists) {
	            throw new RuntimeException("Order does not exist");
	        }

	        OrderValidator.validate(newOrder);

	        OrderDAO orderDAO = new OrderDAO();
	        orderDAO.update(orderId, newOrder);
	    } catch (PersistanceException e) {
	        e.printStackTrace();
	        throw new ServiceException(e.getMessage());
	    }
	}
	
	public List<OrderDetailDTO> getOrdersByUserId(int userId) throws ServiceException, ValidationException{
		List<OrderDetailDTO> orderList = new ArrayList<>();
		
		ProductService productService = new ProductService();
        
        ProductPriceService priceService = new ProductPriceService();
		
		try {
			OrderDAO orderDAO = new OrderDAO();
			orderList = orderDAO.findOrdersByUserId(userId);
			
			for(OrderDetailDTO order : orderList) {
				ProductDetailDTO product = productService.getByProductId(order.getProduct().getId());
				ProductPrice price = priceService.findByPriceId(order.getProductPrice().getId());
				order.setProductPrice(price);
				order.setProduct(product);
			}
			
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return orderList;
	}
	
	public OrderDetailDTO getOrderByOrderId(int orderId) throws ServiceException, ValidationException{
		
		IntUtil.rejectIfInvalidInt(orderId);
		
		OrderDetailDTO order = new OrderDetailDTO();
		
		ProductService productService = new ProductService();
        
        ProductPriceService priceService = new ProductPriceService();
		
		try {
			OrderDAO orderDAO = new OrderDAO();
			order = orderDAO.findOrdersByOrderId(orderId);
				ProductDetailDTO product = productService.getByProductId(order.getProduct().getId());
				ProductPrice price = priceService.findByPriceId(order.getProductPrice().getId());
				order.setProductPrice(price);
				order.setProduct(product);
			
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return order;
	}
	
	public void changeStatusOfOrder(int orderId, OrderStatus status) throws ServiceException, ValidationException {
		IntUtil.rejectIfInvalidInt(orderId);
		

		try {
			OrderDAO orderDAO = new OrderDAO();
			orderDAO.changeStatus(orderId, status);
			
		} catch (PersistanceException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
}
