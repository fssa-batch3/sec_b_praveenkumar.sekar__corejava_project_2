package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Time;
import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.dto.OrderDetailDTO;
import in.fssa.homebakery.service.OrderService;

public class TestCreateOrder {
	
	@Test
	@Order(1)
	public void testCreateOrderWithValidInput() {
	    OrderService orderService = new OrderService();

	    OrderDetailDTO orderDetailDto = new OrderDetailDTO();

	    // Set order details
	    orderDetailDto.setAddress("123 Main Street");
	    orderDetailDto.setStatus(OrderDetailDTO.OrderStatus.NOT_DELIVERED);
	    orderDetailDto.setQuantity(2);
	    Date currentDate = new Date();
	    Time currentTime = new Time(currentDate.getTime()); // Current time

	    orderDetailDto.setDeliveryDate(currentDate);
	    orderDetailDto.setDeliveryTime(currentTime);

	    assertDoesNotThrow(() -> {
	        orderService.createOrder(orderDetailDto, 1, 1, "john@example.com");
	    });
	}
}
