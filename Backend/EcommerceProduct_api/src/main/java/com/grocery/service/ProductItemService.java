package com.grocery.service;

import com.grocery.exception.ProductItemException;
import com.grocery.model.ProductItem;
import com.grocery.model.Units;
import com.grocery.repository.ProductItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductItemService {
	
    private ProductItemRepository productItemRepository;
    private UnitsService unitsService;
    private ProductService productService;
    
    public ProductItemService(ProductItemRepository productItemRepository, UnitsService unitsService, ProductService productService) {
    	this.productItemRepository = productItemRepository;
    	this.unitsService = unitsService;
    	this.productService = productService;
    }

    
    public List<ProductItem> findAllProductItems() {
        return productItemRepository.findAll();
    }

    @Transactional
    public ProductItem saveProductItem(ProductItem productItem) {
        // Check if the unit exists, if not create it
        if (productItem.getUnit() != null) {
            Optional<Units> existingUnit = unitsService.findByUnitName(productItem.getUnit().getUnitName());
            if (existingUnit.isPresent()) {
                productItem.setUnit(existingUnit.get());
            } else {
                Units newUnit = unitsService.save(productItem.getUnit());
                productItem.setUnit(newUnit);
            }
        }
        return productItemRepository.save(productItem);
    }


    public void deleteProductItem(Long productItemId) {
        ProductItem productItem = productItemRepository.findById(productItemId).orElse(null);
        if (productItem != null) {
            productItemRepository.delete(productItem);
            productService.deleteProductIfNoItemsLeft(productItem.getProduct().getId());
        }
    }
    
    @Transactional
    public ProductItem updateStockQuantity(Long productItemId, int newQuantity) throws ProductItemException{
        ProductItem productItem = productItemRepository.findById(productItemId)
                .orElseThrow(()->new ProductItemException("No product item found for the id: "+productItemId));
        productItem.setQuantityInStock(newQuantity);
        return productItemRepository.save(productItem);
    }
}
