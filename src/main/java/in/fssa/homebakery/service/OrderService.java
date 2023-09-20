package in.fssa.homebakery.service;

import com.google.protobuf.ServiceException;

import in.fssa.homebakery.dao.OrderDAO;
import in.fssa.homebakery.dao.ProductDAO;
import in.fssa.homebakery.dao.ProductPriceDAO;
import in.fssa.homebakery.dao.UserDAO;
import in.fssa.homebakery.dto.OrderDetailDTO;
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
	
}
