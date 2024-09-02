package com.grocery.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grocery.exception.ProductItemException;
import com.grocery.model.Product;
import com.grocery.model.ProductItem;
import com.grocery.model.Units;
import com.grocery.repository.ProductItemRepository;
import com.grocery.service.ProductItemService;
import com.grocery.service.ProductService;
import com.grocery.service.UnitsService;

@ExtendWith(MockitoExtension.class)
public class ProductItemServiceTests {

    @Mock
    private ProductItemRepository productItemRepository;

    @Mock
    private UnitsService unitsService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductItemService productItemService;

    private Product product;
    private ProductItem productItem;
    private Units unit;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setProdName("Test Product");
        product.setDescription("Test Description");
        product.setBrand("Test Brand");

        unit = new Units();
        unit.setId(1L);
        unit.setUnitName("Test Unit");

        productItem = new ProductItem();
        productItem.setId(1L);
        productItem.setPrice(100.0);
        productItem.setSalePrice(90.0);
        productItem.setQuantityInStock(10);
        productItem.setProduct(product);
        productItem.setUnit(unit);
    }

    @Test
    void testSaveProductItem_withExistingUnit() {
        when(unitsService.findByUnitName(anyString())).thenReturn(Optional.of(unit));
        when(productItemRepository.save(any(ProductItem.class))).thenReturn(productItem);

        ProductItem savedProductItem = productItemService.saveProductItem(productItem);

        assertNotNull(savedProductItem);
        assertEquals(unit, savedProductItem.getUnit());
        verify(unitsService, times(1)).findByUnitName(anyString());
        verify(productItemRepository, times(1)).save(any(ProductItem.class));
    }

    @Test
    void testSaveProductItem_withNewUnit() {
        when(unitsService.findByUnitName(anyString())).thenReturn(Optional.empty());
        when(unitsService.save(any(Units.class))).thenReturn(unit);
        when(productItemRepository.save(any(ProductItem.class))).thenReturn(productItem);

        ProductItem savedProductItem = productItemService.saveProductItem(productItem);

        assertNotNull(savedProductItem);
        assertEquals(unit, savedProductItem.getUnit());
        verify(unitsService, times(1)).findByUnitName(anyString());
        verify(unitsService, times(1)).save(any(Units.class));
        verify(productItemRepository, times(1)).save(any(ProductItem.class));
    }

    @Test
    void testDeleteProductItem() throws ProductItemException {
        when(productItemRepository.findById(anyLong())).thenReturn(Optional.of(productItem));
        doNothing().when(productItemRepository).delete(any(ProductItem.class));
        doNothing().when(productService).deleteProductIfNoItemsLeft(anyLong());

        productItemService.deleteProductItem(productItem.getId());

        verify(productItemRepository, times(1)).findById(anyLong());
        verify(productItemRepository, times(1)).delete(any(ProductItem.class));
        verify(productService, times(1)).deleteProductIfNoItemsLeft(anyLong());
    }

    @Test
    void testDeleteProductItem_notFound() throws ProductItemException {
        when(productItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        productItemService.deleteProductItem(productItem.getId());

        verify(productItemRepository, times(1)).findById(anyLong());
        verify(productItemRepository, times(0)).delete(any(ProductItem.class));
        verify(productService, times(0)).deleteProductIfNoItemsLeft(anyLong());
    }

    @Test
    void testUpdateStockQuantity() throws ProductItemException {
        int newQuantity = 20;
        when(productItemRepository.findById(anyLong())).thenReturn(Optional.of(productItem));
        when(productItemRepository.save(any(ProductItem.class))).thenReturn(productItem);

        ProductItem updatedProductItem = productItemService.updateStockQuantity(productItem.getId(), newQuantity);

        assertNotNull(updatedProductItem);
        assertEquals(newQuantity, updatedProductItem.getQuantityInStock());
        verify(productItemRepository, times(1)).findById(anyLong());
        verify(productItemRepository, times(1)).save(any(ProductItem.class));
    }

    @Test
    void testUpdateStockQuantity_notFound() {
        when(productItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductItemException.class, () -> {
            productItemService.updateStockQuantity(productItem.getId(), 20);
        });

        verify(productItemRepository, times(1)).findById(anyLong());
        verify(productItemRepository, times(0)).save(any(ProductItem.class));
    }
}
