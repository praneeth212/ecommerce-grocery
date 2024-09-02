package com.grocery.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.grocery.exception.ImageServiceException;
import com.grocery.exception.ProductException;
import com.grocery.model.Category;
import com.grocery.model.Product;
import com.grocery.model.ProductItem;
import com.grocery.model.Units;
import com.grocery.repository.ProductRepository;
import com.grocery.service.CategoryService;
import com.grocery.service.ImageStorageService;
import com.grocery.service.ProductService;
import com.grocery.service.UnitsService;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ImageStorageService imageStorageService;

    @Mock
    private UnitsService unitService;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setProdName("Test Product 1");
        product1.setImageUrl("URL 1");
        product1.setDescription("This is a test product 1");
        product1.setBrand("Test Brand 1");
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Test Category 1");
        ProductItem productItem1 = new ProductItem();
        productItem1.setId(1L);
        productItem1.setQuantityInStock(10);
        productItem1.setPrice(100.0);
        productItem1.setProduct(product1);
        product1.setCategory(category1);
        product1.setProductItems(new ArrayList<>(Collections.singletonList(productItem1)));

        product2 = new Product();
        product2.setId(2L);
        product2.setProdName("Test Product 2");
        product2.setImageUrl("URL 2");
        product2.setDescription("This is a test product 2");
        product2.setBrand("Test Brand 2");
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Test Category 2");
        ProductItem productItem2 = new ProductItem();
        productItem2.setId(2L);
        productItem2.setQuantityInStock(10);
        productItem2.setPrice(100.0);
        productItem2.setProduct(product2);
        product2.setCategory(category2);
        product2.setProductItems(new ArrayList<>(Collections.singletonList(productItem2)));
    }

    @Test
    void testProductInitialization() {
        assertNotNull(product1);
        assertNotNull(product1.getId());
        assertNotNull(product1.getProdName());
        assertNotNull(product1.getImageUrl());
        assertNotNull(product1.getDescription());
        assertNotNull(product1.getBrand());
        assertNotNull(product1.getCategory());
        assertNotNull(product1.getProductItems());
    }

    @Test
    void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> foundProducts = productService.findAllProducts();

        Product foundProduct1 = foundProducts.get(0);
        assertEquals(product1.getId(), foundProduct1.getId());
        assertEquals(product1.getProdName(), foundProduct1.getProdName());
        assertEquals(product1.getImageUrl(), foundProduct1.getImageUrl());
        assertEquals(product1.getDescription(), foundProduct1.getDescription());
        assertEquals(product1.getBrand(), foundProduct1.getBrand());
        assertEquals(product1.getCategory().getId(), foundProduct1.getCategory().getId());
        assertEquals(product1.getCategory().getName(), foundProduct1.getCategory().getName());

        Product foundProduct2 = foundProducts.get(1);
        assertEquals(product2.getId(), foundProduct2.getId());
        assertEquals(product2.getProdName(), foundProduct2.getProdName());
        assertEquals(product2.getImageUrl(), foundProduct2.getImageUrl());
        assertEquals(product2.getDescription(), foundProduct2.getDescription());
        assertEquals(product2.getBrand(), foundProduct2.getBrand());
        assertEquals(product2.getCategory().getId(), foundProduct2.getCategory().getId());
        assertEquals(product2.getCategory().getName(), foundProduct2.getCategory().getName());

        assertEquals(1, foundProduct1.getProductItems().size());
        assertEquals(1L, foundProduct1.getProductItems().get(0).getId());
        assertEquals(10, foundProduct1.getProductItems().get(0).getQuantityInStock());
        assertEquals(100.0, foundProduct1.getProductItems().get(0).getPrice());
        assertEquals(product1, foundProduct1);

        assertEquals(1, foundProduct2.getProductItems().size());
        assertEquals(2L, foundProduct2.getProductItems().get(0).getId());
        assertEquals(10, foundProduct2.getProductItems().get(0).getQuantityInStock());
        assertEquals(100.0, foundProduct2.getProductItems().get(0).getPrice());
        assertEquals(product2, foundProduct2);
    }

    @Test
    void testFindProductById() throws ProductException {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        Product foundProduct = productService.findProduct(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals("Test Product 1", foundProduct.getProdName());
        assertEquals("URL 1", foundProduct.getImageUrl());
        assertEquals("This is a test product 1", foundProduct.getDescription());
        assertEquals("Test Brand 1", foundProduct.getBrand());
        assertNotNull(foundProduct.getCategory());
        assertNotNull(foundProduct.getProductItems());
        assertEquals(1, foundProduct.getProductItems().size());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindProductByIdNotFound() {
        Long productId = 2L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class, () -> productService.findProduct(productId));

        assertEquals("Product not found with id " + productId, exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testSaveProduct_withNewCategoryAndUnit() throws ImageServiceException {
        Product product = new Product();
        Category category = new Category();
        category.setName("TestCategory");
        product.setCategory(category);

        Units unit = new Units();
        unit.setUnitName("TestUnit");

        ProductItem productItem = new ProductItem();
        productItem.setUnit(unit);
        product.setProductItems(Arrays.asList(productItem));

        MultipartFile categoryImageFile = mock(MultipartFile.class);
        MultipartFile productImageFile = mock(MultipartFile.class);

        when(categoryImageFile.isEmpty()).thenReturn(false);
        when(productImageFile.isEmpty()).thenReturn(false);
        when(imageStorageService.storeFile(categoryImageFile)).thenReturn("categoryImageUrl");
        when(imageStorageService.storeFile(productImageFile)).thenReturn("productImageUrl");
        when(categoryService.findByName(anyString())).thenReturn(Optional.empty());
        when(unitService.findByUnitName(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(unitService.save(any(Units.class))).thenReturn(unit);

        Product savedProduct = productService.saveProduct(product, categoryImageFile, productImageFile);

        assertNotNull(savedProduct);
        assertEquals("categoryImageUrl", savedProduct.getCategory().getImageUrl());
        assertEquals("productImageUrl", savedProduct.getImageUrl());
        verify(categoryService, times(1)).saveCategory(any(Category.class), eq(categoryImageFile));
        verify(unitService, times(1)).save(any(Units.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_withExistingCategoryAndUnit() throws ImageServiceException {
        Product product = new Product();
        Category category = new Category();
        category.setName("TestCategory");
        product.setCategory(category);

        Units unit = new Units();
        unit.setUnitName("TestUnit");

        ProductItem productItem = new ProductItem();
        productItem.setUnit(unit);
        product.setProductItems(Arrays.asList(productItem));

        MultipartFile categoryImageFile = mock(MultipartFile.class);
        MultipartFile productImageFile = mock(MultipartFile.class);

        when(categoryImageFile.isEmpty()).thenReturn(true);
        when(productImageFile.isEmpty()).thenReturn(true);
        when(categoryService.findByName(anyString())).thenReturn(Optional.of(category));
        when(unitService.findByUnitName(anyString())).thenReturn(Optional.of(unit));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product, categoryImageFile, productImageFile);

        assertNotNull(savedProduct);
        assertNull(savedProduct.getCategory().getImageUrl());
        assertNull(savedProduct.getImageUrl());
        verify(categoryService, times(0)).saveCategory(any(Category.class), eq(categoryImageFile));
        verify(unitService, times(0)).save(any(Units.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_withNoCategoryOrUnit() throws ImageServiceException {
        Product product = new Product();
        product.setProdName("TestProduct");
        product.setDescription("TestDescription");

        MultipartFile categoryImageFile = mock(MultipartFile.class);
        MultipartFile productImageFile = mock(MultipartFile.class);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product, categoryImageFile, productImageFile);

        assertNotNull(savedProduct);
        assertNull(savedProduct.getCategory());
        assertNull(savedProduct.getImageUrl());
        verify(categoryService, times(0)).saveCategory(any(Category.class), eq(categoryImageFile));
        verify(unitService, times(0)).save(any(Units.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_withOnlyProductImage() throws ImageServiceException {
        Product product = new Product();
        product.setProdName("TestProduct");
        product.setDescription("TestDescription");

        MultipartFile productImageFile = mock(MultipartFile.class);

        when(productImageFile.isEmpty()).thenReturn(false);
        when(imageStorageService.storeFile(productImageFile)).thenReturn("productImageUrl");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product, null, productImageFile);

        assertNotNull(savedProduct);
        assertEquals("productImageUrl", savedProduct.getImageUrl());
        verify(categoryService, times(0)).saveCategory(any(Category.class), any(MultipartFile.class));
        verify(unitService, times(0)).save(any(Units.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_withOnlyCategoryImage() throws ImageServiceException {
        Product product = new Product();
        Category category = new Category();
        category.setName("TestCategory");
        product.setCategory(category);

        MultipartFile categoryImageFile = mock(MultipartFile.class);
        MultipartFile productImageFile = mock(MultipartFile.class);

        when(categoryImageFile.isEmpty()).thenReturn(false);
        when(productImageFile.isEmpty()).thenReturn(true);
        when(imageStorageService.storeFile(categoryImageFile)).thenReturn("categoryImageUrl");
        when(categoryService.findByName(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product, categoryImageFile, productImageFile);

        assertNotNull(savedProduct);
        assertEquals("categoryImageUrl", savedProduct.getCategory().getImageUrl());
        assertNull(savedProduct.getImageUrl());
        verify(categoryService, times(1)).saveCategory(any(Category.class), eq(categoryImageFile));
        verify(unitService, times(0)).save(any(Units.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }
    
    @Test
    void testUpdateProduct() throws ProductException, ImageServiceException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        lenient().when(imageStorageService.storeFile(any(MultipartFile.class))).thenReturn("imageUrl");
        lenient().when(categoryService.findByName(anyString())).thenReturn(Optional.of(product1.getCategory()));
        lenient().when(unitService.findByUnitName(anyString())).thenReturn(Optional.of(new Units()));

        Product result = productService.updateProduct(1L, product2, null, null);

        assertNotNull(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product1);
    }

    
    @Test
    void testUpdateBasicProductDetails() {
        productService.updateBasicProductDetails(product1, product2);

        assertEquals(product2.getProdName(), product1.getProdName());
        assertEquals(product2.getBrand(), product1.getBrand());
        assertEquals(product2.getDescription(), product1.getDescription());
    }

    @Test
    void testUpdateProductImage() throws ImageServiceException {
        MultipartFile productImageFile = mock(MultipartFile.class);
        when(productImageFile.isEmpty()).thenReturn(false);
        when(imageStorageService.storeFile(productImageFile)).thenReturn("productImageUrl");

        productService.updateProductImage(product1, productImageFile);
        assertEquals("productImageUrl", product1.getImageUrl());
        verify(imageStorageService, times(1)).storeFile(productImageFile);
    }

    @Test
    void testUpdateProductCategory() throws ProductException, ImageServiceException {
        MultipartFile categoryImageFile = mock(MultipartFile.class);
        when(categoryImageFile.isEmpty()).thenReturn(false);
        when(imageStorageService.storeFile(categoryImageFile)).thenReturn("categoryImageUrl");

        Category newCategory = new Category();
        newCategory.setName("New Category");
        product2.setCategory(newCategory);

        when(categoryService.findByName(newCategory.getName())).thenReturn(Optional.of(newCategory));

        productService.updateProductCategory(product1, product2, categoryImageFile);
        verify(imageStorageService, times(1)).storeFile(categoryImageFile);
        verify(categoryService, times(1)).findByName(newCategory.getName());
        assertEquals("categoryImageUrl", product1.getCategory().getImageUrl());
    }
    
    @Test
    void testSaveOrUpdateUnit() {
        ProductItem item = new ProductItem();
        Units unit = new Units();
        unit.setUnitName("New Unit");
        item.setUnit(unit);

        when(unitService.findByUnitName(anyString())).thenReturn(Optional.of(unit));

        productService.saveOrUpdateUnit(item);

        verify(unitService, times(1)).findByUnitName(anyString());
        verify(unitService, times(0)).save(any(Units.class));
    }

    @Test
    void testSaveOrUpdateUnitWhenNewUnit() {
        ProductItem item = new ProductItem();
        Units unit = new Units();
        unit.setUnitName("New Unit");
        item.setUnit(unit);

        when(unitService.findByUnitName(anyString())).thenReturn(Optional.empty());
        when(unitService.save(any(Units.class))).thenReturn(unit);

        productService.saveOrUpdateUnit(item);

        verify(unitService, times(1)).findByUnitName(anyString());
        verify(unitService, times(1)).save(any(Units.class));
    }

    @Test
    void testSaveOrUpdateProductItems() {
        Product product = new Product();
        ProductItem item1 = new ProductItem();
        Units unit1 = new Units();
        unit1.setUnitName("Unit 1");
        item1.setUnit(unit1);

        ProductItem item2 = new ProductItem();
        Units unit2 = new Units();
        unit2.setUnitName("Unit 2");
        item2.setUnit(unit2);

        product.setProductItems(Arrays.asList(item1, item2));

        when(unitService.findByUnitName("Unit 1")).thenReturn(Optional.of(unit1));
        when(unitService.findByUnitName("Unit 2")).thenReturn(Optional.of(unit2));

        productService.saveOrUpdateProductItems(product);

        verify(unitService, times(1)).findByUnitName("Unit 1");
        verify(unitService, times(1)).findByUnitName("Unit 2");
        verify(unitService, times(0)).save(any(Units.class));
    }
    
}

