package in.fssa.homebakery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import in.fssa.homebakery.dto.ProductDetailDTO;
import in.fssa.homebakery.exception.ValidationException;
import in.fssa.homebakery.model.ProductPrice;
import in.fssa.homebakery.service.ProductService;

public class TestCreateProduct {
	
	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis()); // Current timestamp

	@Test
	@Order(1)
	public void testCreateProductWithVailidInput() {
		ProductService productService = new ProductService();

		ProductDetailDTO productDetailDto = new ProductDetailDTO();

		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription("You can sleep very good");
		productDetailDto.setCategoryId(3);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> priceList = new ArrayList<>();

		ProductPrice price1 = new ProductPrice();
		price1.setQuantity(1);
		price1.setType(ProductPrice.QuantityType.KG);
		price1.setPrice(10);
		price1.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price2 = new ProductPrice();
		price2.setQuantity(2);
		price2.setType(ProductPrice.QuantityType.KG);
		price2.setPrice(20);
		price2.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price3 = new ProductPrice();
		price3.setQuantity(3);
		price3.setType(ProductPrice.QuantityType.KG);
		price3.setPrice(30);
		price3.setStartDate(currentTimestamp); // Set the start date

		priceList.add(price1);
		priceList.add(price2);
		priceList.add(price3);

		productDetailDto.setPrices(priceList);

		assertDoesNotThrow(() -> {
			productService.createProduct(productDetailDto);
		});

	}

	@Test
	@Order(2)
	public void testCreateProductWithInvalidInput() {
		ProductService productService = new ProductService();

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(null);
		});

		String expectedMessage = "Invalid product input";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));

	}

	@Test
	@Order(3)
	public void testCreateProductWithInvalidProductName() {
		ProductService productService = new ProductService();

		ProductDetailDTO productDetailDto = new ProductDetailDTO();

		productDetailDto.setName("");
		productDetailDto.setDescription("You can sleep very good");
		productDetailDto.setCategoryId(3);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> priceList = new ArrayList<>();

		ProductPrice price1 = new ProductPrice();
		price1.setQuantity(1);
		price1.setType(ProductPrice.QuantityType.KG);
		price1.setPrice(10);
		price1.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price2 = new ProductPrice();
		price2.setQuantity(2);
		price2.setType(ProductPrice.QuantityType.KG);
		price2.setPrice(20);
		price2.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price3 = new ProductPrice();
		price3.setQuantity(3);
		price3.setType(ProductPrice.QuantityType.KG);
		price3.setPrice(30);
		price3.setStartDate(currentTimestamp); // Set the start date

		priceList.add(price1);
		priceList.add(price2);
		priceList.add(price3);

		productDetailDto.setPrices(priceList);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "Product Name cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));

	}

	@Test
	@Order(4)
	public void testCreateProductWithInvalidDescription() {
		ProductService productService = new ProductService();

		ProductDetailDTO productDetailDto = new ProductDetailDTO();

		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription(null);
		productDetailDto.setCategoryId(3);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> priceList = new ArrayList<>();

		ProductPrice price1 = new ProductPrice();
		price1.setQuantity(1);
		price1.setType(ProductPrice.QuantityType.KG);
		price1.setPrice(10);
		price1.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price2 = new ProductPrice();
		price2.setQuantity(2);
		price2.setType(ProductPrice.QuantityType.KG);
		price2.setPrice(20);
		price2.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price3 = new ProductPrice();
		price3.setQuantity(3);
		price3.setType(ProductPrice.QuantityType.KG);
		price3.setPrice(30);
		price3.setStartDate(currentTimestamp); // Set the start date

		priceList.add(price1);
		priceList.add(price2);
		priceList.add(price3);

		productDetailDto.setPrices(priceList);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "Description cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));

	}

	@Test
	@Order(5)
	public void testCreateProductWithInvalidPriceList() {
		ProductService productService = new ProductService();

		ProductDetailDTO productDetailDto = new ProductDetailDTO();

		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription("Hello");
		productDetailDto.setCategoryId(3);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "Price List cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(6)
	public void testCreateProductWithEmptyPriceList() {
		ProductService productService = new ProductService();

		ProductDetailDTO productDetailDto = new ProductDetailDTO();

		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription("Hello");
		productDetailDto.setCategoryId(3);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> list = new ArrayList<>();
		productDetailDto.setPrices(list);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "No price found";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	@Order(7)
	public void testCreateProductWithInvalidPrice() {
		ProductService productService = new ProductService();

		ProductDetailDTO productDetailDto = new ProductDetailDTO();

		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription("Hello");
		productDetailDto.setCategoryId(3);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> priceList = new ArrayList<>();

		priceList.add(null);

		productDetailDto.setPrices(priceList);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "Price cannot be null";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));

	}
	
	@Test 
	@Order(8)
	public void testCreateProductWithInvalidCategoryId() {
		ProductService productService = new ProductService();
		ProductDetailDTO productDetailDto = new ProductDetailDTO();
		
		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription("You can sleep very good");
		productDetailDto.setCategoryId(4);
		productDetailDto.setImageUrl("https://picsum.photos/200");
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> priceList = new ArrayList<>();

		ProductPrice price1 = new ProductPrice();
		price1.setQuantity(1);
		price1.setType(ProductPrice.QuantityType.KG);
		price1.setPrice(10);
		price1.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price2 = new ProductPrice();
		price2.setQuantity(2);
		price2.setType(ProductPrice.QuantityType.KG);
		price2.setPrice(20);
		price2.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price3 = new ProductPrice();
		price3.setQuantity(3);
		price3.setType(ProductPrice.QuantityType.KG);
		price3.setPrice(30);
		price3.setStartDate(currentTimestamp); // Set the start date

		priceList.add(price1);
		priceList.add(price2);
		priceList.add(price3);

		productDetailDto.setPrices(priceList);
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "Invalid category Id";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
		
		
	}
	
	public void testCreateProductWithInvalidImageUrl() {
		ProductService productService = new ProductService();
		ProductDetailDTO productDetailDto = new ProductDetailDTO();
		
		productDetailDto.setName("Banana cake");
		productDetailDto.setDescription("You can sleep very good");
		productDetailDto.setCategoryId(2);
		productDetailDto.setImageUrl(null);
		productDetailDto.setVeg(false);
		productDetailDto.setActive(true);

		List<ProductPrice> priceList = new ArrayList<>();

		ProductPrice price1 = new ProductPrice();
		price1.setQuantity(1);
		price1.setType(ProductPrice.QuantityType.KG);
		price1.setPrice(10);
		price1.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price2 = new ProductPrice();
		price2.setQuantity(2);
		price2.setType(ProductPrice.QuantityType.KG);
		price2.setPrice(20);
		price2.setStartDate(currentTimestamp); // Set the start date

		ProductPrice price3 = new ProductPrice();
		price3.setQuantity(3);
		price3.setType(ProductPrice.QuantityType.KG);
		price3.setPrice(30);
		price3.setStartDate(currentTimestamp); // Set the start date

		priceList.add(price1);
		priceList.add(price2);
		priceList.add(price3);

		productDetailDto.setPrices(priceList);
		
		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.createProduct(productDetailDto);
		});

		String expectedMessage = "Image Url cannot be null or empty";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}
	
}
