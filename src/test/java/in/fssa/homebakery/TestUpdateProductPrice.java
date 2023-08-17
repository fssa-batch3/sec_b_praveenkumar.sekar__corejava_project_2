package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.service.ProductPriceService;
import in.fssa.homebakery.service.ProductService;

public class TestUpdateProductPrice {
	
	@Test
    public void testUpdateValidProduct() throws Exception {
		
		ProductPriceService productService = new ProductPriceService();
        // Arrange
        int productId = 1;
        ProductPrice productPrice = new ProductPrice();
        productPrice.setQuantity(2);
        productPrice.setType(ProductPrice.QuantityType.KG);
        productPrice.setPrice(10);
        double quantity = 2.0;

        // Act and Assert
        assertDoesNotThrow(() -> {
            productService.update(productId, productPrice, quantity);
        });
    }

    @Test
    public void testUpdateInvalidProduct() {
    	
    	ProductPriceService productService = new ProductPriceService();
        // Arrange
        int productId = 1;
        ProductPrice productPrice = null;// Initialize with valid values
        double quantity = 2.0;

        // Act and Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            productService.update(productId, productPrice, quantity);
        });
        
        String expectedMessage = "Price cannot be null";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
    }

    @Test
    public void testUpdateInvalidQuantity() {
    	
    	ProductPriceService productService = new ProductPriceService();
    	
        // Arrange
        int productId = 1;
        ProductPrice productPrice = new ProductPrice();
        productPrice.setQuantity(1);
        productPrice.setType(ProductPrice.QuantityType.KG);
        productPrice.setPrice(10);
        // Set the start date// Initialize with valid values
        double quantity = 0.0;

        // Act and Assert
        Exception exception = assertThrows(ValidationException.class, () -> {
            productService.update(productId, productPrice, quantity);
        });
        
        String expectedMessage = "Invalid Quantity";
		String actualMessage = exception.getMessage();
		
		assertTrue(expectedMessage.equals(actualMessage));
    }
}
