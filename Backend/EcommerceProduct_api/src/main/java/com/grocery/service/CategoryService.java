package com.grocery.service;

import com.grocery.dto.CategoryDTO;
import com.grocery.exception.ImageServiceException;
import com.grocery.model.Category;
import com.grocery.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {


    private CategoryRepository categoryRepository;
    private ImageStorageService imageStorageService;
    
    public CategoryService(CategoryRepository categoryRepository, ImageStorageService imageStorageService) {
    	this.categoryRepository = categoryRepository;
    	this.imageStorageService = imageStorageService;
    }
    
    public List<Category> findAllCategories(){
    	return categoryRepository.findAll();
    }

    public Category saveCategory(Category category, MultipartFile file) throws ImageServiceException {
        if (file != null && !file.isEmpty()) {
            String fileUrl = imageStorageService.storeFile(file);
            category.setImageUrl(fileUrl);
        }

        if (category.getParentCategory() != null) {
            Category parent = categoryRepository.findByName(category.getParentCategory().getName())
                    .orElseGet(() -> categoryRepository.save(category.getParentCategory()));
            category.setParentCategory(parent);
        }
        return categoryRepository.save(category);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    public List<CategoryDTO> getAllCategoriesWithChildren() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().filter(category -> category.getParentCategory() == null)
                .map(this::convertToDTO)
                .toList();
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setChildCategories(categoryRepository.findByParentCategoryId(category.getId()).stream().map(this::convertToDTO)
                        .toList());
        return dto;
    }
}
