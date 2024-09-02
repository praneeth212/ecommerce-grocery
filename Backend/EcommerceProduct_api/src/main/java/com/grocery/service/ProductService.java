package com.grocery.service;

import com.grocery.dto.ProductDetailDTO;
import com.grocery.dto.ProductItemDetails;
import com.grocery.exception.ImageServiceException;
import com.grocery.exception.ProductException;
import com.grocery.model.Category;
import com.grocery.model.Product;
import com.grocery.model.ProductItem;
import com.grocery.model.Units;
import com.grocery.repository.ProductRepository;

import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UnitsService unitService;
    private final ImageStorageService imageStorageService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService,
                          UnitsService unitService, ImageStorageService imageStorageService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.imageStorageService = imageStorageService;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProduct(Long id) throws ProductException {
        return productRepository.findById(id).orElseThrow(() -> new ProductException("Product not found with id " + id));
    }

    @Transactional
    public Product saveProduct(Product product, MultipartFile categoryImageFile, MultipartFile productImageFile) throws ImageServiceException {
        logger.info("Received product: {}", product);

        saveOrUpdateCategory(product, categoryImageFile);
        storeProductImage(product, productImageFile);
        saveOrUpdateProductItems(product);

        return productRepository.save(product);
    }

    public void saveOrUpdateCategory(Product product, MultipartFile categoryImageFile) throws ImageServiceException {
        if (product.getCategory() != null) {
            Category category = product.getCategory();
            if (categoryImageFile != null && !categoryImageFile.isEmpty()) {
                String categoryImageUrl = imageStorageService.storeFile(categoryImageFile);
                category.setImageUrl(categoryImageUrl);
            }
            Optional<Category> existingCategory = categoryService.findByName(category.getName());
            Category savedCategory = existingCategory.orElseGet(() -> {
                try {
					categoryService.saveCategory(category, categoryImageFile);
				} catch (ImageServiceException e) {
					e.printStackTrace();
				}
                return category;
            });
            product.setCategory(savedCategory);
        }
    }

    public void storeProductImage(Product product, MultipartFile productImageFile) throws ImageServiceException {
        if (productImageFile != null && !productImageFile.isEmpty()) {
            String productImageUrl = imageStorageService.storeFile(productImageFile);
            product.setImageUrl(productImageUrl);
        }
    }

    public void saveOrUpdateProductItems(Product product) {
        if (product.getProductItems() != null) {
            for (ProductItem item : product.getProductItems()) {
                saveOrUpdateUnit(item);
                item.setProduct(product);
            }
        }
    }

    public void saveOrUpdateUnit(ProductItem item) {
        Units unit = item.getUnit();
        if (unit != null) {
            Optional<Units> existingUnit = unitService.findByUnitName(unit.getUnitName());
            if (existingUnit.isPresent()) {
                item.setUnit(existingUnit.get());
            } else {
                unit = unitService.save(unit);
                item.setUnit(unit);
            }
        }
    }


    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public void deleteProductIfNoItemsLeft(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.getProductItems().isEmpty()) {
                productRepository.delete(product);
            }
        }
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct, MultipartFile categoryImageFile, MultipartFile productImageFile) throws ProductException, ImageServiceException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id " + id));

        updateBasicProductDetails(existingProduct, updatedProduct);
        updateProductImage(existingProduct, productImageFile);
        updateProductCategory(existingProduct, updatedProduct, categoryImageFile);
        updateProductItems(existingProduct, updatedProduct);

        return productRepository.save(existingProduct);
    }

    public void updateBasicProductDetails(Product existingProduct, Product updatedProduct) {
        existingProduct.setProdName(updatedProduct.getProdName());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setDescription(updatedProduct.getDescription());
    }

    public void updateProductImage(Product existingProduct, MultipartFile productImageFile) throws ImageServiceException {
        if (productImageFile != null && !productImageFile.isEmpty()) {
            String productImageUrl = imageStorageService.storeFile(productImageFile);
            existingProduct.setImageUrl(productImageUrl);
        }
    }

    public void updateProductCategory(Product existingProduct, Product updatedProduct, MultipartFile categoryImageFile) throws ImageServiceException {
        if (updatedProduct.getCategory() != null) {
            Category category = updatedProduct.getCategory();
            if (categoryImageFile != null && !categoryImageFile.isEmpty()) {
                String categoryImageUrl = imageStorageService.storeFile(categoryImageFile);
                category.setImageUrl(categoryImageUrl);
            }
            Category savedCategory = categoryService.findByName(category.getName())
                    .orElseGet(() -> {
                        try {
							categoryService.saveCategory(category, categoryImageFile);
						} catch (ImageServiceException e) {
							e.printStackTrace();
						}
                        return category;
                    });
            existingProduct.setCategory(savedCategory);
        }
    }




    public void updateProductItems(Product existingProduct, Product updatedProduct) {
        if (updatedProduct.getProductItems() != null) {
            existingProduct.setProductItems(new ArrayList<>(existingProduct.getProductItems()));
            existingProduct.getProductItems().clear();

            for (ProductItem item : updatedProduct.getProductItems()) {
                Units unit = item.getUnit();
                if (unit != null) {
                    Optional<Units> existingUnit = unitService.findByUnitName(unit.getUnitName());
                    if (existingUnit.isPresent()) {
                        item.setUnit(existingUnit.get());
                    } else {
                        unit = unitService.save(unit);
                        item.setUnit(unit);
                    }
                }
                item.setProduct(existingProduct);
                existingProduct.getProductItems().add(item);
            }
        }
    }

    public List<ProductDetailDTO> getProductDetails() {
        List<ProductItemDetails> details = productRepository.findProductDetails();
        return details.stream().map(this::mapToProductDetailDTO).toList();
    }

    public Optional<ProductDetailDTO> getProductDetailByProductItemId(Long productItemId) {
        Optional<ProductItemDetails> details = productRepository.findProductItemDetailsByProductItemId(productItemId);
        return details.map(this::mapToProductDetailDTO);
    }

    public List<ProductDetailDTO> searchProductDetails(String query) {
        List<ProductItemDetails> details = productRepository.searchProduct(query);
        return details.stream().map(this::mapToProductDetailDTO).toList();
    }
    
    @Cacheable(value = "products")
    public List<ProductDetailDTO> getProductDetailsByCategory(String categoryName) {
        List<ProductItemDetails> details = productRepository.findByCategory(categoryName.toLowerCase());
        return details.stream().map(this::mapToProductDetailDTO).toList();
    }

    public Page<ProductDetailDTO> getProductDetailsByCategory(String categoryName, int page, int size) {
        List<ProductDetailDTO> prodList = getProductDetailsByCategory(categoryName);        
        int start = Math.min((int) PageRequest.of(page, size).getOffset(), prodList.size());
        int end = Math.min((start + size), prodList.size());
        List<ProductDetailDTO> paginatedList = prodList.subList(start, end);
        
        return new PageImpl<>(paginatedList, PageRequest.of(page, size), prodList.size());

    }

    public List<ProductDetailDTO> displayProductsByDiscount() {
        List<ProductItemDetails> details = productRepository.displayTopDiscounts();
        return details.stream().map(this::mapToProductDetailDTO).toList();
    }

    public List<ProductDetailDTO> getProductDetailsByProdId(Long productId) {
        List<ProductItemDetails> details = productRepository.displayProductsById(productId);
        return details.stream().map(this::mapToProductDetailDTO).toList();
    }

    private ProductDetailDTO mapToProductDetailDTO(ProductItemDetails details) {
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setCategoryName(details.getCategoryName());
        dto.setProductId(details.getProductId());
        dto.setProdName(details.getProdName());
        dto.setProductImg(details.getProductImg());
        dto.setBrand(details.getBrand());
        dto.setDescription(details.getDescription());
        dto.setProductItemId(details.getProductItemId());
        dto.setPrice(details.getPrice());
        dto.setSalePrice(details.getSalePrice());
        dto.setDiscountPercentage(details.getDiscountPercentage());
        dto.setQuantityInStock(details.getQuantityInStock());
        dto.setUnitId(details.getUnitId());
        dto.setUnitName(details.getUnitName());
        return dto;
    }
}
