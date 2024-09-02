package com.grocery.controllertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.controller.ProductController;
import com.grocery.dto.ProductDetailDTO;
import com.grocery.model.Category;
import com.grocery.model.Product;
import com.grocery.model.ProductItem;
import com.grocery.service.CategoryService;
import com.grocery.service.ProductItemService;
import com.grocery.service.ProductService;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @Mock
    private ProductItemService productItemService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private Product product;
    private Category category;
    private ProductItem productItem;
    private ProductDetailDTO productDetailDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        category = new Category();
        category.setId(1L);
        category.setName("Exotic Fruits and Vegetables");
        category.setDescription("Assorted range of exotic fruits and vegetables");
        category.setImageUrl("http://example.com/images/exotic.jpg");

        product = new Product();
        product.setId(1L);
        product.setProdName("Avocado");
        product.setDescription("Fresh avocados");
        product.setBrand("AvocadoCo.");
        product.setImageUrl("http://example.com/images/avocado.jpg");
        product.setCategory(category);

        productItem = new ProductItem();
        productItem.setId(1L);
        productItem.setPrice(120.0);
        productItem.setSalePrice(110.0);
        productItem.setQuantityInStock(50);
        productItem.setProduct(product);

        product.setProductItems(Arrays.asList(productItem));

        productDetailDTO = new ProductDetailDTO();
        productDetailDTO.setCategoryName(category.getName());
        productDetailDTO.setProductId(product.getId());
        productDetailDTO.setProdName(product.getProdName());
        productDetailDTO.setProductImg(product.getImageUrl());
        productDetailDTO.setBrand(product.getBrand());
        productDetailDTO.setDescription(product.getDescription());
        productDetailDTO.setProductItemId(productItem.getId());
        productDetailDTO.setPrice(productItem.getPrice());
        productDetailDTO.setSalePrice(productItem.getSalePrice());
        productDetailDTO.setDiscountPercentage(8.33);
        productDetailDTO.setQuantityInStock(productItem.getQuantityInStock());
        productDetailDTO.setUnitName("250 Gms");
    }

    @Test
    void testGetProductDetails() throws Exception {
        when(productService.getProductDetails()).thenReturn(Arrays.asList(productDetailDTO));

        mockMvc.perform(get("/api/products/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prodName").value(product.getProdName()));

        verify(productService, times(1)).getProductDetails();
    }

    @Test
    void testGetProductByID() throws Exception {
        when(productService.findProduct(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/products/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()));

        verify(productService, times(1)).findProduct(anyLong());
    }

    @Test
    void testGetTopDiscountedProducts() throws Exception {
        when(productService.displayProductsByDiscount()).thenReturn(Arrays.asList(productDetailDTO));

        mockMvc.perform(get("/api/products/getdiscountedproducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prodName").value(product.getProdName()));

        verify(productService, times(1)).displayProductsByDiscount();
    }

    @Test
    void testSearchProduct() throws Exception {
        when(productService.searchProductDetails(anyString())).thenReturn(Arrays.asList(productDetailDTO));

        mockMvc.perform(get("/api/products/search/{query}", "Avocado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prodName").value(product.getProdName()));

        verify(productService, times(1)).searchProductDetails(anyString());
    }

    @Test
    void testGetProductDetail() throws Exception {
        when(productService.getProductDetailByProductItemId(anyLong())).thenReturn(Optional.of(productDetailDTO));

        mockMvc.perform(get("/api/products/getdetail/{productItemId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(product.getId()));

        verify(productService, times(1)).getProductDetailByProductItemId(anyLong());
    }

    @Test
    void testCreateProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        when(productService.saveProduct(any(Product.class), any(MultipartFile.class), any(MultipartFile.class))).thenReturn(product);

        MockMultipartFile categoryImageFile = new MockMultipartFile("categoryImageFile", new byte[0]);
        MockMultipartFile productImageFile = new MockMultipartFile("productImageFile", new byte[0]);
        MockMultipartFile productJsonFile = new MockMultipartFile("product", "", "application/json", productJson.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/products/create")
                .file(categoryImageFile)
                .file(productImageFile)
                .file(productJsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()));

        verify(productService, times(1)).saveProduct(any(Product.class), any(MultipartFile.class), any(MultipartFile.class));
    }

    @Test
    void testEditProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        when(productService.updateProduct(anyLong(), any(Product.class), any(MultipartFile.class), any(MultipartFile.class))).thenReturn(product);

        MockMultipartFile categoryImageFile = new MockMultipartFile("categoryImageFile", new byte[0]);
        MockMultipartFile productImageFile = new MockMultipartFile("productImageFile", new byte[0]);
        MockMultipartFile productJsonFile = new MockMultipartFile("product", "", "application/json", productJson.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/products/editproduct/{id}", 1L)
                .file(categoryImageFile)
                .file(productImageFile)
                .file(productJsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()));

        verify(productService, times(1)).updateProduct(anyLong(), any(Product.class), any(MultipartFile.class), any(MultipartFile.class));
    }


    @Test
    void testGetAllProducts() throws Exception {
        when(productService.findAllProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products/viewproducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product.getId()));

        verify(productService, times(1)).findAllProducts();
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(anyLong());
    }

    @Test
    void testCreateProductItem() throws Exception {
        when(productItemService.saveProductItem(any(ProductItem.class))).thenReturn(productItem);

        ObjectMapper objectMapper = new ObjectMapper();
        String productItemJson = objectMapper.writeValueAsString(productItem);

        mockMvc.perform(post("/api/productitems/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productItem.getId()));

        verify(productItemService, times(1)).saveProductItem(any(ProductItem.class));
    }

    @Test
    void testDeleteProductItem() throws Exception {
        doNothing().when(productItemService).deleteProductItem(anyLong());

        mockMvc.perform(delete("/api/productitems/delete/{id}", 1L))
                .andExpect(status().isOk());

        verify(productItemService, times(1)).deleteProductItem(anyLong());
    }

    @Test
    void testGetAllProductItems() throws Exception {
        when(productService.getProductDetails()).thenReturn(Arrays.asList(productDetailDTO));

        mockMvc.perform(get("/api/productitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(product.getId()));

        verify(productService, times(1)).getProductDetails();
    }

    @Test
    void testGetProductsByProdId() throws Exception {
        when(productService.getProductDetailsByProdId(anyLong())).thenReturn(Arrays.asList(productDetailDTO));

        mockMvc.perform(get("/api/productitems/getbyproduct/{prodId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(product.getId()));

        verify(productService, times(1)).getProductDetailsByProdId(anyLong());
    }

    @Test
    void testGetProductItemById() throws Exception {
        when(productService.getProductDetailByProductItemId(anyLong())).thenReturn(Optional.of(productDetailDTO));

        mockMvc.perform(get("/api/productitems/{productItemId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(product.getId()));

        verify(productService, times(1)).getProductDetailByProductItemId(anyLong());
    }

    @Test
    void testUpdateQuantityInStock() throws Exception {
        when(productItemService.updateStockQuantity(anyLong(), anyInt())).thenReturn(productItem);

        mockMvc.perform(put("/api/productitems/quantity/{productItemId}/{newQuantity}", 1L, 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityInStock").value(productItem.getQuantityInStock()));

        verify(productItemService, times(1)).updateStockQuantity(anyLong(), anyInt());
    }

    @Test
    void testGetAllCategories() throws Exception {
        when(categoryService.findAllCategories()).thenReturn(Arrays.asList(category));

        mockMvc.perform(get("/api/categories/viewcategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(category.getId()));

        verify(categoryService, times(1)).findAllCategories();
    }

    @Test
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyLong());

        mockMvc.perform(delete("/api/categories/deletecategory/{id}", 1L))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }
}
